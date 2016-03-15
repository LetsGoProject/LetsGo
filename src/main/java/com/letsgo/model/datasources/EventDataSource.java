package com.letsgo.model.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.letsgo.model.Event;
import com.letsgo.model.daointerfaces.EventDao;
import com.letsgo.model.queries.Queries;
import com.letsgo.model.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Petkata on 5.3.2016 г..
 */
public class EventDataSource extends DataSource implements EventDao {


    public EventDataSource(Context context) {
        super(context);
    }

    @Override
    public Event createEvent(String name, String description, double price, String date, String location, String type) {
        Event event = new Event();
        // TODO       check if event exists
//        if (checkForExisting(Constants.TABLE_USERS, Constants.USERS_EMAIL, email)) {
//            event.setUsername(Constants.EXISTING_EMAIL);
//            return event;
//        }
//        if (checkForExisting(Constants.TABLE_USERS, Constants.USERS_NAME, username)) {
//            event.setUsername(Constants.EXISTING_USERNAME);
//            return event;
//        }
        ContentValues values = new ContentValues();
        values.put(Constants.EVENTS_NAME, name);
        values.put(Constants.EVENTS_DESCRIPTION, description);
        values.put(Constants.EVENTS_TICKET_PRICE, price);
        values.put(Constants.EVENTS_DATE, date);
        values.put(Constants.EVENTS_LOCATION, location);
        values.put(Constants.EVENTS_TYPE, type);
        long inserId = database.insert(Constants.TABLE_EVENTS, null, values);
        if (inserId < 0) {
            event.setEventName("Could not add event");
        }

        Cursor cursor = database.query(Constants.TABLE_EVENTS, Constants.EVENTS_ALL_COLUMNS, Constants.AUTOINCREMETN_COLUMN + " = "
                + inserId, null, null, null, null);
        cursor.moveToFirst();
        event = cursorToEvent(cursor);
        cursor.close();
        return event;
    }

    @Override
    public Event showEvent(String name) {
//        Cursor cursor = database.query(Constants.TABLE_EVENTS, Constants.EVENTS_ALL_COLUMNS, Constants.EVENTS_NAME + " = "
//                + "'" + name + "'", null, null, null, null);
        Cursor cursor = joinEventsLocationsTypes().query(database, eventColumns(),null, null, null, null, null, null);

        cursor.moveToFirst();
        Event event = cursorToEvent(cursor);
        cursor.close();
        return event;
    }

    @Override
    public boolean editEventName(String name, String newUsername) {
        return false;
    }

    @Override
    public boolean editEventDescription(String name, String newDescription) {
        return false;
    }

    @Override
    public boolean editEventTicketPrice(String name, double newPrice) {
        return false;
    }

    @Override
    public boolean editEventDate(String name, String newDate) {
        return false;
    }

    @Override
    public boolean editEventLocation(String name, String newLocation) {
        return false;
    }

    @Override
    public boolean editEventType(String name, String newType) {
        return false;
    }

    @Override
    public boolean deleteEvent(String name) {
        return false;
    }

    @Override
    public boolean checkForExisting(String table, String column, String selectionArg) {
        return false;
    }

    @Override
    public List<Event> showEvents(String period) {
        List<Event> allEvents = new ArrayList<>();
        Cursor cursor = null;

        if (period.equals("upcoming"))
            cursor = joinEventsLocationsTypes().query(database, eventColumns(), Constants.EVENTS_DATE + " > date('now') ", null, null, null, null, null);
        if (period.equals("past"))
            cursor = joinEventsLocationsTypes().query(database, eventColumns(), Constants.EVENTS_DATE + " < date('now') ", null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Event event = cursorToEvent(cursor);
            allEvents.add(event);
            cursor.moveToNext();
        }
        cursor.close();
        return allEvents;
    }

    @Override
    public List<Event> showSearchResults(String name, String type, String location, String dateAfter, String dateBefore) {

        List<Event> nameSearchResults;
        List<Event> typeSearchResults;
        List<Event> locationSearchResults;
        List<Event> dateAfterSearchResults;
        List<Event> dateBeforeSearchResults;

        if(name != null && name.length() > 0)
            nameSearchResults = results("%"+name+"%", Constants.EVENTS_NAME, "like");
        else
            nameSearchResults = allEvents();

        if(type != null &&!type.equals("Choose"))
            typeSearchResults = results(type, Constants.TYPES_TYPE, "=");
        else
            typeSearchResults = allEvents();

        nameSearchResults.retainAll(typeSearchResults);

        if(location != null && !location.equals("Choose"))
            locationSearchResults = results(location, Constants.LOCATIONS_NAME, "=");
        else
            locationSearchResults = allEvents();

        nameSearchResults.retainAll(locationSearchResults);

        if(dateAfter != null && dateAfter.length() >1)
            dateAfterSearchResults = results(dateAfter, Constants.EVENTS_DATE, ">");
        else
            dateAfterSearchResults = allEvents();

        nameSearchResults.retainAll(dateAfterSearchResults);

        if(dateBefore != null && dateBefore.length() >1)
            dateBeforeSearchResults = results(dateBefore, Constants.EVENTS_DATE, "<");
        else
            dateBeforeSearchResults = allEvents();

        nameSearchResults.retainAll(dateBeforeSearchResults);

        return nameSearchResults;
    }

