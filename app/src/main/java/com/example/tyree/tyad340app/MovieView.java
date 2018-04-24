package com.example.tyree.tyad340app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
//import com.squareup.picasso.Picasso;
//import android.widget.ImageView;
import android.widget.TextView;


public class MovieView extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);

        Intent intent2 = getIntent();

        String title = intent2.getStringExtra("Title");
        String year = intent2.getStringExtra("Year");
        String director = intent2.getStringExtra("Director");
        //String image = intent.getStringExtra("Image");
        String description = intent2.getStringExtra("Description");

        TextView titleView2 = findViewById(R.id.titleView2);
        TextView yearView2 = findViewById(R.id.yearView2);
        TextView descriptionView = findViewById(R.id.descriptionView);
        TextView directorView = findViewById(R.id.directorView);
        //ImageView image = findViewById(R.id.image);

        titleView2.setText("Title: " + title);
        yearView2.setText("Year: " + year);
        directorView.setText("director: " + director);
        descriptionView.setText("Description: " + description);





    }

}