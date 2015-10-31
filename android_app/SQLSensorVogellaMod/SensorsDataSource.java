package com.example.minda.sqlsensorvogellamod;

/**
 * Created by Minda on 10/25/2015.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;

import org.w3c.dom.Comment;

public class SensorsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    // modify here for more columns
    /*private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_COMMENT }; */
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_XACCEL, MySQLiteHelper.COLUMN_YACCEL, MySQLiteHelper.COLUMN_ZACCEL};

    public SensorsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // use "put" to insert comment into SQLHelper column
    /*public Comment createComment(String comment) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
        long insertId = database.insert(MySQLiteHelper.TABLE_SENSOR, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SENSOR,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }*/

    public SensorVal createSensor(float x_accel, float y_accel, float z_accel) {
    //public SensorVal createSensor(String x_accel, String y_accel, String z_accel) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_XACCEL, x_accel);
        values.put(MySQLiteHelper.COLUMN_YACCEL, y_accel);
        values.put(MySQLiteHelper.COLUMN_ZACCEL, z_accel);
        long insertId = database.insert(MySQLiteHelper.TABLE_SENSOR, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SENSOR,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null, null);
        cursor.moveToFirst();
        SensorVal newSensor = cursorToSensor(cursor);
        cursor.close();
        return newSensor;
    }

    // deletes comment based on id, top first
    public void deleteComment(SensorVal sensorVal) {
        long id = sensorVal.getId();

        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_SENSOR, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    // list all comments as array list
    public List<SensorVal> getAllSensors() {
        List<SensorVal> sensors = new ArrayList<SensorVal>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_SENSOR,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SensorVal sensorVal = cursorToSensor(cursor);
            sensors.add(sensorVal);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return sensors;
    }

    private SensorVal cursorToSensor(Cursor cursor) {
        /*Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;*/

        SensorVal sensorVal = new SensorVal();
        sensorVal.setId(cursor.getLong(0));
        sensorVal.setX_accel(cursor.getFloat(1));
        sensorVal.setY_accel(cursor.getFloat(2));
        sensorVal.setZ_accel(cursor.getFloat(3));
        return sensorVal;

    }
}
