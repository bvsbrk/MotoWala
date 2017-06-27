package com.motowala.AfterLogin.CustomerSignedUp;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
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
import android.widget.Toast;
import com.motowala.AfterLogin.CustomerSignedUp.NavFragments.*;
import com.motowala.R;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Util;

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
    BoomMenuButton boomMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_logged_in);

        initializeAllNavClasses();
        setUpFragManagersAndTransactions();
        initializeAllBasicComponents();
        initializeFabAndBoomButtons();

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

        // TODO: 27-06-2017 Create a settings activity for customer
        int id = item.getItemId();
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
            commitFragTransaction(navGarages);
        } else if (id == R.id.nav_buy_spares) {
            commitFragTransaction(navBuySpares);
        } else if (id == R.id.nav_pay_emi) {
            commitFragTransaction(navPayEmi);
        } else if (id == R.id.nav_rent_a_car) {
            commitFragTransaction(navRentCar);
        } else if (id == R.id.nav_help) {
            commitFragTransaction(navHelp);
        } else if (id == R.id.bottom_home) {
            commitFragTransaction(navHome);
        } else if (id == R.id.bottom_offers) {
            commitFragTransaction(navOffers);
        } else if (id == R.id.bottom_history) {
            commitFragTransaction(navHistory);
        } else if (id == R.id.bottom_profile) {
            commitFragTransaction(navProfile);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void commitFragTransaction(Fragment fragToReplace) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.replace_with_cust_nav_frags, fragToReplace).commit();
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
        navHome = new Home();
        navOffers = new Offers();
        navPayEmi = new PayEmi();
        navProfile = new NavProfile();
        navRentCar = new RentCar();
    }

    private void setUpFragManagersAndTransactions() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.replace_with_cust_nav_frags, navHome);
        fragmentTransaction.commit();
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

    private void initializeFabAndBoomButtons() {
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
        boomMenuButton=(BoomMenuButton)findViewById(R.id.boom_button);
        for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalImageRes(R.drawable.full_circle);

            boomMenuButton.addBuilder(builder);
        }
    }
}
