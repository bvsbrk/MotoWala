package com.motowala.AfterLogin.CustomerSignedUp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.motowala.AfterLogin.CustomerSignedUp.NavFragments.*;
import com.motowala.R;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

import java.lang.reflect.Field;

public class CustomerLoggedIn extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView navigationView;
    int exitCount = 0;

    AddCar navAddCar;
    BuySpares navBuySpares;
    Garages navGarages;
    Help navHelp;
    History navHistory;
    Offers navOffers;
    PayEmi navPayEmi;
    NavProfile navProfile;
    RentCar navRentCar;
    Home navHome;
    Settings navSettings;


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String imageUri, userName, userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_logged_in);

        initializeAllNavClasses();
        setUpFragManager();
        initializeAllBasicComponents();
        initializeFab();
        getFromSharedPrefs();
        setDefaults();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (exitCount == 0) {
                Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();
                exitCount += 1;
            } else {
                finishAffinity();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customer_logged_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            performFragTransaction(navSettings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * Handle both navigation drawer and bottom navigation item clicks here
     */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_add_a_car) {
            performFragTransaction(navAddCar);
        } else if (id == R.id.nav_garages) {
            performFragTransaction(navGarages);
        } else if (id == R.id.nav_buy_spares) {
            performFragTransaction(navBuySpares);
        } else if (id == R.id.nav_pay_emi) {
            performFragTransaction(navPayEmi);
        } else if (id == R.id.nav_rent_a_car) {
            performFragTransaction(navRentCar);
        } else if (id == R.id.nav_help) {
            performFragTransaction(navHelp);
        } else if (id == R.id.bottom_offers) {
            performFragTransaction(navOffers);
        } else if (id == R.id.bottom_history) {
            performFragTransaction(navHistory);
        } else if (id == R.id.bottom_profile) {
            performFragTransaction(navProfile);
        } else if (id == R.id.bottom_home) {
            performFragTransaction(navHome);
        } else if (id == R.id.nav_settings) {
            performFragTransaction(navSettings);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void performFragTransaction(Fragment fragToReplace) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.replace_with_cust_nav_frags, fragToReplace).commit();
    }


    public void disableBottomNavItemShift(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    private void initializeAllNavClasses() {
        navAddCar = new AddCar();
        navBuySpares = new BuySpares();
        navGarages = new Garages();
        navHelp = new Help();
        navHistory = new History();
        navOffers = new Offers();
        navPayEmi = new PayEmi();
        navProfile = new NavProfile();
        navRentCar = new RentCar();
        navHome = new Home();
        navSettings = new Settings();
    }

    private void setUpFragManager() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.replace_with_cust_nav_frags, new Home()).commit();
    }

    private void initializeAllBasicComponents() {
        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        disableBottomNavItemShift(navigationView);
        navigationView.setOnNavigationItemSelectedListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Wheelo");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initializeFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "WE ARE ONLINE", Snackbar.LENGTH_LONG)
                        .setAction("CHAT WITH US !!", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(CustomerLoggedIn.this, CustomerChatActivity.class));
                            }
                        }).show();
            }
        });
    }

    public void handleHistoryRecView(int position) {
        /*
         *  Take the recycler view position from HistoryAdapter and pass it to its
         *  respectieve fragment to perform action there
         *  Fragment is the administrator of actions done by rec view
         */
        navHistory.showServiceHistoryTabs(position);
    }

    public void handleOffersRecView(int position) {
        /*
         *  Take the recycler view position from OffersAdapter and pass it to its
         *  respectieve fragment to perform action there
         *  Fragment is the administrator of actions done by rec view
         */
        navOffers.showOfferDetails(position);
    }

    public void setDefaults() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        TextView userNameTv = (TextView) hView.findViewById(R.id.customer_nav_user_name);
        TextView userEmailTv = (TextView) hView.findViewById(R.id.customer_nav_user_email);
        CircleImageView userImageView = (CircleImageView) hView.findViewById(R.id.navUserImage);
        userEmailTv.setText(userEmail);
        userNameTv.setText(userName);
        Picasso.with(this).load(imageUri).into(userImageView);
    }

    public void getFromSharedPrefs() {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.shared_prefs_login_validator), MODE_PRIVATE);
        userName = preferences.getString(getString(R.string.user_name), "");
        userEmail = preferences.getString(getString(R.string.user_email), "");
        imageUri = preferences.getString(getString(R.string.user_image_uri), "");
    }

}
