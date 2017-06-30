package com.motowala.AfterLogin.CustomerSignedUp.CustomerChatRecview;

/**
 * Created by koteswarao on 30-06-2017.
 * ${CLASS}
 */

public class ChatListItem {

    public String message;
    public String time;
    public boolean sentByCustomer;

    public ChatListItem(String message, String time, boolean sentByCustomer) {
        this.message = message;
        this.time = time;
        this.sentByCustomer = sentByCustomer;
    }
}
