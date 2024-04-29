package com.example.karaduser.ModelList;

import org.json.JSONObject;

public class CommentList
{
    String fld_business_id;
    String business_info_id;
    String user_id;
    String review_star;
    String review_text;
    String review_date;

    public CommentList(JSONObject jsonObject)
    {
        try {
            this.fld_business_id = jsonObject.getString("fld_business_id");
            this.business_info_id = jsonObject.getString("business_info_id");
            this.user_id = jsonObject.getString("user_id");
            this.review_star = jsonObject.getString ("review_star");
            this.review_text = jsonObject.getString ("review_text");
            this.review_date = jsonObject.getString ("review_date");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getFld_business_id() {
        return fld_business_id;
    }

    public void setFld_business_id(String fld_business_id) {
        this.fld_business_id = fld_business_id;
    }

    public String getBusiness_info_id() {
        return business_info_id;
    }

    public void setBusiness_info_id(String business_info_id) {
        this.business_info_id = business_info_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReview_star() {
        return review_star;
    }

    public void setReview_star(String review_star) {
        this.review_star = review_star;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }
}
