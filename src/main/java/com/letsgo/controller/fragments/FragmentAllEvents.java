package com.letsgo.controller.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.letsgo.R;
import com.letsgo.controller.controllerutils.Communicator;
import com.letsgo.tasks.TaskShowPeriodEvents;

public class FragmentAllEvents extends Fragment {

    Communicator rootContext;

    @Override
    public void onAttach(Context context) {
        this.rootContext = (Communicator) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_events, container, false);

        TaskShowPeriodEvents taskShowPeriodEvents = new TaskShowPeriodEvents((Context) rootContext,"past");
        taskShowPeriodEvents.execute();
        TaskShowPeriodEvents taskShowUpcomingEvents = new TaskShowPeriodEvents((Context) rootContext,"upcoming");
        taskShowUpcomingEvents.execute();

        return view;
    }
}
