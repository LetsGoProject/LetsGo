package com.letsgo.controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.letsgo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdvancedSearch extends Fragment {

    RadioButton radioBeforeDate;
    RadioButton radioAfterDate;
    RadioButton radioBetweenDates;

    EditText edtBeforeDate;
    EditText edtAfterDate;
    EditText edtFutureDate;
    EditText edtPastDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advanced_search, container, false);

        radioBeforeDate = (RadioButton) view.findViewById(R.id.advanced_search_radio_before);
        radioAfterDate = (RadioButton) view.findViewById(R.id.advanced_search_radio_after);
        radioBetweenDates = (RadioButton) view.findViewById(R.id.advanced_search_radio_between);

        edtBeforeDate = (EditText) view.findViewById(R.id.advanced_search_edt_before);
        edtAfterDate = (EditText) view.findViewById(R.id.advanced_search_edt_after);
        edtFutureDate = (EditText) view.findViewById(R.id.advanced_search_edt_future);
        edtPastDate = (EditText) view.findViewById(R.id.advanced_search_edt_past);

        setFocus(radioBeforeDate,new EditText[]{edtBeforeDate});
        setFocus(radioAfterDate,new EditText[]{edtAfterDate});
        setFocus(radioBetweenDates,new EditText[]{edtFutureDate,edtPastDate});

        return view;
    }

    void setFocus(RadioButton radio,final EditText[] edts){
        radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (EditText edt : edts) {
                        edt.setEnabled(true);
                        edt.requestFocus();
                    }
                } else{
                    for (EditText edt : edts) {
                        edt.setEnabled(false);
                    }
                }
            }
        });
    }
}
