package com.letsgo.tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.letsgo.controller.activities.ActivityMain;
import com.letsgo.model.User;
import com.letsgo.model.daointerfaces.UserDao;
import com.letsgo.model.datasources.UserDataSource;

/**
 * Created by Petkata on 15.3.2016 г..
 */
public class TaskStayLogged extends AsyncTask<Void,Void,Void>{

    Activity activity;
    String username;
    UserDao userDataSource;

    public TaskStayLogged(Activity act,String username){
        this.activity = act;
        this.username = username;
    }

    @Override
    protected Void doInBackground(Void... params) {
        userDataSource = new UserDataSource(activity);
        ((UserDataSource)userDataSource).open();

        Intent intent = new Intent(activity, ActivityMain.class);
        User user = userDataSource.showUser(username);
        intent.putExtra("user",user);
        activity.startActivity(intent);
        activity.finish();
        return null;
    }
}
