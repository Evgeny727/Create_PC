package com.example.createpc.fragments.dataclasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseBuildsHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "builds_info.db";
    private static final int SCHEMA = 1;
    public static final String TABLE = "builds";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CPU = "cpu";
    public static final String COLUMN_GPU = "gpu";
    public static final String COLUMN_MOTHERBOARD = "motherboard";
    public static final String COLUMN_PSU = "psu";
    public static final String COLUMN_RAM = "ram";
    public static final String COLUMN_COOLER = "cooler";
    public static final String COLUMN_CASE = "base";
    public static final String COLUMN_SSDM = "ssdm";
    public static final String COLUMN_SSD2 = "ssd2";
    public static final String COLUMN_HDD = "hdd";
    public static final String COLUMN_FAN = "fan";

    public DatabaseBuildsHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_CPU + " INTEGER,"
                + COLUMN_GPU + " INTEGER,"
                + COLUMN_MOTHERBOARD + " INTEGER,"
                + COLUMN_PSU + " INTEGER,"
                + COLUMN_RAM + " INTEGER,"
                + COLUMN_COOLER + " INTEGER,"
                + COLUMN_CASE + " INTEGER,"
                + COLUMN_SSDM + " INTEGER,"
                + COLUMN_SSD2 + " INTEGER,"
                + COLUMN_HDD + " INTEGER,"
                + COLUMN_FAN + " INTEGER);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
