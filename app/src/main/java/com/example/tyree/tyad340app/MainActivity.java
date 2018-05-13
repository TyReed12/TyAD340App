package com.example.tyree.tyad340app;

import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
    private SharedPreferences sharedPrefs;
    private String visitorName = "";
    private SharedPrefHelper mSharedPrefHelper;

    EditText et_message;
    SharedPreferences sharedPreferences;
    static final String mypref="mypref";
    static final String message="messageKey";
    Boolean navoption = false;
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

        sharedPrefs = this.getPreferences(Context.MODE_PRIVATE);
        mSharedPrefHelper = new SharedPrefHelper(sharedPrefs);

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


    public boolean inputIsValid(String str){
        if (str.length() == 0){
            return false;
        }
        return true;
    }

    //check to see if there edit text field is empty
    public boolean checkEditTextField() {
        EditText etmessage = (EditText) findViewById(R.id.editText);
        String strmessage = etmessage.getText().toString();

        if(TextUtils.isEmpty(strmessage)) {
            etmessage.setError("Input Required");
            return false;
        }
        else
            return true;
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

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Context context = getApplicationContext();
                CharSequence text = "Settings Clicked";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return true;

            case R.id.action_like:
                 Context context2 = getApplicationContext();
                 int duration2 = Toast.LENGTH_SHORT;
                 CharSequence text2 = "Rate My App Clicked";
                 Toast toast2 = Toast.makeText(context2, text2, duration2);
                 toast2.show();

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
            boolean test = checkEditTextField();
            if (test == false) {
                Context context = getApplicationContext();
                CharSequence text = "Input Required before leaving page";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            else {
                Intent intent = new Intent(this, About.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_drawer_movie_list) {
            boolean test = checkEditTextField();
            if (test == false) {
                Context context = getApplicationContext();
                CharSequence text = "Input Required before leaving page";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            else{
            Intent intent = new Intent(this, MovieList.class);
            startActivity(intent);
        }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    //starts MovieList activity
    public void MovieList(View v) {
        boolean test = checkEditTextField();
        if (test == false) {
            return;
        }
        else {
            Intent goToMovieList = new Intent(MainActivity.this, MovieList.class);
            startActivity(goToMovieList);
        }
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        boolean test = checkEditTextField();
        if (test == false) {
            return;
        }
        else {
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
        //Log Errors, Debug, and Warning messages
        Log.e(TAG, "sendMessage Method Error");
        Log.d(TAG, "sendMessage Method Debug");
        Log.w(TAG, "sendMessage Method Warning");
    }
}
