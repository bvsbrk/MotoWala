package com.motowala.CustomerDatabases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bk on 23-06-2017.
 * It is created for Wheelo
 */

public class CustomerDatabase extends SQLiteOpenHelper {
    private Context context;
    private String databaseName;
    private int newVersion;
    private int oldVersion;

    public CustomerDatabase(Context context, String name, int newVersion, int oldVersion) {
        super(context, name, null, newVersion);
        this.context = context;
        this.databaseName = name;
        this.newVersion = newVersion;
        this.oldVersion = oldVersion;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createQry="CREATE TABLE customerInfo(_id INTEGER PRIMARY KEY AUTOINCREMENT, )";
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
