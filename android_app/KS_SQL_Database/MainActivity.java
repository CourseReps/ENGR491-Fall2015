package com.example.kyle.simple_database;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {
    int number=7;
    int numretrieves;


    String wifi, lte, cdma, ret_wifi, ret_lte, ret_cdma;
    Button STORE, RETRIEVE, DELETE, WIFI;
    Context ctx = this;
    Context CTX = this;
    Context Ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numretrieves = 0;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final TextView WIFI_STRENGTH = (TextView) findViewById(R.id.number_text);
        final TextView LTE_STRENGTH = (TextView) findViewById(R.id.number2_text);
        final TextView CDMA_STRENGTH = (TextView) findViewById(R.id.number3_text);

        final TextView ret_WIFI_STRENGTH = (TextView) findViewById(R.id.number4_text);
        final TextView ret_LTE_STRENGTH = (TextView) findViewById(R.id.number5_text);
        final TextView ret_CDMA_STRENGTH = (TextView) findViewById(R.id.number6_text);

        STORE = (Button) findViewById(R.id.button);
        RETRIEVE = (Button) findViewById(R.id.button3);
        DELETE = (Button) findViewById(R.id.button4);
        WIFI = (Button) findViewById(R.id.button5);


        WIFI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WifiManager wifiManager = (WifiManager)ctx.getSystemService(Context.WIFI_SERVICE);
                WifiManager.WifiLock wifiLock =wifiManager.createWifiLock("WakeLockPermissionTest");
                wifiLock.acquire();
                wifiManager.getConnectionInfo();

                WifiInfo wifi_info = wifiManager.getConnectionInfo();
                int rssi = (wifi_info.getRssi());

                TextView wifi_strength = (TextView) findViewById(R.id.RSSI);
                wifi_strength.setText(Float.toString(rssi));
            }
        });

        STORE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifi = WIFI_STRENGTH.getText().toString();
                lte = LTE_STRENGTH.getText().toString();
                cdma = CDMA_STRENGTH.getText().toString();
                Log.d("Wifi", wifi);
                Log.d("Lte", lte);
                Log.d("Cdma", cdma);
                DatabaseOperations DB = new DatabaseOperations(ctx);
                DB.putInformation(DB, wifi, lte, cdma);
            }
        });

        RETRIEVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseOperations DOP = new DatabaseOperations(CTX);
                Cursor CR = DOP.getInformation(DOP);
                if(numretrieves==0) {
                    CR.moveToFirst();
                }
                else {
                    CR.moveToPosition(numretrieves);
                }
                ret_WIFI_STRENGTH.setText(CR.getString(0));
                ret_LTE_STRENGTH.setText(CR.getString(1));
                ret_CDMA_STRENGTH.setText(CR.getString(2));

                numretrieves++;
            }
        });


        DELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            DatabaseOperations dbop = new DatabaseOperations(ctx);
            dbop.delete(ctx);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void generateNumber(View v) {
        TextView wifi_text = (TextView) findViewById(R.id.number_text);
        TextView lte_text = (TextView) findViewById(R.id.number2_text);
        TextView cdma_text = (TextView) findViewById(R.id.number3_text);
        number+=1;
        wifi_text.setText(Float.toString(number));
        number+=1;
        lte_text.setText(Float.toString(number));
        number+=1;
        cdma_text.setText(Float.toString(number));

    }
}
