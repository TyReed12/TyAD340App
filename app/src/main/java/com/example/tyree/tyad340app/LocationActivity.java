package com.example.tyree.tyad340app;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v7.widget.Toolbar;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationActivity extends AppCompatActivity {

    private static final String TAG ="LocationActivity";

    private static String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private Boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 15f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getLocationPermission();
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if(mLocationPermissionGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                           Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);
                        }else{
                            Log.d(TAG,"OnComplete: current location is null");
                            Toast.makeText(LocationActivity.this,"Unable to get current Location",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latlng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to lat:" + latlng.latitude + ",lng: " + latlng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,zoom));
    }
    public void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;

                    if(mLocationPermissionGranted){
                        getDeviceLocation();
                    }
            }
        });
        Log.d(TAG, "parseJSON() called");
        parseJSON();
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length>0) {
                    for(int i = 0; i<grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    //initialize map
                    initMap();
                }

            }
        }
    }

    private void parseJSON() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=17&type=2";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Features");

                            for (int i = 1; i < jsonArray.length(); i++) {
                                JSONObject feature = jsonArray.getJSONObject(i);

                                // get coordinates of camera
                                JSONArray coordinates = feature.getJSONArray("PointCoordinate");
                                double latitude = coordinates.getDouble(0);
                                double longitude = coordinates.getDouble(1);

                                JSONArray cameras = feature.getJSONArray("Cameras");
                                JSONObject camera = cameras.getJSONObject(0);
                                String type = camera.getString("Type");
                                String imageURL = camera.getString("ImageUrl");
                                String desc = camera.getString("Description");
                                if (type.equals("sdot")) {
                                    imageURL = "http://www.seattle.gov/trafficcams/images/"
                                            + imageURL;
                                } else {
                                    imageURL = "http://images.wsdot.wa.gov/nw/" + imageURL;
                                }

                                Camera cam = new Camera(desc,imageURL);

                                //Create InfoWindow
                                CustomInfoWindow customWindow =
                                        new CustomInfoWindow(LocationActivity.this);
                                mMap.setInfoWindowAdapter(customWindow);

                                //add marker
                                Marker marker = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(latitude, longitude))
                                        .title(desc));
                                marker.setTag(cam);
                                marker.showInfoWindow();

                            }
                        } catch (JSONException e) {
                            Toast.makeText(LocationActivity.this, e.toString(),
                                    Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(LocationActivity.this, "Network error. " +
                        "Please check your connection.\n"
                        + e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
        requestQueue.add(request);
        }
        private class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

            private Context context;

            public CustomInfoWindow (Context context) {
                this.context = context;
            }

            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents (Marker marker) {
                View view = ((Activity) context).getLayoutInflater().inflate(R.layout.info_window,
                        null);
                TextView cameraName = view.findViewById(R.id.info_window_desc);
                ImageView cameraImg = view.findViewById(R.id.info_window_image);

                cameraName.setText(marker.getTitle());
                Camera camera = (Camera) marker.getTag();
                String imageURL = camera.getImageurl();

                Picasso.with(view.getContext()).load(imageURL).error(R.mipmap.ic_launcher)
                        .resize(640, 480).into(cameraImg,
                        new MarkerCallback(marker));

                return view;
            }

            private class MarkerCallback implements Callback {
                Marker marker = null;

                MarkerCallback (Marker marker) {
                    this.marker = marker;
                }
                public void onError() {
                }
                @Override
                public void onSuccess() {
                    if (marker == null) {
                        return;
                    }
                    if (!marker.isInfoWindowShown()) {
                        return;
                    }
                    marker.hideInfoWindow();
                    marker.showInfoWindow();
                }
            }
        }
}

