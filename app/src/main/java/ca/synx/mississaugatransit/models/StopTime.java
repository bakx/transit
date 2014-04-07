package ca.synx.mississaugatransit.models;

import java.io.Serializable;

import ca.synx.mississaugatransit.interfaces.IListItem;

public class StopTime implements IListItem, Serializable {
    private String mArrivalTime;
    private String mDepartureTime;

    public StopTime(String arrivalTime, String departureTime) {
        this.mArrivalTime = arrivalTime;
        this.mDepartureTime = departureTime;
    }

    public String getArrivalTime() {
        return this.mArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.mArrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return this.mDepartureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.mDepartureTime = departureTime;
    }

    /* Implementation of interface IListItem */

    @Override
    public String getListItemHeading() {
        return mDepartureTime;
    }

    @Override
    public String getListItemSubject() {
        return null;
    }

    @Override
    public int getListItemImageResource() {
        return 0;
    }
}
