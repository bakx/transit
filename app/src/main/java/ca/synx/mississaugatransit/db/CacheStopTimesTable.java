package ca.synx.mississaugatransit.db;

public final class CacheStopTimesTable {
    public static final String TABLE_NAME = "cache_stop_times";
    public static final String COLUMN_ARRIVAL_TIME = "arrival_time";
    public static final String COLUMN_DEPARTURE_TIME = "departure_time";
    public static final String COLUMN_CACHE_ID = "cache_id";

    public static String CREATE_TABLE() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ARRIVAL_TIME + " TEXT," +
                COLUMN_DEPARTURE_TIME + " TEXT," +
                COLUMN_CACHE_ID + " INTEGER" +
                ")";
    }

    public static String DROP_TABLE() {
        return "DROP TABLE IF EXISTS '" + TABLE_NAME + "';";
    }
}