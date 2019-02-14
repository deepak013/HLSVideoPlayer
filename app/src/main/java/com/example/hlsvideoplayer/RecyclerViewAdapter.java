package com.example.hlsvideoplayer;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<Video> videoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public PlayerView player;
        public ProgressBar progressBar;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.videoTitle);
            player = view.findViewById(R.id.videoPlayer);
            progressBar = view.findViewById(R.id.loadingBar);
        }
    }


    public RecyclerViewAdapter(List<Video> videoList) {
        this.videoList = videoList;
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
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

//    public class ViewHolder extends BaseViewHolder {
//        TextView textViewTitle;
//        TextView userHandle;
//
//        public FrameLayout videoLayout;
//
////        public ImageView mCover;
//
//        public ProgressBar mProgressBar;
//        public final View parent;
////
////
//        public ViewHolder(View itemView) {
//            super(itemView);
//            //ButterKnife.bind(this, itemView);
//            parent = itemView;
//        }
////
//        protected void clear() {
//
//        }
////
////        public void onBind(int position) {
////            super.onBind(position);
////            parent.setTag(this);
////            VideoInfo videoInfo = mInfoList.get(position);
////            textViewTitle.setText(videoInfo.getTitle());
////            userHandle.setText(videoInfo.getUserHandle());
////            Glide.with(itemView.getContext())
////                    .load(videoInfo.getCoverUrl()).apply(new RequestOptions().optionalCenterCrop())
////                    .into(mCover);
////        }
//    }

}
