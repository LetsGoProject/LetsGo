package com.letsgo.controller.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.letsgo.R;
import com.letsgo.controller.adapters.AdapterShowEvents;
import com.letsgo.model.Event;
import com.letsgo.model.daointerfaces.UserDao;
import com.letsgo.model.datasources.UserDataSource;

import java.util.ArrayList;

public class FragmentWatchlist extends Fragment {

    private ListView listWatchList;
    private UserDao userDataSource;
    private long userId;
    private Button clearWatchlist;
    private ArrayList<Event> watchlistData;
    private ArrayAdapter<Event> adapterWatchlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);

        userDataSource = new UserDataSource(getContext());
        ((UserDataSource) userDataSource).open();

        SharedPreferences getUserId = getActivity().getPreferences(Context.MODE_PRIVATE);
        userId = getUserId.getLong("user_id", -1);

        watchlistData = userDataSource.showWathclist(userId);
        listWatchList = (ListView) view.findViewById(R.id.list_view_watchlist);

        adapterWatchlist = new AdapterShowEvents(getContext(), watchlistData);
        listWatchList.setAdapter(adapterWatchlist);

        listWatchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                final String eventName = ((TextView) view.findViewById(R.id.event_list_name)).getText().toString();
                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to remove " + eventName + " from Watchlist?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        userDataSource.removeEventFromWatchlist(userId, eventName);
                        Animation slideRight = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);
                        Animation fadeMoveUp = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out_move_up);
                        final Animation moveUp = AnimationUtils.loadAnimation(getContext(), R.anim.move_up);
                        if (position > 0) {
                            view.startAnimation(slideRight);
                            if (listWatchList.getChildCount() == 2) {
                                setAnimationListener(position, slideRight);
                            }
                        } else
                            view.startAnimation(fadeMoveUp);
                        for (int i = position; i < listWatchList.getChildCount() - 1; i++) {
                            listWatchList.getChildAt(i + 1).startAnimation(moveUp);
                            setAnimationListener(position, moveUp);
                        }
                        if (listWatchList.getChildCount() <= 1) {
                            watchlistData.remove(position);
                            adapterWatchlist.notifyDataSetChanged();
                        }
                    }
                });
                adb.show();
            }
        });

        clearWatchlist = (Button) view.findViewById(R.id.btn_clear_all_watchlist);
        clearWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to remove all from Watchlist?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        userDataSource.removeAllFromWatchlist(userId);
                        watchlistData.clear();
                        Animation fadeOut = AnimationUtils.loadAnimation(getContext(),android.R.anim.fade_out);
                        listWatchList.startAnimation(fadeOut);
                        setAnimationListener(watchlistData.size(),fadeOut);// position doesn't matter - list is empty
                    }
                });
                adb.show();
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

    private void setAnimationListener(final int position, Animation anim) {
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if (!watchlistData.isEmpty())
                    watchlistData.remove(position);
                adapterWatchlist.notifyDataSetChanged();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
