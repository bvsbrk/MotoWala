package com.motowala.AfterLogin.CustomerSignedUp.ServiceTabFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.motowala.AfterLogin.CustomerSignedUp.ServiceTabFragments.ServiceRecView.Data;
import com.motowala.AfterLogin.CustomerSignedUp.ServiceTabFragments.ServiceRecView.ListItem;
import com.motowala.AfterLogin.CustomerSignedUp.ServiceTabFragments.ServiceRecView.ProgressAdapter;
import com.motowala.R;

import java.util.List;

/**
 * Created by bk on 21-06-2017.
 * It is created for Wheelo
 */

public class Progress extends Fragment {
    View layout;
    RecyclerView progressRecyclerView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressRecyclerView= (RecyclerView) layout.findViewById(R.id.progress_rec_view);
        initiateRecView();
    }

    private void initiateRecView() {
        Data data=new Data(getActivity());
        List<ListItem> list=data.getListItems();
        ProgressAdapter adapter=new ProgressAdapter(list,getActivity());
        progressRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        progressRecyclerView.setLayoutManager(manager);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.customer_progress_tab, container, false);
        return layout;
    }
}
