package com.example.karaduser.ModelList;

import org.json.JSONException;
import org.json.JSONObject;

public class VillageList {
    private String district_id;
    private String taluka_id;
    private String village_id;
    private String village_name;



    public VillageList(JSONObject jsonObject) {
        try {

            this.district_id = jsonObject.getString("fld_district_id");
            this.taluka_id = jsonObject.getString("fld_taluka_id");
            this.village_id = jsonObject.getString("fld_city_id");
            this.village_name = jsonObject.getString("fld_city_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String toString() {
        return this.village_name;            // What to display in the Spinner list.
    }
    public VillageList(String village_name) {
        this.village_name = village_name;
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

    public String getVillage_id() {
        return village_id;
    }

    public void setVillage_id(String village_id) {
        this.village_id = village_id;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }
}

