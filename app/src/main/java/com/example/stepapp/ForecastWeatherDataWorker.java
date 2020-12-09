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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Objects;

public class ForecastWeatherDataWorker extends WeatherDataWorker {
    private static final String tableName = "forecast_weather_data";
    private static final String apiEndpoint = "forecast";

    public ForecastWeatherDataWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        Log.d("ForecastWeatherData", getApiUrl(apiEndpoint));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, getApiUrl(apiEndpoint), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ForecastWeatherData", response.toString());

                        try {
                            JSONArray list = response.getJSONArray("list");

                            for (int i = 0, size = list.length(); i < size; i++) {
                                JSONObject data = list.getJSONObject(i);
                                StepAppOpenHelper databaseHelper = new StepAppOpenHelper(getApplicationContext());
                                SQLiteDatabase database = databaseHelper.getReadableDatabase();
                                JSONObject city = response.getJSONObject("city");
                                JSONObject coord = city.getJSONObject("coord");
                                JSONObject main = data.getJSONObject("main");
                                JSONObject wind = data.getJSONObject("wind");
                                JSONObject clouds = data.getJSONObject("clouds");
                                JSONObject sys = data.getJSONObject("sys");
                                ContentValues values = new ContentValues();
                                values.put("datetime", String.valueOf(new Date(main.getLong("dt") * 1000)));
                                values.put("longitude", coord.getDouble("lon"));
                                values.put("latitude", coord.getDouble("lat"));
                                values.put("temp", main.getDouble("temp") - 273.15);
                                values.put("feels_like", main.getDouble("feels_like") - 273.15);
                                values.put("temp_min", main.getDouble("temp_min") - 273.15);
                                values.put("temp_max", main.getDouble("temp_max") - 273.15);
                                values.put("pressure", main.getInt("pressure"));
                                values.put("humidity", main.getInt("humidity"));
                                values.put("visibility", data.getInt("visibility"));
                                values.put("wind_speed", wind.getDouble("speed"));
                                values.put("wind_deg", wind.getDouble("deg"));
                                values.put("cloudiness", clouds.getInt("all"));
                                values.put("sunrise", String.valueOf(new Date(((long) city.getInt("sunrise")) * 1000)));
                                values.put("sunset", String.valueOf(new Date(((long) city.getInt("sunset")) * 1000)));
                                values.put("city_name", city.getString("name"));
                                Log.d("ForecastWeatherData", values.getAsString("datetime"));
                                database.insert(tableName, null, values);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("ForecastWeatherData", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ForecastWeatherData", error.toString());
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjectRequest);

        return Result.success();
    }
}
