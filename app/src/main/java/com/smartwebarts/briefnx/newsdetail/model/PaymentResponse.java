package com.smartwebarts.briefnx.newsdetail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("recentinsertedid")
    @Expose
    private Integer recentinsertedid;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getRecentinsertedid() {
        return recentinsertedid;
    }

    public void setRecentinsertedid(Integer recentinsertedid) {
        this.recentinsertedid = recentinsertedid;
    }
}