package com.motowala.WriteToFirebase;

import android.content.Context;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.motowala.AfterLogin.UserTypes.IndividualStuff.IndividualCustomer;
import com.motowala.AfterLogin.WelcomeActivity;
import com.motowala.AlertAndProgressDialogs.MyProgressDialog;

/**
 * Created by bk on 20-06-2017.
 * It is created for Wheelo
 */

public class WriteUserSignUp implements DatabaseReference.CompletionListener {
    MyProgressDialog dialog;
    private FirebaseDatabase wheelo_main;
    Context context;
    public IndividualCustomer customer;
    public final String firstLevelChild="individual_users";
    public final String secondLevelChild="users";
    private DatabaseReference databaseReference;
    public WriteUserSignUp(Context context,IndividualCustomer customer)
    {
        this.context=context;
        dialog=new MyProgressDialog(context,"Just a moment..","Please wait..");
        dialog.show();
        wheelo_main=FirebaseDatabase.getInstance();
        databaseReference=wheelo_main.getReference();
        this.customer=customer;
        final String thirdLevelChildUserToken=customer.userToken;
        databaseReference.child(firstLevelChild).child(secondLevelChild).child(thirdLevelChildUserToken).
                setValue(customer,this);
    }

    @Override
    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
        dialog.dismiss();
        ((WelcomeActivity) context).userSignedUp();
    }
}
