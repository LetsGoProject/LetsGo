package com.letsgo.controller;

import android.support.v4.app.Fragment;

import com.letsgo.model.Event;

/**
 * Created by Petkata on 11.3.2016 г..
 */
public abstract class AbstractFragment extends Fragment implements Receiver{

//    @Override
    public void getEvent(Event e, boolean isFav) {

    }

//    @Override
    public void getSearchCriteria(String eventName, String eventType, String eventLocation, String afterDate, String beforeDate) {

    }

    public void getLocationByName(String locationName){}

}
