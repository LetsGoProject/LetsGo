package com.letsgo.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.letsgo.R;
import com.letsgo.model.DBAdapter;

public class LoginActivity extends AppCompatActivity {

    EditText edtLoginEmail;
    EditText edtLoginPass;

    Button btnLogin;
    Button btnregister;

    DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbAdapter = new DBAdapter(this);

        edtLoginEmail = (EditText) findViewById(R.id.edt_Login_Email);
        edtLoginPass = (EditText) findViewById(R.id.edt_Login_Pass);

        btnLogin = (Button) findViewById(R.id.btn_Login_Login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = dbAdapter.login(edtLoginEmail.getText().toString(),edtLoginPass.getText().toString());
                Log.i("00000000",String.valueOf(userEmail));
                if (userEmail!= null && userEmail.equals(edtLoginEmail.getText().toString())) {
                    Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    ShowToast.show(LoginActivity.this,"Check input");
            }
        });

        btnregister = (Button) findViewById(R.id.btn_Login_Register);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
//                finish();
            }
        });
    }
}
