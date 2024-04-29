package com.example.karaduser.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.karaduser.Adapter.RequestAdapter;
import com.example.karaduser.ModelList.RequestList;
import com.example.karaduser.NetworkController.APIInterface;
import com.example.karaduser.NetworkController.MyConfig;
import com.example.karaduser.R;
import com.example.karaduser.StartActivity.SimpleArcDialog;
import com.example.karaduser.my_library.Constants;
import com.example.karaduser.my_library.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestAccepted extends AppCompatActivity
{
    ArrayList<RequestList> list = new ArrayList<RequestList>();
    RecyclerView act_rec;
    RequestAdapter requestAdapter;
    private SimpleArcDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_accepted);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Vendor Accepted Request");

        mDialog = new SimpleArcDialog(RequestAccepted.this);
        mDialog.setCancelable(false);
        act_rec=findViewById(R.id.rec_requestaccept);

        RequestAccept();
    }

    private void RequestAccept()
    {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getappintment_history_List(Shared_Preferences.getPrefs(RequestAccepted.this, Constants.REG_ID));
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                try {
                    mDialog.dismiss();
                    String output = response.body().string();
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String id = object.getString( "fld_service_issuedorreturned");

                            if (id.equals("0"))
                            {
                                list.add(new RequestList(object));
                            }
                        }
                        act_rec.setLayoutManager(new LinearLayoutManager(RequestAccepted.this));
                        requestAdapter = new RequestAdapter(RequestAccepted.this,list);
                        act_rec.setAdapter(requestAdapter);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}