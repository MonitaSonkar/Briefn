package com.smartwebarts.briefnx.dashboard.viewmodel;

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

public class NewsViewModel extends AppSharedPreferences {
    private NewsRepository newsRepository;
    private LiveData<Articles_Model> mutableLiveData;
    private LiveData<List<SubscriptionModel>> mutableLiveDatapackages;
//    private LiveData<NewsModelArticle> volumesResponseLiveData;
    public NewsViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        newsRepository = new NewsRepository();
        mutableLiveData = newsRepository.getNewsArtiles();
        mutableLiveDatapackages = newsRepository.getSubscription();
    }

    public void callArticles(String loginUserId, String id, String sub_id, String language_set, Dialog dialog, String search_text, mCallBackResponse mCallBackResponse) {
        newsRepository.getArticlesCall(loginUserId,id, sub_id,language_set,dialog,search_text,mCallBackResponse);
    }

    public void callPayment(String userId, String loginApiKey, String pacakge_id, String payment_id, String order_id, String signature, String status, Dialog dialog, mCallBackResponse mCallBackResponse) {
        newsRepository.apiPaymentCall(userId,loginApiKey, pacakge_id,payment_id,order_id,signature,status,dialog,mCallBackResponse);
    }

    public void callPackage(String packeges, Dialog dialog) {
        newsRepository.getPackageApiCall(packeges,dialog);
    }

    public LiveData<Articles_Model> getArticles() {
        return mutableLiveData;
    }
    public LiveData<List<SubscriptionModel>> getPackages() {
        return mutableLiveDatapackages;
    }
}
