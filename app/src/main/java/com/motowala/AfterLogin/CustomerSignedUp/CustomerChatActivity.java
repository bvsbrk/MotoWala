package com.motowala.AfterLogin.CustomerSignedUp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.motowala.AfterLogin.CustomerSignedUp.CustomerChatRecview.ChatAdapter;
import com.motowala.AfterLogin.CustomerSignedUp.CustomerChatRecview.ChatData;
import com.motowala.AfterLogin.CustomerSignedUp.CustomerChatRecview.ChatListItem;
import com.motowala.Config;
import com.motowala.CustomerDatabases.CustomerDatabase;
import com.motowala.FirebaseActivities.FirebaseListeners;
import com.motowala.FirebaseActivities.WriteToFirebase;
import com.motowala.R;
import com.motowala.SimpleDateSender;

import java.util.HashMap;
import java.util.Map;

public class CustomerChatActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView chatRecView;
    EditText chatMessage;
    ChatAdapter adapter;
    ChatData data;
    Config config;
    FirebaseListeners chatListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_chat);
        config = new Config(this);
        setUpToolbar();
        setUpRecView();
        notifySupportTeamAboutThisCustomer(config.loggedInUserId, config.loggedInUserName);
        chatListener = new FirebaseListeners(this);
        chatListener.updateCustomerChatRecview(adapter, chatRecView);
    }

    private void notifySupportTeamAboutThisCustomer(final String loggedInUserId, final String userName) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = config.customerChatSupportNotifyingUrl;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(CustomerChatActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId", loggedInUserId);
                map.put("userName", userName);
                return map;
            }
        };
        queue.add(request);
    }

    private void setUpRecView() {
        chatRecView = (RecyclerView) findViewById(R.id.customer_chat_rec_view);
        chatMessage = (EditText) findViewById(R.id.customer_chat_message);
        data = new ChatData(this);
        adapter = new ChatAdapter(data.getList(), this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        chatRecView.setAdapter(adapter);
        chatRecView.setLayoutManager(manager);
    }

    void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Support");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        final String message = chatMessage.getText().toString();
        chatMessage.setText("");
        String time = (new SimpleDateSender("hh:mm a")).getTime();
        if (isMessageValid(message)) {
            adapter.listdata.add(0, new ChatListItem(message, time, true));
            adapter.notifyItemInserted(0);
            chatRecView.scrollToPosition(0);// Since I'm adding items to position 0 of listdata so scroll to zeroth position
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String time = (new SimpleDateSender("hh:mm a")).getTime();
                    ChatListItem item = new ChatListItem(message, time, true);
                    long numOfMsgs = storeMessageIntoDatabase(item);
                    addMessageToFirebase(message, true + "", time, numOfMsgs);
                }
            }).start();
        }
    }

    private void addMessageToFirebase(String message, String sentByCustomer, String time, long numOfMsgs) {
        ChatListItem item = new ChatListItem(message, time, Boolean.valueOf(sentByCustomer));
        WriteToFirebase updateChats = new WriteToFirebase(CustomerChatActivity.this);
        updateChats.updateCustomerChats(item, numOfMsgs);
    }

    public long storeMessageIntoDatabase(ChatListItem item) {
        CustomerDatabase database = new CustomerDatabase(this, config.databaseName, config.databaseNewVersion);
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(config.customerChatMessageColumn, item.message);
        values.put(config.customerChatSentByCustomerColumn, item.sentByCustomer + "");
        values.put(config.customerChatTimeColumn, item.time);
        long numOfMessages = db.insert(config.customerChatTableName, null, values);
        db.close();
        return numOfMessages;
    }

    private boolean isMessageValid(String mesage) {
        int length = mesage.length();
        if (mesage.equals("")) return false;
        for (int i = 0; i < mesage.length(); i++) {
            length = mesage.charAt(i) == ' ' ? length -= 1 : length;
        }
        return length != 0;
    }
}
