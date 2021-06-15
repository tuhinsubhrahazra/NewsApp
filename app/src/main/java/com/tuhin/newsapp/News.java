package com.tuhin.newsapp;

public class News {

    String title;
    String description;
    String url;
    String urlToImage;
    String publishAt;
    String content;

    public News(String title, String description , String url, String urlToImage, String publishAt ,
                String content){
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishAt = publishAt;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishAt() {
        return publishAt;
    }

    public String getContent() {
        return content;
    }
}
