package com.letsgo.controller.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.letsgo.R;
import com.letsgo.controller.controllerutils.DateFormater;
import com.letsgo.model.Event;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Petkata on 13.3.2016 г..
 */
public class AdapterShowTickets extends RecyclerView.Adapter<AdapterShowTickets.CustomViewHolder>{

    private Activity activity;
    private ArrayList<Map <String,Map<Event,Integer>>> eventTicketDataSource;


    public AdapterShowTickets(Activity activity,ArrayList<Map <String,Map<Event,Integer>>> dataSource){
        this.activity = activity;
        this.eventTicketDataSource = dataSource;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.ticket_list_element,parent,false);
        CustomViewHolder holder = new CustomViewHolder(row);

        return holder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {

        String dateOfTicketPurchse = (String) eventTicketDataSource.get(position).keySet().toArray()[0];

        Map<Event,Integer> eventTicket = eventTicketDataSource.get(position).get(dateOfTicketPurchse);

        Event event = (Event) eventTicket.keySet().toArray()[0];

        String stringEventName = event.getEventName();
        String stringEventDate = event.getEventDate();

        String stringTicketQuantity = String.valueOf(eventTicket.get(event));

        holder.dateTicketBought.setText(DateFormater.from_yyyyMMddHHmmss_To_dMMMyyyyHHmmss(dateOfTicketPurchse));
        holder.eventName.setText(stringEventName);
        holder.eventDate.setText(DateFormater.from_yyyyMMdd_To_dMMMyyyy(stringEventDate));
        holder.eventTicketQuant.setText(stringTicketQuantity);
        holder.ticketCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.linearLayoutTicketBoughtInfo.getVisibility() == View.VISIBLE){
                    holder.linearLayoutTicketBoughtInfo.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.move_up));
                    holder.linearLayoutTicketBoughtInfo.setVisibility(View.INVISIBLE);
                }else {
                    holder.linearLayoutTicketBoughtInfo.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_down));
                    holder.linearLayoutTicketBoughtInfo.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventTicketDataSource.size();
    }

    protected class CustomViewHolder extends RecyclerView.ViewHolder{

        private CardView ticketCardView;

        private TextView eventName;
        private TextView eventDate;
        private TextView eventTicketQuant;
        private LinearLayout linearLayoutTicketBoughtInfo;
        private TextView dateTicketBought;

        CustomViewHolder(View view){
            super(view);

            ticketCardView = (CardView) view.findViewById(R.id.ticket_card_view);

            eventName = (TextView) view.findViewById(R.id.event_list_name);
            eventDate = (TextView) view.findViewById(R.id.event_list_date);
            eventTicketQuant = (TextView) view.findViewById(R.id.event_list_ticket_quant);
            linearLayoutTicketBoughtInfo = (LinearLayout) view.findViewById(R.id.ticket_bought_info);
            linearLayoutTicketBoughtInfo.setVisibility(View.INVISIBLE);
            dateTicketBought = (TextView) view.findViewById(R.id.date_ticket_bought);
        }
    }

}
