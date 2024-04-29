package com.example.karaduser.Activity;

import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karaduser.Adapter.TermsAdapter;
import com.example.karaduser.ModelList.TermsCList;
import com.example.karaduser.NetworkController.APIInterface;
import com.example.karaduser.NetworkController.MyConfig;
import com.example.karaduser.R;
import com.example.karaduser.StartActivity.SimpleArcDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsConditionActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsandcondition);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Terms And Conditions");

        textallignment();
    }

    private void textallignment()
    {
        TextView termfirst=findViewById(R.id.termfirst);
        TextView termtwo=findViewById(R.id.termtwo);
        TextView termthree=findViewById(R.id.termthree);
        TextView termfour=findViewById(R.id.termfour);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            termfirst.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            termtwo.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            termthree.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            termfour.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

    }
    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

}