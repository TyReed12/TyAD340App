package com.example.tyree.tyad340app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import android.content.Context;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Log Errors, Debug, and Warning messages
        Log.e(TAG, "OnCreate Method Error");
        Log.d(TAG, "OnCreate Method Debug");
        Log.w(TAG, "OnCreate Method Warning");


    }

    //Toast method
    public void makeToast(View view) {
        Context context = getApplicationContext();
        CharSequence text = "YA TOAST!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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
