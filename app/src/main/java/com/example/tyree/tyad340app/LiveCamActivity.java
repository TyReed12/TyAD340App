package com.example.tyree.tyad340app;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class LiveCamActivity extends AppCompatActivity {

    private static final String URL_DATA = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";

    private RecyclerView recyclerView;
    private CameraView adapter;
    private ArrayList<Camera> cameraList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_cam);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cameraList = new ArrayList<>();

        adapter = new CameraView(LiveCamActivity.this, cameraList);
        recyclerView.setAdapter(adapter);

        mRequestQueue = Volley.newRequestQueue(this);

        if (isOnline()){
        loadRecyclerViewData();
        }
        else {//toast}
    }}

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void loadRecyclerViewData() {

            Log.d("JSON","LoadRecycler Start");
            String url = URL_DATA;
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading data...");
            progressDialog.show();

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response",response.toString());
                            try {
                                JSONArray jsonArray = response.getJSONArray("Features");

                                for (int i = 0; i < jsonArray.length(); i+=2) {
                                    JSONObject feature = jsonArray.getJSONObject(i);

                                    JSONArray f =  feature.getJSONArray("Cameras");
                                    for(int j = 0; j < f.length(); j++) {
                                        JSONObject ff =  f.getJSONObject(j);

                                        String description = ff.getString("Description");
                                        String imageURL = ff.getString("ImageUrl");

                                        String type = ff.getString("Type");

                                        if(type.equals("sdot")){
                                            imageURL = "http://www.seattle.gov/trafficcams/images/" + imageURL;
                                        } else {
                                            imageURL = "http://images.wsdot.wa.gov/nw/" + imageURL;
                                        }
                                        cameraList.add(new Camera(description, imageURL));
                                    }

                                }
                                adapter.notifyDataSetChanged();

                            } catch (JSONException error) { // json error
                                Toast.makeText(LiveCamActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            mRequestQueue.add(request);
    }
}