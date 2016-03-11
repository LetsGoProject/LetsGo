package com.letsgo.controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.letsgo.R;
import com.letsgo.model.Event;
import com.letsgo.model.daointerfaces.EventDao;
import com.letsgo.model.datasources.EventDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petkata on 5.3.2016 г..
 */
public class AdapterShowEvents extends ArrayAdapter<Event> {

    private class CustomViewHolder{
        private TextView eventName;
        private TextView eventLocation;
        private TextView eventDate;
        private CheckBox isInFavs;
        CustomViewHolder(View v){
            eventName = (TextView) v.findViewById(R.id.event_list_name);
            eventDate = (TextView) v.findViewById(R.id.event_list_date);
            eventLocation = (TextView) v.findViewById(R.id.event_list_location);
            isInFavs = (CheckBox) v.findViewById(R.id.is_in_fav);
        }
    }

    private Context context;
    private ArrayList<Event> events;

//    TODO Move eventDataSource away
    private EventDao eventDataSource;
    private List<Long> listEventIds;


    public AdapterShowEvents(Context context, ArrayList<Event> events){
        super(context,R.layout.event_list_element,events);
        eventDataSource = new EventDataSource(getContext());
        ((EventDataSource)eventDataSource).open();
        this.context = context;
        this.events = events;
        listEventIds = new ArrayList<>();
        listEventIds = eventDataSource.selectAllFavEventsIds();
        ((EventDataSource)eventDataSource).close();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CustomViewHolder holder= null;
        if (row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.event_list_element,parent,false);
            holder = new CustomViewHolder(row);
            row.setTag(holder);
        }else{
            holder = (CustomViewHolder) row.getTag();
        }
        if (position % 2 == 0)
        {
            row.setBackgroundResource(R.color.list_blue);
        }

        else
        {
            row.setBackgroundResource(R.color.list_light_blue);
        }

        if (listEventIds.contains(events.get(position).getEventId())){
            row.setBackgroundResource(R.color.yellow);
            holder.isInFavs.setChecked(true);
        }
        holder.eventName.setText(events.get(position).getEventName());
        holder.eventDate.setText(events.get(position).getEventDate());
        holder.eventLocation.setText(events.get(position).getEventLocation());
        return row;
    }
}
