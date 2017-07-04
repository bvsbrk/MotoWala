package com.motowala;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by koteswarao on 04-07-2017.
 * ${CLASS}
 */

public class SimpleDateSender {
    public String time;

    public SimpleDateSender(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.US);
        time = dateFormat.format(new Date());
    }

    public String getTime() {
        return time;
    }
}
