package com.tuhin.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.tuhin.newsapp.MainActivity.MainContext;

public class News_recycleview_adapter extends RecyclerView.Adapter<News_recycleview_adapter.ViewHolder> {

    ArrayList<News> mArraylist;
    Context mContext;

    public News_recycleview_adapter(ArrayList<News> mArraylist, Context mContext) {
        this.mArraylist = mArraylist;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_recycleview_adapter,parent,
        false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull News_recycleview_adapter.ViewHolder holder, int position) {
        Glide.with(mContext)
                .load(mArraylist.get(position).getUrlToImage())
                .into(holder.thumble);

        holder.title.setText(mArraylist.get(position).getTitle());
        holder.description.setText(mArraylist.get(position).getDescription());
        holder.content.setText(mArraylist.get(position).getContent());
        String sub = mArraylist.get(position).getPublishAt();
        String time = sub.substring(11,16);
        String date = sub.substring(0,10);
        holder.publishAt.setText(date + ",  "+time);

        holder.showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mArraylist.get(position).getUrl();
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                customTabsIntent.launchUrl(mContext,Uri.parse(url));
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mArraylist.get(position).getUrl();

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, s);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
//                startActivity(shareIntent);
                shareIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(shareIntent);

            }
        });

        holder.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArraylist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumble;
        TextView title;
        TextView description;
        TextView content;
        TextView publishAt;
        CardView showMore;
        ImageView share;
        ImageView refresh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            thumble = itemView.findViewById(R.id.thumble);
            description = itemView.findViewById(R.id.description);
            content = itemView.findViewById(R.id.content);
            showMore = itemView.findViewById(R.id.showMore);
            share = itemView.findViewById(R.id.share);
            refresh = itemView.findViewById(R.id.bookmark);
            publishAt = itemView.findViewById(R.id.publishAt);
        }
    }
}
