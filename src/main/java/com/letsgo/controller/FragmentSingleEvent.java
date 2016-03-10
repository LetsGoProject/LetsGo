package com.letsgo.controller;


import android.content.Context;
import android.content.SharedPreferences;
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
import com.letsgo.model.daointerfaces.UserDao;
import com.letsgo.model.datasources.UserDataSource;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FragmentSingleEvent extends Fragment {

//    TODO find if in watchlist
    boolean isWatched;

    UserDao userDataSource;

    long userId;

    FloatingActionButton fab;
    Button btnBuyTicket;
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


        userDataSource = new UserDataSource(getContext());
        ((UserDataSource)userDataSource).open();

        SharedPreferences getUserId = getActivity().getPreferences(Context.MODE_PRIVATE);
        userId = getUserId.getLong("user_id",-1);

        final TextView eventName = (TextView) view.findViewById(R.id.single_event_name);
        TextView eventDate = (TextView) view.findViewById(R.id.single_event_date);
        TextView eventLocation = (TextView) view.findViewById(R.id.single_event_location);
        TextView eventDescription = (TextView) view.findViewById(R.id.single_event_description);
        TextView eventPrice = (TextView) view.findViewById(R.id.single_event_ticket_price);

        eventName.setText(selectedEvent.getEventName());
        eventDate.setText(selectedEvent.getEventDate());
        eventLocation.setText(selectedEvent.getEventLocation());
        eventDescription.setText(selectedEvent.getEventDescription());
        eventPrice.setText(String.valueOf(selectedEvent.getEventTicketPrice()));

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isWatched){
                    isWatched = true;
                    if (userDataSource.addEventToWatchlist(userId,selectedEvent.getEventName())) {

                        fab.setImageResource(android.R.drawable.btn_star_big_on);
                        Snackbar.make(view, "Event added to your watchlist", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                    else{
                        Snackbar.make(view, "Error adding to watchlist", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                }
                else {
                    isWatched = false;
                    fab.setImageResource(android.R.drawable.btn_star_big_off);
                    Snackbar.make(view, "Event removed from your watchlist", Snackbar.LENGTH_SHORT)
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

        btnBuyTicket = (Button) view.findViewById(R.id.go_to_buy_ticket);
        btnBuyTicket.setOnClickListener(new View.OnClickListener() {
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

        checkIfExpired(eventDate.getText().toString());

        return view;
    }

    private void checkIfExpired(String date){

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-DD");
        String formattedDate = df.format(c.getTime());
        if (date.compareToIgnoreCase(formattedDate) < 0){
            btnBuyTicket.setText("Expired");
            btnBuyTicket.setClickable(false);
            fab.setVisibility(View.GONE);
        }

    }
}
