package com.motowala.AfterLogin.CustomerSignedUp.NavFragments.OffersRecView;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koteswarao on 27-06-2017.
 * ${CLASS}
 */

public class OffersData {
    List<OffersListItem> listItems;
    Context context;
    String offerName = "RANDOM OFFER NAME", offerCarName = "BMW";
    boolean offerValid;

    public OffersData(Context context) {
        listItems = new ArrayList<>();
        this.context = context;
    }

    public List<OffersListItem> getList() {

        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0) offerValid = true;
            else offerValid = false;
            listItems.add(new OffersListItem(offerName, offerValid, offerCarName));
        }
        return listItems;
    }
}
