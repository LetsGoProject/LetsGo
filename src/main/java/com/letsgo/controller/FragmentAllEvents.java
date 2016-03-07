package com.letsgo.controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    public FragmentAllEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_events, container, false);

        eventDataSource = new EventDataSource(getContext());
        ((EventDataSource)eventDataSource).open();

        final List<Event> upcomingEvents = eventDataSource.showEvents("upcoming");
        final List<Event> pastEvents = eventDataSource.showEvents("past");
        listPastEvents = (ListView) view.findViewById(R.id.all_events_past_list);
        ArrayAdapter<Event> adapterPastEvents = new AdapterShowEvents(getContext(),(ArrayList<Event>)pastEvents);
        listPastEvents.setAdapter(adapterPastEvents);

        ArrayAdapter<Event> adapterUpcomingEvents = new AdapterShowEvents(getContext(),(ArrayList<Event>)upcomingEvents);
        listUpcomingEvents = (ListView) view.findViewById(R.id.all_events_upcoming_list);
        listUpcomingEvents.setAdapter(adapterUpcomingEvents);
        adapterUpcomingEvents.notifyDataSetChanged();
        listUpcomingEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
//                Put event object in called fragment
                FragmentSingleEvent sef = new FragmentSingleEvent();
                Bundle bundle = new Bundle();
                bundle.putParcelable("selected_event", upcomingEvents.get(position));
                sef.setArguments(bundle);

                ft.replace(R.id.frag_container, sef, "singleEvent");
                ft.addToBackStack(null);
                ft.commit();
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
