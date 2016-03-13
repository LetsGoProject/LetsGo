package com.letsgo.controller;


import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.ListView;

import com.letsgo.R;
import com.letsgo.controller.adapters.AdapterShowEvents;
import com.letsgo.model.Event;
import com.letsgo.model.daointerfaces.EventDao;
import com.letsgo.model.datasources.EventDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSearchResults extends AbstractFragment {

    String eventName;
    String eventType;
    String eventLocation;
    String afterDate;
    String beforeDate;

    Communicator rootContext;

    EventDao eventDataSource;
    ListView listSearchresults;

    Button btnRefineSearch;
    Button btnNewSearch;

    @Override
    public void onAttach(Context context) {
        rootContext = (Communicator) context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (eventName == null && savedInstanceState != null) {
            eventName = savedInstanceState.getString("name");
            eventType = savedInstanceState.getString("type");
            eventLocation = savedInstanceState.getString("location");
            afterDate = savedInstanceState.getString("after");
            beforeDate = savedInstanceState.getString("before");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        eventDataSource = new EventDataSource(getContext());
        ((EventDataSource)eventDataSource).open();

        final List<Event> searchResultsDataSource = eventDataSource.showSearchResults(eventName,eventType,eventLocation,afterDate,beforeDate);
        listSearchresults = (ListView) view.findViewById(R.id.search_result);

        ArrayAdapter<Event> searchAdapter = new AdapterShowEvents(getContext(),(ArrayList<Event>)searchResultsDataSource);
        listSearchresults.setAdapter(searchAdapter);

        listSearchresults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               LoadSingleEventFragment.load(getFragmentManager(),searchResultsDataSource,position,view);
                CheckBox isFav = (CheckBox) view.findViewById(R.id.is_in_fav);
                if (getActivity().findViewById(R.id.land_frag_container) != null) {
                    getActivity().findViewById(R.id.frag_container).setVisibility(View.GONE);
                    getActivity().findViewById(R.id.land_frag_ticket).setVisibility(View.VISIBLE);
                }
                rootContext.sendEvent(new FragmentSingleEvent(), searchResultsDataSource.get(position), isFav.isChecked());

            }
        });

        btnNewSearch = (Button) view.findViewById(R.id.new_search);
        btnNewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity().findViewById(R.id.land_frag_ticket) != null) {
                    getActivity().findViewById(R.id.land_frag_ticket).setVisibility(View.GONE);
                    getActivity().findViewById(R.id.frag_container).setVisibility(View.VISIBLE);
                }
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment advancedSearch = new FragmentAdvancedSearch();
                ft.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right,R.anim.slide_in_right,R.anim.slide_out_left);
                ft.replace(R.id.frag_container, advancedSearch);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        btnRefineSearch = (Button) view.findViewById(R.id.refine_search);
        btnRefineSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
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

    @Override
    public void getSearchCriteria(String eventName, String eventType, String eventLocation, String afterDate, String beforeDate) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventLocation = eventLocation;
        this.afterDate =afterDate;
        this.beforeDate = beforeDate;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("name",this.eventName);
        outState.putString("type",this.eventType);
        outState.putString("location",this.eventLocation);
        outState.putString("after",this.afterDate);
        outState.putString("before",this.beforeDate);
        super.onSaveInstanceState(outState);
    }
}
