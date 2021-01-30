package com.smartwebarts.briefnx.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.gson.Gson;
import com.smartwebarts.briefnx.LoginActivity;
import com.smartwebarts.briefnx.models.LoginData;
import com.smartwebarts.briefnx.models.LoginData1;


public class AppSharedPreferences extends AndroidViewModel {

//    INSTANCE;

    private static final String PREF_NAME = "SMS";
    private static final String loginDetails = "loginDetails";
    private static final String language = "lang";
    private Context context;
    SharedPreferences preferences;

    public AppSharedPreferences(@NonNull Application application) {
        super(application);
        context = getApplication().getApplicationContext();
    }

    public SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
        return preferences;
    }

    public String getLoginDetails() {
        return getPreferences().getString(loginDetails, "");
    }

    public void setLoginDetails(String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(loginDetails, value);
        editor.commit();
    }
    public void setlanguage(String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(language, value);
        editor.commit();
    }

    public String getLoginUserName() {
        String temp = getLoginDetails();
        if (!temp.isEmpty()) {
            LoginData1 data = new Gson().fromJson(getLoginDetails(), LoginData1.class);
            if (data != null && data.getName() != null && data.getName() != null) {
                return data.getName();
            }
        }
        return "";
    }

    public String getLoginEmail() {
        String temp = getLoginDetails();
        if (!temp.isEmpty()) {
            LoginData1 data = new Gson().fromJson(getLoginDetails(), LoginData1.class);
            if (data != null && data.getDetail().getEmail() != null && data.getDetail().getEmail() != null) {
                return data.getDetail().getEmail();
            }
        }
        return "";
    }

    public String getLoginApiKey() {
        String temp = getLoginDetails();
        if (!temp.isEmpty()) {
            LoginData1 data = new Gson().fromJson(getLoginDetails(), LoginData1.class);
            if (data != null && data.getApiKey() != null && data.getApiKey() != null) {
                return data.getApiKey();
            }
        }
        return "";
    }

    public String getLoginMobile() {
        String temp = getLoginDetails();
        if (!temp.isEmpty()) {
            LoginData1 data = new Gson().fromJson(getLoginDetails(), LoginData1.class);
            if (data != null && data.getDetail().getContact() != null && data.getDetail().getContact() != null) {
                return data.getDetail().getContact();
            }
        }
        return "";
    }


    public String getLoginUserStatus() {
        String temp = getLoginDetails();
        if (!temp.isEmpty()) {
            LoginData data = new Gson().fromJson(getLoginDetails(), LoginData.class);
            if (data != null && data.getProfile() != null && data.getProfile().getStatus() != null) {
                return data.getProfile().getStatus();
            }
        }
        return "";
    }

//    public String getLoginUserProfilePic(){
//        String temp = getLoginDetails();
//        if (!temp.isEmpty())
//        {
//            LoginData data = new Gson().fromJson(getLoginDetails(), LoginData.class);
//            if (data != null && data.getProfile().getProfilePic()!=null){
//                return data.getProfile().getProfilePic();
//            }
//        }
//        return "";
//    }


    public String getLoginUserLoginId() {
        String temp = getLoginDetails();
        if (!temp.isEmpty()) {
            LoginData data = new Gson().fromJson(getLoginDetails(), LoginData.class);
            if (data != null && data.getProfile() != null && data.getProfile().getUserid() != null) {
                return data.getProfile().getUserid();
            }
        }
        return "";
    }

    public String getLoginUserId() {
        String temp = getLoginDetails();
        if (!temp.isEmpty()) {
            LoginData1 data = new Gson().fromJson(getLoginDetails(), LoginData1.class);
            if (data != null && data.getUserid() != null && data.getUserid() != null) {
                return data.getUserid();
            }
        }
        return "";
    }

    public String getLoginRole() {
        String temp = getLoginDetails();
        if (!temp.isEmpty()) {
            LoginData1 data = new Gson().fromJson(getLoginDetails(), LoginData1.class);
            if (data != null && data.getRole() != null && data.getRole() != null) {
                return data.getRole();
            }
        }
        return "";
    }

    public String getLoginUserShopImage() {
        String temp = getLoginDetails();
        if (!temp.isEmpty()) {
            LoginData data = new Gson().fromJson(getLoginDetails(), LoginData.class);
            if (data != null && data.getProfile() != null && data.getProfile().getShopimage() != null) {
                return data.getProfile().getShopimage();
            }
        }
        return "";
    }

    public String getLoginUserGstImage() {
        String temp = getLoginDetails();
        if (!temp.isEmpty()) {
            LoginData data = new Gson().fromJson(getLoginDetails(), LoginData.class);
            if (data != null && data.getProfile() != null && data.getProfile().getGstimage() != null) {
                return data.getProfile().getGstimage();
            }
        }
        return "";
    }

    public String getLoginUserAdhaarImage() {
        String temp = getLoginDetails();
        if (!temp.isEmpty()) {
            LoginData data = new Gson().fromJson(getLoginDetails(), LoginData.class);
            if (data != null && data.getProfile() != null && data.getProfile().getAdhaarimage() != null) {
                return data.getProfile().getAdhaarimage();
            }
        }
        return "";
    }

    public String getLoginUserMechanicImage() {
        String temp = getLoginDetails();
        if (!temp.isEmpty()) {
            LoginData data = new Gson().fromJson(getLoginDetails(), LoginData.class);
            if (data != null && data.getProfile() != null && data.getProfile().getMechanicimage() != null) {
                return data.getProfile().getMechanicimage();
            }
        }
        return "";
    }

    public String getLoginUserAddress() {
        String temp = getLoginDetails();
        if (!temp.isEmpty()) {
            LoginData1 data = new Gson().fromJson(getLoginDetails(), LoginData1.class);
            if (data != null && data.getDetail().getAddress() != null && data.getDetail().getAddress() != null) {
                return data.getDetail().getAddress();
            }
        }
        return "";
    }

    public String getLoginUserShopName() {
        String temp = getLoginDetails();
        if (!temp.isEmpty()) {
            LoginData data = new Gson().fromJson(getLoginDetails(), LoginData.class);
            if (data != null && data.getProfile() != null && data.getProfile().getShopName() != null) {
                return data.getProfile().getShopName();
            }
        }
        return "";
    }

    public String getLoginUserGstin() {
        String temp = getLoginDetails();
        if (!temp.isEmpty()) {
            LoginData data = new Gson().fromJson(getLoginDetails(), LoginData.class);
            if (data != null && data.getProfile() != null && data.getProfile().getGST() != null) {
                return data.getProfile().getGST();
            }
        }
        return "";
    }


    public String getLoginUserImage() {
        String temp = getLoginDetails();
        if (!temp.isEmpty()) {
            LoginData data = new Gson().fromJson(getLoginDetails(), LoginData.class);
            if (data != null && data.getProfile() != null && data.getProfile().getImage() != null) {
                return data.getProfile().getImage();
            }
        }
        return "";
    }

    public void logout(Context context) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public String getLanguage() {
        return getPreferences().getString(language, "");
    }

    public Context getContext() {
        return context;
    }

}
