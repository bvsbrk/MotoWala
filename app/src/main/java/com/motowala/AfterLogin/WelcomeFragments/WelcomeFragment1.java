package com.motowala.AfterLogin.WelcomeFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.motowala.AfterLogin.WelcomeActivity;
import com.motowala.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bk on 15-06-2017.
 * It is created for Wheelo
 */

public class WelcomeFragment1 extends Fragment {

    View v;
    CardView cardView1, cardView2, cardView3;
    CircleImageView userIcon;
    TextView userName1;
    RadioGroup radioGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.welcome_fragment_1, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cardView1 = (CardView) v.findViewById(R.id.cardView1);
        cardView2 = (CardView) v.findViewById(R.id.cardView2);
        cardView3 = (CardView) v.findViewById(R.id.cardView3);
        userIcon = (CircleImageView) v.findViewById(R.id.userIcon);
        userName1 = (TextView) v.findViewById(R.id.userName1);
        radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        ((WelcomeActivity) getActivity()).setImageIcon(userIcon);
        ((WelcomeActivity) getActivity()).setUserName(userName1);
        cardView1.animate().alpha(1f).setDuration(1000);
        cardView2.animate().alpha(1f).setDuration(1000).setStartDelay(500);
        cardView3.animate().alpha(1f).setDuration(1000).setStartDelay(500);
    }

    public String getSelectedRadio(Context context) {
        int id = radioGroup.getCheckedRadioButtonId();
        return id == R.id.individual ? getString(R.string.individual_customer) : getString(R.string.garage_owner);
    }
}
