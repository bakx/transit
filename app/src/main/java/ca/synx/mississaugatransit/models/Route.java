package ca.synx.mississaugatransit.models;

import java.io.Serializable;

import ca.synx.mississaugatransit.interfaces.IFilter;
import ca.synx.mississaugatransit.interfaces.IRoute;
import ca.synx.mississaugatransit.interfaces.ISpinnerItem;

public class Route implements IRoute, ISpinnerItem, IFilter, Serializable {

    private int mStorageId;
    private int mRouteId;
    private String mAgencyId;
    private String mRouteShortName;
    private String mRouteLongName;
    private int mRouteType;
    private String mRouteColor;
    private String mRouteTextColor;
    private String mRouteHeading;

    public Route(int storageId, int routeId, String agencyId, String routeShortName, String routeLongName, int routeType, String routeColor, String routeTextColor, String routeHeading) {
        this.mStorageId = storageId;
        this.mRouteId = routeId;
        this.mAgencyId = agencyId;
        this.mRouteShortName = routeShortName;
        this.mRouteLongName = routeLongName;
        this.mRouteType = routeType;
        this.mRouteColor = routeColor;
        this.mRouteTextColor = routeTextColor;
        this.mRouteHeading = routeHeading;
    }


    public int getStorageId() {
        return mStorageId;
    }

    public void setStorageId(int storageId) {
        this.mStorageId = storageId;
    }

    /* Implementation of interface IRoute */

    @Override
    public int getRouteId() {
        return mRouteId;
    }

    @Override
    public String getAgencyId() {
        return mAgencyId;
    }

    @Override
    public String getRouteShortName() {
        return mRouteShortName;
    }

    @Override
    public String getRouteLongName() {
        return mRouteLongName;
    }

    @Override
    public int getRouteType() {
        return mRouteType;
    }

    @Override
    public String getRouteColor() {
        return mRouteColor;
    }

    @Override
    public String getRouteTextColor() {
        return mRouteTextColor;
    }

    @Override
    public String getRouteHeading() {
        return mRouteHeading;
    }

    @Override
    public int getListItemImageResource() {
        return 0;
    }

    /* Implementation of interface IFilter */

    @Override
    public String getFilterData() {
        return mRouteShortName + mRouteLongName + mRouteHeading;
    }

    /* Implementation of interface ISpinnerItem */

    @Override
    public String getSpinnerItemText() {
        return mRouteShortName + " " + mRouteLongName + " (" + mRouteHeading + ")";
    }

    @Override
    public int getSpinnerItemImageResource() {
        return 0;
    }
}