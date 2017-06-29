package com.motowala;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by koteswarao on 29-06-2017.
 * ${CLASS}
 */

public class SetDefaults {
    Context context;
    SharedPreferences preferences;

    public SetDefaults(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(context.getString(R.string.shared_prefs_login_validator), Context.MODE_PRIVATE);
    }

    public void setUserImageIcon(CircleImageView circleImageView) {
        String uri = preferences.getString(context.getString(R.string.user_image_uri), "");
        Picasso.with(context).load(uri).into(circleImageView);
    }

    public void setUserName(TextView tv) {
        String userName = preferences.getString(context.getString(R.string.user_name), "");
        tv.setText(userName.toUpperCase());
    }

    public void setEmail(TextView tv) {
        String email = preferences.getString(context.getString(R.string.user_email), "");
        tv.setText(email);
    }
}
