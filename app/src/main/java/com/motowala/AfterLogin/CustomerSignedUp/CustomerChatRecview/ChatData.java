package com.motowala.AfterLogin.CustomerSignedUp.CustomerChatRecview;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.motowala.Config;
import com.motowala.CustomerDatabases.CustomerDatabase;
import com.motowala.SimpleDateSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koteswarao on 30-06-2017.
 * ${CLASS}
 */

public class ChatData {
    private Context context;
    private Config config;

    public ChatData(Context context) {
        this.context = context;
        config = new Config(context);
    }

    public List<ChatListItem> getList() {
        List<ChatListItem> list = new ArrayList<>();
        String time = (new SimpleDateSender("hh:mm a")).getTime();
        getPreviousMessagesFromDatabase(list);
        list.add(0, new ChatListItem("Welcome to wheelo support. How can we help you today ?", time, false));
        return list;
    }

    private void getPreviousMessagesFromDatabase(List<ChatListItem> list) {
        CustomerDatabase database = new CustomerDatabase(context,
                config.databaseName,
                config.databaseNewVersion);
        String[] cols = new String[]{config.customerChatMessageColumn,
                config.customerChatSentByCustomerColumn,
                config.customerChatTimeColumn};
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query(config.customerChatTableName, cols, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String message = cursor.getString(0);
            String time = cursor.getString(2);
            String sentByCustomer = cursor.getString(1);
            list.add(0, new ChatListItem(message, time, Boolean.valueOf(sentByCustomer)));
        }
        cursor.close();
        db.close();
    }
}
