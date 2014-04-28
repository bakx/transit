package ca.synx.mississaugatransit.models;

import java.io.Serializable;

import ca.synx.mississaugatransit.interfaces.IStopItem;

public class StopTime implements IStopItem, Serializable {
    private String mTripId;
    private String mArrivalTime;
    private String mDepartureTime;
    private String mStopId;
    private String mStopHeadsign;
    private int mStopSequence;
    private int mPickupType;
    private int mDropOffType;
    private String mStartStopId;
    private String mFinalStopId;

    private Stop mStop;

    public StopTime(String tripId, String arrivalTime, String departureTime, String stopId, String stopHeadsign, int stopSequence, int pickupType, int dropOffType, String startStopId, String finalStopId) {
        this.mTripId = tripId;
        this.mArrivalTime = arrivalTime;
        this.mDepartureTime = departureTime;
        this.mStopId = stopId;
        this.mStopHeadsign = stopHeadsign;
        this.mStopSequence = stopSequence;
        this.mPickupType = pickupType;
        this.mDropOffType = dropOffType;
        this.mStartStopId = startStopId;
        this.mFinalStopId = finalStopId;
    }

    /* Implementation of interface IStopTime */

    @Override
    public String getTripId() {
        return mTripId;
    }

    @Override
    public String getArrivalTime() {
        return mArrivalTime;
    }

    @Override
    public String getDepartureTime() {
        return mDepartureTime;
    }

    @Override
    public String getStopId() {
        return mStopId;
    }

    @Override
    public String getStopHeadsign() {
        return mStopHeadsign;
    }

    @Override
    public int getStopSequence() {
        return mStopSequence;
    }

    @Override
    public int getPickupType() {
        return mPickupType;
    }

    @Override
    public int getDropOffType() {
        return mDropOffType;
    }

    @Override
    public String getStartStopId() {
        return mStartStopId;
    }

    @Override
    public String getFinalStopId() {
        return mFinalStopId;
    }
}