package com.example.hlsvideoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
  private List<Video> videoList;
  private Adapter videoAdapter;
  private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoList = new ArrayList<>();
        //Video tempVideo = new Video();
        videoAdapter = new Adapter(videoList);
    }

    public void btnClicked(View view) {
        Intent i = new Intent(getApplicationContext(),TestActivity.class);
        startActivity(i);
    }
}
