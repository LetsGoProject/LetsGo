package com.letsgo.model.utils;

/**
 * Created by Petkata on 25.2.2016 г..
 */
public class Constants {
    public static final String DB_NAME = "LetsGo.db";
    public static final String ALLOW_FOREIGN_KEYS = "pragma foreign_keys = on";
    public static final int DB_VERSION = 1;

    public static final String TABLE_LOCATIONS = "Locations";
    public static final String TABLE_TYPES = "Types";
    public static final String TABLE_USERS = "Users";

    public static final String TABLE_EVENTS = "Events";

    public static final String TABLE_TICKETS_EVENTS_USERS = "Tickets_Events_Users";
    public static final String TABLE_USERS_FAV_EVENTS = "Users_Favs_Events";

    public static final String AUTOINCREMETN_COLUMN = "_id_pk";
    //        ----LOCATIONS TABLE----
    public static final String LOCATIONS_NAME = "name";
    public static final String LOCATIONS_ADDRESS = "address";
    public static final String LOCATIONS_CONTACT = "contact";
    //        ----TYPES TABLE----
    public static final String TYPES_TYPE = "type";
    //        -----USERS TABLE----
    public static final String USERS_EMAIL = "email";
    public static final String USERS_PASSWORD = "password";
    public static final String USERS_NAME = "name";
    public static final String USERS_ISADMIN = "is_admin";
    //        -----EVENTS TABLE----
    public static final String EVENTS_NAME = "name";
    public static final String EVENTS_DESCRIPTION = "description";
    public static final String EVENTS_TICKET_PRICE = "ticket_price";
    public static final String EVENTS_DATE = "date";
    public static final String EVENTS_LOCATION = "location_fk";
    public static final String EVENTS_TYPE = "type_fk";
    //        -----TICKETS & FAVOURITES TABLE----
    public static final String FKEY_EVENT_ID = "event_id_fk";
    public static final String FKEY_USER_ID = "user_id_fk";
}
