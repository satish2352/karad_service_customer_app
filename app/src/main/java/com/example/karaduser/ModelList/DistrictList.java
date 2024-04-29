package com.example.karaduser.ModelList;

import org.json.JSONException;
import org.json.JSONObject;

public class DistrictList {
    private String district_id;
    private String district_name;


    public DistrictList(JSONObject jsonObject) {
        try {

            this.district_id = jsonObject.getString("fld_district_id");
            this.district_name = jsonObject.getString("fld_district_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String toString() {
        return this.district_name;            // What to display in the Spinner list.
    }
    public DistrictList(String district_name) {
        this.district_name = district_name;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }
}
