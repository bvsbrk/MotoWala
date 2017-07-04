package com.motowala.FirebaseActivities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.motowala.AfterLogin.CustomerSignedUp.CustomerChatRecview.ChatAdapter;
import com.motowala.AfterLogin.CustomerSignedUp.CustomerChatRecview.ChatListItem;

/**
 * Created by koteswarao on 04-07-2017.
 * ${CLASS}
 */

public class FirebaseListeners {
    public final String firstLevelChild = "individual_users";
    public final String secondLevelChild = "users";
    public Context context;
    ChatAdapter adapter;
    private FirebaseDatabase wheelo_main;
    private DatabaseReference databaseReference;

    public FirebaseListeners(Context context) {
        this.context = context;
        wheelo_main = FirebaseDatabase.getInstance();
        databaseReference = wheelo_main.getReference();
    }

    public void updateCustomerChatRecview(final ChatAdapter adapter, final RecyclerView chatRecView) {
        this.adapter = adapter;
        String thirdLevelChildUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(firstLevelChild)
                .child(secondLevelChild)
                .child(thirdLevelChildUid)
                .child("chats")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long lastChildNumber = dataSnapshot.getChildrenCount();
                        DataSnapshot child = dataSnapshot.child(lastChildNumber + "");
                        ChatListItem item = child.getValue(ChatListItem.class);
                        if (!item.sentByCustomer) {
                            adapter.listdata.add(0, item);
                            adapter.notifyItemInserted(0);
                            chatRecView.scrollToPosition(0);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
