package com.letsgo.controller;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.letsgo.R;
import com.letsgo.model.daointerfaces.EventDao;
import com.letsgo.model.daointerfaces.LocationDao;
import com.letsgo.model.datasources.EventDataSource;
import com.letsgo.model.datasources.LocationDataSource;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdvancedSearch extends Fragment {

    EventDao eventDataSource;
    LocationDao locationDataSource;

    RadioButton radioBeforeDate;
    RadioButton radioAfterDate;
    RadioButton radioBetweenDates;
    Button btnClearDates;

    EditText edtFutureDate;
    EditText edtPastDate;

    EditText edtEventName;
    Spinner spinnerEventType;
    Spinner spinnerEventLocation;

    Button search;
    Button btnClearAll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advanced_search, container, false);

        eventDataSource = new EventDataSource(getContext());
        ((EventDataSource) eventDataSource).open();
        locationDataSource = new LocationDataSource(getContext());
        ((LocationDataSource) locationDataSource).open();

        radioBeforeDate = (RadioButton) view.findViewById(R.id.advanced_search_radio_before);
        radioAfterDate = (RadioButton) view.findViewById(R.id.advanced_search_radio_after);
        radioBetweenDates = (RadioButton) view.findViewById(R.id.advanced_search_radio_between);

        ArrayList<String> allTypes = eventDataSource.selectAllTypes();
        allTypes.add(0, "Choose");
        spinnerEventType = (Spinner) view.findViewById(R.id.advanced_search_spinner_type);
        ArrayAdapter<String> adapterTypes = new ArrayAdapter<String>(getContext(), R.layout.spinner_layout, R.id.spinner_element, allTypes);
        spinnerEventType.setAdapter(adapterTypes);

        ArrayList<String> locationNames = locationDataSource.selectAllLocationNames();
        locationNames.add(0, "Choose");
        spinnerEventLocation = (Spinner) view.findViewById(R.id.advanced_search_spinner_location);
        ArrayAdapter<String> adapterLocationNames = new ArrayAdapter<String>(getContext(), R.layout.spinner_layout, R.id.spinner_element, locationNames);
        spinnerEventLocation.setAdapter(adapterLocationNames);

        edtEventName = (EditText) view.findViewById(R.id.search_event_name);

        edtFutureDate = (EditText) view.findViewById(R.id.advanced_search_edt_future);
        edtPastDate = (EditText) view.findViewById(R.id.advanced_search_edt_past);

        edtFutureDate.setVisibility(View.GONE);
        edtPastDate.setVisibility(View.GONE);

        btnClearDates = (Button) view.findViewById(R.id.btn_clear_dates);
        btnClearDates.setVisibility(View.INVISIBLE);
        btnClearDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDates();
            }
        });

        setFocus(radioBeforeDate, new EditText[]{edtFutureDate});
        setFocus(radioAfterDate, new EditText[]{edtFutureDate});
        setFocus(radioBetweenDates, new EditText[]{edtFutureDate, edtPastDate});

        search = (Button) view.findViewById(R.id.advanced_search_search_btn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment searchResults = new FragmentSearchResults();
                Bundle bundle = new Bundle();
                bundle.putString("event_name", edtEventName.getText().toString());
                bundle.putString("event_type", spinnerEventType.getSelectedItem().toString());
                bundle.putString("event_location", spinnerEventLocation.getSelectedItem().toString());
                if (radioAfterDate.isChecked())
                    bundle.putString("afterDate", edtFutureDate.getText().toString());
                else if (radioBeforeDate.isChecked())
                    bundle.putString("beforeDate", edtFutureDate.getText().toString());
                else {
                    bundle.putString("afterDate", edtPastDate.getText().toString());
                    bundle.putString("beforeDate", edtFutureDate.getText().toString());
                }
                searchResults.setArguments(bundle);
                ft.replace(R.id.frag_container, searchResults, "search_results");
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        btnClearAll = (Button) view.findViewById(R.id.advanced_search_clear_all_btn);
        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllFields();
            }
        });

        return view;
    }

    private void clearAllFields(){
        edtEventName.setText("");
        spinnerEventType.setSelection(0);
        spinnerEventLocation.setSelection(0);
        clearDates();
    }

    private void clearDates() {
        edtFutureDate.setText("");
        edtPastDate.setText("");

        btnClearDates.setVisibility(View.GONE);
        edtFutureDate.setVisibility(View.GONE);
        edtPastDate.setVisibility(View.GONE);

        radioAfterDate.setChecked(false);
        radioBetweenDates.setChecked(false);
        radioBeforeDate.setChecked(false);
    }

    void setFocus(RadioButton radio, final EditText[] edts) {

        radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnClearDates.setVisibility(View.VISIBLE);
                    for (final EditText edt : edts) {
                        picDate(edt);
//                        edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                            @Override
//                            public void onFocusChange(View v, boolean hasFocus) {
//                                if (hasFocus)
//                                    picDate(edt);
//                            }
//                        });
                        if (edt.hasFocus()) {
                            edt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    picDate(edt);
                                }
                            });
                        }
                    }
                } else {
                    for (EditText edt : edts) {
                        edt.setText("");
                    }
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

    @Override
    public void onResume() {
        ((EventDataSource)eventDataSource).open();
        ((LocationDataSource) locationDataSource).open();
        super.onResume();
    }

    @Override
    public void onPause() {
        ((EventDataSource)eventDataSource).close();
        ((LocationDataSource) locationDataSource).close();
        super.onPause();
    }
}
