package ca.synx.mississaugatransit.interfaces;

public interface IRoute {

    /* GTFS Specifications */

    /* The route_id field contains an ID that uniquely identifies a route. */
    int getRouteId();

    /* The agency_id field defines an agency for the specified route. */
    String getAgencyId();

    /* The route_short_name contains the short name of a route. */
    String getRouteShortName();

    /* The route_long_name contains the full name of a route. */
    String getRouteLongName();

    /* The route_type field describes the type of transportation used on a route. */
    int getRouteType();

    /* In systems that have colors assigned to routes, the route_color field defines a color that corresponds to a route. */
    String getRouteColor();

    /* The route_text_color field can be used to specify a legible color to use for text drawn against a background of route_color. */
    String getRouteTextColor();

    /* NON GTFS */

    /* The heading of the route (North,South,West,East) */
    String getRouteHeading();

    /* */
    int getListItemImageResource();
}