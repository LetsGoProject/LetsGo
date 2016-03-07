package com.letsgo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Petkata on 24.2.2016 г..
 */
public class User extends Account implements Parcelable{

    private long id;
    private String username;
    private String email;
    private String password;
    private boolean isAdmin;
// it is empty so it is called and not the constructor with parcel
    public User(){}

    private Set<Event> watchtList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Set<Event> getWatchtList() {
        return watchtList;
    }

    public void setWatchtList(Set<Event> watchtList) {
        this.watchtList = watchtList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeByte((byte) (this.isAdmin ? 1 : 0));
    }
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private User (Parcel in) {
        this.id = in.readLong();
        this.username = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.isAdmin = in.readByte() != 0;
    }
}
