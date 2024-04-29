package com.example.karaduser.Activity;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karaduser.NetworkController.APIInterface;
import com.example.karaduser.NetworkController.MyConfig;
import com.example.karaduser.R;
import com.example.karaduser.StartActivity.SimpleArcDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutActivity extends AppCompatActivity {
    SimpleArcDialog mDialog;
    TextView tv_about;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("About Us");
        tv_about=findViewById(R.id.tv_about);
        mDialog = new SimpleArcDialog(AboutActivity.this);
        mDialog.setCancelable(false);


        //about();
        textallignment();
    }

    private void textallignment()
    {
        TextView aboutusfirst=findViewById(R.id.aboutusfirst);
        TextView aboutusSecond=findViewById(R.id.aboutusSecond);
        TextView aboutusthird=findViewById(R.id.aboutusthird);
        TextView aboutusforth=findViewById(R.id.aboutusforth);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            aboutusfirst.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            aboutusSecond.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            aboutusthird.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            aboutusforth.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }
    }

    private void about() {
      mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> call = apiInterface.getaboutdata();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                mDialog.dismiss();
                try {
                    String data = response.body().string();
                    Log.d("about", "onResponse: " + data);
                    jsonObject = new JSONObject(data);

                    if (jsonObject != null) {
                        if (jsonObject.getString("ResponseCode").equals("1")) {
                            Toast.makeText(AboutActivity.this, jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                            JSONArray jsonArray = jsonObject.optJSONArray("Data");
                            JSONObject json=jsonArray.getJSONObject(0);
                            tv_about.setText(json.getString("about_desc"));

                        }else {
                            Toast.makeText(AboutActivity.this, jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();

                        }
                        mDialog.dismiss();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

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