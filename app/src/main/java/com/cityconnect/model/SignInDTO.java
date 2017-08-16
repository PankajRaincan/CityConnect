package com.cityconnect.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by raincan on 16/8/17.
 */

public class SignInDTO implements Serializable {

    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;

    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("mobile")
    String mobile;
    @SerializedName("status")
    String status;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
