package com.motowala.AfterLogin.CustomerSignedUp.NavFragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.motowala.R;

/**
 * Created by bk on 23-06-2017.
 * It is created for Wheelo
 */

public class Home extends Fragment {
    View layout;

    Typeface FONT_CHASING_HEARTS,FONT_HEADING;
    TextView todayText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout=inflater.inflate(R.layout.cust_nav_home,container,false);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        todayText=(TextView)layout.findViewById(R.id.todayText);
        ((TextView)layout.findViewById(R.id.choose_service)).setTypeface(FONT_HEADING);
        FONT_CHASING_HEARTS = Typeface.createFromAsset(getActivity().getAssets(),"fonts/chasing_hearts.ttf");
        FONT_HEADING=Typeface.createFromAsset(getActivity().getAssets(),"fonts/headings.ttf");
        todayText.setTypeface(FONT_CHASING_HEARTS);
    }
}
