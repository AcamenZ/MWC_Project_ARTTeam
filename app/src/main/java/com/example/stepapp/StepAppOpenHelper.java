package com.example.stepapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StepAppOpenHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "stepapp";

    public static final String TABLE_NAME = "num_steps";
    public static final String KEY_ID = "id";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_HOUR = "hour";
    public static final String KEY_DAY = "day";

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
    public static final String NAME_PA = "name_pa";
    public static final String DATE="datetime";
    public static final String RATING="rating";
    public static final String REASON="reason";
    public static final String DATE_PUSH="push_out_time";
    public static final String SENT="sent";


    // Default SQL for creating a table in a database
    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            KEY_ID + " INTEGER PRIMARY KEY, " + KEY_DAY + " TEXT, " + KEY_HOUR + " TEXT, "
            + KEY_TIMESTAMP + " TEXT);";
    public static final String CREATE_TABLE_SQL2 ="CREATE TABLE " + TABLE_NAME_PA +"(" +
            ID_PA + " INTEGER PRIMARY KEY, " + NAME  + " TEXT, " + MIN_TEMP + " FLOAT, " + MAX_TEMP +" FLOAT, " + O_MIN_TEMP +" FLOAT, " +
            O_MAX_TEMP + " FLOAT, " + O_MIN_VIS + " FLOAT, " + MIN_WIND + " FLOAT, " + MAX_WIND + " FLOAT, " + O_MIN_WIND_SPEED + " FLOAT, " +
            O_MAX_WIND_SPEED + " FLOAT, " + MIN_RAIN + " FLOAT, " + MAX_RAIN + " FLOAT, " + O_MIN_RAIN + " FLOAT, " + O_MAX_RAIN + " FLOAT, " +
            MIN_SNOW + " FLOAT, " + MAX_SNOW + " FLOAT, " + O_MIN_SNOW + " FLOAT, " + O_MAX_SNOW + " FLOAT, " + O_DAYTIME + " BOOLEAN);";
    public static final String CREATE_TABLE_SQL3 = "CREATE TABLE " + TABLE_NAME_PAR +" (" +
            ID_PAR + " INTEGER PRIMARY KEY, " + PA_id  + " INT, " + NAME_PA + " TEXT, " + DATE + " TEXT, " + RATING +" INT, " + REASON +" TEXT, " +
            DATE_PUSH + " TEXT, " + SENT + " BOOLEAN);";
    public static final String CREATE_TABLE_SQL4 = "CREATE TABLE forecast_weather_data(\n" +
        "\tid INTEGER NOT NULL PRIMARY KEY,\n" +
        "\tdatetime DATETIME,\n" +
        "\tlongitude FLOAT,\n" +
        "\tlatitude FLOAT,\n" +
        "\ttemp FLOAT,\n" +
        "\tfeels_like FLOAT,\n" +
        "\ttemp_min FLOAT,\n" +
        "\ttemp_max FLOAT,\n" +
        "\tpressure INT,\n" +
        "\thumidity INT,\n" +
        "\tvisibility INT,\n" +
        "\twind_speed FLOAT,\n" +
        "\twind_deg FLOAT,\n" +
        "\tcloudiness FLOAT,\n" +
        "\train_1h FLOAT,\n" +
        "\train_3h FLOAT,\n" +
        "\tsnow_1h FLOAT,\n" +
        "\tsnow_3h FLOAT,\n" +
        "\tsunrise DATETIME,\n" +
        "\tsunset DATETIME,\n" +
        "\tcity_name VARCHAR(50)\n" +
    ")";


    public static final String CREATE_TABLE_SQL5 = "CREATE TABLE historical_weather_data(\n" +
        "\tid INTEGER NOT NULL PRIMARY KEY,\n" +
        "\tdatetime DATETIME,\n" +
        "\tlongitude FLOAT,\n" +
        "\tlatitude FLOAT,\n" +
        "\ttemp FLOAT,\n" +
        "\tfeels_like FLOAT,\n" +
        "\tpressure INT,\n" +
        "\thumidity INT,\n" +
        "\tvisibility INT,\n" +
        "\twind_speed FLOAT,\n" +
        "\twind_deg FLOAT,\n" +
        "\tcloudiness FLOAT,\n" +
        "\tsunrise DATETIME,\n" +
        "\tsunset DATETIME,\n" +
        "\tdescription VARCHAR(50),\n" +
        "\tcity_name VARCHAR(50)\n" +
        ")";

    // Insert in the diffrent table static data
    public static final String sql1 = "INSERT INTO " + TABLE_NAME_PAR + " (" + ID_PAR + ", " + PA_id  + ", " + NAME_PA + ", " + DATE + ", " + RATING + ", " + REASON + ", " +
            DATE_PUSH + ", " + SENT + ") VALUES ('100', '10', 'walking', '14/12/2020', '80', '', '', '')";
    public static final String sql2 = "INSERT INTO " + TABLE_NAME_PAR + " (" + ID_PAR + ", " + PA_id  + ", " + NAME_PA + ", " + DATE + ", " + RATING + ", " + REASON + ", " +
            DATE_PUSH + ", " + SENT + ") VALUES ('101', '20', 'running', '14/12/2020', '70', '', '', '')";
    public static final String sql3 = "INSERT INTO " + TABLE_NAME_PAR + " (" + ID_PAR + ", " + PA_id  + ", " + NAME_PA + ", " + DATE + ", " + RATING + ", " + REASON + ", " +
            DATE_PUSH + ", " + SENT + ") VALUES ('102', '30', 'cycling', '14/12/2020', '60', '', '', '')";
    public static final String sql4 = "INSERT INTO " + TABLE_NAME_PAR + " (" + ID_PAR + ", " + PA_id  + ", " + NAME_PA + ", " + DATE + ", " + RATING + ", " + REASON + ", " +
            DATE_PUSH + ", " + SENT + ") VALUES ('103', '40', 'hiking', '14/12/2020', '50', '', '', '')";
    public static final String sql5 = "INSERT INTO " + TABLE_NAME_PAR + " (" + ID_PAR + ", " + PA_id  + ", " + NAME_PA + ", " + DATE + ", " + RATING + ", " + REASON + ", " +
            DATE_PUSH + ", " + SENT + ") VALUES ('104', '10', 'walking', '15/12/2020', '90', '', '', '')";
    public static final String sql6 = "INSERT INTO " + TABLE_NAME_PAR + " (" + ID_PAR + ", " + PA_id  + ", " + NAME_PA + ", " + DATE + ", " + RATING + ", " + REASON + ", " +
            DATE_PUSH + ", " + SENT + ") VALUES ('105', '20', 'running', '15/12/2020', '80', '', '', '')";
    public static final String sql7 = "INSERT INTO " + TABLE_NAME_PAR + " (" + ID_PAR + ", " + PA_id  + ", " + NAME_PA + ", " + DATE + ", " + RATING + ", " + REASON + ", " +
            DATE_PUSH + ", " + SENT + ") VALUES ('106', '30', 'cycling', '15/12/2020', '70', '', '', '')";
    public static final String sql8 = "INSERT INTO " + TABLE_NAME_PAR + " (" + ID_PAR + ", " + PA_id  + ", " + NAME_PA + ", " + DATE + ", " + RATING + ", " + REASON + ", " +
            DATE_PUSH + ", " + SENT + ") VALUES ('107', '40', 'hiking', '15/12/2020', '60', '', '', '')";
    public static final String sql9 = "INSERT INTO " + TABLE_NAME_PA + " (" + ID_PA + ", " + NAME  + ", " + MIN_TEMP + ", " + MAX_TEMP + ", " + O_MIN_TEMP + ", " + O_MAX_TEMP +  ", " + O_MIN_VIS + ", " + MIN_WIND + ", " + MAX_WIND + ", " + O_MIN_WIND_SPEED + ", " + O_MAX_WIND_SPEED + ", " + MIN_RAIN + ", " + MAX_RAIN + ", " + O_MIN_RAIN + ", " + O_MAX_RAIN + ", " +
            MIN_SNOW + ", " + MAX_SNOW + ", " + O_MIN_SNOW + ", " + O_MAX_SNOW + ", " + O_DAYTIME + ") VALUES ('10', 'walking', '-5', '30', '15', '25', '5', '0', '40', '5', '30', '0', '0', '0', '0', '0', '0', '0', '0', 'FALSE')";
    public static final String sql10 = "INSERT INTO " + TABLE_NAME_PA + " (" + ID_PA + ", " + NAME  + ", " + MIN_TEMP + ", " + MAX_TEMP + ", " + O_MIN_TEMP + ", " + O_MAX_TEMP +  ", " + O_MIN_VIS + ", " + MIN_WIND + ", " + MAX_WIND + ", " + O_MIN_WIND_SPEED + ", " + O_MAX_WIND_SPEED + ", " + MIN_RAIN + ", " + MAX_RAIN + ", " + O_MIN_RAIN + ", " + O_MAX_RAIN + ", " +
            MIN_SNOW + ", " + MAX_SNOW + ", " + O_MIN_SNOW + ", " + O_MAX_SNOW + ", " + O_DAYTIME + ") VALUES ('20', 'running', '0', '25', '10', '20', '10', '0', '30', '5', '25', '0', '0', '0', '0', '0', '0', '0', '0', 'FALSE')";
    public static final String sql11 = "INSERT INTO " + TABLE_NAME_PA + " (" + ID_PA + ", " + NAME  + ", " + MIN_TEMP + ", " + MAX_TEMP + ", " + O_MIN_TEMP + ", " + O_MAX_TEMP +  ", " + O_MIN_VIS + ", " + MIN_WIND + ", " + MAX_WIND + ", " + O_MIN_WIND_SPEED + ", " + O_MAX_WIND_SPEED + ", " + MIN_RAIN + ", " + MAX_RAIN + ", " + O_MIN_RAIN + ", " + O_MAX_RAIN + ", " +
            MIN_SNOW + ", " + MAX_SNOW + ", " + O_MIN_SNOW + ", " + O_MAX_SNOW + ", " + O_DAYTIME + ") VALUES ('30', 'cycling', '0', '25', '5', '20', '15', '0', '30', '5', '25', '0', '0', '0', '0', '0', '0', '0', '0', 'TRUE')";
    public static final String sql12 = "INSERT INTO " + TABLE_NAME_PA + " (" + ID_PA + ", " + NAME  + ", " + MIN_TEMP + ", " + MAX_TEMP + ", " + O_MIN_TEMP + ", " + O_MAX_TEMP +  ", " + O_MIN_VIS + ", " + MIN_WIND + ", " + MAX_WIND + ", " + O_MIN_WIND_SPEED + ", " + O_MAX_WIND_SPEED + ", " + MIN_RAIN + ", " + MAX_RAIN + ", " + O_MIN_RAIN + ", " + O_MAX_RAIN + ", " +
            MIN_SNOW + ", " + MAX_SNOW + ", " + O_MIN_SNOW + ", " + O_MAX_SNOW + ", " + O_DAYTIME + ") VALUES ('40', 'hiking', '10', '30', '15', '25', '20', '0', '25', '5', '20', '0', '0', '0', '0', '0', '0', '0', '0', 'TRUE')";


    // The constructor
    public StepAppOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // onCreate
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
        db.execSQL(CREATE_TABLE_SQL2);
        db.execSQL(CREATE_TABLE_SQL3);
        db.execSQL(CREATE_TABLE_SQL4);
        db.execSQL(CREATE_TABLE_SQL5);
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6);
        db.execSQL(sql7);
        db.execSQL(sql8);
        db.execSQL(sql9);
        db.execSQL(sql10);
        db.execSQL(sql11);
        db.execSQL(sql12);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // nothing to do here
    }

