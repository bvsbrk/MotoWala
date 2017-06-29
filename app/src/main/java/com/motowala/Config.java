package com.motowala;

import android.app.Application;
import android.content.Context;

/**
 * Created by koteswarao on 29-06-2017.
 * ${CLASS}
 */

public class Config extends Application {
    public Context context;
    public String carTablesName, carsTableCarColumn, databaseName;
    public int databaseNewVersion, databaseOldVersion;

    public Config(Context context) {
        this.context = context;
        carTablesName = context.getString(R.string.user_car_table);
        carsTableCarColumn = context.getString(R.string.user_car_table_car_column);
        databaseName = context.getString(R.string.user_db);
        databaseNewVersion = 1;
        databaseOldVersion = 1;
    }
}
