package com.letsgo.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Petkata on 25.2.2016 г..
 */
public class DBAdapter {

    private DBHelper dbHelper;

    public DBAdapter(Context context){
        dbHelper = new DBHelper(context);
    }

    public long addUser(String username,String email,String password){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(DBHelper.USERS_USERNAME,username);
        cv.put(DBHelper.USERS_EMAIL,email);
        cv.put(DBHelper.USERS_PASS,password);
        long num = db.insert(TABLE_USERS,null,cv);
        return num;
    }
//    TODO make insert select update delete queries

    static class DBHelper extends SQLiteOpenHelper {

        static final String DB_NAME = "LetsGo.db";
        static final int DB_VERSION = 1;
        //TODO constants

        private Context context;

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try{
//                TODO create tables
            }
            catch (SQLiteException e){

            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            TODO what is best here ?
        }
    }
}
