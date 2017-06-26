package com.motowala.AfterLogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.motowala.AfterLogin.CustomerSignedUp.CustomerLoggedIn;
import com.motowala.AfterLogin.UserTypes.GaragerStuff.Garager;
import com.motowala.AfterLogin.UserTypes.IndividualStuff.IndividualCustomer;
import com.motowala.AfterLogin.UserTypes.IndividualStuff.IndividualFinalFragment;
import com.motowala.AfterLogin.UserTypes.IndividualStuff.IndividualFragment1;
import com.motowala.AfterLogin.WelcomeFragments.WelcomeFragment1;
import com.motowala.AlertAndProgressDialogs.GoogleMapLocationPicker;
import com.motowala.AlertAndProgressDialogs.MyProgressDialog;
import com.motowala.R;
import com.motowala.WriteToFirebase.WriteUserSignUp;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class WelcomeActivity extends AppCompatActivity {
    FragmentTransaction transaction;
    WelcomeFragment1 frag1;
    IndividualFragment1 frag2;
    IndividualFinalFragment frag3;
    FragmentManager manager;
    String imageUri;
    Button nextButton;
    int fragIdentifierCount = 0;
    MyProgressDialog dialog;
    String userName, userHost, userId, userEmail;
    IndividualCustomer customer;
    Garager garageOwner;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        preferences = getSharedPreferences(getString(R.string.shared_prefs_login_validator), MODE_PRIVATE);
        customer = new IndividualCustomer();
        garageOwner = new Garager();
        dialog = new MyProgressDialog(this, "Please Wait", "Fetching Cars...");
        nextButton = (Button) findViewById(R.id.nextButton);
        userName = preferences.getString(getString(R.string.user_name), "User");
        imageUri = preferences.getString(getString(R.string.user_image_uri), "image");
        userHost = preferences.getString(getString(R.string.user_host), "host");
        userId = preferences.getString(getString(R.string.user_db), "uid");
        userEmail = preferences.getString(getString(R.string.user_email), "email");

        /*
         * If user name is not given by firebase we use string upto @ in email
         */

        userName = userName.equals("User") ?
                userEmail.replace(
                        userEmail.substring(userEmail.indexOf('@'), userEmail.length()),
                        "") :
                userName;

         /*
          *Setting Values to object of Individual User customer
          * User type of customer is set in nextClick method
          */

        customer.name = userName;
        customer.imageLink = imageUri;
        customer.email = userEmail;
        customer.host = userHost;
        customer.userToken = userId;
        customer.userValidated = false;


        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        frag1 = new WelcomeFragment1();
        frag2 = new IndividualFragment1();
        frag3 = new IndividualFinalFragment();
        transaction.replace(R.id.replacable_container, frag1, "welcome_1");
        transaction.commit();
    }

    public List<String> getCarTitles() {
        dialog.show();
        final RequestQueue queue = Volley.newRequestQueue(this);
        final List<String> list = new ArrayList<>();
        JsonArrayRequest carBrands = new JsonArrayRequest("http://wheelo.co/cars_drop_down/get_cars.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        list.add(response.getString(i));
                    }
                    queue.stop();
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                queue.stop();
                dialog.dismiss();
                Toast.makeText(WelcomeActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(carBrands);
        return list;
    }

    public void setImageIcon(CircleImageView imageView) {
        Picasso.with(this).load(imageUri).into(imageView);
    }/*This function is called from WelcomeFragment1*/

    public void setUserName(TextView tv) {
        tv.setText(userName.toUpperCase());
    }

    public void setUserType(TextView tv) {
        tv.setText(customer.userType);
    }

    public void setMobile(TextView tv) {
        tv.setText(customer.mobile);
    }

    public void setCar(TextView tv) {
        tv.setText(customer.userCar.get(0));
    }

    public void setEmail(TextView tv) {
        tv.setText(customer.email);
    }

    @Override
    public void onBackPressed() {

    }

    public void nextClick(View view) {
        customer.userType = frag1.getSelectedRadio(this);
        if (fragIdentifierCount == 0 && customer.userType.equals(getString(R.string.individual_customer))) {
            transaction = manager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.replacable_container, frag2);
            transaction.commit();
            fragIdentifierCount++;
        } else if (fragIdentifierCount == 0 && customer.userType.equals(getString(R.string.garage_owner))) {


            Toast.makeText(this, "merged", Toast.LENGTH_SHORT).show();
            merge(); /* Merging userName, emailId, userIcon, Host, userId */

        } else if (fragIdentifierCount == 1) {
            List<String> details = frag2.getSelectedOptions(this);
            if (details.size() == 2 && customer.userType.equals(getString(R.string.individual_customer))) {
                customer.userCar.add(details.get(0));
                customer.mobile = details.get(1);
                transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.replacable_container, frag3);
                transaction.commit();
                fragIdentifierCount++;
                Button b = (Button) view;
                b.setText("FINISH");
            } else if (details.size() == 2 && customer.userType.equals(getString(R.string.garage_owner))) {

            }
        } else {
            // TODO: 17-06-2017 Send customer object data to firebase depending on usertype
            WriteUserSignUp signUp = new WriteUserSignUp(this, customer);
            editor = preferences.edit();
            editor.putString("processFinished", "customer");
            editor.apply();
        }
    }

    private void merge() {
        garageOwner.garagerName = customer.name;
        garageOwner.email = customer.email;
        garageOwner.host = customer.host;
        garageOwner.userToken = customer.userToken;
        garageOwner.userType = customer.userType;
        garageOwner.imageLink = customer.imageLink;
        garageOwner.userValidated = false;
    }

    public void showGoogleMapOfWelcomeActivity() {

        GoogleMapLocationPicker picker = new GoogleMapLocationPicker();
        picker.show(getFragmentManager(), "Map Fragment");

    }

    public void getUserCoordinates(Location location, Address address) {
        Toast.makeText(this, "received", Toast.LENGTH_SHORT).show();
        customer.latitude = location.getLatitude();
        customer.longitude = location.getLongitude();
        customer.cityName = address.getLocality();
        customer.featureName = address.getFeatureName();
    }

    public void userSignedUp() {
        startActivity(new Intent(this, CustomerLoggedIn.class));
    }
}

