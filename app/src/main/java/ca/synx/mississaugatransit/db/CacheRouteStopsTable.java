package ca.synx.mississaugatransit.db;

public final class CacheRouteStopsTable {
    public static final String TABLE_NAME = "cache_route_stops";

    public static final String COLUMN_STORAGE_ID = "storage_id";
    public static final String COLUMN_STOP_ID = "stop_id";
    public static final String COLUMN_STOP_CODE = "stop_code";
    public static final String COLUMN_STOP_NAME = "stop_name";
    public static final String COLUMN_STOP_DESC = "stop_desc";
    public static final String COLUMN_STOP_LAT = "stop_lat";
    public static final String COLUMN_STOP_LNG = "stop_lng";
    public static final String COLUMN_ZONE_ID = "zone_id";
    public static final String COLUMN_STOP_URL = "stop_url";
    public static final String COLUMN_LOCATION_TYPE = "location_type";
    public static final String COLUMN_PARENT_STATION = "parent_station";
    public static final String COLUMN_STOP_SEQUENCE = "stop_sequence";
    public static final String COLUMN_ROUTE_STORAGE_ID = "route_storage_id";
    public static final String COLUMN_SERVICE_DATE = "service_date";

    public static String CREATE_TABLE() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_STORAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_STOP_ID + " TEXT," +
                COLUMN_STOP_CODE + " TEXT," +
                COLUMN_STOP_NAME + " TEXT," +
                COLUMN_STOP_DESC + " TEXT," +
                COLUMN_STOP_LAT + " REAL," +
                COLUMN_STOP_LNG + " REAL," +
                COLUMN_ZONE_ID + " TEXT," +
                COLUMN_STOP_URL + " TEXT," +
                COLUMN_LOCATION_TYPE + " REAL," +
                COLUMN_PARENT_STATION + " TEXT," +
                COLUMN_STOP_SEQUENCE + " REAL," +
                COLUMN_ROUTE_STORAGE_ID + " REAL," +
                COLUMN_SERVICE_DATE + " TEXT" +
                ")";
    }

    public static String DROP_TABLE() {
        return "DROP TABLE IF EXISTS '" + TABLE_NAME + "';";
    }
}