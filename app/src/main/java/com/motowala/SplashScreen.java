package com.motowala;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.motowala.AfterLogin.CustomerSignedUp.CustomerLoggedIn;
import com.motowala.AfterLogin.WelcomeActivity;

public class SplashScreen extends AppCompatActivity {
    TextView splashText;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        preferences = getSharedPreferences(getResources().getString(R.string.shared_prefs_login_validator), MODE_PRIVATE);
        final boolean isValidated = preferences.getBoolean(getString(R.string.is_user_logged_in), false);
        final String finished = preferences.getString(getString(R.string.is_process_finished), "notFinished");
        splashText = (TextView) findViewById(R.id.splash_text);
        splashText.animate().alpha((float) 0).setDuration(2000);
        splashText.animate().scaleXBy((float) 3).scaleYBy((float) 3).setDuration(2000);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    switch (finished) {
                        case "customer":
                            startActivity(new Intent(SplashScreen.this, CustomerLoggedIn.class));
                            break;
                        case "garager":
                            break;
                        default:
                            if (isValidated) {
                                startActivity(new Intent(SplashScreen.this, WelcomeActivity.class));
                            } else {
                                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                            }
                            break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
// TODO: 21-06-2017 add permission check for first time here and for second time at WelcomeActivity
// TODO: Last fragment place