package com.example.karaduser.ModelList;

import org.json.JSONException;
import org.json.JSONObject;

public class AppointmentList {
    private String business_info_id;
    private String business_info_name;
    private String fld_business_id;
    private String fld_business_name;
    private String address;
    private String vendor_id;
    private String business_image;
    private int variant_cnt;
    private float avg_rating;


    public AppointmentList(JSONObject jsonObject) {
        try {
            this.business_info_id = jsonObject.getString("business_info_id");
            this.business_info_name = jsonObject.getString("business_info_name");
            this.fld_business_id = jsonObject.getString("fld_business_id");
            this.fld_business_name = jsonObject.getString("fld_business_name");
            this.vendor_id = jsonObject.getString("vendor_id");
            this.address = jsonObject.getString("address");
            this.business_image = jsonObject.getString("business_image");
            this.variant_cnt = jsonObject.getInt("variant_cnt");
            this.avg_rating = (float) jsonObject.getDouble("avg_rating");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getBusiness_info_id() {
        return business_info_id;
    }

    public void setBusiness_info_id(String business_info_id) {
        this.business_info_id = business_info_id;
    }

    public String getBusiness_info_name() {
        return business_info_name;
    }

    public void setBusiness_info_name(String business_info_name) {
        this.business_info_name = business_info_name;
    }

    public String getFld_business_id() {
        return fld_business_id;
    }

    public void setFld_business_id(String fld_business_id) {
        this.fld_business_id = fld_business_id;
    }

    public String getFld_business_name() {
        return fld_business_name;
    }

    public void setFld_business_name(String fld_business_name) {
        this.fld_business_name = fld_business_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusiness_image() {
        return business_image;
    }

    public void setBusiness_image(String business_image) {
        this.business_image = business_image;
    }

    public int getVariant_cnt() {
        return variant_cnt;
    }

    public void setVariant_cnt(int variant_cnt) {
        this.variant_cnt = variant_cnt;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public float getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(float avg_rating) {
        this.avg_rating = avg_rating;
    }
}
