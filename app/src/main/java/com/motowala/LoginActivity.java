package com.motowala;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.motowala.AfterLogin.WelcomeActivity;
import com.motowala.ThirdPartyLogins.FacebookLogin.FacebookLoginHandler;
import com.motowala.ThirdPartyLogins.GoogleLogin.GoogleHandler;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    GoogleHandler handler;
    CallbackManager callbackManager;
    LoginButton fb_button;
    ImageView fbLoginImageView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);
        fbLoginImageView = (ImageView) findViewById(R.id.fb_login_img);
        fbLoginImageView.setOnClickListener(this);
        fb_button = (LoginButton) findViewById(R.id.fb_signup_button);
        handler = new GoogleHandler(this);
        callbackManager = CallbackManager.Factory.create();
        FacebookLoginHandler handler = new FacebookLoginHandler(this, callbackManager);
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleHandler.onStartFunction();
    } /* This is for Google Sign in Verification */

    public void loggedInWithFacebook(String profileName, String email, String imageUri, String userId) {
        preferences = getSharedPreferences(getResources().getString(R.string.shared_prefs_login_validator), MODE_PRIVATE);
        editor = preferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("host", "google");
        editor.putString("userEmail",email);
        editor.putString("userDb", userId);
        editor.putString("userName",profileName);
        editor.putString("imageUri",imageUri);
        editor.apply();
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    public void loggedInWithGoogle(String profileName, String email, String userId, String imageUri) {
        preferences = getSharedPreferences(getResources().getString(R.string.shared_prefs_login_validator), MODE_PRIVATE);
        editor = preferences.edit();
        editor.putBoolean(getString(R.string.is_user_logged_in), true);
        editor.putString(getString(R.string.user_host), "google");
        editor.putString(getString(R.string.user_email),email);
        editor.putString(getString(R.string.user_db), userId);
        editor.putString(getString(R.string.user_name),profileName);
        editor.putString(getString(R.string.user_image_uri),imageUri);
        editor.apply();
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    public void loginFailedWithFacebook() {
        Toast.makeText(this, "Failed to Log in With Facebook", Toast.LENGTH_SHORT).show();
    }

    public void loginFailedWithGoogle() {
        Toast.makeText(this, "Failed to Log in with Google", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int RC_SIGN_IN = 9001;
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handler.handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fb_login_img) {
            fb_button.performClick();
        }
    }/* For Custom Facebook Login Button */
}
