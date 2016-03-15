package com.letsgo.model.daointerfaces;

import com.letsgo.model.Event;
import com.letsgo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Petkata on 5.3.2016 г..
 */
public interface UserDao {

    boolean loginUser(String email, String password);

    User createUser(String username,String email, String password);

    User showUser(String email);

    boolean editUsername(long userId,String newUsername);

    boolean editEmail(long userId,String newEmail);

    boolean editPassword(long userId,String newPassword);

    boolean deleteUser(String email);

    boolean addEventToWatchlist(long userId,String eventName);

    boolean removeEventFromWatchlist(long userId,String eventName);

    boolean removeAllFromWatchlist(long userId);

    boolean buyTicket(String date,long userId,long eventId,int quantity);

    ArrayList<Map <String,Map<Event,Integer>>> selectTicketsForUser (long userId);

    boolean checkForExisting(String table,String column,String selectionArg);

    Event showEventFromWatch(String eventName);

    ArrayList<Event> showWathclist(long userId);
//    List<User> listAllUsers();
    //    for testing
    String showAllUsers();
}
