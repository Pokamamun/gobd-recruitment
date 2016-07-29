package com.appshole.ltd.weatherapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.appshole.weatherapps.constants.Constants;
import com.appshole.weatherapps.dao.IWeatherDao;
import com.appshole.weatherapps.dao.WeatherDao;
import com.appshole.weatherapps.model.Weather;
import com.appshole.weatherapps.util.PublicMethods;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {



// isLocationUpdate variable declear
    private boolean isLocationUpdate=false;

    // counting loop
    private int count=0;

    // declear location array
    ArrayList<LatLng> locationArraylist = new ArrayList<LatLng>();

    // adding value to latlang
    LatLng dhakaLatLng = new LatLng(23.7808871,90.278896);
    LatLng ctgLatLng = new LatLng(22.3283643,91.7530673);
    LatLng syletLatLng = new LatLng(24.904539,91.861101);
    LatLng rajshahiLatLng = new LatLng(24.363589,88.624135);
    LatLng khulnaLatLng = new LatLng(22.845641,89.540328);
    LatLng barishalLatLng = new LatLng(22.702921,90.346597);
    LatLng rangpurLatLng = new LatLng(25.743892,89.275227);

    // declear google map
    private GoogleMap mMap;



    // declaering google api client and location  request variable
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // adding latlang in location array list
        locationArraylist.add(dhakaLatLng);
        locationArraylist.add(ctgLatLng);
        locationArraylist.add(syletLatLng);
        locationArraylist.add(rajshahiLatLng);
        locationArraylist.add(khulnaLatLng);
        locationArraylist.add(barishalLatLng);
        locationArraylist.add(rangpurLatLng);


        if(!PublicMethods.isConnected(MapsActivity.this)){
            PublicMethods.showAlertDialog(MapsActivity.this,"No Internet Connection!");

        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.clear();

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View myContentView = MapsActivity.this.getLayoutInflater().inflate(
                        R.layout.custom_marker, null);
                TextView tvTitle = ((TextView) myContentView
                        .findViewById(R.id.txtcityCountry));
                tvTitle.setText("City :"+marker.getTitle());
                TextView tvSnippet = ((TextView) myContentView
                        .findViewById(R.id.snippet));
                tvSnippet.setText(marker.getSnippet());

                return myContentView;
            }
        });


        addMarkerWithInfo();


    }

    // adding marker  with info in google map
    private void addMarkerWithInfo() {


        addLocation();







    }

    // add location into map
    private void addLocation() {

        if(count<=locationArraylist.size()){

            String apiUrl = "http://api.openweathermap.org/data/2.5/weather?lat="+locationArraylist.get(count).latitude+"&lon="+locationArraylist.get(count).longitude+"&APPID="+ Constants.apikey+"&units=metric";
            getJsonObjectFromWeatherApi(apiUrl);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        CheckGPS();
    }


    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }


    protected void onStop() {

        super.onStop();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MapsActivity.this);
        alertDialogBuilder
                .setMessage(
                        "GPS & Google Location Services must be on to use Weather application...")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent callGPSSettingIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);

                        dialog.cancel();

                    }
                });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void CheckGPS() {

        LocationManager locationManager = (LocationManager) MapsActivity.this
                .getSystemService(MapsActivity.this.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            if (mGoogleApiClient != null) {
                if (!mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            } else if (mGoogleApiClient == null) {
                mGoogleApiClient = new GoogleApiClient.Builder(MapsActivity.this)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this).build();

                mGoogleApiClient.connect();
            }

        } else {
            showGPSDisabledAlertToUser();

        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {

            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(1000 * 5);
            locationRequest.setFastestInterval(5);

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, locationRequest, this);

        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location loc) {


        if (loc != null && !isLocationUpdate && mMap!=null) {

            isLocationUpdate=true;

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                    loc.getLatitude(), loc.getLongitude()), 6));




        }


    }





    // Getting response from weather api
    private void getJsonObjectFromWeatherApi(String url) {


        AsyncHttpClient client = new AsyncHttpClient();



        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {


                    IWeatherDao activitiesListDao =new WeatherDao();
                    ArrayList<Weather>weathersarraylist= activitiesListDao.GetActivitiesListFromJSONObject(response);

                    if(weathersarraylist!=null && weathersarraylist.size()>0){


                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(locationArraylist.get(count).latitude,locationArraylist.get(count).longitude))
                                .title(weathersarraylist.get(0).getCity())
                                .snippet("Date :"+weathersarraylist.get(0).getTodayDate()+ " \n" + "Temp :"+weathersarraylist.get(0).getWeatherTemp()+ "\n"+"Description :"+weathersarraylist.get(0).getWeatherDescription()+"\n"+"Wind :"+weathersarraylist.get(0).getWindSpeed() + " km/h"+"\n"+"Humidity :"+weathersarraylist.get(0).getHumidityValue() + " %")
                        );


                    }



                    count++;
                    addLocation();




                } catch (Exception ex) {
                }
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                try {

                    count++;
                    addLocation();




                } catch (Exception ex) {
                }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);

                try {

                    count++;
                    addLocation();

                } catch (Exception ex) {
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                try {

                    count++;
                    addLocation();

                } catch (Exception ex) {
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                count++;
                addLocation();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                count++;
                addLocation();
            }
        });


    }



}
