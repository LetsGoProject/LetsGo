package com.letsgo.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CheckBox;

import com.letsgo.R;
import com.letsgo.model.Event;

import java.util.List;

/**
 * Created by Petkata on 11.3.2016 г..
 */
public class LoadSingleEventFragment {

    public static void sendEventToSingleEventFragment(FragmentManager getFM,Event event,AbstractFragment receiver,boolean isFav){
        receiver.getEvent(event, isFav);
        FragmentTransaction tr = getFM.beginTransaction();
        tr.replace(R.id.frag_container, receiver);
        tr.addToBackStack(null);
        tr.commit();
    }

}
