package com.smartwebarts.briefnx.dashboard.viewmodel;

import android.app.Application;
import android.app.Dialog;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.smartwebarts.briefnx.dashboard.repository.NewsRepository;
import com.smartwebarts.briefnx.models.NewsByCategoryModel;
import com.smartwebarts.briefnx.models.NewsModel;
import com.smartwebarts.briefnx.newsdetail.model.Articles_Model;
import com.smartwebarts.briefnx.newsdetail.model.NewsModelArticle;
import com.smartwebarts.briefnx.newsdetail.model.PaymentArticleModel;
import com.smartwebarts.briefnx.newsdetail.model.SubscriptionModel;
import com.smartwebarts.briefnx.retrofit.UtilMethods;
import com.smartwebarts.briefnx.retrofit.mCallBackResponse;
import com.smartwebarts.briefnx.utils.AppSharedPreferences;
import com.smartwebarts.briefnx.utils.UsefullMethods;
import com.smartwebarts.briefnx.viewmodel.Repository;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DashboardViewModel extends AppSharedPreferences {
    private Repository repository;
    private MutableLiveData<List<NewsModel>> mutableLiveDataCategory;
    private MutableLiveData<List<NewsByCategoryModel>> mutableLiveDataSubcategory;
    private LiveData<List<SubscriptionModel>> mutableLiveDatapackages;
    public DashboardViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repository = new Repository();
        mutableLiveDataCategory = repository.getCategory();
        mutableLiveDatapackages = repository.getSubscription();
        mutableLiveDataSubcategory = repository.getSubCategory();
//        mutableLiveDatapackages = newsRepository.getSubscription();
    }

    /*public MutableLiveData<List<NewsModelArticle>> getApiCallArticles() {
        if(mutableLiveData==null)
        {
            mutableLiveData=new MutableLiveData<>();
        }
         getArtictlesApi();
        return mutableLiveData;
    }*/



    public LiveData<List<NewsModel>> getCategory() {
        return mutableLiveDataCategory;
    }

    public void callCategory(Dialog dialog, mCallBackResponse mCallBackResponse) {
        repository.getCategoryCall(dialog,mCallBackResponse);
    }
    public LiveData<List<SubscriptionModel>> getPackages() {
        return mutableLiveDatapackages;
    }
    public void callPackage(String packeges, Dialog dialog) {
        repository.getPackageApiCall(packeges,dialog);
    }

    public void updateAccessToken(String loginUserId, String loginApiKey, String token, mCallBackResponse callBackResponse) {
        repository.callAccesTokenApi(loginUserId,loginApiKey,token,callBackResponse);
    }


    public LiveData<List<NewsByCategoryModel>> getSubCategory() {
        return mutableLiveDataSubcategory;
    }

    public void callToSubcategoryApi(String id, Dialog dialog, mCallBackResponse callBackResponse) {
        repository.callSubCategory(id,dialog,callBackResponse);
    }
}
