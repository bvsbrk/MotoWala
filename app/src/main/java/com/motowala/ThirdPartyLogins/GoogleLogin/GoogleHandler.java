package com.motowala.ThirdPartyLogins.GoogleLogin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.motowala.AlertAndProgressDialogs.MyProgressDialog;
import com.motowala.LoginActivity;
import com.motowala.R;

/**
 * Created by bk on 14-06-2017.
 * It is created for Wheelo
 */

public class GoogleHandler implements GoogleApiClient.OnConnectionFailedListener {
    private static GoogleApiClient apiClient;
    SignInButton google_signin;
    private Context context;
    private FirebaseAuth auth;
    MyProgressDialog dialog;
    public GoogleHandler(final Context context) {
        this.context = context;
        dialog=new MyProgressDialog(context,"Please wait","Processing your data..!!");
        auth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail().requestProfile().build();
        google_signin = (SignInButton) ((LoginActivity) context).findViewById(R.id.google_sign_in);
        apiClient = new GoogleApiClient.Builder(context).enableAutoManage((FragmentActivity) context, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        google_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
                int RC_SIGN_IN = 9001;
                ((LoginActivity) context).startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    public static void onStartFunction() {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(apiClient);
        if (opr.isDone()) {
            Log.d("See this", "Cached signIn");
            GoogleSignInResult result = opr.get();
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                }
            });
        }
    }

    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            Log.d("See account details", account.getDisplayName() + "\n" +
                    account.getEmail());
            firebaseAuthWithGoogle(account);
        } else {
            Toast.makeText(context, "You are not signed in", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        String userName=user.getDisplayName();
                        String email=user.getEmail();
                        String userId=user.getUid();
                        String photoUrl=user.getPhotoUrl()+"";
                        ((LoginActivity)context).loggedInWithGoogle(userName,email,userId,photoUrl);
                    }
                    else {
                        dialog.dismiss();
                        Toast.makeText(context, "Login Failed...", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    dialog.dismiss();
                    Toast.makeText(context, "Login Failed...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
