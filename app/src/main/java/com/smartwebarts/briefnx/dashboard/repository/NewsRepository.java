package com.smartwebarts.briefnx.dashboard.repository;

import android.app.Dialog;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.smartwebarts.briefnx.newsdetail.model.Articles_Model;
import com.smartwebarts.briefnx.newsdetail.model.PaymentResponse;
import com.smartwebarts.briefnx.newsdetail.model.SubscriptionModel;
import com.smartwebarts.briefnx.retrofit.APIClient;
import com.smartwebarts.briefnx.retrofit.EndPointInterface;
import com.smartwebarts.briefnx.retrofit.mCallBackResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {


    private MutableLiveData<Articles_Model> mutableLiveData;
    private MutableLiveData<List<SubscriptionModel>> mutableLiveDatapackages;


    public NewsRepository() {
        mutableLiveData = new MutableLiveData<>();
        mutableLiveDatapackages = new MutableLiveData<>();
    }

    public void getArticlesCall(String loginUserId, String cotegoryModels, String superCategoryId, String language_set, Dialog dialog, String search_text, mCallBackResponse mCallBackResponse) {
        try {

            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);

            Call<Articles_Model> call = git.article(loginUserId, superCategoryId, cotegoryModels, language_set,search_text);
            call.enqueue(new Callback<Articles_Model>() {
                @Override
                public void onResponse(Call<Articles_Model> call, Response<Articles_Model> response) {
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("NewsRepos===", strResponse);
                    dialog.dismiss();
                    if (response.body() != null) {
                        mutableLiveData.postValue(response.body());
                        if(response.body().getNews().size()<=0)
                        {
                            mCallBackResponse.fail("No Search Result found");
                        }


                    } else {
                        mutableLiveData.postValue(null);
                        mCallBackResponse.fail("No Search Result found");
                    }
                }

                @Override
                public void onFailure(Call<Articles_Model> call, Throwable t) {
                    dialog.dismiss();
                    mutableLiveData.postValue(null);
                    mCallBackResponse.fail("No Search Result found");

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            dialog.dismiss();
            mutableLiveData.postValue(null);

        }

    }

    public void apiPaymentCall(String userId, String loginApiKey, String pacakge_id, String payment_id, String order_id, String signature, String status, Dialog dialog, mCallBackResponse mCallBackResponse) {
        try {

            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);

            Call<PaymentResponse> call = git.getPaymentApi(userId, "payments",pacakge_id ,payment_id,order_id ,signature,status);
            call.enqueue(new Callback<PaymentResponse>() {
                @Override
                public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("NewsRepos===", strResponse);
                    dialog.dismiss();
                    if (response.body() != null) {
                        mCallBackResponse.success("","");
//                        mutableLiveData.postValue(response.body());

                    } else {
//                        mutableLiveData.postValue(null);
                       mCallBackResponse.fail("Unable to get response from server");
                    }
                }

                @Override
                public void onFailure(Call<PaymentResponse> call, Throwable t) {
                    dialog.dismiss();
                    mCallBackResponse.fail("Unable to get response from server");

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            dialog.dismiss();
            mCallBackResponse.fail("Server down, Please try again after sometime");

        }

    }

    public LiveData<Articles_Model> getNewsArtiles() {
        return mutableLiveData;
    }
    public LiveData<List<SubscriptionModel>> getSubscription() {
        return mutableLiveDatapackages;
    }

    public void getPackageApiCall(String packeges, Dialog dialog) {
        {
            try {
                EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
                Call<List<SubscriptionModel>> call = git.getPackage_Api(packeges);
                call.enqueue(new Callback<List<SubscriptionModel>>() {
                    @Override
                    public void onResponse(Call<List<SubscriptionModel>> call, Response<List<SubscriptionModel>> response) {
                        dialog.dismiss();
                        String strResponse = new Gson().toJson(response.body());
                        Log.e("deepika===",strResponse);
                        if (response.body()!=null) {
                            mutableLiveDatapackages.postValue(response.body());
                        } else {
                            mutableLiveDatapackages.postValue(null);
//
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SubscriptionModel>> call, Throwable t) {
                        mutableLiveDatapackages.postValue(null);
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                dialog.dismiss();
                mutableLiveDatapackages.postValue(null);
                dialog.dismiss();
            }
        }

    }
}

