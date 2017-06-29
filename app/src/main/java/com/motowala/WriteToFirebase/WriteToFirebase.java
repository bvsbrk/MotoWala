package com.motowala.WriteToFirebase;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.motowala.AfterLogin.UserTypes.IndividualStuff.IndividualCustomer;
import com.motowala.AfterLogin.WelcomeActivity;
import com.motowala.AlertAndProgressDialogs.MyProgressDialog;
import com.motowala.Config;
import com.motowala.CustomerDatabases.CustomerDatabase;
import com.motowala.R;

/**
 * Created by bk on 20-06-2017.
 * It is created for Wheelo
 */

public class WriteToFirebase {
    public final String firstLevelChild = "individual_users";
    public final String secondLevelChild = "users";
    public IndividualCustomer customer;
    MyProgressDialog dialog;
    Context context;
    private FirebaseDatabase wheelo_main;
    private DatabaseReference databaseReference;

    public WriteToFirebase(Context context) {
        this.context = context;
        wheelo_main = FirebaseDatabase.getInstance();
        databaseReference = wheelo_main.getReference();
        dialog = new MyProgressDialog(context, "Just a moment..", "Please wait..");
        dialog.show();
    }

    public void writeUserSignUp(IndividualCustomer customer) {
        this.customer = customer;
        final String thirdLevelChildUserToken = customer.userToken;
        databaseReference.child(firstLevelChild).child(secondLevelChild).child(thirdLevelChildUserToken).
                setValue(customer, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        dialog.dismiss();
                        ((WelcomeActivity) context).userSignedUp();
                    }
                });
    }

    public void addNewCarToCustomer(String car) {
        addThisNewCarToDatabase(car);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            final SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.shared_prefs_login_validator), Context.MODE_PRIVATE);
            final int noOfCars = preferences.getInt(context.getString(R.string.customer_car_count), 1) + 1;
            databaseReference.child(firstLevelChild).
                    child(secondLevelChild)
                    .child(userId)
                    .child("userCar")
                    .child(noOfCars + "")
                    .setValue(car,
                            new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    preferences.edit().putInt(context.getString(R.string.customer_car_count), noOfCars).apply();
                                    dialog.dismiss();
                                }
                            });
        } else {
            // TODO: 29-06-2017 User is not properly logged in log user out and login again
        }
    }

    private void addThisNewCarToDatabase(final String car) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                Config config = new Config(context);
                CustomerDatabase database = new CustomerDatabase(context,
                        config.databaseName,
                        config.databaseNewVersion,
                        config.databaseOldVersion,
                        config.carTablesName);
                SQLiteDatabase db = database.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(config.carsTableCarColumn, car);
                long num = db.insert(config.carTablesName, null, values);
                Log.d("Number of rows", num + " ");
                db.close();
            }
        })).start();
    }
}
