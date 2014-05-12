package ca.synx.mississaugatransit.handlers;


import android.util.Log;

import java.util.List;

import ca.synx.mississaugatransit.models.Stop;
import ca.synx.mississaugatransit.tasks.StopsTask;

public class StopHandler implements StopsTask.IStopsTask {

    public Stop getStop(String stopId) {

        //
        // Cache check.
        //

        List<Stop> stops = StorageHandler.getInstance().getStops(stopId);

        if (stops.size() > 0)
            return stops.get(0);

        try {
            stops = new StopsTask(this).execute(stopId).get();
        } catch (Exception e) {
            Log.e("StopHandler:getStop", "" + e.getMessage());
            e.printStackTrace();
        }

        if (stops.size() > 0)
            return stops.get(0);
        else

            return null;
    }

    @Override
    public void onStopsTaskComplete(List<Stop> stops) {

    }
}