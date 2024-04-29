package com.example.karaduser.ModelList;

import org.json.JSONArray;

import java.util.ArrayList;

public class AppointModel {
    private JSONArray jsonArray;




    public AppointModel(ArrayList<String> listdata) {
        this.jsonArray = jsonArray;
    }




    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }
}
