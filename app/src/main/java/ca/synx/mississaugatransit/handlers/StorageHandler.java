package ca.synx.mississaugatransit.handlers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ca.synx.mississaugatransit.db.CacheRouteStopsTable;
import ca.synx.mississaugatransit.db.CacheRoutesTable;
import ca.synx.mississaugatransit.db.CacheStopTimesTable;
import ca.synx.mississaugatransit.db.FavoriteTable;
import ca.synx.mississaugatransit.models.Favorite;
import ca.synx.mississaugatransit.models.Route;
import ca.synx.mississaugatransit.models.Stop;
import ca.synx.mississaugatransit.models.StopTime;

public final class StorageHandler {

    private static StorageHandler mStorageHandler;
    private DatabaseHandler mDatabaseHandler;

    public StorageHandler() {
        this.mDatabaseHandler = DatabaseHandler.getInstance();
    }

    public static StorageHandler getInstance() {
        if (mStorageHandler == null)
            mStorageHandler = new StorageHandler();

        return mStorageHandler;
    }

    public List<Route> getRoutes(String routeDate) {

        List<Route> list = new ArrayList<Route>();

        SQLiteDatabase db = mDatabaseHandler.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(CacheRoutesTable.TABLE_NAME,
                    new String[]
                            {
                                    CacheRoutesTable.COLUMN_STORAGE_ID,
                                    CacheRoutesTable.COLUMN_ROUTE_ID,
                                    CacheRoutesTable.COLUMN_AGENCY_ID,
                                    CacheRoutesTable.COLUMN_ROUTE_SHORT_NAME,
                                    CacheRoutesTable.COLUMN_ROUTE_LONG_NAME,
                                    CacheRoutesTable.COLUMN_ROUTE_TYPE,
                                    CacheRoutesTable.COLUMN_ROUTE_COLOR,
                                    CacheRoutesTable.COLUMN_ROUTE_TEXT_COLOR,
                                    CacheRoutesTable.COLUMN_ROUTE_HEADING,
                            },
                    CacheRoutesTable.COLUMN_SERVICE_DATE + " = ? ",
                    new String[]{
                            routeDate
                    }, null, null, null
            );

            if (cursor.moveToFirst()) {

                while (!cursor.isAfterLast()) {

                    Route route = new Route(
                            cursor.getInt(cursor.getColumnIndex(CacheRoutesTable.COLUMN_STORAGE_ID)),
                            cursor.getInt(cursor.getColumnIndex(CacheRoutesTable.COLUMN_ROUTE_ID)),
                            cursor.getString(cursor.getColumnIndex(CacheRoutesTable.COLUMN_AGENCY_ID)),
                            cursor.getString(cursor.getColumnIndex(CacheRoutesTable.COLUMN_ROUTE_SHORT_NAME)),
                            cursor.getString(cursor.getColumnIndex(CacheRoutesTable.COLUMN_ROUTE_LONG_NAME)),
                            cursor.getInt(cursor.getColumnIndex(CacheRoutesTable.COLUMN_ROUTE_TYPE)),
                            cursor.getString(cursor.getColumnIndex(CacheRoutesTable.COLUMN_ROUTE_COLOR)),
                            cursor.getString(cursor.getColumnIndex(CacheRoutesTable.COLUMN_ROUTE_TEXT_COLOR)),
                            cursor.getString(cursor.getColumnIndex(CacheRoutesTable.COLUMN_ROUTE_HEADING))
                    );

                    list.add(route);
                    cursor.moveToNext();
                }
            }

        } catch (Exception e) {
            Log.e("StorageHandler:getRoutes", e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            if (db != null && db.isOpen())
                db.close();
        }

        return list;
    }

    public List<Route> saveRoutes(List<Route> routes, String routeDate) {

        SQLiteDatabase db = this.mDatabaseHandler.getWritableDatabase();

        try {
            for (Route route : routes) {
                ContentValues values = new ContentValues();
                values.put(CacheRoutesTable.COLUMN_ROUTE_ID, route.getRouteId());
                values.put(CacheRoutesTable.COLUMN_AGENCY_ID, route.getAgencyId());
                values.put(CacheRoutesTable.COLUMN_ROUTE_SHORT_NAME, route.getRouteShortName());
                values.put(CacheRoutesTable.COLUMN_ROUTE_LONG_NAME, route.getRouteLongName());
                values.put(CacheRoutesTable.COLUMN_ROUTE_TYPE, route.getRouteType());
                values.put(CacheRoutesTable.COLUMN_ROUTE_COLOR, route.getRouteColor());
                values.put(CacheRoutesTable.COLUMN_ROUTE_TEXT_COLOR, route.getRouteTextColor());
                values.put(CacheRoutesTable.COLUMN_ROUTE_HEADING, route.getRouteHeading());
                values.put(CacheRoutesTable.COLUMN_SERVICE_DATE, routeDate);

                long insertId = db.insert(CacheRoutesTable.TABLE_NAME, null, values);

                // Assign storageId to route.
                route.setStorageId((int) insertId);
            }
        } catch (Exception e) {
            Log.e("StorageHandler:saveRoutes", "" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen())
                db.close();
        }

        return routes;
    }

