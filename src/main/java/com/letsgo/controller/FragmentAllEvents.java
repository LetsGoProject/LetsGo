package com.letsgo.controller;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import com.letsgo.R;
import com.letsgo.model.Event;
import com.letsgo.model.daointerfaces.EventDao;
import com.letsgo.model.datasources.EventDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAllEvents extends Fragment {

    EventDao eventDataSource;
    ListView listUpcomingEvents;
    ListView listPastEvents;

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

        eventDataSource = new EventDataSource(getContext());
        ((EventDataSource)eventDataSource).open();

        final List<Event> upcomingEventsDataSource = eventDataSource.showEvents("upcoming");
        final List<Event> pastEventsDataSource = eventDataSource.showEvents("past");


        listPastEvents = (ListView) view.findViewById(R.id.all_events_past_list);
        ArrayAdapter<Event> adapterPastEvents = new AdapterShowEvents(getContext(),(ArrayList<Event>)pastEventsDataSource);
        listPastEvents.setAdapter(adapterPastEvents);
        listPastEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox isFav = (CheckBox) view.findViewById(R.id.is_in_fav);
                rootContext.sendEvent(new FragmentSingleEvent(),pastEventsDataSource.get(position), isFav.isChecked());
//                LoadSingleEventFragment.load(getFragmentManager(),pastEventsDataSource,position,view);
            }
        });

        ArrayAdapter<Event> adapterUpcomingEvents = new AdapterShowEvents(getContext(),(ArrayList<Event>)upcomingEventsDataSource);
        listUpcomingEvents = (ListView) view.findViewById(R.id.all_events_upcoming_list);
        listUpcomingEvents.setAdapter(adapterUpcomingEvents);
        adapterUpcomingEvents.notifyDataSetChanged();
        listUpcomingEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox isFav = (CheckBox) view.findViewById(R.id.is_in_fav);
                rootContext.sendEvent(new FragmentSingleEvent(),upcomingEventsDataSource.get(position), isFav.isChecked());
//                LoadSingleEventFragment.load(getFragmentManager(),upcomingEventsDataSource,position,view);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        ((EventDataSource)eventDataSource).open();
        super.onResume();
    }

    @Override
    public void onPause() {
        ((EventDataSource)eventDataSource).close();
        super.onPause();
    }
}
