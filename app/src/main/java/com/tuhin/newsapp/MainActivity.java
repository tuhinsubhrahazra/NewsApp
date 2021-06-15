package com.tuhin.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yarolegovich.slidingrootnav.SlidingRootNavLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<News> newsArrayList  = new ArrayList<>();
    RequestQueue requestQueue;
    ViewPager2 viewPager2;
    ProgressBar progressBar;
    Toolbar toolbar;
    ImageView drawerIcon;

    CardView homeCard;
    CardView businessCard;
    CardView entertainmentCard;
    CardView healthCard;
    CardView scienceCard;
    CardView sportsCard;
    CardView technologyCard;
    CardView aboutCard;

    TextView textHome;
    TextView textBusiness;
    TextView textEntertainment;
    TextView textHealth;
    TextView textScience;
    TextView textSports;
    TextView textTechnology;

    TextView catagory;

    ImageView iconHome;
    ImageView iconBusiness;
    ImageView iconEntertainment;
    ImageView iconHealth;
    ImageView iconScience;
    ImageView iconSports;
    ImageView iconTechnology;

    View fragmentContainer;

    public static int menu_position = 0;

    public static Context MainContext;

//    public static String url = "https://gnews.io/api/v4/search?q=example&token=168c1fb74a03ca4a413fc2f9b7378c59";
//    public static String url1 = "https://gnews.io/api/v4/search?q=example&token=168c1fb74a03ca4a413fc2f9b7378c59";
//    public static String url2 = "https://saurav.tech/NewsAPI/everything/cnn.json";
//    public static String url3 = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json";
//    public static String url4 = "https://newsapi.org/v2/everything?q=tesla&from=2021-05-11&sortBy=publishedAt&apiKey=d2c85a25a7d246b382d9af7ca78da649";

    public static String General_url = "https://saurav.tech/NewsAPI/top-headlines/category/general/in.json";
    public static String business_url = "https://saurav.tech/NewsAPI/top-headlines/category/business/in.json";
    public static String entertainment_url = "https://saurav.tech/NewsAPI/top-headlines/category/entertainment/in.json";
    public static String health_url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json";
    public static String science_url = "https://saurav.tech/NewsAPI/top-headlines/category/science/in.json";
    public static String sports_url = "https://saurav.tech/NewsAPI/top-headlines/category/sports/in.json";
    public static String technology_url = "https://saurav.tech/NewsAPI/top-headlines/category/technology/in.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainContext = getApplicationContext();

        drawerIcon = findViewById(R.id.drawerIcon);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SlidingRootNav slidingRootNav = new SlidingRootNavBuilder(this)
                .withMenuLayout(R.layout.menu_left_drawer)
                .withMenuOpened(false)
                .withMenuLocked(false)
                .withGravity(SlideGravity.LEFT)
                .withContentClickableWhenMenuOpened(true)
                .inject();


        drawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingRootNav.isMenuOpened()) {
                    slidingRootNav.closeMenu();
                } else {
                    slidingRootNav.openMenu();
                }
            }
        });

        fragmentContainer = findViewById(R.id.fragment_container);
        fragmentContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingRootNav.closeMenu();
            }
        });

        homeCard = findViewById(R.id.homeCard);
        businessCard = findViewById(R.id.businessCard);
        entertainmentCard = findViewById(R.id.entertainmentCard);
        healthCard = findViewById(R.id.healthCard);
        scienceCard = findViewById(R.id.scienceCard);
        sportsCard = findViewById(R.id.sportsCard);
        technologyCard = findViewById(R.id.technologyCard);
        aboutCard = findViewById(R.id.aboutCard);

        textHome = findViewById(R.id.textHome);
        textBusiness = findViewById(R.id.textBusiness);
        textEntertainment = findViewById(R.id.textEntertainment);
        textHealth = findViewById(R.id.textHealth);
        textScience = findViewById(R.id.textScience);
        textSports = findViewById(R.id.textSports);
        textTechnology = findViewById(R.id.textTechnology);

        catagory = findViewById(R.id.catagory);

        iconHome = findViewById(R.id.iconHome);
        iconBusiness = findViewById(R.id.iconBusiness);
        iconEntertainment = findViewById(R.id.iconEntertainment);
        iconHealth = findViewById(R.id.iconHealth);
        iconScience = findViewById(R.id.iconScience);
        iconSports = findViewById(R.id.iconSports);
        iconTechnology = findViewById(R.id.iconTechnology);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Home()).commit();

        homeCard.setCardBackgroundColor(getResources().getColor(R.color.BlueTint));
        textHome.setTextColor(getResources().getColor(R.color.white));
        iconHome.setImageResource(R.drawable.ic_baseline_library_books_24);
        catagory.setText("All News");

        aboutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,About.class);
                MainActivity.this.startActivity(intent);
            }
        });

        homeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Home()).commit();
                slidingRootNav.closeMenu();
                setcolour(menu_position);
                menu_position = 0;
                textHome.setTextColor(getResources().getColor(R.color.white));
                homeCard.setCardBackgroundColor(getResources().getColor(R.color.BlueTint));
                iconHome.setImageResource(R.drawable.ic_baseline_library_books_24);
                catagory.setText("All News");
            }
        });

        businessCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Business()).commit();
                slidingRootNav.closeMenu();
                setcolour(menu_position);
                menu_position = 1;
                textBusiness.setTextColor(getResources().getColor(R.color.white));
                businessCard.setCardBackgroundColor(getResources().getColor(R.color.BlueTint));
                iconBusiness.setImageResource(R.drawable.ic_baseline_business_center_24);
                catagory.setText("Business");

            }
        });

        entertainmentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Entertainment()).commit();
                slidingRootNav.closeMenu();
                setcolour(menu_position);
                menu_position = 2;
                textEntertainment.setTextColor(getResources().getColor(R.color.white));
                entertainmentCard.setCardBackgroundColor(getResources().getColor(R.color.BlueTint));
                iconEntertainment.setImageResource(R.drawable.ic_baseline_videocam_24);
                catagory.setText("Entertainment");
            }
        });

        healthCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Health()).commit();
                slidingRootNav.closeMenu();
                setcolour(menu_position);
                menu_position = 3;
                textHealth.setTextColor(getResources().getColor(R.color.white));
                healthCard.setCardBackgroundColor(getResources().getColor(R.color.BlueTint));
                iconHealth.setImageResource(R.drawable.ic_baseline_favorite_24);
                catagory.setText("Health");

            }
        });

        scienceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Science()).commit();
                slidingRootNav.closeMenu();
                setcolour(menu_position);
                menu_position = 4;
                textScience.setTextColor(getResources().getColor(R.color.white));
                scienceCard.setCardBackgroundColor(getResources().getColor(R.color.BlueTint));
                iconScience.setImageResource(R.drawable.ic_baseline_biotech_24);
                catagory.setText("Science");
            }
        });

        sportsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Sports()).commit();
                slidingRootNav.closeMenu();
                setcolour(menu_position);
                menu_position = 5;
                textSports.setTextColor(getResources().getColor(R.color.white));
                sportsCard.setCardBackgroundColor(getResources().getColor(R.color.BlueTint));
                iconSports.setImageResource(R.drawable.ic_baseline_sports_handball_24);
                catagory.setText("Sports");
            }
        });

        technologyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Technology()).commit();
                slidingRootNav.closeMenu();
                setcolour(menu_position);
                menu_position = 6;
                textTechnology.setTextColor(getResources().getColor(R.color.white));
                technologyCard.setCardBackgroundColor(getResources().getColor(R.color.BlueTint));
                iconTechnology.setImageResource(R.drawable.ic_baseline_computer_24);
                catagory.setText("Technology");
            }
        });

    }

    private void setcolour(int menu_position) {
        switch (menu_position){
            case 0:
                homeCard.setCardBackgroundColor(getResources().getColor(R.color.white));
                textHome.setTextColor(getResources().getColor(R.color.black));
                iconHome.setImageResource(R.drawable.ic_baseline_library_books_blue_24);
                break;

            case 1:
                businessCard.setCardBackgroundColor(getResources().getColor(R.color.white));
                textBusiness.setTextColor(getResources().getColor(R.color.black));
                iconBusiness.setImageResource(R.drawable.ic_baseline_business_blue_center_24);
                break;

            case 2:
                entertainmentCard.setCardBackgroundColor(getResources().getColor(R.color.white));
                textEntertainment.setTextColor(getResources().getColor(R.color.black));
                iconEntertainment.setImageResource(R.drawable.ic_baseline_videocam_blue_24);
                break;

            case 3:
                healthCard.setCardBackgroundColor(getResources().getColor(R.color.white));
                textHealth.setTextColor(getResources().getColor(R.color.black));
                iconHealth.setImageResource(R.drawable.ic_baseline_favorite_blue_24);
                break;

            case 4:
                scienceCard.setCardBackgroundColor(getResources().getColor(R.color.white));
                textScience.setTextColor(getResources().getColor(R.color.black));
                iconScience.setImageResource(R.drawable.ic_baseline_biotech_blue_24);
                break;

            case 5:
                sportsCard.setCardBackgroundColor(getResources().getColor(R.color.white));
                textSports.setTextColor(getResources().getColor(R.color.black));
                iconSports.setImageResource(R.drawable.ic_baseline_sports_handball_blue_24);
                break;

            case 6:
                technologyCard.setCardBackgroundColor(getResources().getColor(R.color.white));
                textTechnology.setTextColor(getResources().getColor(R.color.black));
                iconTechnology.setImageResource(R.drawable.ic_baseline_computer_bluue_24);
                break;
        }
    }


}