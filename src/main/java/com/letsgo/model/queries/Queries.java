package com.letsgo.model.queries;

import com.letsgo.model.utils.Constants;

public class Queries {
    //        -----CREATE TABLE
    public static final String CREATE_TABLE_LOCATIONS = "CREATE TABLE IF NOT EXISTS " + Constants.TABLE_LOCATIONS + "(" +
            Constants.AUTOINCREMETN_COLUMN + " integer PRIMARY KEY autoincrement NOT NULL," +
            Constants.LOCATIONS_NAME + " varchar NOT NULL UNIQUE," +
            Constants.LOCATIONS_ADDRESS + " varchar NOT NULL UNIQUE," +
            Constants.LOCATIONS_CONTACT + " varchar NOT NULL UNIQUE" +
            ")";
    public static final String CREATE_TABLE_TYPES = "CREATE TABLE IF NOT EXISTS " + Constants.TABLE_TYPES + "(" +
            Constants.AUTOINCREMETN_COLUMN + " integer primary key autoincrement not null," +
            Constants.TYPES_TYPE + " character not null unique" +
            ")";
    public static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " + Constants.TABLE_USERS + "(" +
            Constants.AUTOINCREMETN_COLUMN + " integer primary key autoincrement not null," +
            Constants.USERS_NAME + " VARCHAR NOT NULL UNIQUE," +
            Constants.USERS_EMAIL + " VARCHAR NOT NULL UNIQUE," +
            Constants.USERS_PASSWORD + " CHARACTER NOT NULL," +
            Constants.USERS_ISADMIN + " INT NOT NULL DEFAULT 0" +
            ")";
    public static final String CREATE_TABLE_EVENTS = "CREATE TABLE IF NOT EXISTS " + Constants.TABLE_EVENTS + "(" +
            Constants.AUTOINCREMETN_COLUMN + " integer PRIMARY KEY autoincrement NOT NULL," +
            Constants.EVENTS_NAME + " CHARACTER NOT NULL UNIQUE," +
            Constants.EVENTS_DESCRIPTION + " CHARACTER NOT NULL," +
            Constants.EVENTS_TICKET_PRICE + " NUMERIC NOT NULL," +
            Constants.EVENTS_DATE + " DATE NOT NULL," +
            Constants.EVENTS_LOCATION + " INT NOT NULL REFERENCES " + Constants.TABLE_LOCATIONS + "(" + Constants.AUTOINCREMETN_COLUMN + ")," +
            Constants.EVENTS_TYPE + " INT NOT NULL REFERENCES " + Constants.TABLE_TYPES + "(" + Constants.AUTOINCREMETN_COLUMN + ")" +
            ")";
    public static final String CREATE_TABLE_TICKETS_EVENTS_USERS = "CREATE TABLE IF NOT EXISTS " + Constants.TABLE_TICKETS_EVENTS_USERS + "(" +
            Constants.FKEY_EVENT_ID + " INT NOT NULL REFERENCES " + Constants.TABLE_EVENTS + "(" + Constants.AUTOINCREMETN_COLUMN + ")," +
            Constants.FKEY_USER_ID + " INT NOT NULL REFERENCES " + Constants.TABLE_USERS + "(" + Constants.AUTOINCREMETN_COLUMN + ")" +
            ")";
    public static final String CREATE_TABLE_USERS_FAV_EVENTS = "CREATE TABLE IF NOT EXISTS " + Constants.TABLE_USERS_FAV_EVENTS + "(" +
            Constants.FKEY_USER_ID + " INT NOT NULL REFERENCES " + Constants.TABLE_USERS + "(" + Constants.AUTOINCREMETN_COLUMN + ")," +
            Constants.FKEY_EVENT_ID + " INT NOT NULL REFERENCES " + Constants.TABLE_EVENTS + "(" + Constants.AUTOINCREMETN_COLUMN + ")" +
            ")";
    public static final String EVENT_JOIN_TYPE_LOCATION = "select " + Constants.TABLE_EVENTS + "." + Constants.AUTOINCREMETN_COLUMN +
            ", " + Constants.EVENTS_NAME +
            ", " + Constants.EVENTS_DESCRIPTION +
            ", " + Constants.EVENTS_TICKET_PRICE +
            ", " + Constants.EVENTS_DATE +
            ", " + Constants.TYPES_TYPE +
            ", " + Constants.LOCATIONS_NAME +
            " from " + Constants.TABLE_EVENTS +
            " join " + Constants.TABLE_TYPES + " on " + Constants.TABLE_EVENTS + "." + Constants.EVENTS_TYPE + " = " +
            Constants.TABLE_TYPES + "." + Constants.AUTOINCREMETN_COLUMN +
            " join " + Constants.TABLE_LOCATIONS + " on " + Constants.TABLE_EVENTS + "." + Constants.EVENTS_LOCATION + " = " +
            Constants.TABLE_LOCATIONS + "." + Constants.AUTOINCREMETN_COLUMN;
}
