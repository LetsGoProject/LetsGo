package com.letsgo.controller;

import android.widget.CheckBox;

import com.letsgo.model.Event;

/**
 * Created by Petkata on 11.3.2016 г..
 */
public interface Communicator {
    void sendEvent(AbstractFragment fragment,Event event,boolean isFav);
    void sendSearchCriteria(AbstractFragment fragment,String eventName,String eventType,
                            String eventLocation,String afterDate,String beforeDate);
}