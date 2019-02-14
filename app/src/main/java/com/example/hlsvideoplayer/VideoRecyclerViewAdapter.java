//package com.example.hlsvideoplayer;
//
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.androidwave.exoplayer.model.VideoInfo;
//import com.androidwave.exoplayer.ui.BaseViewHolder;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
//
//    public static final int VIEW_TYPE_EMPTY = 0;
//    public static final int VIEW_TYPE_NORMAL = 1;
//
//    private List<Video> mInfoList;
//
//    public VideoRecyclerViewAdapter(List<VideoInfo> infoList) {
//        mInfoList = infoList;
//    }
//
//    @NonNull
//    @Override
//    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        switch (viewType) {
//            case VIEW_TYPE_NORMAL:
//                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false));
//            case VIEW_TYPE_EMPTY:
//                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
//            default:
//                return null;
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
//        holder.onBind(position);
//    }
//
//
//    @Override
//    public int getItemViewType(int position) {
//        if (mInfoList != null && mInfoList.size() > 0) {
//            return VIEW_TYPE_NORMAL;
//        } else {
//            return VIEW_TYPE_EMPTY;
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        if (mInfoList != null && mInfoList.size() > 0) {
//            return mInfoList.size();
//        } else {
//            return 1;
//        }
//    }
//
//
//    public void onRelease() {
//        if (mInfoList != null) {
//            mInfoList.clear();
//            mInfoList = null;
//        }
//    }
//
//    public class ViewHolder extends BaseViewHolder {
//        @BindView(R.id.textViewTitle)
//        TextView textViewTitle;
//        @BindView(R.id.userHandle)
//        TextView userHandle;
//        @BindView(R.id.video_layout)
//        public FrameLayout videoLayout;
//        @BindView(R.id.cover)
//        public ImageView mCover;
//
//        @BindView(R.id.progressBar)
//        public ProgressBar mProgressBar;
//        public final View parent;
//
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//            parent = itemView;
//        }
//
//        protected void clear() {
//
//        }
//
//        public void onBind(int position) {
//            super.onBind(position);
//            parent.setTag(this);
//            VideoInfo videoInfo = mInfoList.get(position);
//            textViewTitle.setText(videoInfo.getTitle());
//            userHandle.setText(videoInfo.getUserHandle());
//            Glide.with(itemView.getContext())
//                    .load(videoInfo.getCoverUrl()).apply(new RequestOptions().optionalCenterCrop())
//                    .into(mCover);
//        }
//    }
//
//    public class EmptyViewHolder extends BaseViewHolder {
//
//        @BindView(R.id.btn_retry)
//        Button retryButton;
//
//        @BindView(R.id.tv_message)
//        TextView messageTextView;
//
//        public EmptyViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//            itemView.setVisibility(View.GONE);
//        }
//
//        @Override
//        protected void clear() {
//
//        }
//
//        @OnClick(R.id.btn_retry)
//        void onRetryClick() {
//
//        }
//    }
//}
