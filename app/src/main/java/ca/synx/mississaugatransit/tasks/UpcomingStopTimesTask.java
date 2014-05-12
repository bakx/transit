package ca.synx.mississaugatransit.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ca.synx.mississaugatransit.app.R;
import ca.synx.mississaugatransit.models.StopTime;

public class UpcomingStopTimesTask extends AsyncTask<Object, Void, List<StopTime>> {

    private Context mContext;
    private IUpcomingStopTimesTask mListener;
    private int mMaxStops;

    public UpcomingStopTimesTask(Context context, IUpcomingStopTimesTask listener, int maxStops) {
        this.mContext = context;
        this.mListener = listener;
        this.mMaxStops = maxStops;
    }

    @Override
    protected List<StopTime> doInBackground(Object... params) {

        // StopTimes is [0]
        List<StopTime> stopTimes = (List<StopTime>) params[0];

        // Initiate return value.
        List<StopTime> upcomingStopTimes = new ArrayList<StopTime>();

        //
        // Prepare the current time.
        //

        long currentTime = 0;

        try {
            currentTime = new SimpleDateFormat("hh:mm a").parse(
                    new SimpleDateFormat("hh:mm a").format(
                            Calendar.getInstance().getTime()
                    )
            ).getTime();

        } catch (Exception e) {
            Log.e("UpcomingStopTimesTask:doInBackground", "" + e.getMessage());
            e.printStackTrace();

            return upcomingStopTimes;
        }

        //
        // Loop through all departure times to find best match
        //

        boolean reachedPM = false;   // Keep track of stopping times after midnight.

        for (int i = 0; i < stopTimes.size(); i++) {

            try {
                StopTime stopTime = stopTimes.get(i);

                Date stopDate = new SimpleDateFormat("hh:mm a").parse(
                        stopTime.getDepartureTime()
                );

                long timeDifference;
                long departureTime = stopDate.getTime();

                // Check if the time if a future, or past date.
                timeDifference = (departureTime - currentTime) / (60 * 1000);

                // Check if time is AM or PM
                Calendar calendar = Calendar.getInstance();
                calendar.setLenient(false); // Force the parser to throw an error on invalid hours.
                calendar.setTime(stopDate);

                if (calendar.get(Calendar.AM_PM) == Calendar.PM) {
                    reachedPM = true;
                } else {
                    // This timezone is AM. If PM time was reached, it means
                    // this AM time actually presents the next day. To prevent
                    // miscalculation of the 'next time', the time difference
                    // gets a boost of 24 * 60  (all minutes in a day) so the
                    // calculator knows that this is actually the next day.
                    if (reachedPM)
                        timeDifference = timeDifference + (24 * 60);
                }

                if (timeDifference < 0)
                    continue;

                StopTime nearStopTime = new StopTime(
                        stopTime.getArrivalTime(),
                        String.format(
                                mContext.getString(R.string.next_stop_time),
                                String.valueOf(timeDifference)
                        ),
                        stopTime.getPickupType(),
                        stopTime.getDropOffType(),
                        stopTime.getStartStopId(),
                        stopTime.getFinalStopId()
                );


                upcomingStopTimes.add(nearStopTime);
            } catch (Exception e) {
                Log.e("NextStopTimesTask:doInBackground", "" + e.getMessage());
                e.printStackTrace();
            }

            if (upcomingStopTimes.size() >= mMaxStops)
                break;
        }

        return upcomingStopTimes;
    }

    @Override
    protected void onPostExecute(List<StopTime> stopTimes) {
        super.onPostExecute(stopTimes);

        mListener.onUpcomingStopTimesTaskComplete(stopTimes);
    }

    public interface IUpcomingStopTimesTask {
        void onUpcomingStopTimesTaskComplete(List<StopTime> stopTimes);
    }
}