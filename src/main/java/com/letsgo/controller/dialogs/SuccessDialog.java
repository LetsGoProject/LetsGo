package com.letsgo.controller.dialogs;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.letsgo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuccessDialog extends AppCompatDialogFragment {

    String evName;
    String tickets;

    TextView dialogEvName;
    TextView dialogTicketsQ;

    public SuccessDialog(){

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//    if activity is recreated(onCreate is called) or data is corrupted this prevents the dialog from appearing
        if (evName == null || tickets ==null)
            this.dismiss();

        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog successDialog = new Dialog(getContext());
        successDialog.setTitle("SUCCESS!");
        successDialog.setContentView(R.layout.fragment_success_dialog);
//        sets the background
//        successDialog.getWindow().setBackgroundDrawableResource(R.drawable.transparent_background_border);

        dialogEvName = (TextView) successDialog.findViewById(R.id.dialog_event_name);
        dialogTicketsQ = (TextView) successDialog.findViewById(R.id.dialog_ticket_quantity);
        dialogEvName.setText(evName);
        dialogTicketsQ.setText(tickets);

        Button ok = (Button) successDialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                successDialog.dismiss();
//                getFragmentManager().popBackStack();
            }
        });

        return successDialog;
    }



    public void getData(String name, String quant) {
        this.evName = name;
        this.tickets = quant;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        getFragmentManager().popBackStack();
    }
}
