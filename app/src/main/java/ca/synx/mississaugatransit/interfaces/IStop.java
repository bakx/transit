package ca.synx.mississaugatransit.interfaces;

import java.util.List;

import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.models.StopTime;

public interface IStop {

    String getStopId();

    String getStopCode();

    String getStopName();

    String getStopDesc();

    double getStopLat();

    double getStopLng();

    String getZoneId();

    String getStopUrl();

    int getLocationType();

    String getParentStation();

    int getStopSequence();


    int getListItemImageResource();

    List<StopTime> getStopTimes();

    List<StopTime> getNextStopTimes();

    Route getRoute();
}