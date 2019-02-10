package com.example.hlsvideoplayer;

public class Video {
    String Title;
    String url;

    public Video(String title, String url) {
        Title = title;
        this.url = url;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
