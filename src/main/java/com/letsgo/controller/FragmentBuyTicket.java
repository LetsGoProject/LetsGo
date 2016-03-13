package com.letsgo.controller;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBuyTicket extends AbstractFragment {

    Event event;
    int quantity;
    double price;
    double total;
    long userId;

    UserDao userDataSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_ticket, container, false);

        userDataSource = new UserDataSource(getContext());
        ((UserDataSource)userDataSource).open();
        SharedPreferences getUserId = getActivity().getPreferences(Context.MODE_PRIVATE);
        userId = getUserId.getLong("user_id", -1);

        quantity = 0;
        price = event.getEventTicketPrice();
        total = 0;

        TextView eventName = (TextView) view.findViewById(R.id.ticket_label_for_event);
        TextView eventDate = (TextView) view.findViewById(R.id.ticket_date);
        TextView eventLocation = (TextView) view.findViewById(R.id.ticket_location);
        TextView eventPrice = (TextView) view.findViewById(R.id.ticket_price);

        eventName.setText(event.getEventName());
        eventDate.setText(event.getEventDate());
        eventLocation.setText(event.getEventLocation());
        eventPrice.setText(String.valueOf(price));

        Button add = (Button) view.findViewById(R.id.ticket_add);
        Button remove = (Button) view.findViewById(R.id.ticket_remove);
        final TextView ticketQuan = (TextView) view.findViewById(R.id.ticket_quan);

        ticketQuan.setText(String.valueOf(quantity));
        final TextView totalPrice = (TextView) view.findViewById(R.id.ticket_total);
        totalPrice.setText(String.valueOf(total));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticketQuan.setText(String.valueOf(++quantity));
                totalPrice.setText(String.valueOf(calcTotalPrice(quantity)));
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity>0)
                    ticketQuan.setText(String.valueOf(--quantity));
                totalPrice.setText(String.valueOf(calcTotalPrice(quantity)));
            }
        });

        Button buy = (Button) view.findViewById(R.id.ticket_buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                ToDo add to db insert into TABLE_TICKETS_EVENTS_USERS (FKEY_EVENT_ID,FKEY_USER_ID)
                                    values(select AUTOINCREMETN_COLUMN from TABLE_EVENTS where EVENTS_NAME = event.getEventName(),
                                    select AUTOINCREMETN_COLUMN from TABLE_USERS where USERS_EMAIL = user.getEmail()
                                     )
*/
                if (userDataSource.buyTicket(userId,event.getEventId(),Integer.valueOf(ticketQuan.getText().toString())))
                    Toast.makeText(getContext(), "YEI", Toast.LENGTH_SHORT).show();

            }
        });

        Button cancel = (Button) view.findViewById(R.id.ticket_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                getActivity().onBackPressed();
//                fm.popBackStack();
            }
        });
        return view;
    }



    double calcTotalPrice(int quant){
        return price*quant;
    }

    @Override
    public void getEvent(Event e, boolean isFav) {
        event = e;
    }
}
