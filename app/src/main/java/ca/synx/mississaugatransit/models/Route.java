package ca.synx.mississaugatransit.models;

import java.io.Serializable;

import ca.synx.mississaugatransit.interfaces.IListItem;
import ca.synx.mississaugatransit.interfaces.ISpinnerItem;

public class Route implements IListItem, ISpinnerItem, Serializable {
    private String mRouteNumber;
    private String mRouteName;
    private String mRouteHeading;

    public Route(String routeNumber, String routeName, String routeHeading) {
        this.mRouteNumber = routeNumber;
        this.mRouteName = routeName;
        this.mRouteHeading = routeHeading;
    }

    public String getRouteName() {
        return this.mRouteName;
    }

    public String getRouteNumber() {
        return this.mRouteNumber;
    }

    public String getRouteHeading() {
        return this.mRouteHeading;
    }

    /* Implementation of interface IListItem */

    @Override
    public String getListItemHeading() {
        return mRouteNumber + " " + mRouteName;
    }

    @Override
    public String getListItemSubject() {
        return mRouteHeading;
    }

    @Override
    public int getListItemImageResource() {
        return 0;
    }

    /* Implementation of interface ISpinnerItem */

    @Override
    public String getSpinnerItemText() {
        return mRouteNumber + " " + mRouteName + " (" + mRouteHeading + ")";
    }

    @Override
    public int getSpinnerItemImageResource() {
        return 0;
    }
}