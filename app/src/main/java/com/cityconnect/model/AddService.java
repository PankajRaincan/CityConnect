package com.cityconnect.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by raincan on 17/8/17.
 */

public class AddService implements Serializable {

    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("mobile")
    String mobile;
    @SerializedName("address_line_one")
    String address1;
    @SerializedName("address_line_two")
    String address2;
    @SerializedName("dob")
    String dob;
    @SerializedName("description")
    String desc;
    @SerializedName("lat")
    String latitude;
    @SerializedName("lng")
    String longitude;
    @SerializedName("image_url")
    String image;
    @SerializedName("status")
    String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
