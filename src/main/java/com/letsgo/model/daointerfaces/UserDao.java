package com.letsgo.model.daointerfaces;

import com.letsgo.model.Event;
import com.letsgo.model.User;

import java.util.List;

/**
 * Created by Petkata on 5.3.2016 г..
 */
public interface UserDao {

    boolean loginUser(String email, String password);
    User createUser(String username,String email, String password);
    User showUser(String email);
    boolean editUsername(String email,String newUsername);
    boolean editEmail(String email,String newEmail);
    boolean editPassword(String email,String newPassword);
    boolean deleteUser(String email);
    boolean addEventToWatchlist(String emial,String event_name);
    boolean removeEventFromWatchlist(String emial,String eventName);

    boolean checkForExisting(String table,String column,String selectionArg);

    Event showEventFromWatch(String eventName);
    List<Event> showWathclist();
//    List<User> listAllUsers();
    //    for testing
    String showAllUsers();
}
