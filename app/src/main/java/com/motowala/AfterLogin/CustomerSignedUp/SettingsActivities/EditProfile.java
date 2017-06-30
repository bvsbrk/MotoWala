package com.motowala.AfterLogin.CustomerSignedUp.SettingsActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import com.motowala.R;
import com.motowala.SetDefaults;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    CircleImageView userImage;
    EditText userName, userEmail, userMobile;
    SetDefaults defaults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        defaults = new SetDefaults(this);
        userImage = (CircleImageView) findViewById(R.id.edit_profile_user_image);
        userName = (EditText) findViewById(R.id.edit_profile_userName);
        userEmail = (EditText) findViewById(R.id.edit_profile_email);
        userMobile = (EditText) findViewById(R.id.edit_profile_usermobile);
        setDefaults();
    }

    private void setDefaults() {
        defaults.setUserImageIcon(userImage);
        defaults.setEmail(userEmail);
        defaults.setUserName(userName);
        defaults.setMobile(userMobile);
    }
}
