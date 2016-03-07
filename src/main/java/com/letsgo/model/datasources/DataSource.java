package com.letsgo.model.datasources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.letsgo.model.DBHelper;

/**
 * Created by Petkata on 5.3.2016 г..
 */
public abstract class DataSource {

    protected SQLiteDatabase database;
    protected DBHelper dbHelper;

    public DataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}
