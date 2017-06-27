package com.motowala.AfterLogin.CustomerSignedUp.ServiceTabFragments.ServiceRecView;

import android.content.Context;
import com.motowala.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koteswarao on 27-06-2017.
 * ${CLASS}
 */


// TODO: 27-06-2017 Get status from server
public class Data {
    List<ListItem> listItems;
    Context context;
    public Data(Context context) {
        listItems = new ArrayList<>();
        this.context=context;
    }

    public List<ListItem> getListItems() {
        for (int i = 0; i < 10; i++) {
            if (i>0 && i<=3){
                listItems.add(new ListItem("Heading"+i,"content "+i,context.getString(R.string.customer_progress_status_finished)));
            } else if (i==4){
                listItems.add(new ListItem("Heading"+i,"content "+i,context.getString(R.string.customer_progress_status_processed)));
            } else if (i>4 && i<=8) {
                listItems.add(new ListItem("Heading"+i,"content "+i,context.getString(R.string.customer_progress_status_started)));
            } else if (i==9) {
                listItems.add(new ListItem("Heading"+i,"content "+i,context.getString(R.string.customer_progress_status_error)));
            }
        }
        return listItems;
    }
}
