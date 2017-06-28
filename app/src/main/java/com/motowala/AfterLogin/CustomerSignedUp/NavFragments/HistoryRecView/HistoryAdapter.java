package com.motowala.AfterLogin.CustomerSignedUp.NavFragments.HistoryRecView;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.motowala.AfterLogin.CustomerSignedUp.CustomerLoggedIn;
import com.motowala.R;

import java.util.List;

/**
 * Created by koteswarao on 28-06-2017.
 * ${CLASS}
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.Holder> {

    List<HistoryListItem> listdata;
    Context context;
    LayoutInflater inflater;

    public HistoryAdapter(Context context, List<HistoryListItem> listdata) {
        this.context = context;
        this.listdata = listdata;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.customer_history_recview_list_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        HistoryListItem item = listdata.get(position);
        String dateNumber = item.dateNumber;
        String dateDay = item.dateDay;
        String carUsed = item.carUsed;
        String serviceName = item.serviceName;
        boolean serviceSuccess = item.serviceSuccess;
        holder.serviceDateDay.setText(item.dateDay);
        holder.serviceDateNum.setText(item.dateNumber);
        holder.servicedCar.setText(item.carUsed);
        holder.serviceName.setText(item.serviceName);
        holder.serviceStatus.setText(item.serviceSuccess ? "Success" : "Failed");
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView serviceDateNum, serviceDateDay, serviceStatus, serviceName, servicedCar;
        CardView serviceHistoryCardView;

        public Holder(View itemView) {
            super(itemView);
            serviceDateNum = (TextView) itemView.findViewById(R.id.history_recview_date_number);
            serviceDateDay = (TextView) itemView.findViewById(R.id.history_recview_date_day);
            serviceStatus = (TextView) itemView.findViewById(R.id.history_recview_service_status);
            serviceName = (TextView) itemView.findViewById(R.id.history_recview_service_name);
            servicedCar = (TextView) itemView.findViewById(R.id.history_recview_service_car_used);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ((CustomerLoggedIn) context).handleHistoryRecView(getAdapterPosition());
        }
    }
}
