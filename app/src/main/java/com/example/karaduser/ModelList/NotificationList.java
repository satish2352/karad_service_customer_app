package com.example.karaduser.ModelList;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationList {
    private String notification_id;
    private String business_info_name;
    private String fld_business_id;
    private String business_image;
    private String user_id;
    private String user_type;
    private String note_text;
    private String readnotification;
    private String created_at;
    private String updated_at;


    public NotificationList(JSONObject jsonObject) {
        try {
            this.notification_id = jsonObject.getString("notification_id");
            this.business_info_name = jsonObject.getString("business_info_name");
            this.fld_business_id = jsonObject.getString("fld_business_id");
            this.business_image = jsonObject.getString("business_image");
            this.user_id = jsonObject.getString("user_id");
            this.user_type = jsonObject.getString("user_type");
            this.note_text = jsonObject.getString("note_text");
            this.readnotification = jsonObject.getString("readnotification");
            this.created_at = jsonObject.getString("created_at");
            this.updated_at = jsonObject.getString("updated_at");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getFld_business_id() {
        return fld_business_id;
    }

    public void setFld_business_id(String fld_business_id) {
        this.fld_business_id = fld_business_id;
    }

    public String getBusiness_image() {
        return business_image;
    }

    public void setBusiness_image(String business_image) {
        this.business_image = business_image;
    }

    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public String getBusiness_info_name() {
        return business_info_name;
    }

    public void setBusiness_info_name(String business_info_name) {
        this.business_info_name = business_info_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getNote_text() {
        return note_text;
    }

    public void setNote_text(String note_text) {
        this.note_text = note_text;
    }

    public String getReadnotification() {
        return readnotification;
    }

    public void setReadnotification(String readnotification) {
        this.readnotification = readnotification;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
