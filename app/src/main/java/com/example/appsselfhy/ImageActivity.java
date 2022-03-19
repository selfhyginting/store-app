package com.example.appsselfhy;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ImageView imageView = findViewById(R.id.image_view);
        String url = "https://cdn-images-1.medium.com/max/1200/1*bc-9JmEtRiKTwVqHRTGqbQ.png";
        Picasso.get().load(url).into(imageView);
    }
}