package com.example.stepapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.example.stepapp.ui.home.HomeFragment;
import com.example.stepapp.ui.profile.ProfileFragment;
import com.example.stepapp.ui.report.DayFragment;
import com.example.stepapp.ui.report.HourFragment;
import com.example.stepapp.ui.report.TempFragment;
import com.example.stepapp.ui.report.RankFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;

    private DrawerLayout drawer;
    private NavigationView navigationView;

    private AppBarConfiguration mAppBarConfiguration;
    private static final int REQUEST_ACTIVITY_RECOGNITION_PERMISSION = 45;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 46;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 47;

    private boolean runningQOrLater =
            android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;

    protected static String lat = "46.0037";
    protected static String lon = "8.9511";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            lat = Double.valueOf(location.getLatitude()).toString();
                            lon = Double.valueOf(location.getLongitude()).toString();
                        }
                    }
                });
        }



        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        // Setup drawer view

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_hour,R.id.nav_day, R.id.nav_temp, R.id.nav_rank, R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        setupDrawerContent(navigationView);

        getWriteExternalStorage();
        getReadExternalStorage();

        // Ask for activity recognition permission
        if (runningQOrLater) {
            getActivity();
        }

        Data.Builder data = new Data.Builder();
        data.putString("lat", lat);
        data.putString("lon", lon);

        // create periodic work request for the weather data
        PeriodicWorkRequest forecastWeatherDataWorkRequest =
                new PeriodicWorkRequest.Builder(ForecastWeatherDataWorker.class, 6, TimeUnit.HOURS)
                        .setInputData(data.build())
                        .build();

        PeriodicWorkRequest historicalWeatherDataWorkRequest =
                new PeriodicWorkRequest.Builder(HistoricalWeatherDataWorker.class, 1, TimeUnit.HOURS)
                        .setInputData(data.build())
                        .build();

        // submit the weather data work request
        WorkManager
                .getInstance(this.getApplicationContext())
                .enqueue(forecastWeatherDataWorkRequest);
        WorkManager
                .getInstance(this.getApplicationContext())
                .enqueue(historicalWeatherDataWorkRequest);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                fragmentClass = HomeFragment.class;
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Steps");
                }
                break;
            case R.id.nav_hour:
                fragmentClass = HourFragment.class;
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Hour");
                }
                break;
            case R.id.nav_day:
                fragmentClass = DayFragment.class;
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Day");
                }
                break;
            case R.id.nav_temp:
                fragmentClass = TempFragment.class;
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Weather");
                }
                break;
            case R.id.nav_rank:
                fragmentClass = RankFragment.class;
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Ranking");
                }
                break;
            case R.id.nav_profile:
                fragmentClass = ProfileFragment.class;
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Profile");
                }
                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawer.closeDrawers();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    // Ask for permission
    private void getActivity() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACTIVITY_RECOGNITION},
                    REQUEST_ACTIVITY_RECOGNITION_PERMISSION);
        }
    }

    // Ask for write external storage permission
    private void getWriteExternalStorage() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    // Ask for read external storage permission
    private void getReadExternalStorage() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_EXTERNAL_STORAGE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ACTIVITY_RECOGNITION_PERMISSION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getActivity();
                }  else {
                    Toast.makeText(this,
                            R.string.permission_denied,
                            Toast.LENGTH_SHORT).show();
                }

            case REQUEST_WRITE_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getWriteExternalStorage();
                }  else {
                    Toast.makeText(this,
                            R.string.permission_denied,
                            Toast.LENGTH_SHORT).show();
                }
            case REQUEST_READ_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getReadExternalStorage();
                }  else {
                    Toast.makeText(this,
                            R.string.permission_denied,
                            Toast.LENGTH_SHORT).show();
                }
        }
    }
}