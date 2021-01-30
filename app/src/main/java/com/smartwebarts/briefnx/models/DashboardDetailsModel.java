package com.smartwebarts.briefnx.models;

public class DashboardDetailsModel {

    int paid;
    int workInProgress;
    int unpaid;
    int all;
    String status;

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getWorkInProgress() {
        return workInProgress;
    }

    public void setWorkInProgress(int workInProgress) {
        this.workInProgress = workInProgress;
    }

    public int getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(int unpaid) {
        this.unpaid = unpaid;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
