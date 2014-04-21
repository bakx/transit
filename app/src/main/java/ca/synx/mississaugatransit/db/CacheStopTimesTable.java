package ca.synx.mississaugatransit.db;

public final class CacheStopTimesTable {
    public static final String TABLE_NAME = "cache_stop_times";
    public static final String COLUMN_TRIP_ID = "trip_id";
    public static final String COLUMN_ARRIVAL_TIME = "arrival_time";
    public static final String COLUMN_DEPARTURE_TIME = "departure_time";
    public static final String COLUMN_STOP_ID = "stop_id";
    public static final String COLUMN_STOP_SEQUENCE = "stop_sequence";
    public static final String COLUMN_STOP_HEADSIGN = "stop_headsign";
    public static final String COLUMN_PICKUP_TYPE = "pickup_type";
    public static final String COLUMN_DROP_OFF_TYPE = "drop_off_type";
    public static final String COLUMN_START_STOP_ID = "start_stop_id";
    public static final String COLUMN_FINAL_STOP_ID = "final_stop_id";
    public static final String COLUMN_STOP_STORAGE_ID = "stop_storage_id";

    public static String CREATE_TABLE() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_TRIP_ID + " TEXT," +
                COLUMN_ARRIVAL_TIME + " TEXT," +
                COLUMN_DEPARTURE_TIME + " TEXT," +
                COLUMN_STOP_ID + " TEXT," +
                COLUMN_STOP_SEQUENCE + " REAL," +
                COLUMN_STOP_HEADSIGN + " TEXT," +
                COLUMN_PICKUP_TYPE + " REAL," +
                COLUMN_DROP_OFF_TYPE + " REAL," +
                COLUMN_START_STOP_ID + " TEXT," +
                COLUMN_FINAL_STOP_ID + " TEXT," +
                COLUMN_STOP_STORAGE_ID + " REAL" +
                ")";
    }

    public static String DROP_TABLE() {
        return "DROP TABLE IF EXISTS '" + TABLE_NAME + "';";
    }
}