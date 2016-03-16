package com.letsgo.controller.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.letsgo.R;
import com.letsgo.controller.controllerutils.AbstractFragment;
import com.letsgo.controller.controllerutils.Communicator;
import com.letsgo.controller.adapters.AdapterShowEvents;
import com.letsgo.model.Event;
import com.letsgo.model.Location;
import com.letsgo.model.daointerfaces.EventDao;
import com.letsgo.model.daointerfaces.LocationDao;
import com.letsgo.model.datasources.EventDataSource;
import com.letsgo.model.datasources.LocationDataSource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLocationInfo extends AbstractFragment {

    private TextView txtLocationName;
    private TextView txtLocationAddress;
    private TextView txtLocationContacts;
    private String locationName;
    private Button btnDial;
    private ListView listViewEventsForLocation;


    EventDao eventDataSource;

    private LocationDao locationDataSource;

    Communicator rootContext;

    @Override
    public void onAttach(Context context) {
        rootContext = (Communicator)context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_info, container, false);

        locationDataSource = new LocationDataSource(getContext());
        ((LocationDataSource)locationDataSource).open();
        eventDataSource = new EventDataSource(getContext());
        ((EventDataSource)eventDataSource).open();

        Location location = locationDataSource.selectLocation(locationName);

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final List<Event> listUpcomingEventsForLocation = eventDataSource.showSearchResults(null, null, location.getLocationName(), sdf.format(d), null);

        listViewEventsForLocation = (ListView) view.findViewById(R.id.recycler_view_location_events);
        ArrayAdapter<Event> locationListAdapter = new AdapterShowEvents(getContext(),(ArrayList<Event>)listUpcomingEventsForLocation);
        listViewEventsForLocation.setAdapter(locationListAdapter);
        listViewEventsForLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox isFav = (CheckBox) view.findViewById(R.id.is_in_fav);
                if (getActivity().findViewById(R.id.land_frag_container) != null) {
                    getActivity().findViewById(R.id.frag_container).setVisibility(View.GONE);
                    getActivity().findViewById(R.id.land_frag_ticket).setVisibility(View.VISIBLE);
                }
                rootContext.sendEvent(new FragmentSingleEvent(), listUpcomingEventsForLocation.get(position), isFav.isChecked());
            }
        });


        txtLocationName = (TextView) view.findViewById(R.id.location_name);
        txtLocationName.setText(location.getLocationName());

        txtLocationAddress = (TextView) view.findViewById(R.id.location_address);
        txtLocationAddress.setText(location.getLocationAddress());
        txtLocationContacts = (TextView) view.findViewById(R.id.location_contacts);
        txtLocationContacts.setText(location.getLocationContact());
//        TODO correct from tel xxx to tel:xxx
        final String telNumber = location.getLocationContact().substring(location.getLocationContact().indexOf(' '), location.getLocationContact().length());
        btnDial = (Button) view.findViewById(R.id.location_contacts_dial);
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+359"+telNumber));
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void getLocationByName(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public void onResume() {
        ((LocationDataSource)locationDataSource).open();
        ((EventDataSource)eventDataSource).open();
        super.onResume();
    }

    @Override
    public void onPause() {
        ((LocationDataSource)locationDataSource).close();
        ((EventDataSource)eventDataSource).close();
        super.onPause();
    }
}