    public List<Stop> getRouteStops(Route route, String routeDate) {

        List<Stop> list = new ArrayList<Stop>();

        SQLiteDatabase db = mDatabaseHandler.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(CacheRouteStopsTable.TABLE_NAME,
                    new String[]
                            {
                                    CacheRouteStopsTable.COLUMN_STOP_ID,
                                    CacheRouteStopsTable.COLUMN_STOP_CODE,
                                    CacheRouteStopsTable.COLUMN_STOP_NAME,
                                    CacheRouteStopsTable.COLUMN_STOP_DESC,
                                    CacheRouteStopsTable.COLUMN_STOP_LAT,
                                    CacheRouteStopsTable.COLUMN_STOP_LNG,
                                    CacheRouteStopsTable.COLUMN_ZONE_ID,
                                    CacheRouteStopsTable.COLUMN_STOP_URL,
                                    CacheRouteStopsTable.COLUMN_LOCATION_TYPE,
                                    CacheRouteStopsTable.COLUMN_PARENT_STATION,
                                    CacheRouteStopsTable.COLUMN_STOP_SEQUENCE
                            },
                    CacheRouteStopsTable.COLUMN_ROUTE_STORAGE_ID + " = ? " +
                            "AND " + CacheRouteStopsTable.COLUMN_SERVICE_DATE + " = ? ",
                    new String[]{
                            String.valueOf(route.getStorageId()),
                            routeDate
                    }, null, null, null
            );

            if (cursor.moveToFirst() && cursor.getCount() > 0) {

                while (cursor.isAfterLast() == false) {

                    Stop stop = new Stop(
                            cursor.getString(cursor.getColumnIndex(CacheRouteStopsTable.COLUMN_STOP_ID)),
                            cursor.getString(cursor.getColumnIndex(CacheRouteStopsTable.COLUMN_STOP_CODE)),
                            cursor.getString(cursor.getColumnIndex(CacheRouteStopsTable.COLUMN_STOP_NAME)),
                            cursor.getString(cursor.getColumnIndex(CacheRouteStopsTable.COLUMN_STOP_DESC)),
                            cursor.getDouble(cursor.getColumnIndex(CacheRouteStopsTable.COLUMN_STOP_LAT)),
                            cursor.getDouble(cursor.getColumnIndex(CacheRouteStopsTable.COLUMN_STOP_LNG)),
                            cursor.getInt(cursor.getColumnIndex(CacheRouteStopsTable.COLUMN_STOP_SEQUENCE)),
                            cursor.getString(cursor.getColumnIndex(CacheRouteStopsTable.COLUMN_ZONE_ID)),
                            cursor.getString(cursor.getColumnIndex(CacheRouteStopsTable.COLUMN_STOP_URL)),
                            cursor.getInt(cursor.getColumnIndex(CacheRouteStopsTable.COLUMN_LOCATION_TYPE)),
                            cursor.getString(cursor.getColumnIndex(CacheRouteStopsTable.COLUMN_PARENT_STATION))

                    );

                    // Attach route.
                    stop.setRoute(route);

                    // Add item to list.
                    list.add(stop);

                    // Continue.
                    cursor.moveToNext();
                }
            }

        } catch (Exception e) {
            Log.e("StorageHandler:getRouteStops", "" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();

            if (db != null && db.isOpen())
                db.close();
        }

        return list;
    }

