package com.letsgo.controller;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.letsgo.R;
import com.letsgo.model.daointerfaces.UserDao;
import com.letsgo.model.datasources.UserDataSource;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEditProfile extends Fragment {

    UserDao userDataSource;

    long userId;
    EditText edtUsername;

    Button btnChange;

    Communicator rootContext;

    @Override
    public void onAttach(Context context) {
        this.rootContext = (Communicator) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        userDataSource = new UserDataSource(getContext());
        ((UserDataSource)userDataSource).open();

        SharedPreferences getUserId = getActivity().getPreferences(Context.MODE_PRIVATE);
        userId = getUserId.getLong("user_id", -1);

        edtUsername = (EditText) view.findViewById(R.id.profile_edit_username);

        btnChange = (Button) view.findViewById(R.id.profile_change);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = edtUsername.getText().toString();
                if (newUsername.length()<3) {
                    edtUsername.setError("Must be at least 3 characters long");
                    edtUsername.requestFocus();
                }
                else{
                    if (userDataSource.editUsername(userId,newUsername)){
                        rootContext.getUsername(newUsername);
                        Toast.makeText(getContext(), "CHANGET SUCCESS", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        return view;
    }

}