/*  The following method computes the score for each physical activity and adds in the table "Physical_activities_rankings" the activity with the highest score along with its id and name.
    Then, it will automatically send a notification to the user. At the same time, it adds in the table date and time of the notification.
    Based on the score, this method will choose the right notification. Below 50%, it chooses one message among the "negative" ones, while over 50% it chooses one message among the "positive" ones.

    public static void loadvalueinPAR(Context context, String notification){


        StepAppOpenHelper databaseHelper = new StepAppOpenHelper(context);
        SQLiteDatabase databaseR = databaseHelper.getReadableDatabase();
        SQLiteDatabase databaseW = databaseHelper.getWritableDatabase();

        String day;
        String hour;

        Cursor cursor = databaseR.query(StepAppOpenHelper.forecast_weather_data, null, where, whereArgs, null,
                null, null );

        int score10= 1; //-5, 30, 15, 25, 5, 0, 40, 5, 30, 0, 0, 0, 0, 0, 0, 0, 0;
        int score20= 2; //0, 25, 10, 20, 10, 0, 30, 5, 25, 0, 0, 0, 0, 0, 0, 0, 0;
        int score30= 10; //0, 25, 5, 20, 15, 0, 30, 5, 25, 0, 0, 0, 0, 0, 0, 0, 0;
        int score40= 3; //10, 30, 15, 25, 20, 0, 25, 5, 20, 0, 0, 0, 0, 0, 0, 0, 0;


        int score = Math.max(score10, Math.max(score20, Math.max(score30,score40)));

        long timeInMillis = System.currentTimeMillis() ;
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        jdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        String date = jdf.format(timeInMillis);


        // Get the date, the day and the hour
        day = date.substring(0,10);
        hour = date.substring(11,13);

        ContentValues values = new ContentValues();
        values.put(StepAppOpenHelper.DATE, day);
        values.put(StepAppOpenHelper.RATING,score);
        values.put(StepAppOpenHelper.REASON, );
        values.put(StepAppOpenHelper.DATE_PUSH, );
        values.put(StepAppOpenHelper.SENT, true);
        databaseW.insert(StepAppOpenHelper.TABLE_NAME_PAR, null, values);

        /* Possible notification messages
1.Hey it's nice outside how about some physical activity?
2.It's a shame to waste a beautiful day like today, do some physical activity :)
3.Are you planning to exercise today? It's a great day outside.
4.Make some physical activity would be a good idea today, take advantage of this beautiful day.
5.Have you already done physical activity today? If the answer is no, get out of the house, it's nice outside.
6.Today it is better to do some activities at home, the weather is bad.
7.The weather today is not favorable for physical activity, tomorrow will be better.
8.Today, you could stay home and get some rest or do some physical activity.
9.Postpone your physical activity outdoors until tomorrow.
10.I hope you have something to do at home, I don't recommend you to do physical activity outside today.


    } */



    /**
     * Utility function to load all records in the database
     *
     * @param context: application context
     */
    public static void loadRecords(Context context){
        List<String> dates = new LinkedList<String>();
        StepAppOpenHelper databaseHelper = new StepAppOpenHelper(context);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        String [] columns = new String [] {StepAppOpenHelper.KEY_TIMESTAMP};
        Cursor cursor = database.query(StepAppOpenHelper.TABLE_NAME, columns, null, null, StepAppOpenHelper.KEY_TIMESTAMP,
                null, null );

        // iterate over returned elements
        cursor.moveToFirst();
        for (int index=0; index < cursor.getCount(); index++){
            dates.add(cursor.getString(0));
            cursor.moveToNext();
        }
        database.close();

        Log.d("STORED TIMESTAMPS: ", String.valueOf(dates));
    }


    /**
     * Utility function to delete all records from the data base
     *
     * @param context: application context
     */
    public static void deleteRecords(Context context){
        StepAppOpenHelper databaseHelper = new StepAppOpenHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        int numberDeletedRecords =0;

        numberDeletedRecords = database.delete(StepAppOpenHelper.TABLE_NAME, null, null);
        database.close();

        // display the number of deleted records with a Toast message
        Toast.makeText(context,"Deleted " + String.valueOf(numberDeletedRecords) + " steps",Toast.LENGTH_LONG).show();
    }

    /**
     * Utility function to load records from a single day
     *
     * @param context: application context
     * @param date: today's date
     * @return numSteps: an integer value with the number of records in the database
     */
    //
    public static Integer loadSingleRecord(Context context, String date){
        List<String> steps = new LinkedList<String>();
        // Get the readable database
        StepAppOpenHelper databaseHelper = new StepAppOpenHelper(context);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        String where = StepAppOpenHelper.KEY_DAY + " = ?";
        String [] whereArgs = { date };

        Cursor cursor = database.query(StepAppOpenHelper.TABLE_NAME, null, where, whereArgs, null,
                null, null );

        // iterate over returned elements
        cursor.moveToFirst();
        for (int index=0; index < cursor.getCount(); index++){
            steps.add(cursor.getString(0));
            cursor.moveToNext();
        }
        database.close();

        Integer numSteps = steps.size();
        Log.d("STORED STEPS TODAY: ", String.valueOf(numSteps));
        return numSteps;
    }

    /**
     * Utility function to get the number of steps by hour for current date
     *
     * @param context: application context
     * @param date: today's date
     * @return map: map with key-value pairs hour->number of steps
     */
    //
    public static Map<Integer, Integer> loadStepsByHour(Context context, String date){
        // 1. Define a map to store the hour and number of steps as key-value pairs
        Map<Integer, Integer>  map = new HashMap<> ();

        // 2. Get the readable database
        StepAppOpenHelper databaseHelper = new StepAppOpenHelper(context);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        // 3. Define the query to get the data
        Cursor cursor = database.rawQuery("SELECT hour, COUNT(*)  FROM num_steps " +
                "WHERE day = ? GROUP BY hour ORDER BY  hour ASC ", new String [] {date});

        // 4. Iterate over returned elements on the cursor
        cursor.moveToFirst();
        for (int index=0; index < cursor.getCount(); index++){
            Integer tmpKey = Integer.parseInt(cursor.getString(0));
            Integer tmpValue = Integer.parseInt(cursor.getString(1));

            //2. Put the data from the database into the map
            map.put(tmpKey, tmpValue);


            cursor.moveToNext();
        }

        // 5. Close the cursor and database
        cursor.close();
        database.close();

        // 6. Return the map with hours and number of steps
        return map;
    }

    /**
     * Utility function to get the number of steps by day
     *
     * @param context: application context
     * @return map: map with key-value pairs hour->number of steps
     */
    //
    public static Map<String, Integer> loadStepsByDay(Context context){
        // 1. Define a map to store the hour and number of steps as key-value pairs
        Map<String, Integer>  map = new TreeMap<>();

        // 2. Get the readable database
        StepAppOpenHelper databaseHelper = new StepAppOpenHelper(context);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        // 3. Define the query to get the data
        Cursor cursor = database.rawQuery("SELECT day, COUNT(*)  FROM num_steps " +
                "GROUP BY day ORDER BY day ASC ", new String [] {});

        // 4. Iterate over returned elements on the cursor
        cursor.moveToFirst();
        for (int index=0; index < cursor.getCount(); index++){
            String tmpKey = cursor.getString(0);
            Integer tmpValue = Integer.parseInt(cursor.getString(1));

            // Put the data from the database into the map
            map.put(tmpKey, tmpValue);
            cursor.moveToNext();
        }

        // 5. Close the cursor and database
        cursor.close();
        database.close();

        // 6. Return the map with hours and number of steps
        return map;
    }

    public static Map<String, Double> loadTempByDay(Context context) {

        Map<String,Double> map = new HashMap<>();

        StepAppOpenHelper databaseHelper = new StepAppOpenHelper(context);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Cursor cursor = database.query(
                "forecast_weather_data",
                new String[] { "id", "datetime", "temp" },
                null, null, // selection, selectionArgs
                null, null, "datetime", null);

        try {
            while(cursor.moveToNext()) {
                SimpleDateFormat formatIn = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'XXX YYYY");
                SimpleDateFormat formatOut = new SimpleDateFormat("EEE a");
                String date = formatOut.format(formatIn.parse (cursor.getString(1)));
                Double temp = cursor.getDouble(2);
                map.put(date, temp);
            }
        } catch (Exception e) {
            Log.d("loadTempByDay", e.toString());
        } finally {
            cursor.close();
            database.close();
        }


        // 6. Return the map with dates and temperatures
        return map;
    }

    public static Map<String, Integer> loadPressureByDay(Context context) {
        Map<String,Integer> map = new HashMap<>();

        StepAppOpenHelper databaseHelper = new StepAppOpenHelper(context);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Cursor cursor = database.query(
                "forecast_weather_data",
                new String[] { "id", "datetime", "pressure" },
                null, null, // selection, selectionArgs
                null, null, "datetime", null);

        try {
            while(cursor.moveToNext()) {
                SimpleDateFormat formatIn = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'XXX YYYY");
                SimpleDateFormat formatOut = new SimpleDateFormat("EEE a");
                String date = formatOut.format(formatIn.parse (cursor.getString(1)));
                Integer pressure = cursor.getInt(2);
                map.put(date, pressure);
            }
        } catch (Exception e) {
            Log.d("loadPressureByDay", e.toString());
        } finally {
            cursor.close();
            database.close();
        }


        // 6. Return the map with dates and pressures
        return map;
    }


    public static Map<String, Double> loadWindSpeedByDay(Context context) {

        Map<String,Double> map = new HashMap<>();

        StepAppOpenHelper databaseHelper = new StepAppOpenHelper(context);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Cursor cursor = database.query(
                "forecast_weather_data",
                new String[] { "id", "datetime", "wind_speed" },
                null, null, // selection, selectionArgs
                null, null, "datetime", null);

        try {
            while(cursor.moveToNext()) {
                SimpleDateFormat formatIn = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'XXX YYYY");
                SimpleDateFormat formatOut = new SimpleDateFormat("EEE a");
                String date = formatOut.format(formatIn.parse (cursor.getString(1)));
                Double temp = cursor.getDouble(2);
                map.put(date, temp);
            }
        } catch (Exception e) {
            Log.d("loadWindSpeedByDay", e.toString());
        } finally {
            cursor.close();
            database.close();
        }


        // 6. Return the map with dates and wind speeds
        return map;
    }

    public static Map<String, Integer> loadHumidityByDay(Context context) {

        Map<String,Integer> map = new HashMap<>();

        StepAppOpenHelper databaseHelper = new StepAppOpenHelper(context);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Cursor cursor = database.query(
                "forecast_weather_data",
                new String[] { "id", "datetime", "humidity" },
                null, null, // selection, selectionArgs
                null, null, "datetime", null);

        try {
            while(cursor.moveToNext()) {
                SimpleDateFormat formatIn = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'XXX YYYY");
                SimpleDateFormat formatOut = new SimpleDateFormat("EEE a");
                String date = formatOut.format(formatIn.parse (cursor.getString(1)));
                Integer humidity = cursor.getInt(2);
                map.put(date, humidity);
            }
        } catch (Exception e) {
            Log.d("loadHumidityByDay", e.toString());
        } finally {
            cursor.close();
            database.close();
        }


        // 6. Return the map with dates and humidity
        return map;
    }

    public static Map<String, Integer> loadRankingByDay(Context context) {

        Map<String,Integer> map = new HashMap<>();

        StepAppOpenHelper databaseHelper = new StepAppOpenHelper(context);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        Cursor cursor = database.query(
                "Physical_activities_rankings",
                new String[] { "id_r", "name_pa", "rating" },
                null, null, // selection, selectionArgs
                null, null, "datetime", null);

        try {
            while(cursor.moveToNext()) {
                String activity_name = cursor.getString(1);
                Integer rating = cursor.getInt(2);
                map.put(activity_name, rating);
            }
        } catch (Exception e) {
            Log.d("loadRankingByDay", e.toString());
        } finally {
            cursor.close();
            database.close();
        }


        // 6. Return the map with activity names and ratings
        return map;
    }
}









