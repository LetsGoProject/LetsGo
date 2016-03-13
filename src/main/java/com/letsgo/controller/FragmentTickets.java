package com.letsgo.controller;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.letsgo.R;
import com.letsgo.model.Event;
import com.letsgo.model.daointerfaces.UserDao;
import com.letsgo.model.datasources.UserDataSource;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTickets extends Fragment {

    UserDao userDataSource;
    long userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tickets, container, false);

        userDataSource = new UserDataSource(getContext());
        ((UserDataSource)userDataSource).open();

        SharedPreferences getUserId = getActivity().getPreferences(Context.MODE_PRIVATE);
        userId = getUserId.getLong("user_id", -1);

        Map<Event,Integer> bindedEventsTickets = userDataSource.selectTicketsForUser(userId);

        Log.i("USER TICKTEST", bindedEventsTickets.toString());

        return v;
    }

}
