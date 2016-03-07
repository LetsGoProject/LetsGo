package com.letsgo.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.letsgo.R;
import com.letsgo.model.User;
import com.letsgo.model.daointerfaces.UserDao;
import com.letsgo.model.datasources.UserDataSource;

public class ActivityLogin extends AppCompatActivity {

    UserDao userDataSource;

    EditText edtLoginEmail;
    EditText edtLoginPass;

    Button btnLogin;
    Button btnregister;

    CheckBox chbKeepLogged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDataSource = new UserDataSource(this);
        ((UserDataSource)userDataSource).open();

        if (logInfo()!= null &&logInfo().length()>0)
        {
            Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
            User user = userDataSource.showUser(logInfo());
            intent.putExtra("user",user);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_login);



        edtLoginEmail = (EditText) findViewById(R.id.edt_Login_Email);
        edtLoginPass = (EditText) findViewById(R.id.edt_Login_Pass);

        chbKeepLogged = (CheckBox) findViewById(R.id.chb_keep_logged);

        btnLogin = (Button) findViewById(R.id.btn_Login_Login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = edtLoginEmail.getText().toString();
                String userPass = edtLoginPass.getText().toString();
                if (userDataSource.loginUser(userEmail,userPass)) {
//                    save isLoggedState and User ho is logged
                    if (chbKeepLogged.isChecked())
                        keepLogged(userEmail);
//                    TODO put user as extra vie parcelable
                    User user = userDataSource.showUser(userEmail);
                    Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(ActivityLogin.this, "Check input", Toast.LENGTH_SHORT).show();
            }
        });

        btnregister = (Button) findViewById(R.id.btn_Login_Register);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void keepLogged(String email){
//        TODO save user info (mail)
        SharedPreferences isLogged = PreferenceManager.getDefaultSharedPreferences(ActivityLogin.this);
        SharedPreferences.Editor editor = isLogged.edit();
        editor.putString("userMail",email);
        editor.commit();
    }

    private String logInfo(){
        SharedPreferences log = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        return log.getString("userMail", null);
    }

    @Override
    protected void onResume() {
        ((UserDataSource)userDataSource).open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        ((UserDataSource)userDataSource).close();
        super.onPause();
    }
}
