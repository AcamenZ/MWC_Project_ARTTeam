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

abstract public class WeatherDataWorker extends Worker {
    protected static final String apiKey = "3cbfd6817134b07b4fc72b6743692dd8";
    protected static String lat = "46.0037";
    protected static String lon = "8.9511";

    protected String getApiUrl(String endpoint) {
        return String.format(
                "https://api.openweathermap.org/data/2.5/%s?lat=%s&lon=%s&appid=%s",
                endpoint,
                lat,
                lon,
                apiKey
        );
    }

    protected String getApiUrl(String endpoint, Long timestamp) {
        return String.format(
                "https://api.openweathermap.org/data/2.5/%s?lat=%s&lon=%s&appid=%s&dt=%s",
                endpoint,
                lat,
                lon,
                apiKey,
                timestamp.toString()
        );
    }

    public WeatherDataWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    abstract public Result doWork();
}
