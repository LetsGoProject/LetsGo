package com.letsgo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Petkata on 8.3.2016 г..
 */
public class Location implements Parcelable{

    private long locationId;
    private String locationName;
    private String locationAddress;
    private String locationContact;

    public Location(){}

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationContact() {
        return locationContact;
    }

    public void setLocationContact(String locationContact) {
        this.locationContact = locationContact;
    }

    protected Location(Parcel in) {
        locationId = in.readLong();
        locationName = in.readString();
        locationAddress = in.readString();
        locationContact = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(locationId);
        dest.writeString(locationName);
        dest.writeString(locationAddress);
        dest.writeString(locationContact);
    }
}
