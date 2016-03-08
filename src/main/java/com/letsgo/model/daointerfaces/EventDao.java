package com.letsgo.model.daointerfaces;

import com.letsgo.model.Event;
import com.letsgo.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petkata on 5.3.2016 г..
 */
public interface EventDao {

    Event createEvent(String name,String description,double price, String date,String location,String type);
    Event showEvent(String name);
    boolean editEventName(String name,String newUsername);
    boolean editEventDescription(String name,String newDescription);
    boolean editEventTicketPrice(String name,double newPrice);
    boolean editEventDate(String name,String newDate);
    boolean editEventLocation(String name,String newLocation);
    boolean editEventType(String name,String newType);
    boolean deleteEvent(String name);
    boolean checkForExisting(String table,String column,String selectionArg);
    List<Event> showEvents(String period);
    List<Event> showSearchResults(String name,String type,String location,String dateBefore,String dateAfter);
//    TEST DATEPICKER
    List<Event> showSearchResults(String date);
    ArrayList<String> selectAllTypes();

}
