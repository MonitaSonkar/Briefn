package com.smartwebarts.briefnx.youtube.youtubedetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.retrofit.UtilMethods;

public class YoutubeVedioViewActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    YouTubePlayerView youTubePlayerView;
    String API_KEY;
    private static final int RECOVERY_REQUEST=1;
    String TAG="VideoActivity";


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_youtube_vedio_view);
        youTubePlayerView=findViewById(R.id.youtubeplayer);

        youTubePlayerView.initialize(UtilMethods.youtube_apiKey,this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        String show_video=getIntent().getStringExtra("videoId");
        youTubePlayer.cueVideo(show_video);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if(youTubeInitializationResult.isUserRecoverableError())
        {
            youTubeInitializationResult.getErrorDialog(this,RECOVERY_REQUEST);
        }
        else
        {
            Toast.makeText(this, "Error Intializing Youtube Player",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RECOVERY_REQUEST)
        {
            getYouTubeplayerProvider();
        }
    }

    protected YouTubePlayer.Provider getYouTubeplayerProvider() {
        return youTubePlayerView;
    }
}