package com.example.karaduser.ModelList;

import org.json.JSONException;
import org.json.JSONObject;

public class HistoryList {
    private String business_info_name;
    private String fld_business_id;
    private String fld_actual_booking_slot;
    private String fld_service_requested_date;
    private String status;



    public HistoryList(JSONObject jsonObject) {
        try {
            this.business_info_name = jsonObject.getString("business_info_name");
            this.fld_business_id = jsonObject.getString("fld_business_id");
            this.fld_actual_booking_slot = jsonObject.getString("fld_actual_booking_slot");
            this.fld_service_requested_date = jsonObject.getString("fld_service_requested_date");
            this.status = jsonObject.getString("status");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getFld_actual_booking_slot() {
        return fld_actual_booking_slot;
    }

    public void setFld_actual_booking_slot(String fld_actual_booking_slot) {
        this.fld_actual_booking_slot = fld_actual_booking_slot;
    }

    public String getFld_service_requested_date() {
        return fld_service_requested_date;
    }

    public void setFld_service_requested_date(String fld_service_requested_date) {
        this.fld_service_requested_date = fld_service_requested_date;
    }
}
