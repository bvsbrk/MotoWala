package com.motowala.AfterLogin.UserTypes.IndividualStuff;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.motowala.AfterLogin.WelcomeActivity;
import com.motowala.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bk on 16-06-2017.
 * It is created for Wheelo
 */

public class IndividualFinalFragment extends Fragment implements View.OnClickListener {


    CircleImageView userIcon3;
    TextView userName3, email3, userType3, userCar3, mobile3;
    Button locationButton;
    View v;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.individual_final_fragment, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userIcon3 = (CircleImageView) v.findViewById(R.id.userIcon3);
        userName3 = (TextView) v.findViewById(R.id.userName3);
        email3 = (TextView) v.findViewById(R.id.email3);
        userType3 = (TextView) v.findViewById(R.id.userType3);
        userCar3 = (TextView) v.findViewById(R.id.carType3);
        mobile3 = (TextView) v.findViewById(R.id.mobile3);
        locationButton = (Button) v.findViewById(R.id.locationButton);
        locationButton.setOnClickListener(this);

        /*
         * Setting respective values to all fields here
         */

        ((WelcomeActivity) getActivity()).setImageIcon(userIcon3);
        ((WelcomeActivity) getActivity()).setUserName(userName3);
        ((WelcomeActivity) getActivity()).setUserType(userType3);
        ((WelcomeActivity) getActivity()).setCar(userCar3);
        ((WelcomeActivity) getActivity()).setMobile(mobile3);
        ((WelcomeActivity) getActivity()).setEmail(email3);

        /*
         * Handling Location Click here
         */
    }

    @Override
    public void onClick(View view) {
        showGoogleMapFromWelcomeActivity();
    }

    private void showGoogleMapFromWelcomeActivity() {
        ((WelcomeActivity) getActivity()).showGoogleMapOfWelcomeActivity();
    }
}