    public void saveRouteStops(List<Stop> stops, String routeDate) {

        SQLiteDatabase db = this.mDatabaseHandler.getWritableDatabase();

        try {
            for (Stop stop : stops) {
                ContentValues values = new ContentValues();
                values.put(CacheRouteStopsTable.COLUMN_STOP_ID, stop.getStopId());
                values.put(CacheRouteStopsTable.COLUMN_STOP_CODE, stop.getStopCode());
                values.put(CacheRouteStopsTable.COLUMN_STOP_NAME, stop.getStopName());
                values.put(CacheRouteStopsTable.COLUMN_STOP_DESC, stop.getStopDesc());
                values.put(CacheRouteStopsTable.COLUMN_STOP_LAT, stop.getStopLat());
                values.put(CacheRouteStopsTable.COLUMN_STOP_LNG, stop.getStopLng());
                values.put(CacheRouteStopsTable.COLUMN_ZONE_ID, stop.getZoneId());
                values.put(CacheRouteStopsTable.COLUMN_STOP_URL, stop.getStopUrl());
                values.put(CacheRouteStopsTable.COLUMN_LOCATION_TYPE, stop.getLocationType());
                values.put(CacheRouteStopsTable.COLUMN_PARENT_STATION, stop.getParentStation());
                values.put(CacheRouteStopsTable.COLUMN_STOP_SEQUENCE, stop.getStopSequence());

                values.put(CacheRouteStopsTable.COLUMN_ROUTE_STORAGE_ID, stop.getRoute().getStorageId());
                values.put(CacheRouteStopsTable.COLUMN_SERVICE_DATE, routeDate);

                db.insert(CacheRouteStopsTable.TABLE_NAME, null, values);
            }
        } catch (Exception e) {
            Log.e("StorageHandler:saveRouteStops", "" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen())
                db.close();
        }
    }

    public List<Stop> getStops() {

        List<Stop> list = new ArrayList<Stop>();

        SQLiteDatabase db = mDatabaseHandler.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + CacheStopTimesTable.TABLE_NAME + " ORDER BY " + CacheStopTimesTable.COLUMN_STOP_STORAGE_ID, null);

