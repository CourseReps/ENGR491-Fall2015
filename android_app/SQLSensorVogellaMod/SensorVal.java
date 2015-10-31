package com.example.minda.sqlsensorvogellamod;

import android.widget.TextView;

/**
 * Created by Minda on 10/25/2015.
 */
public class SensorVal {
    private long id;
    //private String comment;
    /*private float x_accel;
    private float y_accel;
    private float z_accel;*/
    private float x_accel, y_accel, z_accel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /*public String getComment() {
        return comment;
    }*/

    /*public float getX_accel() {
        return x_accel;
    }

    public float getY_accel() {
        return y_accel;
    }

    public float getZ_accel() {
        return z_accel;
    }*/

    public String getAccels() {
        return Float.toString(x_accel) + " " + Float.toString(y_accel) + " " + Float.toString(z_accel);
    }

    /*public void setComment(String comment) {
        this.comment = comment;
    }*/

    public void setX_accel(float x_accel) {
        this.x_accel = x_accel;
    }

    public void setY_accel(float y_accel) {
        this.y_accel = y_accel;
    }

    public void setZ_accel(float z_accel) {
        this.z_accel = z_accel;
    }

    // Will be used by the ArrayAdapter in the ListView

    // DONT THINK NEED THIS IN SENSOR VERSION
    //@Override
    /*public String toString() {
        return comment;
    }

    public String toString(){
        return toString()
    } */
}