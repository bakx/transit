package ca.synx.mississaugatransit.models;

import java.io.Serializable;
import java.util.List;

import ca.synx.mississaugatransit.interfaces.IFilter;
import ca.synx.mississaugatransit.interfaces.IStop;

public class Stop implements IStop, IFilter, Serializable {
    private String mStopId;
    private String mStopName;
    private double mStopLat;
    private double mStopLng;
    private int mStopSequence;
    private List<StopTime> mStopTimes;
    private List<StopTime> mNextStopTimes;
    private Route mRoute;

    public Stop(String stopId, String stopName, double stopLat, double stopLng, int stopSequence) {
        this.mStopId = stopId;
        this.mStopName = stopName;
        this.mStopLat = stopLat;
        this.mStopLng = stopLng;
        this.mStopSequence = stopSequence;
    }

    public String getStopId() {
        return mStopId;
    }

    /* Implementation of interface IStop */

    public String getStopName() {
        return mStopName;
    }

    public double getStopLat() {
        return mStopLat;
    }

    public double getStopLng() {
        return mStopLng;
    }

    public int getStopSequence() {
        return mStopSequence;
    }

    public int getListItemImageResource() {
        return 0;
    }

    public List<StopTime> getStopTimes() {
        return mStopTimes;
    }

    public List<StopTime> getNextStopTimes() {
        return mNextStopTimes;
    }

    public Route getRoute() {
        return mRoute;
    }

    public void setRoute(Route route) {
        mRoute = route;
    }

    /* Implementation of interface IFilter */

    @Override
    public String getFilterData() {
        return this.mStopId + this.mStopName;
    }
}