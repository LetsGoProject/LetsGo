package com.letsgo.model.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.letsgo.model.DBHelper;
import com.letsgo.model.Event;
import com.letsgo.model.User;
import com.letsgo.model.daointerfaces.UserDao;
import com.letsgo.model.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Petkata on 5.3.2016 г..
 */
public class UserDataSource extends DataSource implements UserDao {

    public UserDataSource(Context context) {
        super(context);
    }

    @Override
    public boolean loginUser(String email, String password) {

        String[] columns = {Constants.USERS_EMAIL, Constants.USERS_PASSWORD};
        String selection = Constants.USERS_EMAIL + " =? AND " + Constants.USERS_PASSWORD + " =?";
        String[] selArgs = {email, password};
        Cursor cursor = database.query(Constants.TABLE_USERS, columns, selection, selArgs, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    @Override
    public User createUser(String username, String email, String password) {
        User user = new User();
        //        check if email exists
        if (checkForExisting(Constants.TABLE_USERS, Constants.USERS_EMAIL, email)) {
            user.setUsername(Constants.EXISTING_EMAIL);
            return user;
        }
        if (checkForExisting(Constants.TABLE_USERS, Constants.USERS_NAME, username)) {
            user.setUsername(Constants.EXISTING_USERNAME);
            return user;
        }
        ContentValues values = new ContentValues();
        values.put(Constants.USERS_NAME, username);
        values.put(Constants.USERS_PASSWORD, password);
        values.put(Constants.USERS_EMAIL, email);
        long inserId = database.insert(Constants.TABLE_USERS, null, values);
        if (inserId < 0) {
            user.setUsername("Could not add user");
        }

        Cursor cursor = database.query(Constants.TABLE_USERS, Constants.USERS_ALL_COLUMNS, Constants.AUTOINCREMETN_COLUMN + " = "
                + inserId, null, null, null, null);
        cursor.moveToFirst();
        user = cursorToUser(cursor);
        cursor.close();
        return user;
    }

    @Override
    public User showUser(String email) {

        Cursor cursor = database.query(Constants.TABLE_USERS, Constants.USERS_ALL_COLUMNS, Constants.USERS_EMAIL + " = "
                + "'" + email + "'", null, null, null, null);
        cursor.moveToFirst();
        User user = cursorToUser(cursor);
        cursor.close();
        return user;

    }

    @Override
    public boolean editUsername(long userId, String newUsername) {

        ContentValues values = new ContentValues();
        values.put(Constants.USERS_NAME, newUsername);
        String where = Constants.AUTOINCREMETN_COLUMN + " =?";
        String[] args = {String.valueOf(userId)};
        int result = database.update(Constants.TABLE_USERS, values, where, args);

        if (result > 0)
            return true;

        return false;
    }

    @Override
    public boolean editEmail(long userId, String newEmail) {
        return false;
    }

    @Override
    public boolean editPassword(long userId, String newPassword) {
        return false;
    }

    @Override
    public boolean deleteUser(String email) {
        return false;
    }

    @Override
    public boolean addEventToWatchlist(long userId, String eventName) {
//        insert into Constants.TABLE_USERS_FAV_EVENTS (Constants.FKEY_USER_ID,Constants.FKEY_EVENT_ID)
//        values
//        (select Constants.TABLE_USERS.Constants.AUTOINCREMETN_COLUMN from Constants.TABLE_USERS where Constants.USERS_EMAIL = email,
//          select Constants.TABLE_EVENTS.AUTOINCREMETN_COLUMN from Constants.TABLE_EVENTS where Constants.EVENTS_NAME = event_name)

        long eventId = getEventIdByName(eventName);

        ContentValues values = new ContentValues();
        values.put(Constants.FKEY_USER_ID, userId);
        values.put(Constants.FKEY_EVENT_ID, eventId);

        long insertedRow = database.insert(Constants.TABLE_USERS_FAV_EVENTS, null, values);

        if (insertedRow < 0)
            return false;
        else
            return true;
    }

    @Override
    public boolean removeEventFromWatchlist(long userId, String eventName) {
        long eventId = getEventIdByName(eventName);
//
//        ContentValues values = new ContentValues();
//        values.put(Constants.FKEY_USER_ID, userId);
//        values.put(Constants.FKEY_EVENT_ID, eventId);

        String[] args = {String.valueOf(userId), String.valueOf(eventId)};

        long deletedRow = database.delete(Constants.TABLE_USERS_FAV_EVENTS, Constants.FKEY_USER_ID + " = ? AND " + Constants.FKEY_EVENT_ID + " = ? ", args);

        if (deletedRow < 0)
            return false;
        else
            return true;
    }

    @Override
    public boolean removeAllFromWatchlist(long userId) {

        String[] args = {String.valueOf(userId)};

        long deletedRow = database.delete(Constants.TABLE_USERS_FAV_EVENTS, Constants.FKEY_USER_ID + " = ? ", args);

        if (deletedRow < 0)
            return false;
        else
            return true;
    }

    @Override
    public boolean buyTicket(String date, long userId, long eventId, int quantity) {

        ContentValues values = new ContentValues();
        values.put(Constants.TICKET_PURCHASE_DATE, date);
        values.put(Constants.FKEY_EVENT_ID, eventId);
        values.put(Constants.FKEY_USER_ID, userId);
        values.put(Constants.TICKET_QUANTITY, quantity);
        long insertedRow = database.insert(Constants.TABLE_TICKETS_EVENTS_USERS, null, values);
        if (insertedRow < 0)
            return false;
        else
            return true;
    }

    @Override
    public ArrayList<Map<String, Map<Event, Integer>>> selectTicketsForUser(long userId) {

        /**
         * select Constants.EVENTS_NAME,Constants.EVENTS_DATE,Constants.TICKET_QUANTITY
         * from TABLE_TICKETS_EVENTS_USERS teu
         * join TABLE_EVENTS e
         * on teu.FKEY_EVENT_ID = e.AUTOINCREMETN_COLUMN
         * where teu.FKEY_USER_ID = userid
         */

        ArrayList<Map<String, Map<Event, Integer>>> bindEventsTickets = new ArrayList<>();

        String[] columns = {Constants.TICKET_PURCHASE_DATE, Constants.EVENTS_NAME, Constants.EVENTS_DATE, Constants.TICKET_QUANTITY};
        String selection = Constants.FKEY_USER_ID + " = ? ";
        String[] args = {String.valueOf(userId)};

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(Constants.TABLE_TICKETS_EVENTS_USERS +
                " join " + Constants.TABLE_EVENTS +
                " on " + Constants.TABLE_TICKETS_EVENTS_USERS + "." + Constants.FKEY_EVENT_ID + " = " +
                Constants.TABLE_EVENTS + "." + Constants.AUTOINCREMETN_COLUMN);

        Cursor cursor = queryBuilder.query(database, columns, selection, args, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String dateOfPurchase = cursor.getString(cursor.getColumnIndex(Constants.TICKET_PURCHASE_DATE));
            Event event = new Event();
            event.setEventName(cursor.getString(cursor.getColumnIndex(Constants.EVENTS_NAME)));
            event.setEventDate(cursor.getString(cursor.getColumnIndex(Constants.EVENTS_DATE)));

            Map<Event, Integer> eventTicketEntry = new HashMap<>();
            eventTicketEntry.put(event, cursor.getInt(cursor.getColumnIndex(Constants.TICKET_QUANTITY)));

            Map<String, Map<Event, Integer>> dateEventQuantity = new HashMap<>();
            dateEventQuantity.put(dateOfPurchase, eventTicketEntry);

            bindEventsTickets.add(dateEventQuantity);
            cursor.moveToNext();
        }
        cursor.close();
        return bindEventsTickets;
    }

    @Override
    public boolean checkForExisting(String table, String column, String selectionArg) {
        String[] columns = {column};
        String[] selectionArgs = {selectionArg};
        Cursor cursor = database.query(table, columns, column + " =? ", selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    @Override
    public Event showEventFromWatch(String eventName) {
        return null;
    }

    @Override
    public ArrayList<Event> showWathclist(long userId) {

        ArrayList<Event> watchList = new ArrayList<>();
//        String[] args = {String.valueOf(userId)};
//        String[] evnameColumn = {Constants.EVENTS_NAME};
//        Cursor cursorEventName = joinFavsEventsLocations().query(database,evnameColumn,Constants.FKEY_USER_ID + " = ? ", args,null,null,null);
//        cursorEventName.moveToFirst();
//        while (!cursorEventName.isAfterLast()){
//            String name = cursorEventName.getString(cursorEventName.getColumnIndex(Constants.EVENTS_NAME));
//            Cursor cursor = EventDataSource.joinEventsLocationsTypes().query(database, eventColumns(),Constants.EVENTS_NAME + " = "
//                    + "'" + name + "'" , null, null, null, null, null);
////            Cursor cursor = database.query(Constants.TABLE_EVENTS, Constants.EVENTS_ALL_COLUMNS, Constants.EVENTS_NAME + " = "
////                    + "'" + name + "'", null, null, null, null);
//            cursor.moveToFirst();
//            Event event = EventDataSource.cursorToEvent(cursor);
//            cursor.close();
//            watchList.add(event);
//        }

//        Cursor cursor = joinFavsEventsLocations().query(database,eventColumns(),Constants.FKEY_USER_ID + " = ? ", args,null,null,null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//            Event event = EventDataSource.cursorToEvent(cursor);
//            watchList.add(event);
//            cursor.moveToNext();
//        }
//        cursor.close();

//        String longQuery = "select e._id_pk," +
//                "event_name," +
//                "event_description," +
//                "event_ticket_price," +
//                "event_date," +
//                "(select location_name from Events e join Locations loc on e.event_location_" +
//                "fk = loc._id_pk " +
//                "where  e._id_pk =(select Users_Favs_Events.event_id_fk from Users_Favs_Events where user_id_fk ="+userId+") ) as "+Constants.LOCATIONS_NAME+"" +
//                ",(select type from Events e join Types t on e.event_type_fk = t._id_pk " +
//                "where  e._id_pk =(select Users_Favs_Events.event_id_fk from Users_Favs_Events where user_id_fk ="+userId+") ) as "+Constants.TYPES_TYPE+" " +
//                "from Users_Favs_Events ufe  " +
//                "join Events e " +
//                "on ufe.event_id_fk = e._id_pk " +
//                "where user_id_fk = "+userId;

        Cursor cursor = database.query(Constants.TABLE_USERS_FAV_EVENTS, new String[]{Constants.FKEY_EVENT_ID}, Constants.FKEY_USER_ID + " = ? ", new String[]{String.valueOf(userId)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long eventId = cursor.getLong(cursor.getColumnIndex(Constants.FKEY_EVENT_ID));

            Cursor c = EventDataSource.joinEventsLocationsTypes().query(database, EventDataSource.eventColumns(), Constants.TABLE_EVENTS + "." + Constants.AUTOINCREMETN_COLUMN + " = ? ", new String[]{String.valueOf(eventId)}, null, null, null);
            c.moveToFirst();
            Event event = EventDataSource.cursorToEvent(c);
            watchList.add(event);
            c.close();
            cursor.moveToNext();
        }
        cursor.close();
        return watchList;
    }
//TODO join types .. fix Locations
//    private SQLiteQueryBuilder joinFavsEventsLocations() {
//        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
//        queryBuilder.setTables(Constants.TABLE_USERS_FAV_EVENTS +
//                " join " + Constants.TABLE_EVENTS +
//                " on " + Constants.TABLE_USERS_FAV_EVENTS + "." + Constants.FKEY_EVENT_ID + " = " +
//                Constants.TABLE_EVENTS + "." + Constants.AUTOINCREMETN_COLUMN);
//        return queryBuilder;
//    }

//    private String[] eventColumns() {
//        String[] columns = {
//                Constants.TABLE_EVENTS + "." + Constants.AUTOINCREMETN_COLUMN,
//                Constants.EVENTS_NAME,
//                Constants.EVENTS_DESCRIPTION,
//                Constants.EVENTS_TICKET_PRICE,
//                Constants.EVENTS_DATE,
//                Constants.LOCATIONS_NAME};
//        return columns;
//    }

    //    for testing
    @Override
    public String showAllUsers() {
        StringBuffer allUsers = new StringBuffer();
        String[] columns = {Constants.USERS_NAME, Constants.USERS_EMAIL, Constants.USERS_PASSWORD};
        Cursor cursor = database.query(Constants.TABLE_USERS, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String userName = cursor.getString(cursor.getColumnIndex(Constants.USERS_NAME));
            String userEmail = cursor.getString(cursor.getColumnIndex(Constants.USERS_EMAIL));
            String userPass = cursor.getString(cursor.getColumnIndex(Constants.USERS_PASSWORD));
            allUsers.append(userName + " " + userEmail + " " + userPass + "\n");
        }
        cursor.close();
        return allUsers.toString();
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(cursor.getColumnIndex(Constants.AUTOINCREMETN_COLUMN)));
        user.setUsername(cursor.getString(cursor.getColumnIndex(Constants.USERS_NAME)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(Constants.USERS_EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(Constants.USERS_PASSWORD)));

        if (cursor.getInt(cursor.getColumnIndex(Constants.USERS_ISADMIN)) == 1)
            user.setIsAdmin(true);
        else
            user.setIsAdmin(false);

        return user;
    }

    private long getEventIdByName(String eventName) {
        String[] columns = {Constants.AUTOINCREMETN_COLUMN};
        String[] eventsSelectArgs = {eventName};
        Cursor cursor = database.query(Constants.TABLE_EVENTS, columns, Constants.EVENTS_NAME + " = ? ", eventsSelectArgs, null, null, null);
        cursor.moveToFirst();
        long eventId = cursor.getLong(cursor.getColumnIndex(Constants.AUTOINCREMETN_COLUMN));
        cursor.close();
        return eventId;
    }
}
