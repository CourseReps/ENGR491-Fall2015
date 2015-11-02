package com.example.kyle.simple_database;

import android.provider.BaseColumns;

/**
 * Created by Kyle on 10/30/2015.
 */
public class TableData {

    public TableData() {

    }

    public static abstract class TableInfo implements BaseColumns {

        public static final String WIFI_STRENGTH ="wifi_strength";
        public static final String LTE_STRENGTH ="lte_strength";
        public static final String CDMA_STRENGTH ="cdma_strength";
        public static final String DATABASE_NAME = "signal_info_2";
        public static final String TABLE_NAME = "table_name";
    }

}
