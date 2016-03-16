package com.letsgo.controller.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.letsgo.R;
import com.letsgo.model.User;
import com.letsgo.model.daointerfaces.UserDao;
import com.letsgo.model.datasources.UserDataSource;
import com.letsgo.model.utils.Constants;

public class ActivityRegister extends AppCompatActivity {

    UserDao userDataSource;

    EditText edtName;
    EditText edtEmail;
    EditText edtPass;
    EditText edtRePass;

    Button btnRegister;
    Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userDataSource = new UserDataSource(this);
        ((UserDataSource)userDataSource).open();
//        Delete db
//        Log.i("111111111", Arrays.toString(this.databaseList()));
//        this.deleteDatabase("LetsGo.db");
//        Log.i("22222222", Arrays.toString(this.databaseList()));

        edtName = (EditText) findViewById(R.id.edt_Nickname);
        edtEmail = (EditText) findViewById(R.id.edt_Register_Email);
        edtPass = (EditText) findViewById(R.id.edt_Register_Pass);
        edtRePass = (EditText) findViewById(R.id.edt_Register_Retype_Pass);

        btnRegister = (Button) findViewById(R.id.btn_Registration_Register);
        btnReturn = (Button) findViewById(R.id.btn_Registration_Login);

        registerUser(btnRegister);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
                startActivity(intent);
                finish();
            }
        });

        ((Button) findViewById(R.id.btn_Show_All)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityRegister.this, userDataSource.showAllUsers(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void registerUser(Button btn){

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidInput(edtName, edtPass, edtRePass)) {
                    User user = userDataSource.createUser(edtName.getText().toString(), edtEmail.getText().toString(), edtPass.getText().toString());

                    if (user.getUsername().equals(Constants.EXISTING_EMAIL)){
                        edtEmail.setError("Email already exists");
                        edtEmail.requestFocus();
                        Toast.makeText(ActivityRegister.this, "User with this email already exists", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (user.getUsername().equals(Constants.EXISTING_USERNAME)){
                        edtName.setError("Username already exists");
                        edtName.requestFocus();
                        Toast.makeText(ActivityRegister.this, "User with this name already taken", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (user.getUsername().equals("Could not add user")) {
                        Toast.makeText(ActivityRegister.this, "Could not register", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(ActivityRegister.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityRegister.this,ActivityMain.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean isValidInput(EditText username,EditText password, EditText rePass){
//        TODO validate email
        if (username.getText().toString().length()>2 && password.getText().toString().length()>=4 && password.getText().toString().equals(rePass.getText().toString()))
            return true;
        else {
            if (username.getText().toString().length()<3) {
                username.setError("Must be at least 3 characters long");
                username.requestFocus();
            }
            if (password.getText().toString().length()< 4) {
                password.setError("Must be at least 4 characters long");
                password.requestFocus();
            }
            if (!password.getText().toString().equals(rePass.getText().toString())) {
                rePass.setError("Must match password");
                rePass.requestFocus();
            }
            return false;
        }
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
