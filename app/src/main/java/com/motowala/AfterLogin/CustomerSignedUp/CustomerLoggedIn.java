package com.motowala.AfterLogin.CustomerSignedUp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.motowala.AfterLogin.CustomerSignedUp.NavFragments.AddCar;
import com.motowala.AfterLogin.CustomerSignedUp.NavFragments.BuySpares;
import com.motowala.AfterLogin.CustomerSignedUp.NavFragments.Garages;
import com.motowala.AfterLogin.CustomerSignedUp.NavFragments.Help;
import com.motowala.AfterLogin.CustomerSignedUp.NavFragments.History;
import com.motowala.AfterLogin.CustomerSignedUp.NavFragments.Home;
import com.motowala.AfterLogin.CustomerSignedUp.NavFragments.NavProfile;
import com.motowala.AfterLogin.CustomerSignedUp.NavFragments.Offers;
import com.motowala.AfterLogin.CustomerSignedUp.NavFragments.PayEmi;
import com.motowala.AfterLogin.CustomerSignedUp.NavFragments.RentCar;
import com.motowala.R;

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
    Home navHome;
    Offers navOffers;
    PayEmi navPayEmi;
    NavProfile navProfile;
    RentCar navRentCar;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_logged_in);

        navAddCar = new AddCar();
        navBuySpares = new BuySpares();
        navGarages = new Garages();
        navHelp = new Help();
        navHistory = new History();
        navHome = new Home();
        navOffers = new Offers();
        navPayEmi = new PayEmi();
        navProfile = new NavProfile();
        navRentCar = new RentCar();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.replace_with_cust_nav_frags,navHome);
        fragmentTransaction.commit();


        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        disableBottomNavItemShift(navigationView);
        navigationView.setOnNavigationItemSelectedListener(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Wheelo");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "WE ARE ONLINE", Snackbar.LENGTH_LONG)
                        .setAction("CHAT WITH US !!", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(CustomerLoggedIn.this,CustomerChatActivity.class));
                            }
                        }).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_logged_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Handle both navigation drawer and bottom navigation item clicks here
     */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_add_a_car) {

        } else if (id == R.id.nav_garages) {

        } else if (id == R.id.nav_buy_spares) {

        } else if (id == R.id.nav_pay_emi) {

        } else if (id == R.id.nav_rent_a_car) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.bottom_home) {

        } else if (id == R.id.bottom_offers) {

        } else if (id == R.id.bottom_history) {
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.replace_with_cust_nav_frags,navHistory);
            fragmentTransaction.commit();
        } else if (id == R.id.bottom_profile) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
     *  Disable Shift of bottom nav view
     */
    public void disableBottomNavItemShift(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }


}
