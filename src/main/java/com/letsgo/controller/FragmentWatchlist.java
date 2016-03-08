package com.letsgo.controller;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.letsgo.R;
import com.letsgo.model.Event;
import com.letsgo.model.daointerfaces.UserDao;
import com.letsgo.model.datasources.UserDataSource;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentWatchlist extends Fragment {

    ListView watchList;
    UserDao userDataSource;
    long userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);

        userDataSource = new UserDataSource(getContext());
        ((UserDataSource)userDataSource).open();

        SharedPreferences getUserId = getActivity().getPreferences(Context.MODE_PRIVATE);
        userId = getUserId.getLong("user_id",-1);

        ArrayList<Event> watchlistEvents = userDataSource.showWathclist(userId);
        watchList = (ListView) view.findViewById(R.id.list_view_watchlist);

        ArrayAdapter<Event> adapterWatchlist = new AdapterShowEvents(getContext(),watchlistEvents);
        watchList.setAdapter(adapterWatchlist);


        return view;
    }

}
