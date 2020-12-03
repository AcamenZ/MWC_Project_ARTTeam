package com.example.stepapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
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

public class WeatherDataWorker extends Worker {
    private static final String apiKey = "3cbfd6817134b07b4fc72b6743692dd8";
    private static final String cityId = "2659836";
    private static final String apiUrl = String.format(
        "http://api.openweathermap.org/data/2.5/weather?id=%s&appid=%s",
        cityId,
        apiKey
    );


    public WeatherDataWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            (Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.d("WeatherData", response.toString());

                    try {
                        StepAppOpenHelper databaseHelper = new StepAppOpenHelper(getApplicationContext());
                        SQLiteDatabase database = databaseHelper.getReadableDatabase();
                        JSONObject coord = response.getJSONObject("coord");
                        JSONObject main = response.getJSONObject("main");
                        JSONObject wind = response.getJSONObject("wind");
                        JSONObject clouds = response.getJSONObject("clouds");
                        JSONObject sys = response.getJSONObject("sys");
                        ContentValues values = new ContentValues();
                        values.put("datetime", String.valueOf(new Date()));
                        values.put("longitude", coord.getDouble("lon"));
                        values.put("latitude", coord.getDouble("lat"));
                        values.put("temp", main.getDouble("temp") - 273.15);
                        values.put("feels_like", main.getDouble("feels_like") - 273.15);
                        values.put("temp_min", main.getDouble("temp_min") - 273.15);
                        values.put("temp_max", main.getDouble("temp_max") - 273.15);
                        values.put("pressure", main.getInt("pressure"));
                        values.put("humidity", main.getInt("humidity"));
                        values.put("visibility", response.getInt("visibility"));
                        values.put("wind_speed", wind.getDouble("speed"));
                        values.put("wind_deg", wind.getDouble("deg"));
                        values.put("cloudiness", clouds.getInt("all"));
                        values.put("sunrise", String.valueOf(new Date(((long)sys.getInt("sunrise")) * 1000)));
                        values.put("sunset", String.valueOf(new Date(((long)sys.getInt("sunset")) * 1000)));
                        values.put("city_name", response.getString("name"));
                        database.insert("forecast_weather_data", null, values);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("WeatherData", e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("WeatherData", error.getMessage());
                }
            });

        queue.add(jsonObjectRequest);

        return Result.success();
    }
}
