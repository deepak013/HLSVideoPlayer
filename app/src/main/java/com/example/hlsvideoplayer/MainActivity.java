package com.example.hlsvideoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
  private List<Video> videoList;
  private RecyclerViewAdapter videoAdapter;
  private ExoPlayerRecyclerView recyclerView;
    private String videoUrlHLS = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Video tempVideo = new Video("Video1",videoUrlHLS);
        videoList = new ArrayList<>();
        videoList.add(tempVideo);
        videoList.add(tempVideo);
        recyclerView = findViewById(R.id.rvVideos);
        recyclerView.setVideoInfoList(videoList);


        videoAdapter = new RecyclerViewAdapter(videoList);
        recyclerView.setAdapter(videoAdapter);
       LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
       recyclerView.setLayoutManager(layoutManager);
    }

    public void btnClicked(View view) {
        Intent i = new Intent(getApplicationContext(),TestHLSActivity.class);
        startActivity(i);
    }
}
