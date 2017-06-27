package com.motowala.AfterLogin.CustomerSignedUp.ServiceTabFragments.ServiceRecView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.vipulasri.timelineview.TimelineView;
import com.motowala.R;

import java.util.List;

/**
 * Created by koteswarao on 27-06-2017.
 * ${CLASS}
 */

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.Holder> {
    List<ListItem> listdata;
    Context context;
    LayoutInflater inflater;

    public ProgressAdapter(List<ListItem> list, Context context) {
        this.listdata = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.customer_progress_recview_list_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ListItem item = listdata.get(position);
        holder.heading.setText(item.heading);
        holder.content.setText(item.content);
        Log.d("See this",item.status+" "+position+" "+item.status.equals(context.getString(R.string.customer_progress_status_finished)));
        switch (item.status) {
            case "error": {
                holder.timelineView.setMarker(context.getResources().getDrawable(R.drawable.error_icon));
                break;
            }
            case "processing": {
                holder.timelineView.setMarker(context.getResources().getDrawable(R.drawable.radio_button_circle));
                break;
            }
            case "finished": {
                holder.timelineView.setMarker(context.getResources().getDrawable(R.drawable.tick_icon));
                break;
            }
            default: {
                holder.timelineView.setMarker(context.getResources().getDrawable(R.drawable.stroke_circle));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView heading, content;
        TimelineView timelineView;

        public Holder(View itemView) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.heading_tv);
            content = (TextView) itemView.findViewById(R.id.content_tv);
            timelineView = (TimelineView) itemView.findViewById(R.id.time_line_view);
        }
    }
}
