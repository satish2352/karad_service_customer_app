package com.example.karaduser.ModelList;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class FeatureadsList implements Serializable
{
    private String fld_business_category_name;
    private String title;
    private String discription;
    private String feature_image;

        public FeatureadsList(JSONObject jsonObject)
        {
            try
            {
                this.fld_business_category_name = jsonObject.getString("fld_business_category_name");
                this.title = jsonObject.getString("title");
                this.discription = jsonObject.getString("discription");
                this.feature_image = jsonObject.getString("feature_image");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }


    public String getFld_business_category_name() {
        return fld_business_category_name;
    }

    public void setFld_business_category_name(String fld_business_category_name) {
        this.fld_business_category_name = fld_business_category_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getFeature_image() {
        return feature_image;
    }

    public void setFeature_image(String feature_image) {
        this.feature_image = feature_image;
    }
}