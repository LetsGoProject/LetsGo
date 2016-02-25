package com.letsgo.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DataBase {

    private static DataBase instance = new DataBase();

    private Set<Event> eventList;
    private Set<User> userList;

    private DataBase() {
        this.eventList = new HashSet<Event>();
        this.userList = new HashSet<User>();
    }

    public static DataBase getInstance() {
        return instance;
    }

    Set<Event> getEventList() {
        return this.eventList;
    }

    Set<User> getUserList() {
        return this.userList;
    }

    Event getEvent(String eventName) {

        for (Event event : this.eventList) {
            if (event.getEventName().equals(eventName)) {
                return event;
            }
        }
        return null;
    }

    void addEventToDb(Event newEvent) {
        if (this.eventList.add(newEvent)) {
            return;
        }
    }

    void removeEventFromDb(String eventName) {
        Iterator<Event> it;
        it = this.eventList.iterator();
        while (it.hasNext()) {
            if (it.next().getEventName().equals(eventName)) {
                it.remove();
                return;
            }
        }
    }

    public void ShowAllEvents() {
        for (Event event : eventList) {
        }
        return;
    }

    void addUserToDb(User newUser) {
        this.userList.add(newUser);
    }

    void removeUserFromDb(User user) {
        this.userList.remove(user);
    }

    public void showUserList() {
    }
}
