package com.tuhin.newsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tuhin.newsapp.MainActivity.MainContext;
import static com.tuhin.newsapp.MainActivity.business_url;

public class Business extends Fragment {

    public static ArrayList<News> newsArrayList  = new ArrayList<>();
    RequestQueue requestQueue;
    ViewPager2 businessViewPager2;
    ProgressBar businessProgress_Bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_business, container, false);

        businessViewPager2 = view.findViewById(R.id.businessViewPager2);
        businessProgress_Bar = view.findViewById(R.id.businessProgress_Bar);
        businessViewPager2.setVisibility(View.INVISIBLE);

        requestQueue = Volley.newRequestQueue(MainContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                business_url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray articles = response.getJSONArray("articles");
                    for (int i =0; i< articles.length();i++){
                        JSONObject objectArticles = articles.getJSONObject(i);

                        String title = objectArticles.getString("title");
                        String description = objectArticles.getString("description");
                        String url = objectArticles.getString("url");
                        String Image = objectArticles.getString("urlToImage");
                        String publishedAt = objectArticles.getString("publishedAt");
                        String content = objectArticles.getString("content");

                        News news = new News(title,description,url,Image,publishedAt,
                                content);

                        newsArrayList.add(news);
                    }

//                    recyclerView.setVisibility(View.VISIBLE);
                    businessViewPager2.setVisibility(View.VISIBLE);
                    businessProgress_Bar.setVisibility(View.INVISIBLE);
                    logData();
                    callAdapter();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainContext,"Connection Error !", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

        return view;
    }

    public void callAdapter(){
        News_recycleview_adapter adapter = new News_recycleview_adapter(newsArrayList
                ,MainContext);

        businessViewPager2.setAdapter(adapter);
//        viewPager2.setClipToPadding(true);
//        viewPager2.setClipChildren(false);

        businessViewPager2.setOffscreenPageLimit(1);
//        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(20));

        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float v = 1-Math.abs(position);

                page.setScaleX(0.8f+v*0.2f);
            }
        });

        businessViewPager2.setPageTransformer(transformer);
    }


    public void logData(){
        Log.e("size :",""+newsArrayList.size());

        for (int i =0 ; i < newsArrayList.size();i++){
            Log.e("News Data", newsArrayList.get(i).getTitle());
            Log.e("News Data", newsArrayList.get(i).getDescription());
            Log.e("News Data", newsArrayList.get(i).getUrl());
            Log.e("News Data", newsArrayList.get(i).getUrlToImage());
            Log.e("News Data", newsArrayList.get(i).getPublishAt());
            Log.e("News Data", newsArrayList.get(i).getContent());
        }
    }
}