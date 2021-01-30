package com.smartwebarts.briefnx.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("states_id")
    @Expose
    private String statesId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getStatesId() {
        return statesId;
    }

    public void setStatesId(String statesId) {
        this.statesId = statesId;
    }

}
