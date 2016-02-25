package com.letsgo.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.letsgo.R;
import com.letsgo.model.DBAdapter;

public class RegisterActivity extends AppCompatActivity {

    DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbAdapter = new DBAdapter(this);
    }

    private long registerUser(String username,String email, String password){
        long num = dbAdapter.addUser(String username,String email, String password);
        return num;
    }
}
