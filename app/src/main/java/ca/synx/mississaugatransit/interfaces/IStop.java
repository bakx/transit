package ca.synx.mississaugatransit.interfaces;

import java.util.List;

import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.models.StopTime;

public interface IStop {

    /* GTFS Specifications */

    /* The stop_id field contains an ID that uniquely identifies a stop or station. */
    String getStopId();

    /* The stop_code field contains short text or a number that uniquely identifies the stop for passengers. */
    String getStopCode();

    /* The stop_name field contains the name of a stop or station. */
    String getStopName();

    /* The stop_desc field contains a description of a stop. */
    String getStopDesc();

    /* The stop_lat field contains the latitude of a stop or station. */
    double getStopLat();

    /* The stop_lon field contains the longitude of a stop or station. */
    double getStopLng();

    /* The zone_id field defines the fare zone for a stop ID. */
    String getZoneId();

    /* The stop_url field contains the URL of a web page about a particular stop. */
    String getStopUrl();

    /* The location_type field identifies whether this stop ID represents a stop or station. */
    int getLocationType();

    /* For stops that are physically located inside stations, the parent_station field identifies the station associated with the stop. */
    String getParentStation();

    /* NON GTFS */

    /* The stop_sequence field identifies the order of the stops for a particular trip. */
    int getStopSequence();


    int getListItemImageResource();

    List<StopTime> getStopTimes();

    List<StopTime> getNextStopTimes();

    Route getRoute();
}