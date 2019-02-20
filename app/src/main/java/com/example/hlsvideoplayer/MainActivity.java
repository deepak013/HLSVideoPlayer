package com.example.hlsvideoplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements Player.EventListener, View.OnClickListener{
  private List<Video> videoList;
  private List<Boolean> isInialized;
  private RecyclerViewAdapter videoAdapter;
  private RecyclerView recyclerView;
  ProgressBar mProgressBar;
    SimpleExoPlayer player;
  private int playingIndex=-1;
    private boolean firstTime = true;
    private String url0 = "https://player.vimeo.com/external/286837767.m3u8?s=42570e8c4a91b98cdec7e7bfdf0eccf54e700b69";
    private String url1 = "https://player.vimeo.com/external/286837810.m3u8?s=610b4fee49a71c2dbf22c01752372ff1c6459b9e";
    private String url2 = "https://player.vimeo.com/external/286837723.m3u8?s=3df60d3c1c6c7a11df4047af99c5e05cc2e7ae96";
    private String url3 = "https://player.vimeo.com/external/286837649.m3u8?s=9e486e9b932be72a8875afc6eaae21bab124a35a";
    private String url4 = "https://player.vimeo.com/external/286837529.m3u8?s=20f83af6ea8fbfc8ce0c2001f32bf037f8b0f65f";
    private String url5 = "https://player.vimeo.com/external/286837402.m3u8?s=7e01c398e2f01c29ecbd46e5e2dd53e0d6c1905d";
//    private String videoUrlHLS = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8";
//    private String videoUrlHLS1 = "https://mnmedias.api.telequebec.tv/m3u8/29880.m3u8";
//    private String videoUrlHLS2 = "http://184.72.239.149/vod/smil:BigBuckBunny.smil/playlist.m3u8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Video tempVideo = new Video("Video0",url0);
        Video tempVideo1 = new Video("Video1",url1);
        Video tempVideo2 = new Video("Video2",url2);
        Video tempVideo3 = new Video("Video3",url3);
        Video tempVideo4 = new Video("Video4",url4);
        Video tempVideo5 = new Video("Video5",url5);
        videoList = new ArrayList<>();
        videoList.add(tempVideo);
        videoList.add(tempVideo1);
        videoList.add(tempVideo2);
        videoList.add(tempVideo3);
        videoList.add(tempVideo4);
        videoList.add(tempVideo5);
        recyclerView = findViewById(R.id.rvVideos);
        isInialized = new ArrayList<>(Arrays.asList(new Boolean[videoList.size()]));
        Collections.fill(isInialized,false);
        //recyclerView.setVideoInfoList(videoList);

        videoAdapter = new RecyclerViewAdapter(videoList, getApplicationContext());
        recyclerView.setAdapter(videoAdapter);
       LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
       recyclerView.setLayoutManager(layoutManager);
//        if (firstTime) {
//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
//                   // recyclerView.playVideo();
//                }
//            });
//            firstTime = false;
//        }
        recyclerView.scrollToPosition(0);
        firstInitialization();

        recyclerView.addOnLayoutChangeListener(new RecyclerView.OnLayoutChangeListener() {

            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                 if(!firstTime) {recyclerView.removeOnLayoutChangeListener(this); return;}
                firstInitialization();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                    int firstVisiable = linearLayoutManager.findFirstVisibleItemPosition();
                    int lastVisiable = linearLayoutManager.findLastVisibleItemPosition();
                    batchInialize(firstVisiable,lastVisiable);
                }
            }



            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view ) {
                PlayerView plv = view.findViewById(R.id.videoPlayer);
                Player tempPlayer = plv.getPlayer();
                //if(tempPlayer==null) startPlayer(tempPlayer);
            }



            @Override
            public void onChildViewDetachedFromWindow(View view) {
//                if (addedVideo && rowParent != null && rowParent.equals(view)) {
//                    //removeVideoView(videoSurfaceView);
//                    playPosition = -1;
                    PlayerView plv = view.findViewById(R.id.videoPlayer);
                    Player tempPlayer = plv.getPlayer();
                    if(tempPlayer!=null) pausePlayer(view, tempPlayer);
//                    else initializePlayer(plv);
//                    //videoSurfaceView.setVisibility(INVISIBLE);
//                }

            }
        });
    }

    private void firstInitialization() {
        if(firstTime){
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
            int firstVisiable = linearLayoutManager.findFirstVisibleItemPosition();
            int lastVisiable = linearLayoutManager.findLastVisibleItemPosition();
            if(firstVisiable==-1 || lastVisiable==-1) return;
            batchInialize(firstVisiable,lastVisiable);
        }
        firstTime = false;
    }

    public void btnClicked(View view) {
        Intent i = new Intent(getApplicationContext(),TestHLSActivity.class);
        startActivity(i);
    }

    private void initializePlayer(int pos, Boolean defaultPlayState, int firstVisiableItemIndex) {
        PlayerView playerView;
        int count = recyclerView.getChildCount();
        View child = recyclerView.getChildAt(pos-firstVisiableItemIndex);
        if (child == null) {
            return;
        }

        RecyclerViewAdapter.MyViewHolder holder
                = (RecyclerViewAdapter.MyViewHolder) child.getTag();
        if (holder == null) {
            return;
        }
        mProgressBar = holder.mProgressBar;
        FrameLayout frameLayout = holder.itemView.findViewById(R.id.videoFrameLayout);
        playerView = holder.itemView.findViewById(R.id.videoPlayer);
        player = (SimpleExoPlayer)playerView.getPlayer();
        if(player!=null){
            return;
        }
        player = ExoPlayerFactory.newSimpleInstance(this,
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);
        player.setPlayWhenReady(defaultPlayState);
        player.seekTo(0, 0);
        Uri uri = Uri.parse(videoList.get(pos).url);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource,true,false);
        player.addListener(this);
        int id = player.getAudioSessionId();
        Log.d("AudioSessionId", pos+" "+id);
        if(defaultPlayState) holder.btnStartStop.setImageResource(R.drawable.ic_pause);
        playerView.hideController();

        Player.EventListener eventListener = new Player.EventListener() {
//            @Override
//            public void onTimelineChanged(Timeline timeline, Object manifest) {
//
//            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                holder.mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                switch (playbackState){
                    case Player.STATE_IDLE :      // The player does not have any media to play yet.
                        holder.mProgressBar.setVisibility(View.VISIBLE);
                    case Player.STATE_BUFFERING : // The player is buffering (loading the content)
                        holder.mProgressBar.setVisibility(View.VISIBLE);
                    case Player.STATE_ENDED :      // The player has finished playing the media
                        holder.mProgressBar.setVisibility(View.GONE);
                    case Player.STATE_READY :     // The player is able to immediately play
                        holder.mProgressBar.setVisibility(View.GONE);
                    default: holder.mProgressBar.setVisibility(View.GONE);

                }
