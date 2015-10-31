package com.example.minda.sqlsensorvogellamod;

import android.app.Activity;
import android.app.ListActivity;



// utilized tutorial: http://examples.javacodegeeks.com/android/core/hardware/sensor/android-accelerometer-example/

import android.app.ListActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SimpleCursorAdapter;

import org.w3c.dom.Comment;

import java.util.List;

// Not sure if should be extends Activity or ListActivity
// The two apps have different things here?
public class MainActivity extends ListActivity implements SensorEventListener {

    private float lastX, lastY, lastZ;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    // initialize display values to 0
    private float deltaXMax = 0;
    private float deltaYMax = 0;
    private float deltaZMax = 0;

/*    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaZ = 0;*/

    public float deltaX = 0;
    public float deltaY = 0;
    public float deltaZ = 0;

    private SensorsDataSource datasource;

    // private float vibrateThreshold = 0;

    private TextView currentX, currentY, currentZ, maxX, maxY, maxZ;

    //public Vibrator v;

    // on create of application
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // load the .xml layout
        initializeViews();

        // INITIALIZES SENSORS
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // test case, are we sensing an accelerometer?
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer

            // sets "accelerometer" as the sensor used (specified through sensor.type)
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            // declares registerListener of accelerometer as "this"
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            // vibrateThreshold = accelerometer.getMaximumRange() / 2;
        } else {
            // fail! we dont have an accelerometer!
        }


        // INITIALIZES DATABASE
        datasource = new SensorsDataSource(this);
        datasource.open();

        // INITIALIZES LISTVIEW WIDGET
        List<SensorVal> values = datasource.getAllSensors();

        // use the SimpleCursorAdapter to show elements in a ListView
        ArrayAdapter<SensorVal> adapter = new ArrayAdapter<SensorVal>(this, android.R.layout.simple_expandable_list_item_1, values);
        setListAdapter(adapter);

        // INITIALIZES BUTTON
        Button button1 = (Button) findViewById(R.id.btnStore);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Store button pressed", Toast.LENGTH_LONG).show();

                // COMMENTED OUT FOR CRASH TESTING
                //ArrayAdapter<SensorVal> adapter = (ArrayAdapter<SensorVal>) getListAdapter();


                // VALUES BEING SAVED ARE WEIRD SENSORVAL@ID INSTEAD OF ACCEL NUMBERS
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();

                SensorVal sensor = null;

                sensor = datasource.createSensor(deltaX, deltaY, deltaZ);

                //Toast.makeText(getApplicationContext(), sensor.getAccels(), Toast.LENGTH_LONG).show();
                //adapter.add(sensor);
                adapter.add(sensor.getAccels());

                adapter.notifyDataSetChanged();
            }

        });
    }

    public void initializeViews() {
        // values as returned by sensor data
        currentX = (TextView) findViewById(R.id.currentX);
        currentY = (TextView) findViewById(R.id.currentY);
        currentZ = (TextView) findViewById(R.id.currentZ);
/*
        // REMOVED BECAUSE MAX VALUES NOT UTILIZED IN THIS APP
        maxX = (TextView) findViewById(R.id.maxX);
        maxY = (TextView) findViewById(R.id.maxY);
        maxZ = (TextView) findViewById(R.id.maxZ);*/
    }

    //onResume() register the accelerometer for listening the events
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //onPause() unregister the accelerometer for stop listening the events
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // when read changes to accelerometer, changes display
    @Override
    public void onSensorChanged(SensorEvent event) {

        // clean current values on reset (= 0.0)
        displayCleanValues();
        // display the current x,y,z accelerometer values
        displayCurrentValues();
        // display the max x,y,z accelerometer values
        // REMOVED BECAUSE MAX VALUES NOT UTILIZED IN THIS APP
        // displayMaxValues();

        // get the change of the x,y,z values of the accelerometer
        deltaX = Math.abs(lastX - event.values[0]);
        deltaY = Math.abs(lastY - event.values[1]);
        deltaZ = Math.abs(lastZ - event.values[2]);

        // if the change is below 2, it is just plain noise
        if (deltaX < 2)
            deltaX = 0;
        if (deltaY < 2)
            deltaY = 0;
        //if (deltaZ  vibrateThreshold) || (deltaY > vibrateThreshold) || (deltaZ > vibrateThreshold)) {
        //    v.vibrate(50);
        //}
    }

    // cleans current values
    public void displayCleanValues() {
        currentX.setText("0.0");
        currentY.setText("0.0");
        currentZ.setText("0.0");
    }

    // display the current x,y,z accelerometer values
    public void displayCurrentValues() {
        currentX.setText(Float.toString(deltaX));
        currentY.setText(Float.toString(deltaY));
        currentZ.setText(Float.toString(deltaZ));
    }

    // display the max x,y,z accelerometer values
    /*
    // REMOVED BECAUSE MAX VALUES NOT UTILIZED IN THIS APP
    public void displayMaxValues() {

        if (deltaX > deltaXMax) {
            deltaXMax = deltaX;
            maxX.setText(Float.toString(deltaXMax));
        }
        if (deltaY > deltaYMax) {
            deltaYMax = deltaY;
            maxY.setText(Float.toString(deltaYMax));
        }
        if (deltaZ > deltaZMax) {
            deltaZMax = deltaZ;
            maxZ.setText(Float.toString(deltaZMax));
        }
    }
    */

}


