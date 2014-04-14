package ca.synx.mississaugatransit.models;

import java.io.Serializable;

import ca.synx.mississaugatransit.interfaces.IListItem;

public class Favorite implements IListItem, Serializable {

    private int mId;
    private Stop mStop;

    public Favorite(int id, Stop stop) {
        this.mId = id;
        this.mStop = stop;
    }

    public Favorite(Stop stop) {
        this.mStop = stop;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public Stop getStop() {
        return mStop;
    }

    /* Implementation of interface IListItem */

    @Override
    public String getListItemHeading() {
        return mStop.getRoute().getRouteHeading();
    }

    @Override
    public String getListItemSubject() {
        return mStop.getStopName();
    }

    @Override
    public int getListItemImageResource() {
        return 0;
    }
}

