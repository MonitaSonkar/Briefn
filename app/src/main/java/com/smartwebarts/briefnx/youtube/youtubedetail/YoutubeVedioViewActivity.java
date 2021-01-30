package com.smartwebarts.briefnx.youtube.youtubedetail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.retrofit.UtilMethods;

public class YoutubeVedioViewActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    YouTubePlayerView youTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_vedio_view);
        youTubePlayerView=findViewById(R.id.play_video);
        youTubePlayerView.initialize(getResources().getString(R.string.youtube_api_key),this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}