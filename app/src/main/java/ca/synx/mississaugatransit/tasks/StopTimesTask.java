package ca.synx.mississaugatransit.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ca.synx.mississaugatransit.handlers.StorageHandler;
import ca.synx.mississaugatransit.models.Stop;
import ca.synx.mississaugatransit.models.StopTime;
import ca.synx.mississaugatransit.util.GTFSDataExchange;
import ca.synx.mississaugatransit.util.GTFSParser;

public class StopTimesTask extends AsyncTask<Object, Void, List<StopTime>> {

    private IStopTimesTask mListener;
    private StorageHandler mStorageHandler;

    public StopTimesTask(IStopTimesTask listener) {
        this.mListener = listener;
        this.mStorageHandler = StorageHandler.getInstance();
    }

    @Override
    protected List<StopTime> doInBackground(Object... params) {

        // Route is [0]
        Stop stop = (Stop) params[0];

        // Route Date is [1]
        String routeDate = (String) params[1];


        //
        // Cache check.
        //

        List<StopTime> stopTimes = mStorageHandler.getStopTimes(stop);

        if (stopTimes.size() > 0)
            return stopTimes;

        //
        // Fetch data from web service.
        //

        stopTimes = new ArrayList<StopTime>();

        try {
            // Fetch data from web service.
            String data = (new GTFSDataExchange().getStopTimesData(stop, routeDate));

            // Process web service data.
            stopTimes = GTFSParser.getStopTimes(data);

        } catch (JSONException e) {
            Log.e("StopTimesTask:doInBackground", "" + e.getMessage());
            e.printStackTrace();
        }

        // Store items in cache.
        mStorageHandler.saveStopTimes(stop);

        return stopTimes;
    }

    @Override
    protected void onPostExecute(List<StopTime> stopTimes) {
        super.onPostExecute(stopTimes);

        SimpleDateFormat currentDateFormat = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("hh:mm aa");

        /*
        for (StopTime stopTime : stopTimes) {
            try {

                stopTime.setDepartureTime(
                        newDateFormat.format(
                                currentDateFormat.parse(stopTime.getDepartureTime()
                                )
                        )
                );
            } catch (Exception e) {
                Log.e("StopTimesTask:onPostExecute", "" + e.getMessage());
                e.printStackTrace();
            }

        }
 */
        mListener.onStopTimesTaskComplete(stopTimes);

    }

    public interface IStopTimesTask {
        void onStopTimesTaskComplete(List<StopTime> stopTimes);
    }
}