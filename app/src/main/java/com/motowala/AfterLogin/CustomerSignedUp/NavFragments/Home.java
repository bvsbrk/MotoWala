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
    boolean activeServices = false;
    Typeface FONT_CHASING_HEARTS, FONT_HEADING;
    InternalNavHome internalNavHome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.cust_nav_home, container, false);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        internalNavHome = new InternalNavHome();
        ((TextView) layout.findViewById(R.id.choose_service)).setTypeface(FONT_HEADING);
        FONT_CHASING_HEARTS = Typeface.createFromAsset(getActivity().getAssets(), "fonts/chasing_hearts.ttf");
        FONT_HEADING = Typeface.createFromAsset(getActivity().getAssets(), "fonts/headings.ttf");
        if (!activeServices) {
            // TODO: 28-06-2017 No active services are there disable rec view here get active services from server
            getChildFragmentManager().beginTransaction().replace(R.id.replace_with_internal_home_frag, internalNavHome).commit();
        } else {
            // TODO: 28-06-2017 Don't forget to change layout parms of rec view to match parent and match parent as it is causing error if set to match parent when active services are zero

        }
    }
}
