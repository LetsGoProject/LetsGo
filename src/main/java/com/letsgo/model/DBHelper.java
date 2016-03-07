package com.letsgo.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.letsgo.model.queries.Queries;
import com.letsgo.model.utils.Constants;

/**
 * Created by Petkata on 25.2.2016 г..
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(Queries.CREATE_TABLE_LOCATIONS);
            db.execSQL(Queries.CREATE_TABLE_TYPES);
            db.execSQL(Queries.CREATE_TABLE_USERS);
            db.execSQL(Queries.CREATE_TABLE_EVENTS);
            db.execSQL(Queries.CREATE_TABLE_TICKETS_EVENTS_USERS);
            db.execSQL(Queries.CREATE_TABLE_USERS_FAV_EVENTS);


        } catch (SQLiteException e) {
//                TODO what should be shown here
            Log.e("SQL ERROR", "UNABLE TO CREATE TABLES" + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            TODO what is best here ?
    }

}
