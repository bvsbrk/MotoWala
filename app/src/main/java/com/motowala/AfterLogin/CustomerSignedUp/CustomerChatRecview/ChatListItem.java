package com.motowala.AfterLogin.CustomerSignedUp.CustomerChatRecview;

/**
 * Created by koteswarao on 30-06-2017.
 * ${CLASS}
 */

public class ChatListItem {

    public String message;
    public boolean sentByCustomer;
    public String time;

    public ChatListItem() {
        /*
         * This useless constructor is for firebase retreival
         */
    }

    public ChatListItem(String message, String time, boolean sentByCustomer) {
        this.message = message;
        this.time = time;
        this.sentByCustomer = sentByCustomer;
    }
}
