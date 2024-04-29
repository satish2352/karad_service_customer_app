package com.example.karaduser.ModelList;

import org.json.JSONException;
import org.json.JSONObject;

public class StateList {
    private String State_Id;
    private String Country_Id;
    private String State_Name;

    public StateList(JSONObject jsonObject) {
        try {

            this.State_Id = jsonObject.getString("state_id");
//            this.Country_Id = jsonObject.getString("fld_country_id");
            this.State_Name = jsonObject.getString("state_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String toString() {
        return this.State_Name;            // What to display in the Spinner list.
    }
    public StateList(String State_Name) {
        this.State_Name = State_Name;
    }

    public String getState_Id() {
        return State_Id;
    }

    public void setState_Id(String state_Id) {
        State_Id = state_Id;
    }

    public String getCountry_Id() {
        return Country_Id;
    }

    public void setCountry_Id(String country_Id) {
        Country_Id = country_Id;
    }

    public String getState_Name() {
        return State_Name;
    }

    public void setState_Name(String state_Name) {
        State_Name = state_Name;
    }


}
