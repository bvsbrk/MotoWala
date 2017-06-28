package com.motowala.AfterLogin.CustomerSignedUp.NavFragments.HistoryRecView;

/**
 * Created by koteswarao on 28-06-2017.
 * ${CLASS}
 */

public class HistoryListItem {
    public String dateNumber, dateDay, carUsed, serviceName;
    public boolean serviceSuccess;

    public HistoryListItem(String dateNumber, String dateDay, String carUsed,
                           String serviceName, boolean serviceSuccess) {
        this.dateNumber = dateNumber;
        this.dateDay = dateDay;
        this.carUsed = carUsed;
        this.serviceName = serviceName;
        this.serviceSuccess = serviceSuccess;
    }
}
