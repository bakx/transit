package ca.synx.mississaugatransit.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class GTFS {
    public static String getServiceTimeStamp() {
        return new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
    }

}
