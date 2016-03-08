package com.letsgo.model.utils;

/**
 * Created by Petkata on 25.2.2016 г..
 */
public class Constants {

    public static final String EXISTING_EMAIL = "email exists";
    public static final String EXISTING_USERNAME = "username exists";

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
    public static final String LOCATIONS_NAME = "location_name";
    public static final String LOCATIONS_ADDRESS = "location_address";
    public static final String LOCATIONS_CONTACT = "location_contact";
    public static final String[] LOCATIONS_ALL_COLUMNS = {AUTOINCREMETN_COLUMN,LOCATIONS_NAME,LOCATIONS_ADDRESS,
            LOCATIONS_CONTACT};
    //        ----TYPES TABLE----
    public static final String TYPES_TYPE = "type";
    //        -----USERS TABLE----
    public static final String USERS_EMAIL = "user_email";
    public static final String USERS_PASSWORD = "user_password";
    public static final String USERS_NAME = "user_name";
    public static final String USERS_ISADMIN = "is_admin";
    public static final String[] USERS_ALL_COLUMNS = {AUTOINCREMETN_COLUMN,USERS_NAME,USERS_EMAIL,USERS_PASSWORD,USERS_ISADMIN};
    //        -----EVENTS TABLE----
    public static final String EVENTS_NAME = "event_name";
    public static final String EVENTS_DESCRIPTION = "event_description";
    public static final String EVENTS_TICKET_PRICE = "event_ticket_price";
    public static final String EVENTS_DATE = "event_date";
    public static final String EVENTS_LOCATION = "event_location_fk";
    public static final String EVENTS_TYPE = "event_type_fk";
    public static final String[] EVENTS_ALL_COLUMNS = {AUTOINCREMETN_COLUMN,EVENTS_NAME,EVENTS_DESCRIPTION,
            EVENTS_TICKET_PRICE,EVENTS_DATE,EVENTS_LOCATION,EVENTS_TYPE};
    //        -----TICKETS & FAVOURITES TABLE----
    public static final String FKEY_EVENT_ID = "event_id_fk";
    public static final String FKEY_USER_ID = "user_id_fk";
}
