package com.example.karaduser.ModelList;

import org.json.JSONException;
import org.json.JSONObject;

public class LandmarkList {

    private String fld_landmark_id;
    private String fld_landmark_name;


    public LandmarkList(JSONObject jsonObject) {
        try {

            this.fld_landmark_id = jsonObject.getString("fld_landmark_id");
            this.fld_landmark_name = jsonObject.getString("fld_landmark_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return this.fld_landmark_name;            // What to display in the Spinner list.
    }

    public LandmarkList(String fld_landmark_name) {
        this.fld_landmark_name = fld_landmark_name;
    }

    public String getFld_landmark_id() {
        return fld_landmark_id;
    }

    public void setFld_landmark_id(String fld_landmark_id) {
        this.fld_landmark_id = fld_landmark_id;
    }

    public String getFld_landmark_name() {
        return fld_landmark_name;
    }

    public void setFld_landmark_name(String fld_landmark_name) {
        this.fld_landmark_name = fld_landmark_name;
    }
}

