package com.letsgo.model;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import com.letsgo.model.utils.FormatMoney;

/**
 * Created by Petkata on 24.2.2016 г..
 */
public class Event implements Parcelable{

    private long eventId;
    private String eventName;
    private String eventDescription;
    private double eventTicketPrice;
    private String eventDate;
    private String eventLocation;
    private String eventType;

    // it is empty so it is called and not the constructor with parcel
    public Event(){}

    protected Event(Parcel in) {
        eventId = in.readLong();
        eventName = in.readString();
        eventDescription = in.readString();
        eventTicketPrice = in.readDouble();
        eventDate = in.readString();
        eventLocation = in.readString();
        eventType = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public double getEventTicketPrice() {
        return eventTicketPrice;
    }

    public void setEventTicketPrice(double eventTicketPrice) {
        this.eventTicketPrice = eventTicketPrice;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(this.eventId);
        dest.writeString(this.eventName);
        dest.writeString(this.eventDescription);
        dest.writeDouble(this.eventTicketPrice);
        dest.writeString(this.eventDate);
        dest.writeString(this.eventLocation);
        dest.writeString(this.eventType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return eventName.equals(event.eventName);

    }

    @Override
    public int hashCode() {
        return eventName.hashCode();
    }
}
