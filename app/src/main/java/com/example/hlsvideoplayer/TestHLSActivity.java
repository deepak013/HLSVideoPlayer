package com.example.hlsvideoplayer;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import androidx.appcompat.app.AppCompatActivity;

public class TestHLSActivity extends AppCompatActivity implements Player.EventListener, View.OnClickListener {
    private BandwidthMeter bandwidthMeter;
    private TrackSelector trackSelector;
    private TrackSelection.Factory trackSelectionFactory;
    private SimpleExoPlayer player;
    private DataSource.Factory dataSourceFactory;
    private ExtractorsFactory extractorsFactory;
    private DefaultBandwidthMeter defaultBandwidthMeter;
    private MediaSource mediaSource;

    private String songUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4";
    private String songUrlHLS = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8";

    private ImageButton stopButton;
    private ImageButton startButton;
    private PlayerView playerView;
    ProgressBar loading;
    long playbackPosition=0;
    Boolean playWhenReady=true;
    int currentWindow=0;
    Boolean isPaused =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        playerView = findViewById(R.id.testVideoPlayer);
        loading = findViewById(R.id.testLoadingBar);
        startButton =  findViewById(R.id.btnStartStop);
        stopButton =  findViewById(R.id.btnStartStop);
//        startButton.setVisibility(View.GONE);
//        stopButton.setVisibility(View.GONE);
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        //playWhenReady=true;
    }


    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(this,
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        Uri uri = Uri.parse(songUrlHLS);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
        player.addListener(this);
        playerView.hideController();
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new HlsMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
        //startPlayer();
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
        pausePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    public void pausePlayer(){
        if(player!=null) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
            isPaused = true;
        }
    }

    private void startPlayer(){
        if(player!=null) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
            isPaused=false;
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        if(!isPaused) pausePlayer();
//        else startPlayer();
//        return  true;
//    }


    @Override
    public void onClick(View v) {
        int i=6;
        if(v.getId()==R.id.btnStartStop){
            pausePlayer();
            stopButton.setVisibility(View.GONE);
            startButton.setVisibility(View.VISIBLE);
        }
        if(v.getId()==R.id.btnStartStop){
            startPlayer();
            stopButton.setVisibility(View.VISIBLE);
            startButton.setVisibility(View.GONE);
            playerView.hideController();
        }
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState){
            case Player.STATE_IDLE :      // The player does not have any media to play yet.
                loading.setVisibility(View.VISIBLE);
            case Player.STATE_BUFFERING : // The player is buffering (loading the content)
                loading.setVisibility(View.VISIBLE);
            case Player.STATE_ENDED :      // The player has finished playing the media
                loading.setVisibility(View.GONE);
            case Player.STATE_READY :     // The player is able to immediately play
                loading.setVisibility(View.GONE);
            default: loading.setVisibility(View.GONE);

        }
    }
}
