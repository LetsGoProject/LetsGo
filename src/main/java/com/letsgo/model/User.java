package com.letsgo.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Petkata on 24.2.2016 г..
 */
public class User extends Account {

    private Set<Event> watchtList;

    public User(String email, String password) {
        super(email, password);
    }

    @Override
    public void login(String pass) {
        if (!DataBase.getInstance().getUserList().contains(this)) {
            DataBase.getInstance().addUserToDb(this);
        }
        super.login(pass);

    }

    public void userAddToWatchList(String eventName){
        if (this.checkUser()) {
            if (this.watchtList == null) {
                this.watchtList = new HashSet<Event>();
            }
            if (DataBase.getInstance().getEvent(eventName) != null) {
                if(this.watchtList.add(DataBase.getInstance().getEvent(eventName))){

                }
            }
            return;
        }
    }

    public void userRemoveFromWatchList(String eventName){
        if (this.checkUser()) {
            Iterator<Event> it;
            it = this.watchtList.iterator();
            while (it.hasNext()) {
                if (it.next().getEventName().equals(eventName)) {
                    it.remove();
                    return;
                }
            }
            return;
        }
    }

    public void userShowWatchList(){
        if (this.checkUser()) {
            for (Event event : watchtList) {
                System.out.println(event);
            }
            return;
        }
    }

    public void editprofile(String newEmail, String newPassword){
        if (newEmail!=null) {
            super.setEmail(newEmail);
        }
        if (newPassword!=null) {
            super.setPassword(newPassword);
        }
    }

    private boolean checkUser(){
        if (super.getHasLogged() && DataBase.getInstance().getUserList().contains(this)) {
            return true;
        }
        else return false;
    }
}
