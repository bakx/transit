package ca.synx.mississaugatransit.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.util.List;

import ca.synx.mississaugatransit.handlers.StorageHandler;
import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.models.Stop;
import ca.synx.mississaugatransit.util.GTFSDataExchange;
import ca.synx.mississaugatransit.util.GTFSParser;

public class RouteStopsTask extends AsyncTask<Route, Void, List<Stop>> {

    private IRouteStopsTask mListener;
    private StorageHandler mStorageHandler;

    public RouteStopsTask(IRouteStopsTask routeStopsTask) {
        this.mListener = routeStopsTask;
        this.mStorageHandler = StorageHandler.getInstance();
    }

    @Override
    protected List<Stop> doInBackground(Route... params) {

        Route route = params[0];

        List<Stop> stops = mStorageHandler.getRouteStops(route);

        // Check if items were found in cache.
        if (stops.size() > 0)
            return stops;


        String data = (new GTFSDataExchange().getStopsData(route, ""));

        if (data == null)
            return null;

        try {
            stops = GTFSParser.getStops(data);

        } catch (JSONException e) {
            Log.e("RouteStopsTask:doInBackground", "" + e.getMessage());
            e.printStackTrace();
        }

        for (Stop stop : stops)
            stop.setRoute(route);


        // Store items in cache.
        mStorageHandler.saveRouteStops(stops);

        return stops;
    }

    @Override
    protected void onPostExecute(List<Stop> stops) {
        super.onPostExecute(stops);

        mListener.onRoutesTaskComplete(
                stops
        );
    }

    public interface IRouteStopsTask {
        void onRoutesTaskComplete(List<Stop> stops);
    }
}