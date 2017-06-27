package com.motowala.AfterLogin.CustomerSignedUp;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.motowala.AfterLogin.CustomerSignedUp.ServiceTabFragments.Details;
import com.motowala.AfterLogin.CustomerSignedUp.ServiceTabFragments.Invoice;
import com.motowala.AfterLogin.CustomerSignedUp.ServiceTabFragments.Progress;
import com.motowala.R;

public class ServiceHistoryTabs extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_history_tabs);
        initializeTablayoutAndToolbarAndViewpager();
    }


    class PageAdapter extends FragmentPagerAdapter {

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Details();
                case 1:
                    return new Progress();
                default:
                    return new Invoice();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Details";
                case 1:
                    return "Progress";
                default:
                    return "Invoice";
            }
        }
    }
    private void initializeTablayoutAndToolbarAndViewpager() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.temp_tab);
        viewPager = (ViewPager) findViewById(R.id.temp_view_pager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
    }
}
