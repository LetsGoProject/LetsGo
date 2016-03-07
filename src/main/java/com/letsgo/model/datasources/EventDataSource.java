package com.letsgo.model.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.letsgo.model.Event;
import com.letsgo.model.daointerfaces.EventDao;
import com.letsgo.model.queries.Queries;
import com.letsgo.model.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petkata on 5.3.2016 г..
 */
public class EventDataSource extends DataSource implements EventDao{


    public EventDataSource(Context context) {
        super(context);
    }

    @Override
    public Event createEvent(String name, String description, double price, String date, String location, String type) {
        Event event = new Event();
        // TODO       check if email exists
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
        Cursor cursor = database.query(Constants.TABLE_EVENTS, Constants.EVENTS_ALL_COLUMNS, Constants.EVENTS_NAME + " = "
                + "'" + name + "'", null, null, null, null);
        cursor.moveToFirst();
        Event event = cursorToEvent(cursor);
        cursor.close();
        return event;
    }

    private Event cursorToEvent(Cursor cursor) {
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
//        String[] columns = {Constants.EVENTS_NAME,Constants.EVENTS_DATE,Constants.EVENTS_LOCATION};
//        TODO remake rawquery to query
        Cursor cursor = null;
        if (period.equals("upcoming")){
            cursor = database.rawQuery(Queries.EVENT_UPCOMING_JOIN_TYPE_LOCATION,null);
        }
        if (period.equals("past")){
            cursor = database.rawQuery(Queries.EVENT_PAST_JOIN_TYPE_LOCATION,null);
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Event event = cursorToEvent(cursor);
            allEvents.add(event);
            cursor.moveToNext();
        }
        cursor.close();
        return allEvents;
    }

    @Override
    public List<Event> showUpcommingEvents() {
        return null;
    }

    @Override
    public List<Event> showPastEvents() {
        return null;
    }
}
