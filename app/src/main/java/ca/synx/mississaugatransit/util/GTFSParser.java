package ca.synx.mississaugatransit.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.models.Stop;
import ca.synx.mississaugatransit.models.StopTime;

public class GTFSParser {

    public static List<Route> getRoutes(String data) throws JSONException {
        List<Route> routes = new ArrayList<Route>();

        JSONArray jsonArray = new JSONArray(data);

        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            try {
                routes.add(
                        new Route(
                                jsonObject.getString("RouteNumber"),
                                jsonObject.getString("RouteName"),
                                jsonObject.getString("RouteHeading")
                        )
                );
            } catch (Exception e) {
                Log.e("GTFSParser:getRoutes", "" + e.getMessage());
                e.printStackTrace();
            }
        }
        return routes;
    }

    public static List<Stop> getStops(String data) throws JSONException {
        List<Stop> stops = new ArrayList<Stop>();

        JSONArray jsonArray = new JSONArray(data);

        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            try {
                stops.add(
                        new Stop(
                                jsonObject.getString("StopId"),
                                jsonObject.getString("StopName"),
                                jsonObject.getDouble("StopLat"),
                                jsonObject.getDouble("StopLng"),
                                jsonObject.getInt("StopSequence")
                        )
                );
            } catch (Exception e) {
                Log.e("GTFSParser:getStops", "" + e.getMessage());
                e.printStackTrace();
            }
        }
        return stops;
    }

    public static List<StopTime> getStopTimes(String data) throws JSONException {
        List<StopTime> stopTimes = new ArrayList<StopTime>();

        JSONArray jsonArray = new JSONArray(data);

        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            try {
                stopTimes.add(
                        new StopTime(
                                jsonObject.getString("ArrivalTime"),
                                jsonObject.getString("DepartureTime")
                        )
                );
            } catch (Exception e) {
                Log.e("GTFSParser:getStopTimes", "" + e.getMessage());
                e.printStackTrace();
            }
        }
        return stopTimes;
    }
}