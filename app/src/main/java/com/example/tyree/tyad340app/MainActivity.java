package com.example.tyree.tyad340app;

import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Menu;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import android.content.Context;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final String TAG = MainActivity.class.getSimpleName();

    EditText et_message;
    SharedPreferences sharedPreferences;
    static final String mypref="mypref";
    static final String message="messageKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_drawer);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, myToolbar, R.string.drawerOpen, R.string.drawerClose);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        et_message=(EditText)findViewById(R.id.editText);
        sharedPreferences = getSharedPreferences(mypref,Context.MODE_PRIVATE);
        if(sharedPreferences.contains(message)){
            et_message.setText(sharedPreferences.getString(message,""));
        }

        //Log Errors, Debug, and Warning messages
        Log.e(TAG, "OnCreate Method Error");
        Log.d(TAG, "OnCreate Method Debug");
        Log.w(TAG, "OnCreate Method Warning");
    }

    public void save(View v){
        String m=et_message.getText().toString();
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(message,m);
        editor.commit();

        //Toast
        Context context = getApplicationContext();
        CharSequence text = "Input Saved";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void clear(View v) {
        et_message.setText("");

        //Toast
        Context context = getApplicationContext();
        CharSequence text = "Input Field Cleared";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void retrieve(View v) {
        sharedPreferences = getSharedPreferences(mypref,Context.MODE_PRIVATE);
        if(sharedPreferences.contains(message)) {
            et_message.setText(sharedPreferences.getString(message, ""));
            Context context = getApplicationContext();
            CharSequence text = "Last Saved Entry Retreived";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else{
            Context context = getApplicationContext();
            CharSequence text = "No Previous Entry Found";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Configure the search info and add any event listeners...

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_like:
                // User chose the "Like" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_drawer_about) {

            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        } else if (id == R.id.nav_drawer_movie_list) {

            Intent intent = new Intent(this, MovieList.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //starts MovieList activity
    public void MovieList(View v) {
        Intent goToMovieList = new Intent(MainActivity.this, MovieList.class);
        startActivity(goToMovieList);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

        //Log Errors, Debug, and Warning messages
        Log.e(TAG, "sendMessage Method Error");
        Log.d(TAG, "sendMessage Method Debug");
        Log.w(TAG, "sendMessage Method Warning");
    }
}
