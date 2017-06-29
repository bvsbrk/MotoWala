package com.motowala.AfterLogin.CustomerSignedUp.NavFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.motowala.R;
import com.motowala.SetDefaults;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bk on 24-06-2017.
 * It is created for Wheelo
 */

public class Settings extends Fragment {
    View layout;
    CircleImageView imageView;
    TextView userName;
    TextView userEmail;
    SetDefaults defaults;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.cust_nav_settings, container, false);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        defaults = new SetDefaults(getActivity());
        imageView = (CircleImageView) layout.findViewById(R.id.settings_image_view);
        userName = (TextView) layout.findViewById(R.id.settings_user_name);
        userEmail = (TextView) layout.findViewById(R.id.settings_email_id);
        setDefaults();
    }

    private void setDefaults() {
        defaults.setUserImageIcon(imageView);
        defaults.setUserName(userName);
        defaults.setEmail(userEmail);
    }
}
