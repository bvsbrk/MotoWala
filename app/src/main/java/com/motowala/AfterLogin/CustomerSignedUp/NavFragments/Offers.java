package com.motowala.AfterLogin.CustomerSignedUp.NavFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.motowala.AfterLogin.CustomerSignedUp.NavFragments.OffersRecView.OffersAdapter;
import com.motowala.AfterLogin.CustomerSignedUp.NavFragments.OffersRecView.OffersData;
import com.motowala.R;

/**
 * Created by bk on 23-06-2017.
 * It is created for Wheelo
 */

public class Offers extends Fragment {
    View layout;
    RecyclerView offersRecView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.cust_nav_offers, container, false);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        offersRecView = (RecyclerView) layout.findViewById(R.id.offers_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        OffersData data = new OffersData(getActivity());
        OffersAdapter adapter = new OffersAdapter(data.getList(), getActivity());
        offersRecView.setLayoutManager(manager);
        offersRecView.setAdapter(adapter);
    }

    public void showOfferDetails(int position) {
        //// TODO: 28-06-2017 Show offer details here this came from clicking offers rec view
        Toast.makeText(getActivity(), position + " ", Toast.LENGTH_SHORT).show();
    }
}
