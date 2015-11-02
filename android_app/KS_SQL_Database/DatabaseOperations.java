package com.example.kyle.simple_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.kyle.simple_database.TableData.TableInfo;

/**
 * Created by Kyle on 10/30/2015.
 */
public class DatabaseOperations extends SQLiteOpenHelper {

    public static final int database_version = 1;
    public String CREATE_QUERY = " CREATE TABLE "+ TableInfo.TABLE_NAME + " ("+ TableInfo.WIFI_STRENGTH + " TEXT, " + TableInfo.CDMA_STRENGTH+ " TEXT, " + TableInfo.LTE_STRENGTH + " TEXT );";


    public DatabaseOperations(Context context) {
        super(context, TableInfo.DATABASE_NAME, null, database_version);
        Log.d("Databade operations", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {

        sdb.execSQL(CREATE_QUERY);
        Log.d("Database operations", "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }

    public void putInformation(DatabaseOperations dop, String wifi, String lte, String cdma ){

        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableInfo.WIFI_STRENGTH,wifi);
        cv.put(TableInfo.LTE_STRENGTH,lte);
        cv.put(TableInfo.CDMA_STRENGTH,cdma);
        long k = SQ.insert(TableInfo.TABLE_NAME,null,cv);
        Log.d("Database operations", "One row inserted");

    }

    public Cursor getInformation(DatabaseOperations dop) {

        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] columns = {TableInfo.WIFI_STRENGTH,TableInfo.LTE_STRENGTH,TableInfo.CDMA_STRENGTH};
        Cursor CR = SQ.query(TableInfo.TABLE_NAME, columns, null, null, null, null, null);
        Log.d("Database operations", "information retrieved");
        return CR;
    }

    public void delete(Context ctx ) {
        ctx.deleteDatabase(TableData.TableInfo.DATABASE_NAME);
        Log.d("Databade operations", "Database deleted");
    }
}

