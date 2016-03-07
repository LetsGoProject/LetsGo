package com.letsgo.controller;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.letsgo.R;
import com.letsgo.model.Event;

public class FragmentSingleEvent extends Fragment {

//    TODO find if in watchlist
    boolean isWatched;

    Event selectedEvent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        selectedEvent = getArguments().getParcelable("selected_event");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_single_event, container, false);

        TextView eventName = (TextView) view.findViewById(R.id.single_event_name);
        TextView eventDate = (TextView) view.findViewById(R.id.single_event_date);
        TextView eventLocation = (TextView) view.findViewById(R.id.single_event_location);
        TextView eventDescription = (TextView) view.findViewById(R.id.single_event_description);
        TextView eventPrice = (TextView) view.findViewById(R.id.single_event_ticket_price);

        eventName.setText(selectedEvent.getEventName());
        eventDate.setText(selectedEvent.getEventDate());
        eventLocation.setText(selectedEvent.getEventLocation());
        eventDescription.setText(selectedEvent.getEventDescription());
        eventPrice.setText(String.valueOf(selectedEvent.getEventTicketPrice()));

        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isWatched){
                    isWatched = true;
                    fab.setImageResource(android.R.drawable.btn_star_big_on);
                    Snackbar.make(view, "Event added to your watchlist", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    isWatched = false;
                    fab.setImageResource(android.R.drawable.btn_star_big_off);
                    Snackbar.make(view, "Event removed from your watchlist", Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Snackbar.make(view, "Event restored", Snackbar.LENGTH_SHORT).show();
                                    isWatched = true;
                                    fab.setImageResource(android.R.drawable.btn_star_big_on);
                                }
                            }).show();
                }

            }
        });

        Button btn = (Button) view.findViewById(R.id.go_to_buy_ticket);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                FragmentBuyTicket btf = new FragmentBuyTicket();
                Bundle bundle = new Bundle();
                bundle.putParcelable("current_event", selectedEvent);
                btf.setArguments(bundle);
                ft.replace(R.id.frag_container, btf, "ticketFragment");
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view;
    }

}