    @Override
    public List<Long> selectAllFavEventsIdsForUserId(long userId) {
        List<Long> listIds = new ArrayList<>();
        String select = Constants.FKEY_USER_ID  + " = ? ";
        String[] args = {String.valueOf(userId)};
        Cursor cursor = database.query(Constants.TABLE_USERS_FAV_EVENTS,new String[]{Constants.FKEY_EVENT_ID},select,args,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            long id = cursor.getLong(cursor.getColumnIndex(Constants.FKEY_EVENT_ID));
            listIds.add(id);
            cursor.moveToNext();
        }
        cursor.close();
        return listIds;
    }

    @Override
    public List<Event> showSearchResults(String date) {
        List<Event> searchResults = new ArrayList<>();
        Cursor cursor = null;

        String[] args = {date};

        cursor = joinEventsLocationsTypes().query(database, eventColumns(), Constants.EVENTS_DATE + " > ? ", args, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Event event = cursorToEvent(cursor);
            searchResults.add(event);
            cursor.moveToNext();
        }
        cursor.close();
        return searchResults;
    }

    private List<Event> results(String searchCriteria, String column, String comparator) {
        List<Event> searchResults = new ArrayList<>();
        Cursor cursor = null;

        String[] args = {searchCriteria};

        cursor = joinEventsLocationsTypes().query(database, eventColumns(), column + " " + comparator + " ? ", args, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Event event = cursorToEvent(cursor);
            searchResults.add(event);
            cursor.moveToNext();
        }
        cursor.close();
        return searchResults;
    }

    private List<Event> allEvents() {
        List<Event> allEvents = new ArrayList<>();
        Cursor cursor = null;
        cursor = joinEventsLocationsTypes().query(database, eventColumns(), null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Event event = cursorToEvent(cursor);
            allEvents.add(event);
            cursor.moveToNext();
        }
        cursor.close();
        return allEvents;
    }

    public static SQLiteQueryBuilder joinEventsLocationsTypes() {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(Constants.TABLE_EVENTS + " join " + Constants.TABLE_TYPES + " on " + Constants.TABLE_EVENTS + "." + Constants.EVENTS_TYPE + " = " +
                Constants.TABLE_TYPES + "." + Constants.AUTOINCREMETN_COLUMN +
                " join " + Constants.TABLE_LOCATIONS + " on " + Constants.TABLE_EVENTS + "." + Constants.EVENTS_LOCATION + " = " +
                Constants.TABLE_LOCATIONS + "." + Constants.AUTOINCREMETN_COLUMN);
        return queryBuilder;
    }

    public static String[] eventColumns() {
        String[] columns = {
                Constants.TABLE_EVENTS + "." + Constants.AUTOINCREMETN_COLUMN,
                Constants.EVENTS_NAME,
                Constants.EVENTS_DESCRIPTION,
                Constants.EVENTS_TICKET_PRICE,
                Constants.EVENTS_DATE,
                Constants.TYPES_TYPE,
                Constants.LOCATIONS_NAME};
        return columns;
    }

    public static Event cursorToEvent(Cursor cursor) {
        Event event = new Event();
        event.setEventId(cursor.getLong(cursor.getColumnIndex(Constants.AUTOINCREMETN_COLUMN)));
        event.setEventName(cursor.getString(cursor.getColumnIndex(Constants.EVENTS_NAME)));
        event.setEventDescription(cursor.getString(cursor.getColumnIndex(Constants.EVENTS_DESCRIPTION)));
        event.setEventTicketPrice(cursor.getDouble(cursor.getColumnIndex(Constants.EVENTS_TICKET_PRICE)));
        event.setEventDate(cursor.getString(cursor.getColumnIndex(Constants.EVENTS_DATE)));
        event.setEventLocation(cursor.getString(cursor.getColumnIndex(Constants.LOCATIONS_NAME)));
        event.setEventType(cursor.getString(cursor.getColumnIndex(Constants.TYPES_TYPE)));

        return event;
    }

    @Override
    public ArrayList<String> selectAllTypes() {
        ArrayList<String> allTypes = new ArrayList<>();
        Cursor cursor = null;
        String[] columns = {Constants.TYPES_TYPE};
        cursor = database.query(Constants.TABLE_TYPES, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String type = cursor.getString(cursor.getColumnIndex(Constants.TYPES_TYPE));
            allTypes.add(type);
            cursor.moveToNext();
        }
        cursor.close();
        return allTypes;
    }
}
