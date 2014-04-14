package ca.synx.mississaugatransit.interfaces;

import java.util.List;

import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.models.StopTime;

public interface IStop {

    String getStopId();

    String getStopName();

    double getStopLat();

    double getStopLng();

    int getStopSequence();

    int getListItemImageResource();

    List<StopTime> getStopTimes();

    List<StopTime> getNextStopTimes();

    Route getRoute();
}