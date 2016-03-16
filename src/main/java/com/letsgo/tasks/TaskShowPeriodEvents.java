package com.letsgo.tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.letsgo.R;
import com.letsgo.controller.controllerutils.Communicator;
import com.letsgo.controller.fragments.FragmentSingleEvent;
import com.letsgo.controller.adapters.AdapterShowEvents;
import com.letsgo.model.Event;
import com.letsgo.model.daointerfaces.EventDao;
import com.letsgo.model.datasources.EventDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petkata on 15.3.2016 г..
 */
public class TaskShowPeriodEvents extends AsyncTask<Void, Void, ArrayAdapter<Event>> {

    Context context;
    EventDao eventDataSource;
    ListView listPeriodEvents;
    List<Event> periodEventsDataSource;
    String period;

    public TaskShowPeriodEvents(Context c, String period) {
        context = c;
        this.period = period;
    }

    @Override
    protected ArrayAdapter<Event> doInBackground(Void... params) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        eventDataSource = new EventDataSource(context);
        ((EventDataSource) eventDataSource).open();

        periodEventsDataSource = eventDataSource.showEvents(period);

        return new AdapterShowEvents(context, (ArrayList<Event>) periodEventsDataSource);
    }

    @Override
    protected void onPostExecute(final ArrayAdapter<Event> adapterPastEvents) {
//        ProgressBar
        ProgressBar pastPB = (ProgressBar) ((Activity)context).findViewById(R.id.progressBar_all_past);
        ProgressBar upcomingPB = (ProgressBar) ((Activity)context).findViewById(R.id.progressBar_all_upcoming);
        if (period.equalsIgnoreCase("past") && pastPB != null)
            pastPB.setVisibility(View.GONE);
        else if (upcomingPB != null)
            upcomingPB.setVisibility(View.GONE);

//        ListViews
        if (period.equalsIgnoreCase("past"))
            listPeriodEvents = (ListView) ((Activity) context).findViewById(R.id.all_events_past_list);
        else
            listPeriodEvents = (ListView) ((Activity) context).findViewById(R.id.all_events_upcoming_list);
        Animation fadeIn = AnimationUtils.loadAnimation(context,android.R.anim.fade_in);
        fadeIn.setDuration(750);
        if (listPeriodEvents!=null) {
            listPeriodEvents.startAnimation(fadeIn);
            listPeriodEvents.setAdapter(adapterPastEvents);
            listPeriodEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CheckBox isFav = (CheckBox) view.findViewById(R.id.is_in_fav);
                    view.setBackgroundResource(R.color.indigo);
                    ((Communicator) context).sendEvent(new FragmentSingleEvent(), periodEventsDataSource.get(position), isFav.isChecked());
                }
            });
        }
    }
}
