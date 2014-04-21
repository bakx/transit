package ca.synx.mississaugatransit.models;

import java.io.Serializable;
import java.util.List;

import ca.synx.mississaugatransit.interfaces.IFilter;
import ca.synx.mississaugatransit.interfaces.IStop;

public class Stop implements IStop, IFilter, Serializable {

    private String mStopId;
    private String mStopCode;
    private String mStopName;
    private String mStopDesc;
    private double mStopLat;
    private double mStopLng;
    private int mStopSequence;
    private String mZoneId;
    private String mStopUrl;
    private int mLocationType;
    private String mParentStation;

    private List<StopTime> mStopTimes;
    private List<StopTime> mNextStopTimes;
    private Route mRoute;

    public Stop(String stopId, String stopCode, String stopName, String stopDesc, double stopLat, double stopLng, int stopSequence, String zoneId, String stopUrl, int locationType, String parentStation) {
        this.mStopId = stopId;
        this.mStopCode = stopCode;
        this.mStopName = stopName;
        this.mStopDesc = stopDesc;
        this.mStopLat = stopLat;
        this.mStopLng = stopLng;
        this.mStopSequence = stopSequence;
        this.mZoneId = zoneId;
        this.mStopUrl = stopUrl;
        this.mLocationType = locationType;
        this.mParentStation = parentStation;
    }

   /* Implementation of interface IStop */

    @Override
    public String getStopId() {
        return mStopId;
    }

    @Override
    public String getStopCode() {
        return mStopCode;
    }

    @Override
    public String getStopName() {
        return mStopName;
    }

    @Override
    public String getStopDesc() {
        return mStopDesc;
    }

    @Override
    public double getStopLat() {
        return mStopLat;
    }

    @Override
    public double getStopLng() {
        return mStopLng;
    }

    @Override
    public String getZoneId() {
        return mZoneId;
    }

    @Override
    public String getStopUrl() {
        return mStopUrl;
    }

    @Override
    public int getStopSequence() {
        return mStopSequence;
    }

    @Override
    public int getLocationType() {
        return mLocationType;
    }

    @Override
    public String getParentStation() {
        return mParentStation;
    }

    /* Implementation of interface IFilter */

    @Override
    public String getFilterData() {
        return this.mStopId + this.mStopName;
    }

    /* */

    public int getListItemImageResource() {
        return 0;
    }

    public Route getRoute() {
        return mRoute;
    }

    public void setRoute(Route route) {
        mRoute = route;
    }

    public List<StopTime> getStopTimes() {
        return mStopTimes;
    }

    public List<StopTime> getNextStopTimes() {
        return mNextStopTimes;
    }
}