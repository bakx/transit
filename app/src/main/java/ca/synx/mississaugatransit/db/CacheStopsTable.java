package ca.synx.mississaugatransit.db;

public final class CacheStopsTable {
    public static final String TABLE_NAME = "cache_stop_times";

    public static final String COLUMN_STOP_ID = "stop_id";
    public static final String COLUMN_STOP_NAME = "stop_name";
    public static final String COLUMN_STOP_LAT = "stop_lat";
    public static final String COLUMN_STOP_LNG = "stop_lng";
    public static final String COLUMN_STOP_SEQUENCE = "stop_sequence";
    public static final String COLUMN_SERVICE_DATE = "service_date";

    public static String CREATE_TABLE() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_STOP_ID + " TEXT," +
                COLUMN_STOP_NAME + " TEXT," +
                COLUMN_STOP_LAT + " REAL," +
                COLUMN_STOP_LNG + " REAL," +
                COLUMN_STOP_SEQUENCE + " INT," +
                COLUMN_SERVICE_DATE + " TEXT" +
                ")";
    }

    public static String DROP_TABLE() {
        return "DROP TABLE IF EXISTS '" + TABLE_NAME + "';";
    }
}