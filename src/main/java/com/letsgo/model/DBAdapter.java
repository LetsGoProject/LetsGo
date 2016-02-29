package com.letsgo.model;

import android.content.ContentValues;
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
public class DBAdapter {

    private DBHelper dbHelper;

    public DBAdapter(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long addUser(String name, String email, String password) {

//        TODO chech if user exists

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.USERS_NAME, name);
        cv.put(Constants.USERS_EMAIL, email);
        cv.put(Constants.USERS_PASSWORD, password);
        return db.insert(Constants.TABLE_USERS, null, cv);
    }

    public String login(String email, String pass){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {Constants.USERS_EMAIL,Constants.USERS_PASSWORD};
        String[] selectionArgs = {email,pass};
        Cursor cursor = db.query(Constants.TABLE_USERS,columns,Constants.USERS_EMAIL + " =? And " + Constants.USERS_PASSWORD + " =?",selectionArgs,null,null,null);
        if (cursor.moveToFirst()) {
            String userEmail;
            int emailColumnIndex = cursor.getColumnIndex(Constants.USERS_EMAIL);
            userEmail = cursor.getString(emailColumnIndex);
            return userEmail;
        }
        return null;
    }

    //    FOR TESTING
    public String showAllUsers() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {Constants.USERS_NAME, Constants.USERS_EMAIL,Constants.USERS_PASSWORD};
//        String orderBy = "ColumnName" + "ASC" or "DESC"
        Cursor cursor = db.query(Constants.TABLE_USERS, columns, null, null, null, null, null);
        StringBuffer sb = new StringBuffer();
        while (cursor.moveToNext()) {
            String userName = cursor.getString(getColumnIndex(cursor, Constants.USERS_NAME));
            String userEmail = cursor.getString(getColumnIndex(cursor, Constants.USERS_EMAIL));
            String userPass = cursor.getString(getColumnIndex(cursor, Constants.USERS_PASSWORD));
            sb.append(userName + " " + userEmail + " " + userPass + "\n");
        }
        return sb.toString();
    }
//    TODO make insert select update delete queries

    static class DBHelper extends SQLiteOpenHelper {

        private Context context;

        public DBHelper(Context context) {
            super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
            this.context = context;
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

    private int getColumnIndex(Cursor cursor, String colName) {
        return cursor.getColumnIndex(colName);
    }
}
