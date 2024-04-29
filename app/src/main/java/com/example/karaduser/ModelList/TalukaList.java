package com.example.karaduser.ModelList;

import org.json.JSONException;
import org.json.JSONObject;

public class TalukaList {
    private String district_id;
    private String taluka_id;
    private String taluka_name;


    public TalukaList(JSONObject jsonObject) {
        try {

            this.district_id = jsonObject.getString("fld_district_id");
            this.taluka_id = jsonObject.getString("fld_taluka_id");
            this.taluka_name = jsonObject.getString("fld_taluka_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String toString() {
        return this.taluka_name;            // What to display in the Spinner list.
    }
    public TalukaList(String taluka_name) {
        this.taluka_name = taluka_name;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getTaluka_id() {
        return taluka_id;
    }

    public void setTaluka_id(String taluka_id) {
        this.taluka_id = taluka_id;
    }

    public String getTaluka_name() {
        return taluka_name;
    }

    public void setTaluka_name(String taluka_name) {
        this.taluka_name = taluka_name;
    }
}

