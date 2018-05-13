package com.example.tyree.tyad340app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Actionbar extends AppCompatActivity {

    public final String TAG = "Actionbar activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionbar);

        Toolbar myChildToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        Log.d(TAG, "onCreate done");

        Intent intent = getIntent();
        Log.d(TAG, "getIntent done");

        String welcome = intent.getStringExtra("Name");
        Log.d(TAG, "retrieved name");

        TextView greeting = findViewById(R.id.actionbarTextView);

        greeting.setText("Welcome, " + welcome);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_favorite:

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }
}
