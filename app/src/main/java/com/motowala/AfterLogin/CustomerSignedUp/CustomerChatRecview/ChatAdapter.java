package com.motowala.AfterLogin.CustomerSignedUp.CustomerChatRecview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.motowala.R;

import java.util.List;

/**
 * Created by koteswarao on 30-06-2017.
 * ${CLASS}
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.Holder> {

    public List<ChatListItem> listdata;
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
        RelativeLayout chatView = holder.chatView;
        holder.time.setText(listItem.time);
        holder.message.setText(listItem.message);
        if (!listItem.sentByCustomer) {
            chatView.setBackground(context.getResources().getDrawable(R.drawable.balloon_incoming_normal));

            int rel_wrap_content = RelativeLayout.LayoutParams.WRAP_CONTENT;
            RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(rel_wrap_content, rel_wrap_content);
            relParams.setMarginEnd(100);
            relParams.setMarginStart(10);
            chatView.setLayoutParams(relParams);

        }
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        RelativeLayout chatView;
        TextView message, time;

        public Holder(View itemView) {
            super(itemView);
            chatView = (RelativeLayout) itemView.findViewById(R.id.customer_chat_view);
            message = (TextView) itemView.findViewById(R.id.customer_chat_message);
            time = (TextView) itemView.findViewById(R.id.chat_time);
        }
    }
}
