package com.motowala.AfterLogin.CustomerSignedUp.NavFragments.OffersRecView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.motowala.AfterLogin.CustomerSignedUp.CustomerLoggedIn;
import com.motowala.R;

import java.util.List;

/**
 * Created by koteswarao on 27-06-2017.
 * ${CLASS}
 */

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.Holder> {
    List<OffersListItem> listdata;
    LayoutInflater inflater;
    Context context;

    public OffersAdapter(List<OffersListItem> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.customer_offers_recview_list_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        OffersListItem item = listdata.get(position);
        holder.offerCar.setText(item.offerCarname);
        holder.offerName.setText(item.offerName);
        String validity;
        int imageResourceId;
        if (item.offerValid) {
            validity = "VALID NOW";
            imageResourceId = R.drawable.full_circle_green;
        } else {
            validity = "EXPIRED";
            imageResourceId = R.drawable.full_circle_red;
        }
        holder.offerValidity.setText(validity);
        holder.offerColor.setImageResource(imageResourceId);
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView offerName, offerValidity, offerCar;
        ImageView offerColor;

        public Holder(View itemView) {
            super(itemView);
            offerName = (TextView) itemView.findViewById(R.id.offer_title_tv);
            offerValidity = (TextView) itemView.findViewById(R.id.offer_validity_tv);
            offerCar = (TextView) itemView.findViewById(R.id.offer_car_name);
            offerColor = (ImageView) itemView.findViewById(R.id.offer_status_circle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ((CustomerLoggedIn) context).handleOffersRecView(getAdapterPosition());
        }
    }
}
