package com.example.hlsvideoplayer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Player.EventListener{

    private List<Video> videoList;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public PlayerView playerView;
        public ProgressBar mProgressBar;
        public ImageButton btnStartStop;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.videoTitle);
            playerView = view.findViewById(R.id.videoPlayer);
            mProgressBar = view.findViewById(R.id.loadingBar);
            btnStartStop = view.findViewById(R.id.btnStartStop);
            itemView.setTag(this);
        }
    }


    public RecyclerViewAdapter(List<Video> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Video video = videoList.get(position);
        holder.title.setText(video.getTitle());
        holder.btnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  switchPlayPauseButton(holder);
                //((MyViewHolder) holder).isMuted = !((MyViewHolder) holder).isMuted;
            }
        });





       // holder.playerView.getPlayer().addListener(eventListener);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class ViewHolder extends BaseViewHolder {
        TextView textViewTitle;

        public FrameLayout videoLayout;

//        public ImageView mCover;

        public ProgressBar mProgressBar;
        public final View parent;
//
//
        public ViewHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            parent = itemView;
            mProgressBar = itemView.findViewById(R.id.loadingBar);
            textViewTitle = itemView.findViewById(R.id.videoTitle);
        }
//
        protected void clear() {

        }
//
        public void onBind(int position) {
            super.onBind(position);
            parent.setTag(this);
            Video videoInfo = videoList.get(position);
            textViewTitle.setText(videoInfo.getTitle());
//            Glide.with(itemView.getContext())
//                    .load(videoInfo.getCoverUrl()).apply(new RequestOptions().optionalCenterCrop())
//                    .into(mCover);
        }
    }

    public void pausePlayer(Player playerForItem){
        if(playerForItem!=null) {
            playerForItem.setPlayWhenReady(false);
            playerForItem.getPlaybackState();
            //isPaused = true;
        }
    }

    public void startPlayer(Player playerForItem){
        if(playerForItem!=null) {
            playerForItem.setPlayWhenReady(true);
            playerForItem.getPlaybackState();
            //isPaused=false;
        }
    }

    public void switchPlayPauseButton(MyViewHolder holder){
        SimpleExoPlayer player = (SimpleExoPlayer)holder.playerView.getPlayer();
        if (player.getPlayWhenReady()==true) {
            pausePlayer(player);
             holder.btnStartStop.setImageResource(R.drawable.ic_play);
        } else {
            startPlayer(player);
             holder.btnStartStop.setImageResource(R.drawable.ic_pause);
        }
    }






}
