package com.smartwebarts.briefnx.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData1 {
    @SerializedName("status_number")
    @Expose
    private Integer statusNumber;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("api_key")
    @Expose
    private String apiKey;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("detail")
    @Expose
    private Detail detail;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getStatusNumber() {
        return statusNumber;
    }

    public void setStatusNumber(Integer statusNumber) {
        this.statusNumber = statusNumber;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
