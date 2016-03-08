package com.letsgo.controller;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.letsgo.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdvancedSearch extends Fragment {

    RadioButton radioBeforeDate;
    RadioButton radioAfterDate;
    RadioButton radioBetweenDates;

    EditText edtFutureDate;
    EditText edtPastDate;

    Button search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advanced_search, container, false);

        radioBeforeDate = (RadioButton) view.findViewById(R.id.advanced_search_radio_before);
        radioAfterDate = (RadioButton) view.findViewById(R.id.advanced_search_radio_after);
        radioBetweenDates = (RadioButton) view.findViewById(R.id.advanced_search_radio_between);

        edtFutureDate = (EditText) view.findViewById(R.id.advanced_search_edt_future);
        edtPastDate = (EditText) view.findViewById(R.id.advanced_search_edt_past);

//        edtBeforeDate.setVisibility(View.GONE);
//        edtAfterDate.setVisibility(View.GONE);
        edtFutureDate.setVisibility(View.GONE);

        edtPastDate.setVisibility(View.GONE);


        search = (Button) view.findViewById(R.id.advanced_search_search_btn);

        setFocus(radioBeforeDate, new EditText[]{edtFutureDate});
        setFocus(radioAfterDate, new EditText[]{edtFutureDate});
        setFocus(radioBetweenDates, new EditText[]{edtFutureDate, edtPastDate});

        return view;
    }

    void setFocus(RadioButton radio, final EditText[] edts) {

        radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (final EditText edt : edts) {
                        picDate(edt);
                    }
                } else {
                    if (edts.length > 1)
                        edts[1].setVisibility(View.GONE);
                }
            }
        });
    }

    private void picDate(final EditText edt) {
        class FragmentDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
            public FragmentDatePicker() {
                // Required empty public constructor
            }

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                return new DatePickerDialog(getActivity(), this, year, month, day);
            }

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                String date = year + "-" + ((monthOfYear < 10) ? "0" : "") + monthOfYear + "-" + ((dayOfMonth < 10) ? "0" : "") + dayOfMonth;
                edt.setText(date);
            }
        }
        edt.setKeyListener(null);
        edt.setVisibility(View.VISIBLE);
        edt.requestFocus();
        if (edt.isFocused()) {
            DialogFragment dateFragment = new FragmentDatePicker();
            dateFragment.show(getFragmentManager(), "datePicker");
        }
    }
}
