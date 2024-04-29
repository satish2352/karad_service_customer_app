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

public class RequestCancelActivity extends AppCompatActivity
{
    ArrayList<RequestList> list = new ArrayList<RequestList>();
    RecyclerView cancel_rec;
    RequestAdapter requestAdapter;
    private SimpleArcDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_cancel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("User Cancelled Request");

        mDialog = new SimpleArcDialog(RequestCancelActivity.this);
        mDialog.setCancelable(false);
        cancel_rec=findViewById(R.id.rec_requestcancel);

        cancelListing();
    }

    private void cancelListing()
    {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getappintment_history_List(Shared_Preferences.getPrefs(RequestCancelActivity.this, Constants.REG_ID));
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                try {
                    mDialog.dismiss();
                    String output = response.body().string();
                    Log.d("Response", "RequestList:- " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString( "fld_service_issuedorreturned");

                            if (id.equals("4"))
                            {
                                list.add(new RequestList(object));
                            }
                        }
                        cancel_rec.setLayoutManager(new LinearLayoutManager(RequestCancelActivity.this));
                        requestAdapter = new RequestAdapter(RequestCancelActivity.this,list);
                        cancel_rec.setAdapter(requestAdapter);
                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                } catch (JSONException e)
                {
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