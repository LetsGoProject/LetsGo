package com.letsgo.controller;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.letsgo.R;
import com.letsgo.model.Event;
import com.letsgo.model.daointerfaces.UserDao;
import com.letsgo.model.datasources.UserDataSource;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentWatchlist extends Fragment {

    ListView listWatchList;
    UserDao userDataSource;
    long userId;
    Button clearWatchlist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);

        userDataSource = new UserDataSource(getContext());
        ((UserDataSource) userDataSource).open();

        SharedPreferences getUserId = getActivity().getPreferences(Context.MODE_PRIVATE);
        userId = getUserId.getLong("user_id", -1);

        final ArrayList<Event> watchlistData = userDataSource.showWathclist(userId);
        listWatchList = (ListView) view.findViewById(R.id.list_view_watchlist);

        final ArrayAdapter<Event> adapterWatchlist = new AdapterShowEvents(getContext(), watchlistData);
        listWatchList.setAdapter(adapterWatchlist);

        listWatchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String eventName = ((TextView) view.findViewById(R.id.event_list_name)).getText().toString();

                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to remove " + eventName + " from Watchlist?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        userDataSource.removeEventFromWatchlist(userId, eventName);

                        watchlistData.remove(position);
                        adapterWatchlist.notifyDataSetChanged();
                    }
                });
                adb.show();
            }
        });

        clearWatchlist = (Button) view.findViewById(R.id.btn_clear_all_watchlist);
        clearWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to remove all from Watchlist?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        userDataSource.removeAllFromWatchlist(userId);

                        watchlistData.clear();
                        adapterWatchlist.notifyDataSetChanged();
                    }
                });
                adb.show();
            }
        });
        return view;
    }
    @Override
    public void onResume() {
        ((UserDataSource) userDataSource).open();
        super.onResume();
    }

    @Override
    public void onPause() {
        ((UserDataSource) userDataSource).close();
        super.onPause();
    }
}
