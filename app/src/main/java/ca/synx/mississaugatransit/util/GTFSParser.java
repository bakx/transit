package ca.synx.mississaugatransit.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
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
                                0,
                                jsonObject.getInt("RouteId"),
                                jsonObject.getString("AgencyId"),
                                jsonObject.getString("RouteShortName"),
                                jsonObject.getString("RouteLongName"),
                                jsonObject.getInt("RouteType"),
                                jsonObject.getString("RouteColor"),
                                jsonObject.getString("RouteTextColor"),
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
                                0,
                                jsonObject.getString("StopId"),
                                jsonObject.getString("StopCode"),
                                jsonObject.getString("StopName"),
                                jsonObject.getString("StopDesc"),
                                jsonObject.getDouble("StopLat"),
                                jsonObject.getDouble("StopLng"),
                                jsonObject.getInt("StopSequence"),
                                jsonObject.getString("ZoneId"),
                                jsonObject.getString("StopUrl"),
                                jsonObject.getInt("LocationType"),
                                jsonObject.getString("ParentStation")

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

        SimpleDateFormat currentDateFormat = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("hh:mm aa");

        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            try {
                stopTimes.add(
                        new StopTime(
                                jsonObject.getString("TripId"),
                                newDateFormat.format(currentDateFormat.parse(jsonObject.getString("ArrivalTime"))),
                                newDateFormat.format(currentDateFormat.parse(jsonObject.getString("DepartureTime"))),
                                jsonObject.getString("StopId"),
                                jsonObject.getString("StopHeadsign"),
                                jsonObject.getInt("PickupType"),
                                jsonObject.getInt("DropOffType"),
                                jsonObject.getInt("StopSequence"),
                                jsonObject.getString("StartStopId"),
                                jsonObject.getString("FinalStopId")
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