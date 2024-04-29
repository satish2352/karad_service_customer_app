package com.example.karaduser.ModelList;

import org.json.JSONException;
import org.json.JSONObject;

public class TermsCList {
    private String term_condition_heading;
    private String term_condition_desc;



    public TermsCList(JSONObject jsonObject) {
        try {
            this.term_condition_heading = jsonObject.getString("term_condition_heading");
            this.term_condition_desc = jsonObject.getString("term_condition_desc");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getTerm_condition_heading() {
        return term_condition_heading;
    }

    public void setTerm_condition_heading(String term_condition_heading) {
        this.term_condition_heading = term_condition_heading;
    }

    public String getTerm_condition_desc() {
        return term_condition_desc;
    }

    public void setTerm_condition_desc(String term_condition_desc) {
        this.term_condition_desc = term_condition_desc;
    }
}
