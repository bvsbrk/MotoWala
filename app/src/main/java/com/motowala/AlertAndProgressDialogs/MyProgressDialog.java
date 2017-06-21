package com.motowala.AlertAndProgressDialogs;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by bk on 14-06-2017.
 * It is created for Wheelo
 */

public class MyProgressDialog extends ProgressDialog {
    String title, message;

    public MyProgressDialog(Context context, String title, String message) {
        super(context);
        if (!title.equals("")) this.setTitle(title);
        this.setMessage(message);
        this.setCancelable(false);
        this.setIndeterminate(false);
    }
}
