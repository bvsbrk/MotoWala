package com.motowala.CustomerDatabases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.motowala.R;

/**
 * Created by bk on 23-06-2017.
 * It is created for Wheelo
 */

public class CustomerDatabase extends SQLiteOpenHelper {

    private Context context;
    private String databaseName;
    private String carTablesName;

    public CustomerDatabase(Context context, String name, int newVersion, int oldVersion, String tableName) {
        super(context, name, null, newVersion);
        this.context = context;
        databaseName = context.getString(R.string.user_db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createCarsTable = "CREATE TABLE " + context.getString(R.string.user_car_table) + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + context.getString(R.string.user_car_table_car_column) + " VARCHAR(100))";
        sqLiteDatabase.execSQL(createCarsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
