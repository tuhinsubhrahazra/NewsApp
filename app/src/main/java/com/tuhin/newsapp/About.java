package com.tuhin.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class About extends AppCompatActivity {

    ImageView backButton;
    ImageView linkDin;
    ImageView instagram;
    TextView appName;
    TextView aboutText;
    TextView devoloperName;
    CardView card;
    ImageView supportId;
    ImageView devoloper;
    ImageView gitHub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        backButton = findViewById(R.id.aboutBackButton);
        linkDin = findViewById(R.id.linkDin);
        instagram = findViewById(R.id.instagram);
        appName = findViewById(R.id.app_name);
        aboutText = findViewById(R.id.aboutText);
        card = findViewById(R.id.card);
        supportId = findViewById(R.id.supportId);
        devoloper = findViewById(R.id.devoloper);
        devoloperName = findViewById(R.id.devoloperName);
        gitHub = findViewById(R.id.gitHub);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linkDin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("https://www.linkedin.com/in/tuhin-subhra-hazra-79831a1b8");
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("https://www.instagram.com/htuhinsubhra/");
            }
        });

        gitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("https://github.com/tuhinsubhrahazra");
            }
        });

    }

    private void goToUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}