package com.example.karaduser.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;

import android.widget.ListView;

import com.example.karaduser.Adapter.NotificationAdapter;

import com.example.karaduser.ModelList.NotificationList;
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

public class NotificationActivity extends AppCompatActivity
{
    private SimpleArcDialog mDialog;
    ListView notilist;
    private ArrayList<NotificationList> notificationLists = new ArrayList<NotificationList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Notification");

        mDialog = new SimpleArcDialog(NotificationActivity.this);
        mDialog.setCancelable(false);
        notilist=findViewById(R.id.listNotification);
        NotificationListting();
    }


    private void NotificationListting() {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getnofication(Shared_Preferences.getPrefs(NotificationActivity.this, Constants.REG_ID));

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    String output = response.body().string();
                    Log.d("ResponseNoti", "getAllBusiness" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("user_type");
                            if(id.equals("User"))
                            {
                                notificationLists.add(new NotificationList(object));
                            }

                            Log.d("lists","list:- "+notificationLists);
                        }
                        NotificationAdapter notificationAdapter = new NotificationAdapter(NotificationActivity.this,notificationLists);
                        notilist.setAdapter(notificationAdapter);

                    } else {
                        mDialog.dismiss();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}