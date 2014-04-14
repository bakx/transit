package ca.synx.mississaugatransit.util;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.models.Stop;

public class GTFSDataExchange {

    private static String GTFS_BASE_URL = "http://gtfs.dataservices.synx.ca/api/GTFS/";
    private static String GET_FEED_INFO = "GetFeedInfo";
    private static String GET_ROUTES_URL = "GetRoutes/%s";
    private static String GET_STOPS_URL = "GetStops";
    private static String GET_STOPS_ROUTE_URL = "GetStops/%s/%s/%s";
    private static String GET_STOP_TIMES_URL = "GetStopTimes/%s/%s/%s/%s";

    private String getData(String dataURL) throws IOException {

        HttpClient client = null;
        InputStream is = null;
        String data = "";

        try {

            client = new DefaultHttpClient();
            HttpResponse response = client.execute(new HttpGet(dataURL));
            is = response.getEntity().getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + '\n');
            }
            is.close();

            data = sb.toString();
        } catch (Exception e) {
            Log.e("GTFSDataExchange:getData", "" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }

        return data;
    }

    public String getRouteData(String routeDate) {
        String data = "";

        try {
            data = getData(
                    String.format(GTFS_BASE_URL + GET_ROUTES_URL,
                            routeDate
                    )
            );

        } catch (IOException e) {
            Log.e("GTFSDataExchange:getRouteData", "" + e.getMessage());
            e.printStackTrace();
        }

        return data;
    }

    public String getStopsData(String routeDate) {
        String data = "";

        try {
            data = getData(
                    String.format(GTFS_BASE_URL + GET_STOPS_URL)
            );

        } catch (IOException e) {
            Log.e("GTFSDataExchange:getStopsData", "" + e.getMessage());
            e.printStackTrace();
        }

        return data;
    }

    public String getStopsData(Route route, String routeDate) {
        String data = "";

        try {
            data = getData(
                    String.format(GTFS_BASE_URL + GET_STOPS_ROUTE_URL,
                            route.getRouteNumber(),
                            route.getRouteHeading(),
                            routeDate
                    )
            );

        } catch (IOException e) {
            Log.e("GTFSDataExchange:getStopsData", "" + e.getMessage());
            e.printStackTrace();
        }

        return data;
    }

    public String getStopTimesData(Stop stop, String routeDate) {
        String data = "";

        try {
            data = getData(
                    String.format(GTFS_BASE_URL + GET_STOP_TIMES_URL,
                            stop.getRoute().getRouteNumber(),
                            stop.getRoute().getRouteHeading(),
                            stop.getStopId(),
                            routeDate
                    )
            );

        } catch (IOException e) {
            Log.e("GTFSDataExchange:getStopTimesData", "" + e.getMessage());
            e.printStackTrace();
        }

        return data;
    }
}