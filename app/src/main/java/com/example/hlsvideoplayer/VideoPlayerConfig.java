package com.example.hlsvideoplayer;

public class VideoPlayerConfig {
    //Minimum Video you want to buffer while Playing
    public static final int MIN_BUFFER_DURATION = 3000;
    //Max Video you want to buffer during PlayBack
    public static final int MAX_BUFFER_DURATION = 5000;
    //Min Video you want to buffer before start Playing it
    public static final int MIN_PLAYBACK_START_BUFFER = 1500;
    //Min video You want to buffer when user resumes video
    public static final int MIN_PLAYBACK_RESUME_BUFFER = 3000;
}

