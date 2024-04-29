package com.example.karaduser.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.karaduser.Adapter.CommentListAdapter;

import com.example.karaduser.ModelList.CommentList;
import com.example.karaduser.NetworkController.APIInterface;
import com.example.karaduser.NetworkController.MyConfig;
import com.example.karaduser.R;
import com.example.karaduser.StartActivity.SimpleArcDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewComments extends AppCompatActivity
{
    ListView listview;
    SimpleArcDialog mDialog;
    private ArrayList<CommentList> commentLists = new ArrayList<CommentList>();
    RelativeLayout nocomment,comment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);

        mDialog = new SimpleArcDialog(ViewComments.this);
        mDialog.setCancelable(false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Comments");

        listview=findViewById(R.id.listcomment);
        nocomment=(RelativeLayout) findViewById(R.id.imageLayout);
        comment=(RelativeLayout) findViewById(R.id.listLayout);



        String busID= getIntent().getStringExtra("business_info_id");
        String type= getIntent().getStringExtra("fld_business_id");

        getcomment();


    }

    private void getcomment()
    {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getcomment(getIntent().getStringExtra("business_info_id"),getIntent().getStringExtra("fld_business_id"));
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                mDialog.dismiss();
                try {
                    String output = response.body().string();
                    Log.d("Response", "GetComments:-" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    //String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            try {
                                JSONObject object = jsonArray.getJSONObject(i);
                                commentLists.add(new CommentList(object));
                                Log.d("lists","list:- "+commentLists);
                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }

                        }
                        CommentListAdapter commentListAdapter = new CommentListAdapter(ViewComments.this,commentLists);
                        listview.setAdapter(commentListAdapter);

                    }
                    else
                    {
                        nocomment.setVisibility(View.VISIBLE);
                        comment.setVisibility(View.GONE);
                    }
                    mDialog.dismiss();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                nocomment.setVisibility(View.VISIBLE);
                comment.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}