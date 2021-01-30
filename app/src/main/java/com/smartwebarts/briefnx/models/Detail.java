package com.smartwebarts.briefnx.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("father_name")
        @Expose
        private String fatherName;
        @SerializedName("contact")
        @Expose
        private String contact;
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
        private Object image;
        @SerializedName("address")
        @Expose
        private String address;

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

        public String getFatherName() {
            return fatherName;
        }

        public void setFatherName(String fatherName) {
            this.fatherName = fatherName;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
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

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

