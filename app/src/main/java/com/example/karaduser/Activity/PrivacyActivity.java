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

public class PrivacyActivity extends AppCompatActivity {
    private SimpleArcDialog mDialog;
    RelativeLayout noRecordLayout;
    TextView tv_privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Privacy Policy");

        mDialog = new SimpleArcDialog(PrivacyActivity.this);
        mDialog.setCancelable(false);
        noRecordLayout = findViewById(R.id.noRecordLayout);
        tv_privacy = findViewById(R.id.tv_privacy);
        textallignment();
    }
    private void textallignment()
    {
        TextView privacyfirst=findViewById(R.id.privacyfirst);
        TextView privacysecond=findViewById(R.id.privacysecond);
        TextView privacythird=findViewById(R.id.privacythird);
        TextView privacyforth=findViewById(R.id.privacyforth);
        TextView privacyfive=findViewById(R.id.privacyfive);
        TextView privacysix=findViewById(R.id.privacysix);
        TextView privacyseven=findViewById(R.id.privacyseven);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            privacyfirst.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            privacysecond.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            privacythird.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            privacyforth.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            privacyfive.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            privacysix.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            privacyseven.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }
    }

    private void PrivacyPolicyListting() {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getprivacy_policy();

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    String output = response.body().string();
                    Log.d("Response", "getAllBusiness" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        JSONObject object = jsonArray.getJSONObject(0);
                        tv_privacy.setText(object.getString("privacy_policy_desc"));

                        noRecordLayout.setVisibility(View.GONE);
                        Toast.makeText(PrivacyActivity.this, reason, Toast.LENGTH_SHORT).show();

                    } else {
                        mDialog.dismiss();
                        noRecordLayout.setVisibility(View.VISIBLE);

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