package com.letsgo.controller.controllerutils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Petkata on 13.3.2016 г..
 */
public class DateFormater {

    public static String from_yyyyMMdd_To_dMMMyyyy(String dateToFormat){
        if (dateToFormat == null || dateToFormat.equals(""))
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(dateToFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf = new SimpleDateFormat("d MMM,yyyy");
        return sdf.format(date);
    }

    public static String from_yyyyMMddHHmmss_To_dMMMyyyyHHmmss(String dateToFormat){
        if (dateToFormat == null || dateToFormat.equals(""))
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(dateToFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf = new SimpleDateFormat("d MMM,yyyy - HH:mm:ss");
        return sdf.format(date);
    }

    public static String from_dMMMyyyy_To_yyyyMMdd(String dateToFormat){
        if (dateToFormat == null || dateToFormat.equals(""))
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM,yyyy");
        Date date = new Date();
        try {
            date = sdf.parse(dateToFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

}
