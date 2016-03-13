package com.letsgo.controller;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.letsgo.R;
import com.letsgo.model.Event;
import com.letsgo.model.daointerfaces.UserDao;
import com.letsgo.model.datasources.UserDataSource;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FragmentSingleEvent extends AbstractFragment {

//    TODO find if in watchlist

    UserDao userDataSource;

    long userId;

    FloatingActionButton fab;
    Button btnBuyTicket;
    Event selectedEvent;
    boolean isFav;

    Communicator rootContext;

    @Override
    public void onAttach(Context context) {
        rootContext = (Communicator) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_single_event, container, false);


        userDataSource = new UserDataSource(getContext());
        ((UserDataSource) userDataSource).open();

        SharedPreferences getUserId = getActivity().getPreferences(Context.MODE_PRIVATE);
        userId = getUserId.getLong("user_id", -1);

        final TextView eventName = (TextView) view.findViewById(R.id.single_event_name);
        TextView eventDate = (TextView) view.findViewById(R.id.single_event_date);
        TextView eventLocation = (TextView) view.findViewById(R.id.single_event_location);
        TextView eventDescription = (TextView) view.findViewById(R.id.single_event_description);
        TextView eventPrice = (TextView) view.findViewById(R.id.single_event_ticket_price);

        eventName.setText(selectedEvent.getEventName());
        eventDate.setText(DateFormater.from_yyyyMMdd_To_dMMMyyyy(selectedEvent.getEventDate()));
        eventLocation.setText(selectedEvent.getEventLocation());
        eventDescription.setText(selectedEvent.getEventDescription());
        eventPrice.setText(String.valueOf(selectedEvent.getEventTicketPrice()));

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        if (isFav)
            fab.setImageResource(android.R.drawable.btn_star_big_on);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isFav) {
                    if (userDataSource.addEventToWatchlist(userId, selectedEvent.getEventName())) {

                        fab.setImageResource(android.R.drawable.btn_star_big_on);
                        Snackbar.make(view, "Event added to your watchlist", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    } else {
                        Snackbar.make(view, "Error adding to watchlist", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                } else {
                    if (userDataSource.removeEventFromWatchlist(userId, selectedEvent.getEventName())) {
                        isFav = false;
                        fab.setImageResource(android.R.drawable.btn_star_big_off);
                        Snackbar.make(view, "Event removed from your watchlist", Snackbar.LENGTH_SHORT)
                                .setAction("UNDO", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (userDataSource.addEventToWatchlist(userId, selectedEvent.getEventName())) {
                                            Snackbar.make(view, "Event restored", Snackbar.LENGTH_SHORT).show();
                                            isFav = true;
                                            fab.setImageResource(android.R.drawable.btn_star_big_on);
                                        } else {
                                            Snackbar.make(view, "Error adding to watchlist", Snackbar.LENGTH_SHORT)
                                                    .setAction("Action", null).show();
                                        }
                                    }
                                }).show();
                    } else
                        Snackbar.make(view, "Error removing from watchlist", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                }

            }
        });

        btnBuyTicket = (Button) view.findViewById(R.id.go_to_buy_ticket);
        btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity().findViewById(R.id.land_frag_container) != null) {
                    getActivity().findViewById(R.id.frag_container).setVisibility(View.GONE);
                    getActivity().findViewById(R.id.land_frag_ticket).setVisibility(View.VISIBLE);
                }
                rootContext.sendEvent(new FragmentBuyTicket(), selectedEvent, isFav);
            }
        });

        checkIfExpired(eventDate.getText().toString());

        return view;
    }

    private void checkIfExpired(String date) {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        if (DateFormater.from_dMMMyyyy_To_yyyyMMdd(date).compareToIgnoreCase(formattedDate) < 0) {
            btnBuyTicket.setText("Expired");
            btnBuyTicket.setClickable(false);
            fab.setVisibility(View.GONE);
        }

    }

    @Override
    public void getEvent(Event e, boolean isFav) {
        selectedEvent = e;
        this.isFav = isFav;
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
