package com.smartwebarts.briefnx.retrofit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.gson.Gson;
import com.rampo.updatechecker.UpdateChecker;
import com.smartwebarts.briefnx.BuildConfig;
import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.models.ArticleCategoryModel;
import com.smartwebarts.briefnx.models.DashboardDetailsModel;
import com.smartwebarts.briefnx.models.LoginData;
import com.smartwebarts.briefnx.models.LoginData1;
import com.smartwebarts.briefnx.models.MessageModel;
import com.smartwebarts.briefnx.models.NewsByCategoryModel;
import com.smartwebarts.briefnx.models.NewsForFegmentFront;
import com.smartwebarts.briefnx.models.NewsModel;
import com.smartwebarts.briefnx.models.NewsSubCategoryModel;
import com.smartwebarts.briefnx.models.OTPModel;

import com.smartwebarts.briefnx.models.ProfileModel;
import com.smartwebarts.briefnx.models.SignUpModel;
import com.smartwebarts.briefnx.models.VersionModel;
import com.smartwebarts.briefnx.models.YoutubeMainModel;
import com.smartwebarts.briefnx.newsdetail.model.Articles_Model;
import com.smartwebarts.briefnx.newsdetail.model.SubscriptionModel;
import com.smartwebarts.briefnx.utils.AppSharedPreferences;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public enum UtilMethods {
    INSTANCE;
    public static final String dateformat = "yyyy-MM-dd";
    public static final SimpleDateFormat format = new SimpleDateFormat(dateformat);
    public static final String user_table = "user";
    public static final String service_requests_table = "service_requests";
    public static final String youtube_channel_id="UC0M6ijmZd9iI8SR_t_rOZQg";
//    public static final String youtube_channel_id="UC4qz5w2M-Xmju7WC9ynqRtw";
    public static final String youtube_apiKey = "AIzaSyB3mE05Q-5P4qO-1vMZVTzTw_oGDk_AUb0";
    public static List<SubscriptionModel> subscribtion_plans = null;
    public boolean isNetworkAvialable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void internetNotAvailableMessage(Context context) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        dialog .setContentText("Internet Not Available");
        dialog.show();
    }

    public boolean isValidMobile(String mobile) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String mobilePattern = "^(?:0091|\\\\+91|0)[7-9][0-9]{9}$";
        String mobileSecPattern = "[7-9][0-9]{9}$";
        if (mobile.matches(mobilePattern) || mobile.matches(mobileSecPattern)) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isValidEmail(String email) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public Dialog getProgressDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        return dialog;
    }

    public static Dialog getCommonProgressDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        return dialog;
    }

    public void Login(final Context context, String mobile, String password , final mCallBackResponse callBackResponse) {

        if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {
            final Dialog dialog = getProgressDialog(context);
            dialog.show();
            try {
                EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
                Call<LoginData1> call = git.login( "user",mobile, password);
                call.enqueue(new Callback<LoginData1>() {
                    @Override
                    public void onResponse(@NotNull Call<LoginData1> call, @NotNull Response<LoginData1> response) {
                        dialog.dismiss();
                       String strResponse = new Gson().toJson(response.body());
                       Log.e("strResponse",strResponse);
                       if (response.body()!=null) {
                           if (response.body().getStatus()!=null
                                   && !response.body().getStatus().isEmpty()
                                   && response.body().getStatus().equalsIgnoreCase("success")) {
                               callBackResponse.success("", strResponse);
                           }
                           else {
                               callBackResponse.fail("Invalid UserId or Password");
                           }
                       } else {
                           callBackResponse.fail("Unable to get response from server");
                       }
                    }

                    @Override
                    public void onFailure(@NotNull Call<LoginData1> call, @NotNull Throwable t) {
                        callBackResponse.fail("Server down, Please try again after sometime");
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                callBackResponse.fail("Server down, Please try again after sometime");
                dialog.dismiss();
            }
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(context);
        }
    }
    public void userdetail(final Context context, final mCallBackResponse callBackResponse) {

        if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {
            final Dialog dialog = getProgressDialog(context);
            dialog.show();
            try {

                AppSharedPreferences preferences = new AppSharedPreferences(((Activity) context).getApplication());

                EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
                Call<List<ProfileModel>> call = git.userdetail( "user",preferences.getLoginUserId(), preferences.getLoginApiKey(), preferences.getLoginUserId());
                call.enqueue(new Callback<List<ProfileModel>>() {
                    @Override
                    public void onResponse(Call<List<ProfileModel>> call, Response<List<ProfileModel>> response) {
                        dialog.dismiss();
                       String strResponse = new Gson().toJson(response.body());
                       Log.e("strResponse",strResponse);
                       if (response.body()!=null) {
                           if (response.body().size()>0) {
                               callBackResponse.success("", strResponse);
                               LoginData data = new Gson().fromJson(preferences.getLoginDetails(), LoginData.class);
                               data.setProfile(response.body().get(0));
                               preferences.setLoginDetails(new Gson().toJson(data));
                               callBackResponse.success("", "");
                           }
                           else {
                               callBackResponse.fail("Unable to get response from server");
                           }
                       } else {
                           callBackResponse.fail("Unable to get response from server");
//                           LoginData loginData = new LoginData();
//                           loginData.setName("Sunny Shah");
//                           callBackResponse.success("", new Gson().toJson(loginData));
                       }
                    }

                    @Override
                    public void onFailure(Call<List<ProfileModel>> call, Throwable t) {
                        callBackResponse.fail("Server down, Please try again after sometime");
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                callBackResponse.fail("Server down, Please try again after sometime");
                dialog.dismiss();
            }
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(context);
        }
    }
    public void newsCureent(final Context context, final mCallBackResponse callBackResponse) {

        if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {
            final Dialog dialog = getProgressDialog(context);
            dialog.show();
            try {

                AppSharedPreferences preferences = new AppSharedPreferences(((Activity) context).getApplication());

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
                                callBackResponse.success("Str", strResponse);
                            }
                            else {
                                callBackResponse.fail("Unable to get response from server");
                            }
                        } else {
                            callBackResponse.fail("Unable to get response from server");
//
                        }
                    }

                    @Override
                    public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                        callBackResponse.fail("Server down, Please try again after sometime");
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                callBackResponse.fail("Server down, Please try again after sometime");
                dialog.dismiss();
            }
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(context);
        }
    }

    public void subCategory_Article(final Context context,final mCallBackResponse callBackResponse) {

        if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {
            final Dialog dialog = getProgressDialog(context);
            dialog.show();
            try {

                AppSharedPreferences preferences = new AppSharedPreferences(((Activity) context).getApplication());

                EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
                Call<List<NewsSubCategoryModel>> call = git.subCategory( "article_category");
                call.enqueue(new Callback<List<NewsSubCategoryModel>>() {
                    @Override
                    public void onResponse(Call<List<NewsSubCategoryModel>> call, Response<List<NewsSubCategoryModel>> response) {
                        dialog.dismiss();
                        String strResponse = new Gson().toJson(response.body());
                        Log.e("strResponse",strResponse);
                        if (response.body()!=null) {
                            if (response.body().size()>0) {
                                callBackResponse.success("", strResponse);
                            }
                            else {
                                callBackResponse.fail("Unable to get response from server");
                            }
                        } else {
                            callBackResponse.fail("Unable to get response from server");
//
                        }
                    }

                    @Override
                    public void onFailure(Call<List<NewsSubCategoryModel>> call, Throwable t) {
                        callBackResponse.fail("Server down, Please try again after sometime");
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                callBackResponse.fail("Server down, Please try again after sometime");
                dialog.dismiss();
            }
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(context);
        }
    }



    public void TopnewsCureent(final Context context, String id,final mCallBackResponse callBackResponse) {

        if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {
            final Dialog dialog = getProgressDialog(context);
            dialog.show();
            try {

                AppSharedPreferences preferences = new AppSharedPreferences(((Activity) context).getApplication());

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
                            }
                            else {
                                callBackResponse.fail("Unable to get response from server");
                            }
                        } else {
                            callBackResponse.fail("Unable to get response from server");
//
                        }
                    }

                    @Override
                    public void onFailure(Call<List<NewsByCategoryModel>> call, Throwable t) {
                        callBackResponse.fail("Server down, Please try again after sometime");
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                callBackResponse.fail("Server down, Please try again after sometime");
                dialog.dismiss();
            }
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(context);
        }
    }


    public void Article_Category(final Context context, String cotegoryModels, String superCategoryId, final mCallBackResponse callBackResponse) {

       /* if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {
            final Dialog dialog = getProgressDialog(context);
            dialog.show();
            try {

                AppSharedPreferences preferences = new AppSharedPreferences(((Activity) context).getApplication());
                String language_set="";
                EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
                if (!TextUtils.isEmpty(preferences.getLanguage())) {
                    Log.e("Language===", "" + preferences.getLanguage());
                    if (preferences.getLanguage().equalsIgnoreCase("hi")) {
                        language_set = "hindi";
                    } else {
                        language_set = "english";
                    }

                }
                Call<Articles_Model> call = git.article( preferences.getLoginUserId(),superCategoryId,cotegoryModels,language_set);
                call.enqueue(new Callback<Articles_Model>() {
                    @Override
                    public void onResponse(Call<Articles_Model> call, Response<Articles_Model> response) {
                        dialog.dismiss();
                        String strResponse = new Gson().toJson(response.body());
                        Log.e("deepika===",strResponse);
                        if (response.body()!=null) {
//                            if (response.body().size()>0) {
                                callBackResponse.success("", strResponse);
                         *//*   }
                            else {
                                
                                callBackResponse.fail("Unable to get response from server");
                            }*//*
                        } else {
                            callBackResponse.fail("Unable to get response from server");
//
                        }
                    }

                    @Override
                    public void onFailure(Call<Articles_Model> call, Throwable t) {
                        callBackResponse.fail("Server down, Please try again after sometime");
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                callBackResponse.fail("Server down, Please try again after sometime");
                dialog.dismiss();
            }
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(context);
        }*/
    }

    public void getPackageCall(Activity context,String packeges, final mCallBackResponse callBackResponse) {

        if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {
            final Dialog dialog = getProgressDialog(context);
            dialog.show();
            try {

                AppSharedPreferences preferences = new AppSharedPreferences(((Activity) context).getApplication());
                String language_set="";
                EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
                Call<List<SubscriptionModel>> call = git.getPackage_Api(packeges);
                call.enqueue(new Callback<List<SubscriptionModel>>() {
                    @Override
                    public void onResponse(Call<List<SubscriptionModel>> call, Response<List<SubscriptionModel>> response) {
                        dialog.dismiss();
                        String strResponse = new Gson().toJson(response.body());
                        Log.e("deepika===",strResponse);
                        if (response.body()!=null) {
//                            if (response.body().size()>0) {
                                callBackResponse.success("", strResponse);
                         /*   }
                            else {

                                callBackResponse.fail("Unable to get response from server");
                            }*/
                        } else {
                            callBackResponse.fail("Unable to get response from server");
//
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SubscriptionModel>> call, Throwable t) {
                        callBackResponse.fail("Server down, Please try again after sometime");
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                callBackResponse.fail("Server down, Please try again after sometime");
                dialog.dismiss();
            }
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(context);
        }
    }


    public void youTube(final Context context,final mCallBackResponse callBackResponse) {

        /*if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {
            final Dialog dialog = getProgressDialog(context);
            dialog.show();
            try {
//                "UC1NF71EwP41VdjAU1iXdLkw"
                AppSharedPreferences preferences = new AppSharedPreferences(((Activity) context).getApplication());

                EndPointInterface git = ApiclientsYoutube.getClient().create(EndPointInterface.class);
                Call<YoutubeMainModel> call = git.youtube( "snippet",youtube_channel_id,"date",context.getResources().getString(R.string.youtube_api_key));
                call.enqueue(new Callback <YoutubeMainModel>() {
                    @Override
                    public void onResponse(Call <YoutubeMainModel> call, Response<YoutubeMainModel> response) {
                        dialog.dismiss();
                        String strResponse = new Gson().toJson(response.body());
                        Log.e("youtube==",strResponse);
                        if (response.body()!=null) {
                            callBackResponse.success("", strResponse);
                           *//* if (response.body().size()>0) {

                            }*//*
                            *//*else {
                                callBackResponse.fail("Unable to get response from server");
                            }*//*
                        } else {
                            callBackResponse.fail("Unable to get response from server");
//
                        }
                    }
                    @Override
                    public void onFailure(Call <YoutubeMainModel> call, Throwable t) {
                        callBackResponse.fail("Server down, Please try again after sometime");
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                callBackResponse.fail("Server down, Please try again after sometime");
                dialog.dismiss();
            }
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(context);
        }*/
    }


    public void signup(final Context context, String firstname, String fatherName, String mobile, String email, String full_address, String password, String stateid, String cityid, final mCallBackResponse callBackResponse) {

        if (UtilMethods.INSTANCE.isNetworkAvialable(context))
        {
            final Dialog dialog = getProgressDialog(context);
            dialog.show();

            try {
                EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
                Call<SignUpModel> call = git.signup("user", "user",firstname, fatherName,mobile,email,full_address,password,"success",stateid,cityid);
                call.enqueue(new Callback<SignUpModel>() {
                    @Override
                    public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                        dialog.dismiss();
                        String strResponse = new Gson().toJson(response.body());
                        Log.e("strResponse",strResponse);
                        if (response.body()!=null) {
                            if (response.body()!=null && response.body().getStatus().equalsIgnoreCase("success")) {
                                callBackResponse.success("", response.body().getMessage());
                            }
                            else {
                                callBackResponse.fail(response.body().getMessage());
                            }
                        } else {
                            callBackResponse.fail("Invalid UserId or Password");
                        }
                    }

                    @Override
                    public void onFailure(Call<SignUpModel> call, Throwable t) {
                        callBackResponse.fail(t.getMessage());
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                callBackResponse.fail(e.getMessage());
                dialog.dismiss();
            }
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(context);
        }

    }


    public void uploadImage(Context context, File file, String table, String colName,  final mCallBackResponse callBackResponse) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {
            final Dialog dialog = getProgressDialog(context);
            dialog.show();
            try {
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);
                // MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part partfile = MultipartBody.Part.createFormData(colName, file.getName(), requestFile);

                AppSharedPreferences preferences = new AppSharedPreferences(((Activity) context).getApplication());
                EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
                Call<MessageModel> call = git.updateImage(
                        RequestBody.create(MediaType.parse("multipart/form-data"), preferences.getLoginUserId() ),
                        RequestBody.create(MediaType.parse("multipart/form-data"), preferences.getLoginUserId() ),
                        RequestBody.create(MediaType.parse("multipart/form-data"), preferences.getLoginApiKey() ),
                        RequestBody.create(MediaType.parse("multipart/form-data"), table ),
                        RequestBody.create(MediaType.parse("multipart/form-data"), preferences.getLoginUserName() ),partfile);
                call.enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(@NotNull Call<MessageModel> call, @NotNull Response<MessageModel> response) {
                        dialog.dismiss();
                        String strResponse = new Gson().toJson(response.body());
                        Log.e("strResponse",strResponse);
                        if (response.body()!=null && response.body().getStatus()!=null && response.body().getStatus().equalsIgnoreCase("success")) {
                            callBackResponse.success("", response.body().getMessage());
                        } else {
                            callBackResponse.fail(""+response.body().getStatus());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<MessageModel> call, @NotNull Throwable t) {
                        callBackResponse.fail(t.getMessage());
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                callBackResponse.fail(e.getMessage());
                dialog.dismiss();
            }
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(context);
        }
    }


    public void updateStatus(Context context, String id, String status,final mCallBackResponse callBackResponse) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {
            final Dialog dialog = getProgressDialog(context);
            dialog.show();
            try {

                AppSharedPreferences preferences = new AppSharedPreferences(((Activity) context).getApplication());
                EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
                Call<MessageModel> call = git.updateStatus(
                        RequestBody.create(MediaType.parse("multipart/form-data"), id),
                        RequestBody.create(MediaType.parse("multipart/form-data"), preferences.getLoginUserId() ),
                        RequestBody.create(MediaType.parse("multipart/form-data"), preferences.getLoginApiKey() ),
                        RequestBody.create(MediaType.parse("multipart/form-data"), service_requests_table ),
                        RequestBody.create(MediaType.parse("multipart/form-data"), status),
                        RequestBody.create(MediaType.parse("multipart/form-data"), preferences.getLoginUserId())
                );

                call.enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(@NotNull Call<MessageModel> call, @NotNull Response<MessageModel> response) {
                        dialog.dismiss();
                        String strResponse = new Gson().toJson(response.body());
                        Log.e("strResponse",strResponse);
                        if (response.body()!=null && response.body().getStatus()!=null && response.body().getStatus().equalsIgnoreCase("success")) {
                            callBackResponse.success("", response.body().getMessage());
                        } else {
                            callBackResponse.fail(""+response.body().getStatus());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<MessageModel> call, @NotNull Throwable t) {
                        callBackResponse.fail(t.getMessage());
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                callBackResponse.fail(e.getMessage());
                dialog.dismiss();
            }
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(context);
        }
    }

    public void updateAccessToken(Context context, String token,final mCallBackResponse callBackResponse) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {
//            final Dialog dialog = getProgressDialog(context);
//            dialog.show();
            try {

                AppSharedPreferences preferences = new AppSharedPreferences(((Activity) context).getApplication());
                EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
                Call<MessageModel> call = git.updateAccessToken(preferences.getLoginUserId(),user_table,token);
                call.enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(@NotNull Call<MessageModel> call, @NotNull Response<MessageModel> response) {
//                        dialog.dismiss();
//                        String strResponse = new Gson().toJson(response.body());
//                        Log.e("strResponse",strResponse);
//                        if (response.body()!=null && response.body().getStatus()!=null && response.body().getStatus().equalsIgnoreCase("success")) {
//                            callBackResponse.success("", response.body().getMessage());
//                        } else {
//                            callBackResponse.fail(""+response.body().getStatus());
//                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<MessageModel> call, @NotNull Throwable t) {
//                        callBackResponse.fail(t.getMessage());
//                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
//                callBackResponse.fail(e.getMessage());
//                dialog.dismiss();
            }
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(context);
        }
    }


    public void dashboardDetails(Context context, final mCallBackResponse callBackResponse) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {
            final Dialog dialog = getProgressDialog(context);
            dialog.show();
            try {

                AppSharedPreferences preferences = new AppSharedPreferences(((Activity) context).getApplication());
                EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
                Call<DashboardDetailsModel> call = git.dashboardDetails(preferences.getLoginUserId(), preferences.getLoginApiKey());
                call.enqueue(new Callback<DashboardDetailsModel>() {
                    @Override
                    public void onResponse(@NotNull Call<DashboardDetailsModel> call, @NotNull Response<DashboardDetailsModel> response) {
                        dialog.dismiss();
                        String strResponse = new Gson().toJson(response.body());
                        Log.e("strResponse",strResponse);
                        if (response.body()!=null && response.body().getStatus().equalsIgnoreCase("success")) {
                            callBackResponse.success("", strResponse);
                        } else {
                            callBackResponse.fail("No data");
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<DashboardDetailsModel> call, @NotNull Throwable t) {
                        callBackResponse.fail(t.getMessage());
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                callBackResponse.fail(e.getMessage());
                dialog.dismiss();
            }
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(context);
        }
    }




    public void version(Context context, final mCallBackResponse callBackResponse) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<List<VersionModel>> call = git.version("user");
            call.enqueue(new Callback<List<VersionModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<VersionModel>> call, @NotNull Response<List<VersionModel>> response) {
                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body().size()>0) {
//                            callbackresponse.success("", strresponse);
                            if (response.body().get(0).getVcode() > BuildConfig.VERSION_CODE) {
                                UpdateDialog(context);
                            }
                        }
                        else {
//                            callBackResponse.fail("No data");
                        }
                    } else {
//                        callBackResponse.fail("No data");
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<VersionModel>> call, @NotNull Throwable t) {
//                    callBackResponse.fail(t.getMessage());
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
            dialog.dismiss();
        }
    }


    public void UpdateDialog(final Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.update_available, null);

        TextView tvLater = (TextView) view.findViewById(R.id.tv_later);
        Button tvOk = (Button) view.findViewById(R.id.tv_ok);

        final Dialog dialog = new Dialog(context);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMarket(context);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private static void goToMarket(Context mContext) {
        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(UpdateChecker.ROOT_PLAY_STORE_DEVICE + mContext.getPackageName())));
    }

    public void updateStatus(Context context, String id, String status, String reason, final mCallBackResponse callBackResponse) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {
            final Dialog dialog = getProgressDialog(context);
            dialog.show();
            try {

                AppSharedPreferences preferences = new AppSharedPreferences(((Activity) context).getApplication());
                EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
                Call<MessageModel> call = git.updateStatus(
                        RequestBody.create(MediaType.parse("multipart/form-data"), id),
                        RequestBody.create(MediaType.parse("multipart/form-data"), preferences.getLoginUserId() ),
                        RequestBody.create(MediaType.parse("multipart/form-data"), preferences.getLoginApiKey() ),
                        RequestBody.create(MediaType.parse("multipart/form-data"), service_requests_table ),
                        RequestBody.create(MediaType.parse("multipart/form-data"), status),
                        RequestBody.create(MediaType.parse("multipart/form-data"), reason),
                        RequestBody.create(MediaType.parse("multipart/form-data"), preferences.getLoginUserId())
                );
                call.enqueue(new Callback<MessageModel>() {
                    @Override
                    public void onResponse(@NotNull Call<MessageModel> call, @NotNull Response<MessageModel> response) {
                        dialog.dismiss();
                        String strResponse = new Gson().toJson(response.body());
                        Log.e("strResponse",strResponse);
                        if (response.body()!=null && response.body().getStatus()!=null && response.body().getStatus().equalsIgnoreCase("success")) {
                            callBackResponse.success("", response.body().getMessage());
                        } else {
                            if (response.body() != null) {
                                callBackResponse.fail(""+response.body().getMessage());
                            } else {
                                callBackResponse.fail("Unable to get response");
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<MessageModel> call, @NotNull Throwable t) {
                        callBackResponse.fail(t.getMessage());
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                callBackResponse.fail(e.getMessage());
                dialog.dismiss();
            }
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(context);
        }
    }

    public void changePassword(final Context context, String number, String newpassword, final mCallBackResponse callBackResponse) {

        final Dialog dialog = getProgressDialog(context);
        dialog.show();
        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<OTPModel> call = git.changePassword( number,newpassword);
            call.enqueue(new Callback<OTPModel>() {
                @Override
                public void onResponse(@NotNull Call<OTPModel> call, @NotNull Response<OTPModel> response) {
                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body().getMessage().equalsIgnoreCase("success")) {
                            callBackResponse.success("", strResponse);
                        }
                        else {
                            callBackResponse.fail(response.body().getMessage());
                        }
                    } else {
                        callBackResponse.fail("Invalid Mobile Number");
                    }
                }

                @Override
                public void onFailure(@NotNull Call<OTPModel> call, @NotNull Throwable t) {
                    callBackResponse.fail(t.getMessage());
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
            dialog.dismiss();
        }

    }


    public void otp(final Context context,  String mobile, String sms, final mCallBackResponse callBackResponse) {

        final Dialog dialog = getProgressDialog(context);
        dialog.show();
        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<OTPModel> call = git.otp( mobile, sms);
            call.enqueue(new Callback<OTPModel>() {
                @Override
                public void onResponse(@NotNull Call<OTPModel> call, @NotNull Response<OTPModel> response) {
                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body().getMessage().equalsIgnoreCase("success")) {
                            callBackResponse.success("", strResponse);
                        }
                        else {
                            callBackResponse.fail(response.body().getMessage());
                        }
                    } else {
                        callBackResponse.fail("Invalid UserId or Password");
                    }
                }

                @Override
                public void onFailure(@NotNull Call<OTPModel> call, @NotNull Throwable t) {
                    callBackResponse.fail(t.getMessage());
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
            dialog.dismiss();
        }

    }


}
