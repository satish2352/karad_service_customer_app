package com.example.karaduser.ModelList;

import org.json.JSONException;
import org.json.JSONObject;

public class
RequestList
{
    String business_info_name;
    String fld_business_id;
    String mobile;
    String owner_name;
    String email;
    String fld_actual_booking_slot;
    String fld_service_issuedorreturned;
    String fld_service_requested_date;
    String business_image;
    String fld_user_issued_servicesApp;
    String vendor_id;
    String business_info_id;
    String fld_business_name;

    public RequestList()
    {

    }

    public RequestList(JSONObject jsonObject)
    {
        try
        {
            this.business_info_id = jsonObject.getString("business_info_id");
            this.business_info_name = jsonObject.getString("business_info_name");
            this.fld_business_id = jsonObject.getString("fld_business_id");
            this.fld_business_name = jsonObject.getString("fld_business_name");
            this.vendor_id = jsonObject.getString("vendor_id");
            this.mobile = jsonObject.getString("mobile");
            this.owner_name = jsonObject.getString("owner_name");
            this.email = jsonObject.getString("email");
            this.fld_actual_booking_slot = jsonObject.getString("fld_actual_booking_slot");
            this.fld_service_issuedorreturned = jsonObject.getString("fld_service_issuedorreturned");
            this.fld_user_issued_servicesApp = jsonObject.getString("fld_user_issued_servicesApp");
            this.fld_service_requested_date = jsonObject.getString("fld_service_requested_date");
            this.business_image = jsonObject.getString("business_image");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getFld_user_issued_servicesApp() {
        return fld_user_issued_servicesApp;
    }

    public void setFld_user_issued_servicesApp(String fld_user_issued_servicesApp) {
        this.fld_user_issued_servicesApp = fld_user_issued_servicesApp;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getBusiness_info_id() {
        return business_info_id;
    }

    public void setBusiness_info_id(String business_info_id) {
        this.business_info_id = business_info_id;
    }

    public String getFld_business_name() {
        return fld_business_name;
    }

    public void setFld_business_name(String fld_business_name) {
        this.fld_business_name = fld_business_name;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFld_actual_booking_slot() {
        return fld_actual_booking_slot;
    }

    public void setFld_actual_booking_slot(String fld_actual_booking_slot) {
        this.fld_actual_booking_slot = fld_actual_booking_slot;
    }

    public String getFld_service_issuedorreturned() {
        return fld_service_issuedorreturned;
    }

    public void setFld_service_issuedorreturned(String status) {
        this.fld_service_issuedorreturned = status;
    }

    public String getFld_service_requested_date() {
        return fld_service_requested_date;
    }

    public void setFld_service_requested_date(String fld_service_requested_date) {
        this.fld_service_requested_date = fld_service_requested_date;
    }

    public String getBusiness_image() {
        return business_image;
    }

    public void setBusiness_image(String business_image) {
        this.business_image = business_image;
    }
}
