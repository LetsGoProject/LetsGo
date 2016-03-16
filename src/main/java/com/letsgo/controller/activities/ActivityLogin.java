package com.letsgo.controller.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.letsgo.R;
import com.letsgo.tasks.TaskLogin;
import com.letsgo.tasks.TaskStayLogged;

public class ActivityLogin extends AppCompatActivity {

    EditText edtLoginEmail;
    EditText edtLoginPass;

    Button btnLogin;
    Button btnregister;

    CheckBox chbKeepLogged;

    TaskLogin taskLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      Check if keep logged in is checked
        if (logInfo()!= null &&logInfo().length()>0)
        {
            new TaskStayLogged(this,logInfo()).execute();

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

                if (chbKeepLogged.isChecked())
                    keepLogged(userEmail);
                taskLogin = new TaskLogin(ActivityLogin.this,userEmail,userPass);
                taskLogin.execute();
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
//        ((UserDataSource)userDataSource).open();
        super.onResume();
    }

    @Override
    protected void onPause() {
//        ((UserDataSource)userDataSource).close();
        super.onPause();
    }



}
