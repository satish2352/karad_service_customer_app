package com.example.karaduser.ModelList;

import org.json.JSONException;
import org.json.JSONObject;

public class CountryList {
    private String fld_business_category_id;
    private String fld_business_id;
    private String fld_business_category_name;





    public CountryList(JSONObject jsonObject) {
        try {

            this.fld_business_category_id = jsonObject.getString("fld_business_category_id");
            this.fld_business_id = jsonObject.getString("fld_business_id");
            this.fld_business_category_name = jsonObject.getString("fld_business_category_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String toString() {
        return this.fld_business_category_name;            // What to display in the Spinner list.
    }
    public CountryList(String fld_business_category_name) {
        this.fld_business_category_name = fld_business_category_name;
    }

    public String getFld_business_category_id() {
        return fld_business_category_id;
    }

    public void setFld_business_category_id(String fld_business_category_id) {
        this.fld_business_category_id = fld_business_category_id;
    }

    public String getFld_business_id() {
        return fld_business_id;
    }

    public void setFld_business_id(String fld_business_id) {
        this.fld_business_id = fld_business_id;
    }

    public String getFld_business_category_name() {
        return fld_business_category_name;
    }

    public void setFld_business_category_name(String fld_business_category_name) {
        this.fld_business_category_name = fld_business_category_name;
    }
}
