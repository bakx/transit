package ca.synx.mississaugatransit.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import ca.synx.mississaugatransit.handlers.StorageHandler;
import ca.synx.mississaugatransit.models.Stop;
import ca.synx.mississaugatransit.util.GTFSDataExchange;
import ca.synx.mississaugatransit.util.GTFSParser;

public class StopsTask extends AsyncTask<String, Void, List<Stop>> {

    private IStopsTask mListener;
    private StorageHandler mStorageHandler;

    public StopsTask(IStopsTask stopsTask) {
        this.mListener = stopsTask;
        this.mStorageHandler = StorageHandler.getInstance();
    }

    @Override
    protected List<Stop> doInBackground(String... params) {

        String stopId = (params.length > 0) ? (String) params[0] : "";

        //
        // Cache check.
        //

        List<Stop> stops = mStorageHandler.getStops(stopId);

        // Check if items were found in cache.
        if (stops.size() > 0)
            return stops;

        //
        // Fetch data from web service.
        //

        try {
            // Fetch data from web service.
            String data = (new GTFSDataExchange().getStopsData());

            // Process web service data.
            stops = GTFSParser.getStops(data);

        } catch (Exception e) {
            Log.e("StopsTask:doInBackground", "" + e.getMessage());
            e.printStackTrace();
            return null;
        }

        // Store items in cache.
        mStorageHandler.saveStops(stops);

        return stops;
    }

    @Override
    protected void onPostExecute(List<Stop> stops) {
        super.onPostExecute(stops);

        mListener.onStopsTaskComplete(
                stops
        );
    }

    public interface IStopsTask {
        void onStopsTaskComplete(List<Stop> stops);
    }
}