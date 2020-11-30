
    
    public class StepAppOpenHelper extends SQLiteOpenHelper {
    
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "stepapp";

    public static final String TABLE_NAME_PA = "Physical_activities";
    public static final String ID_PA = "id";
    public static final String NAME = "name";
    public static final String MIN_TEMP = "min_temp";
    public static final String MAX_TEMP = "max_temp";
    public static final String O_MIN_TEMP = "optimal_min_temp";
    public static final String O_MAX_TEMP = "optimal_max_temp";
    public static final String O_MIN_VIS = "optimal_min_visibility";
    public static final String MIN_WIND = "min_wind_speed";
    public static final String MAX_WIND = "max_wind_speed";
    public static final String O_MIN_WIND_SPEED = "optimal_min_wind_speed";
    public static final String O_MAX_WIND_SPEED = "optimal_max_wind_speed";
    public static final String MIN_RAIN = "min_rain_1h";
    public static final String MAX_RAIN = "max_rain_1h";
    public static final String O_MIN_RAIN = "optimal_min_rain_1h";
    public static final String O_MAX_RAIN = "optimal_max_rain_1h";
    public static final String MIN_SNOW = "min_snow_1h";
    public static final String MAX_SNOW = "max_snow_1h";
    public static final String O_MIN_SNOW = "optimal_min_snow_1h";
    public static final String O_MAX_SNOW = "optimal_max_snow_1h";
    public static final String O_DAYTIME = "optimal_daytime";
    
    public static final String TABLE_NAME_PAR = "Physical_activities_rankings";
    public static final String ID_PAR ="id_r";
    public static final String PA_id="physical_activity_id";
    public static final String DATE="datetime";
    public static final String RATING="rating";
    public static final String REASON="reason";
    public static final String DATE_PUSH="push_out_time";


    // The constructor
    public StepAppOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PA +"(id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(30),
    min_temp FLOAT, # hard limit, e.g. cycling below 277K could potentially be hazardous
    max_temp FLOAT,
    optimal_min_temp FLOAT, # soft limit, e.g. optimal temperature range for running is probably around 287-293K
    optimal_max_temp FLOAT,
    optimal_min_visibility INT,
    min_wind_speed FLOAT,
    max_wind_speed FLOAT,
    optimal_min_wind_speed FLOAT,
    optimal_max_wind_speed FLOAT,
    min_rain_1h FLOAT,
    max_rain_1h FLOAT,
    optimal_min_rain_1h FLOAT,
    optimal_max_rain_1h FLOAT,
    min_snow_1h FLOAT,
    max_snow_1h FLOAT,
    optimal_min_snow_1h FLOAT,
    optimal_max_snow_1h FLOAT,
    optimal_daytime BOOLEAN)");
         db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PAR +"(
    id_r INTEGER NOT NULL PRIMARY KEY,
    physical_activity_id INT,
    datetime DATETIME,
    rating INT, # can be any rating, e.g. from 0 to 100 how optimal this activity is on that given day
    reason VARCHAR(255), # this can be the push notification in text form
    push_out_time DATETIME # when to send this notification
        )");

        db.execSQL("INSERT INTO " + TABLE_NAME_PA+ "(id, name, min_temp, max_temp, optimal_min_temp, optimal_max_temp, optimal_min_visibility, min_wind_speed, max_wind_speed, optimal_min_wind, optimal_max_wind, min_rain_1h, max_rain_1h, optimal_min_rain_1h, optimal_max_rain_1h, min_snow_1h, max_snow_1h, optimal_min_snow_1h, optimal_max_snow_1h, optimal_daytime) VALUES (01, "walking", -5, 30, 20, 25, 5, 0, 40, 5, 30, 0, 0, 0, 0, 0, 0, 0, 0, True)");
        db.execSQL("INSERT INTO " + TABLE_NAME_PA+ "(id, name, min_temp, max_temp, optimal_min_temp, optimal_max_temp, optimal_min_visibility, min_wind_speed, max_wind_speed, optimal_min_wind, optimal_max_wind, min_rain_1h, max_rain_1h, optimal_min_rain_1h, optimal_max_rain_1h, min_snow_1h, max_snow_1h, optimal_min_snow_1h, optimal_max_snow_1h, optimal_daytime) VALUES ()");
        db.execSQL("INSERT INTO " + TABLE_NAME_PA+ "(id, name, min_temp, max_temp, optimal_min_temp, optimal_max_temp, optimal_min_visibility, min_wind_speed, max_wind_speed, optimal_min_wind, optimal_max_wind, min_rain_1h, max_rain_1h, optimal_min_rain_1h, optimal_max_rain_1h, min_snow_1h, max_snow_1h, optimal_min_snow_1h, optimal_max_snow_1h, optimal_daytime) VALUES ()");     
        db.execSQL("INSERT INTO " + TABLE_NAME_PA+ "(id, name, min_temp, max_temp, optimal_min_temp, optimal_max_temp, optimal_min_visibility, min_wind_speed, max_wind_speed, optimal_min_wind, optimal_max_wind, min_rain_1h, max_rain_1h, optimal_min_rain_1h, optimal_max_rain_1h, min_snow_1h, max_snow_1h, optimal_min_snow_1h, optimal_max_snow_1h, optimal_daytime) VALUES ()");
        
        }

            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            }

        public void insertphysical_activity (int id, String name, float min_temp, float max_temp, float optimal_min_temp, float optimal_max_temp, float optimal_min_visibility, float min_wind_speed, float max_wind_speed, float optimal_min_wind, float optimal_max_wind, float min_rain_1h, float max_rain_1h, float optimal_min_rain_1h, float optimal_max_rain_1h, float min_snow_1h, float max_snow_1h, float optimal_min_snow_1h, float optimal_max_snow_1h, float optimal_daytime) {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_PA, id);
        contentValues.put(NAME, name);
        contentValues.put(MIN_TEMP, min_temp);
        contentValues.put(MAX_TEMP, max_temp);
        contentValues.put(O_MIN_TEMP, optimal_min_temp);
        contentValues.put(O_MAX_TEMP, optimal_max_temp);
        contentValues.put(O_MIN_VIS, optimal_min_visibility);
        contentValues.put(MIN_WIND, min_wind_speed);
        contentValues.put(MAX_WIND, max_wind_speed);
        contentValues.put(O_MIN_WIND_SPEED, optimal_min_wind_speed);
        contentValues.put(O_MAX_WIND_SPEED, optimal_max_wind_speed);
        contentValues.put(MIN_RAIN, min_rain_1h);
        contentValues.put(MAX_RAIN, max_rain_1h);
        contentValues.put(O_MIN_RAIN, optimal_min_rain_1h);
        contentValues.put(O_MAX_RAIN, optimal_max_rain_1h);
        contentValues.put(MIN_SNOW, min_snow_1h);
        contentValues.put(MAX_SNOW, max_snow_1h);
        contentValues.put(O_MIN_SNOW, optimal_min_snow_1h);
        contentValues.put(O_MAX_SNOW, optimal_max_snow_1h);
        contentValues.put(O_DAYTIME, optimal_daytime);
        db.insert(TABLE_NAME_PA, null, contentValues);
    }




    }