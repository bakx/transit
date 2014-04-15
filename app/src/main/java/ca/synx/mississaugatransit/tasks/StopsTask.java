package ca.synx.mississaugatransit.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.util.List;

import ca.synx.mississaugatransit.handlers.StorageHandler;
import ca.synx.mississaugatransit.models.Stop;
import ca.synx.mississaugatransit.util.GTFSDataExchange;
import ca.synx.mississaugatransit.util.GTFSParser;

public class StopsTask extends AsyncTask<Void, Void, List<Stop>> {

    private IStopsTask mListener;
    private StorageHandler mStorageHandler;

    public StopsTask(IStopsTask stopsTaskListener) {
        this.mListener = stopsTaskListener;
        this.mStorageHandler = StorageHandler.getInstance();
    }

    @Override
    protected List<Stop> doInBackground(Void... params) {

        List<Stop> stops = mStorageHandler.getStops();

        // Check if items were found in cache.
        if (stops.size() > 0)
            return stops;

        String data = (new GTFSDataExchange().getStopsData(""));

        if (data == null)
            return null;

        try {
            stops = GTFSParser.getStops(data);

        } catch (JSONException e) {
            Log.e("StopsTask:doInBackground", "" + e.getMessage());
            e.printStackTrace();
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