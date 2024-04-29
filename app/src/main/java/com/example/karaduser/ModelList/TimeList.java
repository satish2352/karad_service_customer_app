package com.example.karaduser.ModelList;

import org.json.JSONException;
import org.json.JSONObject;

public class TimeList {

    private String from;
    private String to;
    private String count;



    public TimeList(JSONObject jsonObject) {
        try {
            this.from = jsonObject.getString("from");
            this.to = jsonObject.getString("to");
            this.count = jsonObject.getString("no_of_people");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
