package com.smartwebarts.briefnx.youtube;

import android.app.Application;
import android.app.Dialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.smartwebarts.briefnx.models.YoutubeItemsModels;
import com.smartwebarts.briefnx.models.YoutubeMainModel;
import com.smartwebarts.briefnx.retrofit.mCallBackResponse;
import com.smartwebarts.briefnx.utils.AppSharedPreferences;
import com.smartwebarts.briefnx.viewmodel.Repository;

import java.util.List;

public class YoutubeViewModel extends AppSharedPreferences {
    private Repository repository;
    private LiveData<YoutubeMainModel> youtubeCompletedata;
    private LiveData<List<YoutubeItemsModels>> youtube_videos;
    public YoutubeViewModel(@NonNull Application application) {
        super(application);
    }

    public void intit()
    {
        repository=new Repository();
        youtubeCompletedata=repository.getYoutubedata();
    }

    public  LiveData<YoutubeMainModel> getyoutubeVideo() {
        return youtubeCompletedata;
    }

    public void callYouttube(String page_token, Dialog dialog, mCallBackResponse mCallBackResponse) {
        repository.getYoutubeVideoCall(page_token,dialog,mCallBackResponse);
    }
}
