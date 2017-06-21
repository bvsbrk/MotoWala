package com.motowala.ThirdPartyLogins.FacebookLogin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.motowala.AlertAndProgressDialogs.MyProgressDialog;
import com.motowala.LoginActivity;
import com.motowala.R;
import com.motowala.SignUp;

/**
 * Created by bk on 14-06-2017.
 */

public class FacebookLoginHandler {
    private Context context;
    private FirebaseAuth fb_auth;
    MyProgressDialog dialog;
    public FacebookLoginHandler(final Context context, CallbackManager callbackManager)
    {
        this.context=context;
        fb_auth=FirebaseAuth.getInstance();
        LoginButton fb_button = (LoginButton) ((LoginActivity) context).findViewById(R.id.fb_signup_button);
        fb_button.setReadPermissions("user_friends","email","public_profile");
        dialog=new MyProgressDialog(context,"Please wait...","Please wait...");
        fb_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                dialog.setMessage("Processing your data..!!");
                dialog.show();
                hadleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(context, "Login Process Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(context, "Error occured", Toast.LENGTH_SHORT).show();
                Log.d("Error facebook",error.toString());
            }
        });
    }
    public void hadleFacebookAccessToken(AccessToken token)
    {
        AuthCredential credential= FacebookAuthProvider.getCredential(token.getToken());
        fb_auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    dialog.dismiss();
                    FirebaseUser user=fb_auth.getCurrentUser();
                    String profileName=user.getDisplayName();
                    String email=user.getEmail();
                    String imageUrl=user.getPhotoUrl()+"";
                    String uid=user.getUid();
                    ((LoginActivity)context).loggedInWithFacebook(profileName,email,imageUrl,uid);
                }
                else {
                    dialog.dismiss();
                    ((LoginActivity)context).loginFailedWithFacebook();
                }
            }
        });
    }
}
