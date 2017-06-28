package com.motowala.AfterLogin.CustomerSignedUp.NavFragments.OffersRecView;

/**
 * Created by koteswarao on 27-06-2017.
 * ${CLASS}
 */

public class OffersListItem {
    public String offerName;
    public boolean offerValid;
    public String offerCarname;

    public OffersListItem(String offerName, boolean offerValid, String offerCarName) {
        this.offerName = offerName;
        this.offerValid = offerValid;
        this.offerCarname = offerCarName;
    }
}
