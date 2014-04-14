package ca.synx.mississaugatransit.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import ca.synx.mississaugatransit.handlers.StorageHandler;
import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.util.GTFSDataExchange;
import ca.synx.mississaugatransit.util.GTFSParser;

public class RoutesTask extends AsyncTask<String, Void, List<Route>> {

    private IRoutesTask mListener;
    private StorageHandler mStorageHandler;

    public RoutesTask(IRoutesTask listener) {
        this.mListener = listener;
        this.mStorageHandler = StorageHandler.getInstance();
    }

    @Override
    protected List<Route> doInBackground(String... params) {

        // Get selected route date from params.
        String routeDate = params[0];

        //
        // Cache check.
        //

        List<Route> routes = mStorageHandler.getRoutes(routeDate);

        if (routes.size() > 0)
            return routes;

        //
        // Fetch data from web service.
        //

        routes = new ArrayList<Route>();

        try {
            // Fetch data from web service.
            String data = (new GTFSDataExchange().getRouteData(routeDate));

            // Process web service data.
            routes = GTFSParser.getRoutes(data);

        } catch (JSONException e) {
            Log.e("RoutesTask:doInBackground", "" + e.getMessage());
            e.printStackTrace();
        }

        // Store items in cache.
        mStorageHandler.saveRoutes(routes, routeDate);

        return routes;
    }

    @Override
    protected void onPostExecute(List<Route> routes) {
        super.onPostExecute(routes);

        mListener.onRoutesTaskComplete(routes);
    }

    public interface IRoutesTask {
        void onRoutesTaskComplete(List<Route> routes);
    }

}