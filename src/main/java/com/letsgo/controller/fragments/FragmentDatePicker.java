package com.letsgo.controller.fragments;



import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.letsgo.R;

import java.util.Calendar;

public class FragmentDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {



    public FragmentDatePicker() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year= c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this,year,month,day);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//        TextView show = (TextView) getActivity().findViewById(R.id.advanced_search_edt_before);
//        monthOfYear++;
//        String date = year + "-" + ((monthOfYear < 10)? "0": "") + monthOfYear + "-"+ ((dayOfMonth < 10)? "0": "") + dayOfMonth;
//        show.setText(date);
    }
}
