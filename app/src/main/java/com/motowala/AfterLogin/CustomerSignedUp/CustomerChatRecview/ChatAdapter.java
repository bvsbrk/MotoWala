package com.motowala.AfterLogin.CustomerSignedUp.CustomerChatRecview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.motowala.R;

import java.util.List;

/**
 * Created by koteswarao on 30-06-2017.
 * ${CLASS}
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.Holder> {

    private List<ChatListItem> listdata;
    private LayoutInflater inflater;
    private Context context;

    public ChatAdapter(List<ChatListItem> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = inflater.inflate(R.layout.customer_chat_recview_list_item, parent, false);
        return new Holder(layout);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ChatListItem listItem = listdata.get(position);
        LinearLayout chatView = holder.chatView;
        if (listItem.sentByCustomer) {

        }
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        LinearLayout chatView;
        TextView message, time;

        public Holder(View itemView) {
            super(itemView);
            chatView = (LinearLayout) itemView.findViewById(R.id.customer_chat_view);
            message = (TextView) itemView.findViewById(R.id.chat_message);
            time = (TextView) itemView.findViewById(R.id.chat_time);
        }
    }
}
