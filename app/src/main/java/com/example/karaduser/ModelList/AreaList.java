package com.example.karaduser.ModelList;

import org.json.JSONException;
import org.json.JSONObject;

public class AreaList {

    private String fld_area_id;
    private String fld_area_name;



    public AreaList(JSONObject jsonObject) {
        try {

            this.fld_area_id = jsonObject.getString("fld_area_id");
            this.fld_area_name = jsonObject.getString("fld_area_name");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return this.fld_area_name;            // What to display in the Spinner list.
    }

    public AreaList(String fld_area_name) {
        this.fld_area_name = fld_area_name;
    }

    public String getFld_area_id() {
        return fld_area_id;
    }

    public void setFld_area_id(String fld_area_id) {
        this.fld_area_id = fld_area_id;
    }

    public String getFld_area_name() {
        return fld_area_name;
    }

    public void setFld_area_name(String fld_area_name) {
        this.fld_area_name = fld_area_name;
    }
}

