package com.letsgo.model;

public class Admin extends Account{


/*
    public Admin(String email, String password) {
        super(email, password);
    }

    private DataBase db;
    private boolean hasConnected;

    public void connectToDB(DataBase db){
        if (super.getHasLogged()) {
            this.db = db;
            this.hasConnected = true;
        }
    }

    public void adminAddEvent(Event event){
        if (this.checkAdmin()) {
            this.db.addEventToDb(event);
            return;
        }
    }

    public void adminRemoveEvent(String eventName){
        if (this.checkAdmin()) {
            this.db.removeEventFromDb(eventName);
            return;
        }
    }

    public void adminRemoveUser(User user){
        if (this.checkAdmin()) {
            DataBase.getInstance().removeUserFromDb(user);
            return;
        }

    }

    private boolean checkAdmin(){
        if (super.getHasLogged() && this.hasConnected) {
            return true;
        }
        return false;
    }
    */
}