//                if (playbackState == PlaybackStateCompat.ACTION_SKIP_TO_NEXT) {
//                    Toast.makeText(context, "next", Toast.LENGTH_SHORT).show();
//                }
//
//                if (playbackState == PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS) {
//                    Toast.makeText(context, "previous", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

//            @Override
//            public void onPositionDiscontinuity() {
//
//            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }
        };
        player.addListener(eventListener);
    }

    private void batchInialize(int firstVisiable, int lastVisiable) {
        Boolean defaultPlayState = false;
        for(int pos=firstVisiable;pos<=lastVisiable;pos++){
            defaultPlayState = pos==firstVisiable ? true : false;
            initializePlayer(pos, defaultPlayState,firstVisiable);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new HlsMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    public void pausePlayer(View playerView, Player playerForItem){
        ((ImageButton)playerView.findViewById(R.id.btnStartStop)).setImageResource(R.drawable.ic_play);
        if(playerForItem!=null) {
            playerForItem.setPlayWhenReady(false);
            playerForItem.getPlaybackState();
            //isPaused = true;
        }
    }

   public void startPlayer(View playerView, Player playerForItem){
        if(playerForItem!=null) {
            playerForItem.setPlayWhenReady(true);
            playerForItem.getPlaybackState();
            //isPaused=false;
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        int id = player.getAudioSessionId();
        Log.d("AudioSessionId", "pos"+" "+id);
        switch (playbackState){
            case Player.STATE_IDLE :      // The player does not have any media to play yet.
                mProgressBar.setVisibility(View.VISIBLE);
            case Player.STATE_BUFFERING : // The player is buffering (loading the content)
                mProgressBar.setVisibility(View.VISIBLE);
            case Player.STATE_ENDED :      // The player has finished playing the media
                mProgressBar.setVisibility(View.GONE);
            case Player.STATE_READY :     // The player is able to immediately play
                mProgressBar.setVisibility(View.GONE);
            default: mProgressBar.setVisibility(View.GONE);

        }
    }

}
