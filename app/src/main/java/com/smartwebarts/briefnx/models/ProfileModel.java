package com.smartwebarts.briefnx.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smartwebarts.briefnx.utils.Urls;

public class ProfileModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("GST")
    @Expose
    private String gST;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("full_address")
    @Expose
    private String fullAddress;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("api_key")
    @Expose
    private String apiKey;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("blocked")
    @Expose
    private String blocked;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("shopimage")
    @Expose
    private String shopimage;
    @SerializedName("gstimage")
    @Expose
    private String gstimage;
    @SerializedName("adhaarimage")
    @Expose
    private String adhaarimage;
    @SerializedName("mechanicimage")
    @Expose
    private String mechanicimage;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("status")
    @Expose
    private String status;

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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getGST() {
        return gST;
    }

    public void setGST(String gST) {
        this.gST = gST;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBlocked() {
        return blocked;
    }

    public void setBlocked(String blocked) {
        this.blocked = blocked;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShopimage() {
        return Urls.USER_PROFILE_IMAGE + shopimage;
    }

    public void setShopimage(String shopimage) {
        this.shopimage = shopimage;
    }

    public String getGstimage() {
        return Urls.USER_PROFILE_IMAGE + gstimage;
    }

    public void setGstimage(String gstimage) {
        this.gstimage = gstimage;
    }

    public String getgST() {
        return gST;
    }

    public void setgST(String gST) {
        this.gST = gST;
    }

    public String getAdhaarimage() {
        return Urls.USER_PROFILE_IMAGE + adhaarimage;
    }

    public void setAdhaarimage(String adhaarimage) {
        this.adhaarimage = adhaarimage;
    }

    public String getMechanicimage() {
        return mechanicimage;
    }

    public void setMechanicimage(String mechanicimage) {
        this.mechanicimage = mechanicimage;
    }
}
