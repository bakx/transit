package ca.synx.mississaugatransit.interfaces;

public interface IRoute {
    int getRouteId();

    String getAgencyId();

    String getRouteShortName();

    String getRouteLongName();

    int getRouteType();

    String getRouteColor();

    String getRouteTextColor();

    String getRouteHeading();

    int getListItemImageResource();
}