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
import android.widget.Button;
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
public class FragmentSearchResults extends Fragment {

//    TODO on refine search click send code to not clear fields
    String eventName;
    String eventType;
    String eventLocation;
    String afterDate;
    String beforeDate;

    EventDao eventDataSource;
    ListView listSearchresults;

    Button btnRefineSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        eventName = (String) getArguments().get("event_name");
        eventType = (String) getArguments().get("event_type");
        eventLocation = (String) getArguments().get("event_location");
        afterDate = (String) getArguments().get("afterDate");
        beforeDate = (String) getArguments().get("beforeDate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        eventDataSource = new EventDataSource(getContext());
        ((EventDataSource)eventDataSource).open();

        final List<Event> searchResults = eventDataSource.showSearchResults(eventName,eventType,eventLocation,afterDate,beforeDate);
        listSearchresults = (ListView) view.findViewById(R.id.search_result);

        ArrayAdapter<Event> searchAdapter = new AdapterShowEvents(getContext(),(ArrayList<Event>)searchResults);
        listSearchresults.setAdapter(searchAdapter);

        listSearchresults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
//                Put event object in called fragment
                FragmentSingleEvent sef = new FragmentSingleEvent();
                Bundle bundle = new Bundle();
                bundle.putParcelable("selected_event", searchResults.get(position));
                sef.setArguments(bundle);

                ft.replace(R.id.frag_container, sef, "singleEvent");
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        btnRefineSearch = (Button) view.findViewById(R.id.refine_search);
        btnRefineSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment advancedSearch = new FragmentAdvancedSearch();
                ft.replace(R.id.frag_container,advancedSearch);
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
