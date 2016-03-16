package com.letsgo.controller.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.letsgo.R;
import com.letsgo.controller.adapters.AdapterShowTickets;
import com.letsgo.model.Event;
import com.letsgo.model.daointerfaces.UserDao;
import com.letsgo.model.datasources.UserDataSource;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTickets extends Fragment {

    UserDao userDataSource;
    long userId;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tickets, container, false);

        userDataSource = new UserDataSource(getContext());
        ((UserDataSource)userDataSource).open();

        SharedPreferences getUserId = getActivity().getPreferences(Context.MODE_PRIVATE);
        userId = getUserId.getLong("user_id", -1);

        ArrayList<Map <String,Map<Event,Integer>>> bindedEventsTickets = userDataSource.selectTicketsForUser(userId);

        recyclerView  = (RecyclerView) v.findViewById(R.id.recycler_view_tickets);

        AdapterShowTickets adapterShowTickets = new AdapterShowTickets(getActivity(),bindedEventsTickets);

        recyclerView.setAdapter(adapterShowTickets);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }


    @Override
    public void onResume() {
        ((UserDataSource)userDataSource).open();
        super.onResume();
    }

    @Override
    public void onPause() {
        ((UserDataSource)userDataSource).close();
        super.onPause();
    }

}
