package ca.synx.mississaugatransit.db;

public final class CacheRoutesTable {
    public static final String TABLE_NAME = "cache_routes";

    public static final String COLUMN_STORAGE_ID = "storage_id";
    public static final String COLUMN_ROUTE_ID = "route_id";
    public static final String COLUMN_AGENCY_ID = "agency_id";
    public static final String COLUMN_ROUTE_SHORT_NAME = "route_short_name";
    public static final String COLUMN_ROUTE_LONG_NAME = "route_long_name";
    public static final String COLUMN_ROUTE_TYPE = "route_type";
    public static final String COLUMN_ROUTE_COLOR = "route_color";
    public static final String COLUMN_ROUTE_TEXT_COLOR = "route_text_color";
    public static final String COLUMN_ROUTE_HEADING = "route_heading";
    public static final String COLUMN_SERVICE_DATE = "service_date";

    public static String CREATE_TABLE() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_STORAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_ROUTE_ID + " REAL," +
                COLUMN_AGENCY_ID + " TEXT," +
                COLUMN_ROUTE_SHORT_NAME + " TEXT," +
                COLUMN_ROUTE_LONG_NAME + " TEXT," +
                COLUMN_ROUTE_TYPE + " REAL," +
                COLUMN_ROUTE_COLOR + " TEXT," +
                COLUMN_ROUTE_TEXT_COLOR + " TEXT," +
                COLUMN_ROUTE_HEADING + " TEXT," +
                COLUMN_SERVICE_DATE + " TEXT" +
                ")";
    }

    public static String DROP_TABLE() {
        return "DROP TABLE IF EXISTS '" + TABLE_NAME + "';";
    }
}