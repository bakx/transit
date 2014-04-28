package ca.synx.mississaugatransit.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import ca.synx.mississaugatransit.handlers.StorageHandler;
import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.models.Stop;
import ca.synx.mississaugatransit.util.GTFSDataExchange;
import ca.synx.mississaugatransit.util.GTFSParser;

public class RouteStopsTask extends AsyncTask<Object, Void, List<Stop>> {

    private IRouteStopsTask mListener;
    private StorageHandler mStorageHandler;

    public RouteStopsTask(IRouteStopsTask routeStopsTask) {
        this.mListener = routeStopsTask;
        this.mStorageHandler = StorageHandler.getInstance();
    }

    @Override
    protected List<Stop> doInBackground(Object... params) {

        // Route is [0]
        Route route = (Route) params[0];

        // Route Date is [1]
        String routeDate = (String) params[1];

        //
        // Cache check.
        //

        List<Stop> stops = mStorageHandler.getRouteStops(route, routeDate);

        // Check if items were found in cache.
        if (stops.size() > 0)
            return stops;

        //
        // Fetch data from web service.
        //

        try {
            // Fetch data from web service.
            String data = (new GTFSDataExchange().getStopsData(route, routeDate));

            // Process web service data.
            stops = GTFSParser.getStops(data);

        } catch (Exception e) {
            Log.e("RouteStopsTask:doInBackground", "" + e.getMessage());
            e.printStackTrace();
            return null;
        }

        for (Stop stop : stops)
            stop.setRoute(route);


        // Store items in cache.
        mStorageHandler.saveRouteStops(stops, routeDate);

        return stops;
    }

    @Override
    protected void onPostExecute(List<Stop> stops) {
        super.onPostExecute(stops);

        mListener.onRouteStopsTaskComplete(
                stops
        );
    }

    public interface IRouteStopsTask {
        void onRouteStopsTaskComplete(List<Stop> stops);
    }
}