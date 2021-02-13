package com.smartwebarts.briefnx.viewmodel;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.models.MessageModel;
import com.smartwebarts.briefnx.models.NewsByCategoryModel;
import com.smartwebarts.briefnx.models.NewsModel;
import com.smartwebarts.briefnx.models.StateModel;
import com.smartwebarts.briefnx.models.YoutubeMainModel;
import com.smartwebarts.briefnx.newsdetail.model.Articles_Model;
import com.smartwebarts.briefnx.newsdetail.model.PaymentResponse;
import com.smartwebarts.briefnx.newsdetail.model.SubscriptionModel;
import com.smartwebarts.briefnx.retrofit.APIClient;
import com.smartwebarts.briefnx.retrofit.ApiclientsYoutube;
import com.smartwebarts.briefnx.retrofit.EndPointInterface;
import com.smartwebarts.briefnx.retrofit.UtilMethods;
import com.smartwebarts.briefnx.retrofit.mCallBackResponse;
import com.smartwebarts.briefnx.utils.AppSharedPreferences;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private MutableLiveData<List<StateModel>> mutableLiveDataState;
    private MutableLiveData<List<StateModel>> mutableLiveDataCities;
    private MutableLiveData<List<NewsModel>> mutableLiveDataCategory;
    private MutableLiveData<YoutubeMainModel> youtubeCompletedata;
    private MutableLiveData<List<SubscriptionModel>> mutableLiveDatapackages;
    private MutableLiveData<List<NewsByCategoryModel>> mutableLiveSubcategory;
    public Repository() {
        mutableLiveDataState = new MutableLiveData<>();
        mutableLiveDataCities = new MutableLiveData<>();
        mutableLiveDataCategory = new MutableLiveData<>();
        youtubeCompletedata = new MutableLiveData<>();
        mutableLiveDatapackages = new MutableLiveData<>();
        mutableLiveSubcategory = new MutableLiveData<>();
    }
    public LiveData<List<StateModel>> getState() {
        return mutableLiveDataState;
    }

    public LiveData<List<StateModel>> getCity() {
        return mutableLiveDataCities;
    }

    public void apiCallState(String states, Dialog dialog, mCallBackResponse mCallBackResponse) {
        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<List<StateModel>> call = git.getState(states);
            call.enqueue(new Callback<List<StateModel>>() {
                @Override
                public void onResponse(Call<List<StateModel>> call, Response<List<StateModel>> response) {
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("NewsRepos===", strResponse);
                    dialog.dismiss();
                    if (response.body() != null) {
                        mCallBackResponse.success("","");
                        mutableLiveDataState.postValue(response.body());

                    } else {
                        mutableLiveDataState.postValue(null);
                        mCallBackResponse.fail("Unable to get response from server");
                    }
                }

                @Override
                public void onFailure(Call<List<StateModel>> call, Throwable t) {
                    dialog.dismiss();
                    mutableLiveDataState.postValue(null);
                    mCallBackResponse.fail("Unable to get response from server");

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            mutableLiveDataState.postValue(null);
            mCallBackResponse.fail("Server down, Please try again after sometime");

        }
    }

    public void apiCallCity(String state_id, Dialog dialog, mCallBackResponse mCallBackResponse) {
        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<List<StateModel>> call = git.getCity(state_id,"cities","states_id");
            call.enqueue(new Callback<List<StateModel>>() {
                @Override
                public void onResponse(Call<List<StateModel>> call, Response<List<StateModel>> response) {
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("NewsRepos===", strResponse);
                    dialog.dismiss();
                    if (response.body() != null) {
                        mCallBackResponse.success("","");
                        mutableLiveDataCities.postValue(response.body());

                    } else {
                        mutableLiveDataCities.postValue(null);
                        mCallBackResponse.fail("Unable to get response from server");
                    }
                }

                @Override
                public void onFailure(Call<List<StateModel>> call, Throwable t) {
                    dialog.dismiss();
                    mutableLiveDataCities.postValue(null);
                    mCallBackResponse.fail("Unable to get response from server");

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            dialog.dismiss();
            mutableLiveDataCities.postValue(null);
            mCallBackResponse.fail("Server down, Please try again after sometime");

        }
    }

    public MutableLiveData<List<NewsModel>> getCategory() {
        return mutableLiveDataCategory;
    }

    public void getCategoryCall(Dialog dialog, mCallBackResponse mCallBackResponse) {
        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<List<NewsModel>> call = git.newsCurrent( "super_category");
            call.enqueue(new Callback<List<NewsModel>>() {
                @Override
                public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                    if (dialog != null && dialog.isShowing())
                    {
                        dialog.dismiss();
                    }

                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body().size()>0) {
                            mutableLiveDataCategory.postValue(response.body());
                            mCallBackResponse.success("Str", strResponse);
                        }
                        else {
                            mutableLiveDataCategory.postValue(null);
                            mCallBackResponse.fail("Unable to get response from server");
                        }
                    } else {
                        mutableLiveDataCategory.postValue(null);
                        mCallBackResponse.fail("Unable to get response from server");
//
                    }
                }

                @Override
                public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                    mutableLiveDataCategory.postValue(null);
                    mCallBackResponse.fail("Server down, Please try again after sometime");
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            mutableLiveDataCategory.postValue(null);
            mCallBackResponse.fail("Server down, Please try again after sometime");
            dialog.dismiss();
        }
    }

    public LiveData<YoutubeMainModel> getYoutubedata() {
        return youtubeCompletedata;
    }

    public void getYoutubeVideoCall(String page_token, Dialog dialog, mCallBackResponse callBackResponse) {
        try {
//                "UC1NF71EwP41VdjAU1iXdLkw"
            EndPointInterface git = ApiclientsYoutube.getClient().create(EndPointInterface.class);
            Call<YoutubeMainModel> call = git.youtube( "snippet", UtilMethods.youtube_channel_id,"date",page_token,UtilMethods.youtube_apiKey);
            call.enqueue(new Callback <YoutubeMainModel>() {
                @Override
                public void onResponse(Call <YoutubeMainModel> call, Response<YoutubeMainModel> response) {
                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("youtube==",strResponse);
                    if (response.body()!=null) {
                        youtubeCompletedata.postValue(response.body());
                        callBackResponse.success("", strResponse);

                    } else {
                        youtubeCompletedata.postValue(null);
                        callBackResponse.fail("Unable to get response from server");
//
                    }
                }
                @Override
                public void onFailure(Call <YoutubeMainModel> call, Throwable t) {
                    youtubeCompletedata.postValue(null);
                    callBackResponse.fail("Server down, Please try again after sometime");
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            youtubeCompletedata.postValue(null);
            callBackResponse.fail("Server down, Please try again after sometime");
            dialog.dismiss();
        }
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
//                        dialog.dismiss();
                        String strResponse = new Gson().toJson(response.body());
                        Log.e("Monita===",strResponse);
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
//                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                mutableLiveDatapackages.postValue(null);
//                dialog.dismiss();
            }
        }

    }

    public void callAccesTokenApi(String loginUserId, String loginApiKey, String token, mCallBackResponse callBackResponse) {
        try {

            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<MessageModel> call = git.updateAccessToken(loginUserId,"user",token);
            call.enqueue(new Callback<MessageModel>() {
                @Override
                public void onResponse(@NotNull Call<MessageModel> call, @NotNull Response<MessageModel> response) {
                 String str=new Gson().toJson(response.body());
                 if(response.body()!=null)
                 {
                     callBackResponse.success("",str);
                 }
                 else
                 {
                    callBackResponse.fail(str);
                 }

                }

                @Override
                public void onFailure(@NotNull Call<MessageModel> call, @NotNull Throwable t) {
                    callBackResponse.fail("Server down, Please try again after sometime");
                }
            });

        } catch (Exception e) {
            callBackResponse.fail(e.toString());
            e.printStackTrace();

        }
    }

    public MutableLiveData<List<NewsByCategoryModel>> getSubCategory() {
        return mutableLiveSubcategory;
    }

    public void callSubCategory(String id, Dialog dialog, mCallBackResponse callBackResponse) {
        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<List<NewsByCategoryModel>> call = git.topCurrentNews( "article_category","super_category_id",id);
            call.enqueue(new Callback<List<NewsByCategoryModel>>() {
                @Override
                public void onResponse(Call<List<NewsByCategoryModel>> call, Response<List<NewsByCategoryModel>> response) {
                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body().size()>0) {
                            callBackResponse.success("", strResponse);
                            mutableLiveSubcategory.postValue(response.body());
                        }
                        else {
                            mutableLiveSubcategory.postValue(null);
                            callBackResponse.fail("Unable to get response from server");
                        }
                    } else {
                        mutableLiveSubcategory.postValue(null);
                        callBackResponse.fail("Unable to get response from server");
//
                    }
                }

                @Override
                public void onFailure(Call<List<NewsByCategoryModel>> call, Throwable t) {
                    mutableLiveSubcategory.postValue(null);
                    callBackResponse.fail("Server down, Please try again after sometime");
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            mutableLiveSubcategory.postValue(null);
            callBackResponse.fail("Server down, Please try again after sometime");
            dialog.dismiss();
        }
    }
}
