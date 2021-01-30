package com.smartwebarts.briefnx.dashboard.search;

import android.app.Application;
import android.app.Dialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.smartwebarts.briefnx.dashboard.repository.NewsRepository;
import com.smartwebarts.briefnx.newsdetail.model.Articles_Model;
import com.smartwebarts.briefnx.newsdetail.model.SubscriptionModel;
import com.smartwebarts.briefnx.retrofit.mCallBackResponse;
import com.smartwebarts.briefnx.utils.AppSharedPreferences;

import java.util.List;

public class SearchViewModel extends AppSharedPreferences {
    private NewsRepository newsRepository;
    private LiveData<Articles_Model> mutableLiveData;
    //    private LiveData<NewsModelArticle> volumesResponseLiveData;
    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        newsRepository = new NewsRepository();
        mutableLiveData = newsRepository.getNewsArtiles();
    }

    public void callArticles(String loginUserId, String id, String sub_id, String language_set, Dialog dialog) {
//        newsRepository.getArticlesCall(loginUserId,id, sub_id,language_set,dialog);
    }

    public LiveData<Articles_Model> getArticles() {
        return mutableLiveData;
    }
}
