package com.motowala.AfterLogin.CustomerSignedUp.TabFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.motowala.R;

/**
 * Created by bk on 21-06-2017.
 * It is created for Wheelo
 */

public class TabFragment3 extends Fragment {
    View layout;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout=inflater.inflate(R.layout.customer_tab_3,container,false);
        return layout;
    }
}
