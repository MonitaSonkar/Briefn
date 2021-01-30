package com.smartwebarts.briefnx.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.smartwebarts.briefnx.dashboard.repository.NewsRepository;
import com.smartwebarts.briefnx.models.StateModel;
import com.smartwebarts.briefnx.newsdetail.model.Articles_Model;
import com.smartwebarts.briefnx.newsdetail.model.SubscriptionModel;
import com.smartwebarts.briefnx.utils.AppSharedPreferences;

import java.util.List;

public class LoginViewModel extends AppSharedPreferences {
    private Repository newsRepository;
    private LiveData<List<StateModel>> mutableLiveDataState;
    private LiveData<List<StateModel>> mutableLiveDatapackagesCity;
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        newsRepository = new Repository();
        mutableLiveDataState = newsRepository.getState();
        mutableLiveDatapackagesCity = newsRepository.getCity();
    }

}
