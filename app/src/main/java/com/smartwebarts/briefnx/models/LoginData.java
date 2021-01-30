package com.smartwebarts.briefnx.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("status_number")
    @Expose
    private Integer statusNumber;

    @SerializedName("profile")
    @Expose
    private ProfileModel profile;

    @SerializedName("status")
    @Expose
    private String status;

    public Integer getStatusNumber() {
        return statusNumber;
    }

    public void setStatusNumber(Integer statusNumber) {
        this.statusNumber = statusNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProfileModel getProfile() {
        return profile;
    }

    public void setProfile(ProfileModel profile) {
        this.profile = profile;
    }
}