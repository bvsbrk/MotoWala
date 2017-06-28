package com.motowala.AfterLogin.CustomerSignedUp.NavFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.motowala.AfterLogin.CustomerSignedUp.NavFragments.HistoryRecView.HistoryAdapter;
import com.motowala.AfterLogin.CustomerSignedUp.NavFragments.HistoryRecView.HistoryData;
import com.motowala.AfterLogin.CustomerSignedUp.ServiceHistoryTabs;
import com.motowala.R;

/**
 * Created by bk on 23-06-2017.
 * It is created for Wheelo
 */

public class History extends Fragment {
    View layout;
    RecyclerView historyRecView;
    TextView filterOrders;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout=inflater.inflate(R.layout.cust_nav_history,container,false);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setUpHistoryRecView();
        filterOrders = (TextView) layout.findViewById(R.id.service_history_filter_orders);
        filterOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Comming Soon..", Toast.LENGTH_SHORT).show();
                // TODO: 28-06-2017 Add filter orders here
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    private void setUpHistoryRecView() {
        historyRecView = (RecyclerView) layout.findViewById(R.id.service_history_recview);
        HistoryData data = new HistoryData(getActivity());
        HistoryAdapter adapter = new HistoryAdapter(getActivity(), data.getList());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        historyRecView.setLayoutManager(manager);
        historyRecView.setAdapter(adapter);
    }

    public void showServiceHistoryTabs(int position) {
        startActivity(new Intent(getActivity(), ServiceHistoryTabs.class));
    }
}
