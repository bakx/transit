package ca.synx.mississaugatransit.db;

public final class FavoriteTable {
    public static final String TABLE_NAME = "favorites";
    public static final String COLUMN_FAVORITE_ID = "favorite_id";
    public static final String COLUMN_STOP_ID = "stop_id";
    public static final String COLUMN_STOP_NAME = "stop_name";
    public static final String COLUMN_STOP_LAT = "stop_lat";
    public static final String COLUMN_STOP_LNG = "stop_lng";
    public static final String COLUMN_STOP_SEQUENCE = "stop_sequence";
    public static final String COLUMN_ROUTE_NUMBER = "route_number";
    public static final String COLUMN_ROUTE_NAME = "route_name";
    public static final String COLUMN_ROUTE_HEADING = "route_heading";

    public static String CREATE_TABLE() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_FAVORITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_STOP_ID + " TEXT," +
                COLUMN_STOP_NAME + " TEXT," +
                COLUMN_STOP_LAT + " REAL," +
                COLUMN_STOP_LNG + " REAL," +
                COLUMN_STOP_SEQUENCE + " INT," +
                COLUMN_ROUTE_NUMBER + " TEXT," +
                COLUMN_ROUTE_NAME + " TEXT," +
                COLUMN_ROUTE_HEADING + " TEXT" +
                ")";
    }

    public static String DROP_TABLE() {
        return "DROP TABLE IF EXISTS '" + TABLE_NAME + "';";
    }
}