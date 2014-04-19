package ca.synx.mississaugatransit.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ca.synx.mississaugatransit.app.R;

public final class GTFS {
    public static String getServiceTimeStamp() {
        return new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
    }

    public static int getLocationTypeImage(int locationType, Theme.IconType iconType) {

        switch (locationType) {
            case 0:
                switch (iconType) {
                    case LIGHT:
                        return R.drawable.ic_bus_stop;
                    case DARK:
                        return R.drawable.ic_bus_stop_dark;
                }
            case 1:
                switch (iconType) {
                    case LIGHT:
                        return R.drawable.ic_station;
                    case DARK:
                        return R.drawable.ic_station_dark;
                }
        }

        Log.e("GTFS:getLocationTypeImage", "Invalid locationType");
        return 0;
    }
}