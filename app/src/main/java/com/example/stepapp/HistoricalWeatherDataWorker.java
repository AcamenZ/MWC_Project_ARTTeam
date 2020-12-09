package com.example.stepapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Objects;

public class HistoricalWeatherDataWorker extends WeatherDataWorker {
    private static final String tableName = "historical_weather_data";
    private static final String apiEndpoint = "onecall/timemachine";

    public HistoricalWeatherDataWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        Log.d("HistoricalWeatherData", getApiUrl(apiEndpoint, (long) (System.currentTimeMillis() / 1000 - 3600)));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, getApiUrl(apiEndpoint, (long) (System.currentTimeMillis() / 1000 - 3600)), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("HistoricalWeatherData", response.toString());

                        try {
                            StepAppOpenHelper databaseHelper = new StepAppOpenHelper(getApplicationContext());
                            SQLiteDatabase database = databaseHelper.getReadableDatabase();
                            JSONObject main = response.getJSONObject("current");
                            ContentValues values = new ContentValues();
                            values.put("datetime", String.valueOf(new Date()));
                            values.put("longitude", response.getDouble("lon"));
                            values.put("latitude", response.getDouble("lat"));
                            values.put("temp", main.getDouble("temp") - 273.15);
                            values.put("feels_like", main.getDouble("feels_like") - 273.15);
                            values.put("pressure", main.getInt("pressure"));
                            values.put("humidity", main.getInt("humidity"));
                            values.put("visibility", main.getInt("visibility"));
                            values.put("wind_speed", main.getDouble("wind_speed"));
                            values.put("wind_deg", main.getDouble("wind_deg"));
                            values.put("cloudiness", main.getInt("clouds"));
                            values.put("sunrise", String.valueOf(new Date(((long)main.getInt("sunrise")) * 1000)));
                            values.put("sunset", String.valueOf(new Date(((long)main.getInt("sunset")) * 1000)));
                            values.put("city_name", response.getString("name"));
                            database.insert(tableName, null, values);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("HistoricalWeatherData", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("HistoricalWeatherData", error.toString());
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjectRequest);

        return Result.success();
    }
}