            if (cursor.moveToFirst() && cursor.getCount() > 0) {

                while (cursor.isAfterLast() == false) {

                    /*
                    Stop stop = new Stop(
                            cursor.getString(cursor.getColumnIndex(CacheStopTimesTable.COLUMN_STOP_ID)),
                            cursor.getString(cursor.getColumnIndex(CacheStopTimesTable.COLUMN_STOP_NAME)),
                            cursor.getDouble(cursor.getColumnIndex(CacheStopTimesTable.COLUMN_STOP_LAT)),
                            cursor.getDouble(cursor.getColumnIndex(CacheStopTimesTable.COLUMN_STOP_LNG)),
                            cursor.getInt(cursor.getColumnIndex(CacheStopTimesTable.COLUMN_STOP_SEQUENCE))
                    );

                    // Add item to list.
                    list.add(stop);
*/
                    // Continue.
                    cursor.moveToNext();
                }
            }

        } catch (Exception e) {
            Log.e("StorageHandler:getStops", "" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();

            if (db != null && db.isOpen())
                db.close();
        }

        return list;
    }

    public void saveStops(List<Stop> stops) {
/*
        SQLiteDatabase db = this.mDatabaseHandler.getWritableDatabase();

        try {
            for (Stop stop : stops) {
                ContentValues values = new ContentValues();
                values.put(CacheStopTimesTable.COLUMN_STOP_ID, stop.getStopId());
                values.put(CacheStopTimesTable.COLUMN_STOP_NAME, stop.getStopName());
                values.put(CacheStopTimesTable.COLUMN_STOP_LAT, stop.getStopLat());
                values.put(CacheStopTimesTable.COLUMN_STOP_LNG, stop.getStopLng());
                values.put(CacheStopTimesTable.COLUMN_STOP_SEQUENCE, stop.getStopSequence());
                values.put(CacheStopTimesTable.COLUMN_SERVICE_DATE, GTFS.getServiceTimeStamp());

                db.insert(CacheStopTimesTable.TABLE_NAME, null, values);
            }
        } catch (Exception e) {
            Log.e("StorageHandler:saveStops", "" + e.getMessage());
            e.printStackTrace();
        } finally {
                    if (db != null && db.isOpen())
                db.close();
        }

        */
    }

    public List<StopTime> getStopTimes(Stop stop) {

        List<StopTime> list = new ArrayList<StopTime>();

        SQLiteDatabase db = mDatabaseHandler.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(CacheStopTimesTable.TABLE_NAME,
                    new String[]{
                            CacheStopTimesTable.COLUMN_ARRIVAL_TIME,
                            CacheStopTimesTable.COLUMN_DEPARTURE_TIME
                    },
                    CacheStopTimesTable.COLUMN_STOP_STORAGE_ID + " = ? ",
                    new String[]{
                            ""
                    }, null, null, null
            );

            if (cursor.moveToFirst() && cursor.getCount() > 0) {

                while (!cursor.isAfterLast()) {

                    StopTime stopTime = new StopTime(
                            cursor.getString(cursor.getColumnIndex(CacheStopTimesTable.COLUMN_TRIP_ID)),
                            cursor.getString(cursor.getColumnIndex(CacheStopTimesTable.COLUMN_ARRIVAL_TIME)),
                            cursor.getString(cursor.getColumnIndex(CacheStopTimesTable.COLUMN_DEPARTURE_TIME)),
                            cursor.getString(cursor.getColumnIndex(CacheStopTimesTable.COLUMN_STOP_ID)),
                            cursor.getString(cursor.getColumnIndex(CacheStopTimesTable.COLUMN_STOP_HEADSIGN)),
                            cursor.getInt(cursor.getColumnIndex(CacheStopTimesTable.COLUMN_STOP_SEQUENCE)),
                            cursor.getInt(cursor.getColumnIndex(CacheStopTimesTable.COLUMN_PICKUP_TYPE)),
                            cursor.getInt(cursor.getColumnIndex(CacheStopTimesTable.COLUMN_DROP_OFF_TYPE)),
                            cursor.getString(cursor.getColumnIndex(CacheStopTimesTable.COLUMN_START_STOP_ID)),
                            cursor.getString(cursor.getColumnIndex(CacheStopTimesTable.COLUMN_FINAL_STOP_ID))

                    );

                    // cursor.getInt(cursor.getColumnIndex(CacheStopTimesTable.COLUMN_STOP_STORAGE_ID))

                    // Add item to list.
                    list.add(stopTime);

                    // Continue.
                    cursor.moveToNext();
                }
            }

        } catch (Exception e) {
            Log.e("StorageHandler:getStopTimes", "" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();

            if (db != null && db.isOpen())
                db.close();
        }

        return list;
    }

    public void saveStopTimes(Stop stop) {

        SQLiteDatabase db = this.mDatabaseHandler.getWritableDatabase();

        List<StopTime> stopTimes = stop.getStopTimes();

        try {
            for (int i = 0; i < stopTimes.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(CacheStopTimesTable.COLUMN_ARRIVAL_TIME, stopTimes.get(i).getArrivalTime());
                values.put(CacheStopTimesTable.COLUMN_DEPARTURE_TIME, stopTimes.get(i).getDepartureTime());
                values.put(CacheStopTimesTable.COLUMN_STOP_STORAGE_ID, "");

                db.insert(CacheStopTimesTable.TABLE_NAME, null, values);
            }
        } catch (Exception e) {
            Log.e("StorageHandler:saveStopTimes", "" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen())
                db.close();
        }
    }

    public List<Favorite> getFavorites() {

        List<Favorite> list = new ArrayList<Favorite>();
        SQLiteDatabase db = this.mDatabaseHandler.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + FavoriteTable.TABLE_NAME + " ORDER BY " + FavoriteTable.COLUMN_ROUTE_NUMBER, null);

            if (cursor.moveToFirst() && cursor.getCount() > 0) {

                while (cursor.isAfterLast() == false) {

                  /*  Route route = new Route(
                            cursor.getString(cursor.getColumnIndex(FavoriteTable.COLUMN_ROUTE_NUMBER)),
                            cursor.getString(cursor.getColumnIndex(FavoriteTable.COLUMN_ROUTE_NAME)),
                            cursor.getString(cursor.getColumnIndex(FavoriteTable.COLUMN_ROUTE_HEADING))
                    );


                    Stop stop = new Stop(
                            cursor.getString(cursor.getColumnIndex(FavoriteTable.COLUMN_STOP_ID)),
                            cursor.getString(cursor.getColumnIndex(FavoriteTable.COLUMN_STOP_NAME)),
                            cursor.getDouble(cursor.getColumnIndex(FavoriteTable.COLUMN_STOP_LAT)),
                            cursor.getDouble(cursor.getColumnIndex(FavoriteTable.COLUMN_STOP_LNG)),
                            cursor.getInt(cursor.getColumnIndex(FavoriteTable.COLUMN_STOP_SEQUENCE))
                    );

                    //stop.setRoute(route);

                    Favorite favorite = new Favorite(
                            cursor.getInt(cursor.getColumnIndex(FavoriteTable.COLUMN_FAVORITE_ID)),
                            stop
                    );

                    list.add(favorite); */
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e("StorageHandler:getFavorites", "" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            if (db != null && db.isOpen())
                db.close();
        }

        return list;
    }

    public void saveFavorite(Favorite favorite) {

        SQLiteDatabase db = this.mDatabaseHandler.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(FavoriteTable.COLUMN_STOP_ID, favorite.getStop().getStopId());
            values.put(FavoriteTable.COLUMN_STOP_NAME, favorite.getStop().getStopName());
            values.put(FavoriteTable.COLUMN_STOP_LAT, favorite.getStop().getStopLat());
            values.put(FavoriteTable.COLUMN_STOP_LNG, favorite.getStop().getStopLng());
            values.put(FavoriteTable.COLUMN_STOP_SEQUENCE, favorite.getStop().getStopSequence());
            values.put(FavoriteTable.COLUMN_ROUTE_NUMBER, favorite.getStop().getRoute().getRouteShortName());
            values.put(FavoriteTable.COLUMN_ROUTE_NAME, favorite.getStop().getRoute().getRouteLongName());
            values.put(FavoriteTable.COLUMN_ROUTE_HEADING, favorite.getStop().getRoute().getRouteHeading());

            long insertId = db.insert(FavoriteTable.TABLE_NAME, null, values);
            favorite.setId((int) insertId);
        } catch (Exception e) {
            Log.e("StorageHandler:saveFavorite", "" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen())
                db.close();
        }
    }

    public void removeFavorite(Favorite favorite) {

        SQLiteDatabase db = this.mDatabaseHandler.getWritableDatabase();

        try {
            db.delete(FavoriteTable.TABLE_NAME, FavoriteTable.COLUMN_FAVORITE_ID + "=" + favorite.getId(), null);
            favorite.setId(0);
        } catch (Exception e) {
            Log.e("StorageHandler:removeFavorite", "" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen())
                db.close();
        }
    }

    public Boolean isFavorite(Favorite favorite) {

        SQLiteDatabase db = this.mDatabaseHandler.getReadableDatabase();
        Cursor cursor = null;

        try {
            ContentValues values = new ContentValues();
            values.put(FavoriteTable.COLUMN_STOP_ID, favorite.getStop().getStopId());
            values.put(FavoriteTable.COLUMN_STOP_NAME, favorite.getStop().getStopName());
            values.put(FavoriteTable.COLUMN_STOP_LAT, favorite.getStop().getStopLat());
            values.put(FavoriteTable.COLUMN_STOP_LNG, favorite.getStop().getStopLng());
            values.put(FavoriteTable.COLUMN_STOP_SEQUENCE, favorite.getStop().getStopSequence());
            values.put(FavoriteTable.COLUMN_ROUTE_NUMBER, favorite.getStop().getRoute().getRouteShortName());
            values.put(FavoriteTable.COLUMN_ROUTE_NAME, favorite.getStop().getRoute().getRouteLongName());
            values.put(FavoriteTable.COLUMN_ROUTE_HEADING, favorite.getStop().getRoute().getRouteHeading());

            cursor = db.query(FavoriteTable.TABLE_NAME,
                    new String[]{FavoriteTable.COLUMN_FAVORITE_ID},
                    FavoriteTable.COLUMN_STOP_ID + " = ? " +
                            "AND " + FavoriteTable.COLUMN_STOP_NAME + " = ? " +
                            "AND " + FavoriteTable.COLUMN_ROUTE_NUMBER + " = ? " +
                            "AND " + FavoriteTable.COLUMN_ROUTE_NAME + " = ? " +
                            "AND " + FavoriteTable.COLUMN_ROUTE_HEADING + " = ? ",
                    new String[]{
                            favorite.getStop().getStopId(),
                            favorite.getStop().getStopName(),
                            favorite.getStop().getRoute().getRouteShortName(),
                            favorite.getStop().getRoute().getRouteLongName(),
                            favorite.getStop().getRoute().getRouteHeading()
                    }, null, null, null
            );

            if (cursor.moveToFirst()) {
                favorite.setId(cursor.getInt(cursor.getColumnIndex(FavoriteTable.COLUMN_FAVORITE_ID)));
                return true;
            }
        } catch (Exception e) {
            Log.e("StorageHandler:isFavorite", "" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();

            if (db != null && db.isOpen())
                db.close();
        }

        return false;
    }
}