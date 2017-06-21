package com.motowala.AlertAndProgressDialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.motowala.R;

/**
 * Created by bk on 13-06-2017.
 */

public class SignupDecider extends DialogFragment implements RadioGroup.OnCheckedChangeListener {
    RadioGroup group;
    RadioButton garageOwner, customer;
    View layout;
    TextView status, submitButton;
    boolean isGarageOwner;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        layout = inflater.inflate(R.layout.garage_or_customer, null);
        group = (RadioGroup) layout.findViewById(R.id.radioGroup);
        status = (TextView) layout.findViewById(R.id.userInfo);
        submitButton = (TextView) layout.findViewById(R.id.submitButton);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout);
        group.setOnCheckedChangeListener(this);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userType = status.getText().toString();
            }
        });
        return builder.create();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        garageOwner = (RadioButton) radioGroup.findViewById(R.id.garager);
        if (garageOwner.isChecked()) {
            status.setText("Iam a Garage Owner");
            isGarageOwner = true;
        } else {
            status.setText("Iam an Individual Customer");
            isGarageOwner = false;
        }
    }
}

