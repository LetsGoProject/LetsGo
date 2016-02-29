package com.letsgo.controller;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Petkata on 25.2.2016 г..
 */
public class ShowToast {
    public static void show(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
