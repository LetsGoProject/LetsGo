package com.letsgo.controller.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.letsgo.R;
import com.letsgo.controller.controllerutils.AbstractFragment;
import com.letsgo.controller.dialogs.SuccessDialog;
import com.letsgo.model.Event;
import com.letsgo.model.daointerfaces.UserDao;
import com.letsgo.model.datasources.UserDataSource;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
        ((UserDataSource) userDataSource).open();
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
                if (quantity > 0)
                    ticketQuan.setText(String.valueOf(--quantity));
                totalPrice.setText(String.valueOf(calcTotalPrice(quantity)));
            }
        });

        Button buy = (Button) view.findViewById(R.id.ticket_buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                                    values(select AUTOINCREMETN_COLUMN from TABLE_EVENTS where EVENTS_NAME = event.getEventName(),
                                    select AUTOINCREMETN_COLUMN from TABLE_USERS where USERS_EMAIL = user.getEmail()
                                     )
*/
                if (quantity <= 0) {
                    Toast.makeText(getContext(), "Must buy at least 1 ticket", Toast.LENGTH_SHORT).show();
                    return;
                }
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());
                if (userDataSource.buyTicket(formattedDate, userId, event.getEventId(), quantity)) {
                    SuccessDialog successDialog = new SuccessDialog();
                    successDialog.getData(event.getEventName(), String.valueOf(quantity));
                    successDialog.show(getFragmentManager(), "success");
                }
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

    double calcTotalPrice(int quant) {
        return price * quant;
    }

    @Override
    public void getEvent(Event e, boolean isFav) {
        event = e;
    }
}
