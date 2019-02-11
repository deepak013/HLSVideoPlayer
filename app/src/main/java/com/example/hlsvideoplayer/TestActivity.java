package com.example.hlsvideoplayer;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity implements ExoPlayer.EventListener{
    private BandwidthMeter bandwidthMeter;
    private TrackSelector trackSelector;
    private TrackSelection.Factory trackSelectionFactory;
    private SimpleExoPlayer player;
    private DataSource.Factory dataSourceFactory;
    private ExtractorsFactory extractorsFactory;
    private DefaultBandwidthMeter defaultBandwidthMeter;
    private MediaSource mediaSource;

    private String songUrl = "http://www.mfiles.co.uk/mp3-downloads/edvard-grieg-peer-gynt1-morning-mood-piano.mp3";

    private ImageButton stopButton;
    private ImageButton startButton;
    private PlayerView playerView;
    ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        playerView = findViewById(R.id.testVideoPlayer);
        loading = findViewById(R.id.testLoadingBar);
    }

    @Override
    public void onStart() {
        super.onStart();
        startButton =  findViewById(R.id.btnStart);
        stopButton =  findViewById(R.id.btnStop);

        bandwidthMeter = new DefaultBandwidthMeter();
        extractorsFactory = new DefaultExtractorsFactory();

        trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

        trackSelector = new DefaultTrackSelector(trackSelectionFactory);

        defaultBandwidthMeter = new DefaultBandwidthMeter();

        dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "mediaPlayerSample"),defaultBandwidthMeter);


        mediaSource = new ExtractorMediaSource(Uri.parse(songUrl),
                dataSourceFactory,
                extractorsFactory,
                null,
                null);

        player = ExoPlayerFactory.newSimpleInstance(this,trackSelector);

        player.addListener(this);
        player.prepare(mediaSource);

        Log.d("TEST","playing state : " + player.getPlaybackState());

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setPlayWhenReady(true);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setPlayWhenReady(false);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.setPlayWhenReady(false);
    }


    @Override
    public void onLoadingChanged(boolean isLoading) {
        Log.i("TEST", "onLoadingChanged: " + isLoading + "");
        Log.i("TEST", "Buffered Position: " + player.getBufferedPosition() + "");
        Log.i("TEST", "Buffered Percentage: " + player.getBufferedPercentage() + "");
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        if(playbackState == ExoPlayer.STATE_READY){
            Log.i("TEST", "ExoPlayer State is: READY");
        } else if (playbackState == ExoPlayer.STATE_BUFFERING){
            Log.i("TEST", "ExoPlayer State is: BUFFERING");
        } else if (playbackState == ExoPlayer.STATE_ENDED){
            Log.i("TEST", "ExoPlayer State is: ENDED");
        } else if (playbackState == ExoPlayer.STATE_IDLE){
            Log.i("TEST", "ExoPlayer State is: IDLE");
        }


    }


    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }



    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }
    }
