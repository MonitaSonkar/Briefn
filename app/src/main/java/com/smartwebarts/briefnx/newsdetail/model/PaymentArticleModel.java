package com.smartwebarts.briefnx.newsdetail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentArticleModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("payment_id")
    @Expose
    private String paymentId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("signature")
    @Expose
    private String signature;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("packeges_id")
    @Expose
    private String packegesId;
    @SerializedName("pmt_status")
    @Expose
    private String pmtStatus;
    @SerializedName("remaining_days")
    @Expose
    private Integer remainingDays;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPackegesId() {
        return packegesId;
    }

    public void setPackegesId(String packegesId) {
        this.packegesId = packegesId;
    }

    public String getPmtStatus() {
        return pmtStatus;
    }

    public void setPmtStatus(String pmtStatus) {
        this.pmtStatus = pmtStatus;
    }

    public Integer getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(Integer remainingDays) {
        this.remainingDays = remainingDays;
    }

}
