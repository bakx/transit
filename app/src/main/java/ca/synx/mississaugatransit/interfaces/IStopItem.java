package ca.synx.mississaugatransit.interfaces;

public interface IStopItem {

    /* GTFS Specifications */

    /* The trip_id field contains an ID that identifies a trip. */
    String getTripId();

    /* The arrival_time specifies the arrival time at a specific stop for a specific trip on a route. */
    String getArrivalTime();

    /* The departure_time specifies the departure time from a specific stop for a specific trip on a route. */
    String getDepartureTime();

    /* The stop_id field contains an ID that uniquely identifies a stop. */
    String getStopId();

    /* The stop_headsign field contains the text that appears on a sign that identifies the trip's destination to passengers. */
    String getStopHeadsign();

    /* The pickup_type field indicates whether passengers are picked up at a stop as part of the normal schedule or whether a pickup at the stop is not available. */
    int getPickupType();

    /* The drop_off_type field indicates whether passengers are dropped off at a stop as part of the normal schedule or whether a drop off at the stop is not available. */
    int getDropOffType();

    /* The stop_sequence field identifies the order of the stops for a particular trip. */
    int getStopSequence();

    /* NON GTFS */

    String getStartStopId();

    String getFinalStopId();
}