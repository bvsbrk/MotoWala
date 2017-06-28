package com.motowala.AfterLogin.CustomerSignedUp.NavFragments.HistoryRecView;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koteswarao on 28-06-2017.
 * ${CLASS}
 */


// TODO: 28-06-2017 add all the services booked to app internal database and get these details from there
public class HistoryData {
    List<HistoryListItem> listItems;
    Context context;
    String days[] = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};

    public HistoryData(Context context) {
        listItems = new ArrayList<>();
        this.context = context;
    }

    public List<HistoryListItem> getList() {
        for (int i = 0; i < 10; i++) {
            listItems.add(new HistoryListItem("10", days[i % 6], "Audi", "Car Wash", true));
        }
        return listItems;
    }
}
