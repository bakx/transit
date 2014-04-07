package ca.synx.mississaugatransit.util;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.models.Stop;

public class GTFSDataExchange {

    private static String GTFS_BASE_URL = "http://gtfs.dataservices.synx.ca/api/GTFS/";
    private static String GET_ROUTES_URL = "GetRoutes/%s";
    private static String GET_STOPS_URL = "GetStops";
    private static String GET_STOPS_ROUTE_URL = "GetStops/%s/%s/%s";
    private static String GET_STOP_TIMES_URL = "GetStopTimes/%s/%s/%s/%s";

    public GTFSDataExchange() {
    }

    private String getData(String dataURL) {

        HttpClient client;

        try {
            client = new DefaultHttpClient();
            HttpResponse response = client.execute(new HttpGet(dataURL));
            InputStream is = response.getEntity().getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + '\n');
            }
            is.close();

            return sb.toString();
        } catch (Exception e) {
            Log.e("GTFSDataExchange:getData", "" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String getRouteData() {
        return getData(
                String.format(GTFS_BASE_URL + GET_ROUTES_URL,
                        GTFS.getServiceTimeStamp()
                )
        );
    }

    public String getStopsData() {
        return getData(
                String.format(GTFS_BASE_URL + GET_STOPS_URL)
        );
    }

    public String getStopsData(Route route) {
        return getData(
                String.format(GTFS_BASE_URL + GET_STOPS_ROUTE_URL,
                        route.getRouteNumber(),
                        route.getRouteHeading(),
                        GTFS.getServiceTimeStamp()
                )
        );
    }

    public String getStopTimesData(Stop stop) {
        return getData(
                String.format(GTFS_BASE_URL + GET_STOP_TIMES_URL,
                        stop.getRoute().getRouteNumber(),
                        stop.getRoute().getRouteHeading(),
                        stop.getStopId(),
                        GTFS.getServiceTimeStamp()
                )
        );
    }
}