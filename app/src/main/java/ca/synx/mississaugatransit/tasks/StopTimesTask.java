package ca.synx.mississaugatransit.tasks;

import android.content.Context;
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

public class StopTimesTask extends AsyncTask<Stop, Void, List<StopTime>> {

    private Context mContext;
    private IStopTimesTask mListener;
    private StorageHandler mStorageHandler;

    public StopTimesTask(Context context, IStopTimesTask listener, StorageHandler storageHandler) {
        this.mContext = context;
        this.mListener = listener;
        this.mStorageHandler = storageHandler;
    }

    @Override
    protected List<StopTime> doInBackground(Stop... params) {

        Stop stop = params[0];

        List<StopTime> stopTimes = mStorageHandler.getStopTimes(stop);

        // Check if items were found in cache.
        if (stopTimes.size() > 0)
            return stopTimes;

        stopTimes = new ArrayList<StopTime>();

        String data = (new GTFSDataExchange().getStopTimesData(stop));

        if (data == null)
            return null;

        try {
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

        mListener.onStopTimesTaskComplete(stopTimes);
    }

    public interface IStopTimesTask {
        void onStopTimesTaskComplete(List<StopTime> stopTimes);
    }

}