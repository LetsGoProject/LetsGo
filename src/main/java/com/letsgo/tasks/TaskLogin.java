package com.letsgo.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.letsgo.controller.activities.ActivityMain;
import com.letsgo.controller.dialogs.LoginDialog;
import com.letsgo.model.User;
import com.letsgo.model.daointerfaces.UserDao;
import com.letsgo.model.datasources.UserDataSource;

/**
 * Created by Petkata on 15.3.2016 г..
 */
public class TaskLogin extends AsyncTask<Void, Void, Boolean> {

    UserDao userDataSource;
    Context context;

    String userMail;
    String password;

    LoginDialog loading;

    public TaskLogin(Context context, String username, String password) {
        this.context = context;
        this.userMail = username;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {

        loading = new LoginDialog(context);
        loading.show();

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        userDataSource = new UserDataSource(context);
        ((UserDataSource) userDataSource).open();
        User user = null;
        if (userDataSource.loginUser(userMail, password)) {
            user = userDataSource.showUser(userMail);
            Intent intent = new Intent(context, ActivityMain.class);
            intent.putExtra("user", user);
            context.startActivity(intent);
            ((UserDataSource) userDataSource).close();
            loading.dismiss();
            ((Activity) context).finish();
            return true;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (!aBoolean) {
            Toast.makeText(context, "Check input", Toast.LENGTH_SHORT).show();
            loading.dismiss();
        }
    }
}
