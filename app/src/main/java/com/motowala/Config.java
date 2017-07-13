package com.motowala;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by koteswarao on 29-06-2017.
 * ${CLASS}
 */

public class Config extends Application {
    public Context context;
    public String sharedPrefsName;
    public String carTablesName, carsTableCarColumn, databaseName;
    public int databaseNewVersion, databaseOldVersion;
    public boolean showNotificationsToCustomer;
    public String customerNotificationState;
    public String userMobile;
    public SharedPreferences preferences;
    public String customerChatTableName;
    public String customerChatMessageColumn;
    public String customerChatSentByCustomerColumn;
    public String customerChatTimeColumn;
    public String loggedInUserId;
    public String loggedInUserName;
    public String customerChatSupportNotifyingUrl;

    public Config(Context context) {
        this.context = context;
        customerChatSupportNotifyingUrl = "";
        customerChatTableName = "customerChat";
        customerChatMessageColumn = "customerMessage";
        customerChatTimeColumn = "sentOn";
        customerChatSentByCustomerColumn = "isSentByCustomer";
        sharedPrefsName = context.getString(R.string.shared_prefs_login_validator);
        preferences = context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE);
        customerNotificationState = context.getString(R.string.customer_notification_state);
        showNotificationsToCustomer = preferences.getBoolean(customerNotificationState, true);
        carTablesName = context.getString(R.string.user_car_table);
        carsTableCarColumn = context.getString(R.string.user_car_table_car_column);
        databaseName = context.getString(R.string.user_db);
        databaseNewVersion = 3;
        databaseOldVersion = 1;
        loggedInUserId = preferences.getString(context.getString(R.string.user_db), "");
        userMobile = preferences.getString(context.getString(R.string.user_mobile), "");
        loggedInUserName = preferences.getString(context.getString(R.string.user_name), "");
    }
}
