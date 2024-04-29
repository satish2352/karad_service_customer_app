package com.example.karaduser.Activity;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.karaduser.Adapter.ServiceSubAdapter;
import com.example.karaduser.Fragment.ServiceFragment;
import com.example.karaduser.MainActivity;
import com.example.karaduser.NetworkController.APIInterface;
import com.example.karaduser.NetworkController.MyConfig;
import com.example.karaduser.R;
import com.example.karaduser.StartActivity.SimpleArcDialog;
import com.example.karaduser.ModelList.SubServiceList;
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

public class SubBusinessActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, ServiceSubAdapter.OnCheckboxdata {
    private RecyclerView subbusiness_Recyclerview;
    private ServiceSubAdapter madapter;
    ArrayList<SubServiceList> subservice_list = new ArrayList<SubServiceList>();
    ArrayList<String> homeSliderModelList = new ArrayList<String>();



    //Hemant-Edit
    private TextView tv_businame, tv_address, tv_service;
    RadioButton rd_Request, rd_Delivery, rd_Pick, rd_Delivery_Pick;
    private Button btn_book_service;
    private String SuperRech = "";
    private Dialog dialog;
    private Button btn_yes;
    private Button btn_no;
    String Checkeddata;



    SimpleArcDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_business);
        subbusiness_Recyclerview = (RecyclerView) findViewById(R.id.sub_business_item_recyclerView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Business Name");

        mDialog = new SimpleArcDialog(SubBusinessActivity.this);
        mDialog.setCancelable(false);

        //tv_businame = findViewById(R.id.tv_businame);
        tv_address = findViewById(R.id.tv_address);
        //tv_service = findViewById(R.id.tv_service);
        rd_Request = findViewById(R.id.rd_Request);
        rd_Delivery = findViewById(R.id.rd_Delivery);
        rd_Pick = findViewById(R.id.rd_Pick);
        rd_Delivery_Pick = findViewById(R.id.rd_Delivery_Pick);
        btn_book_service = findViewById(R.id.btn_book_service);


        if (Shared_Preferences.getPrefs(SubBusinessActivity.this, "fld_business_id").equals("1")) {
            rd_Request.setVisibility(View.GONE);
            rd_Delivery.setVisibility(View.GONE);
            rd_Pick.setVisibility(View.GONE);
            rd_Delivery_Pick.setVisibility(View.GONE);
        } else {
            rd_Request.setVisibility(View.VISIBLE);
            rd_Delivery.setVisibility(View.VISIBLE);
            rd_Pick.setVisibility(View.VISIBLE);
            rd_Delivery_Pick.setVisibility(View.VISIBLE);
        }
            tv_address.setText(Shared_Preferences.getPrefs(SubBusinessActivity.this, "business_Adrs"));

        if (Shared_Preferences.getPrefs(SubBusinessActivity.this, "business_type").equals("Services") || Shared_Preferences.getPrefs(SubBusinessActivity.this, "business_type").equals("services"))
        {
            btn_book_service.setText("Send Enquiry");
            btn_book_service.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (rd_Request.isChecked() && Checkeddata!=null )
                    {
                        SuperRech = "0";
                        showDialog();
                    } else if (rd_Delivery.isChecked() && Checkeddata!=null)
                    {
                        SuperRech = "1";

                        showDialog();
                    } else if (rd_Pick.isChecked() && Checkeddata!=null)
                    {
                        SuperRech = "2";

                        showDialog();
                    } else if (rd_Delivery_Pick.isChecked() && Checkeddata!=null)
                    {
                        SuperRech = "3";

                        showDialog();
                    } else {
                        Toast.makeText(SubBusinessActivity.this, "Please Selectv Any One Option", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        else
        {

//            btn_book_service.setText("Book Appointment ");
//            btn_book_service.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(SubBusinessActivity.this, SelectTimeSlotActivity.class));
//                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                }
//            });
        }
        loadBanner();
        SubBusinessListing();


    }


    private void showDialog() {
        dialog = new Dialog(SubBusinessActivity.this);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.alert_dialog);
        btn_yes = dialog.findViewById(R.id.btn_logout_yes);
        btn_no = dialog.findViewById(R.id.btn_logout_no);
        btn_yes.setText("Yes");
        btn_no.setText("No");
        TextView text_msg = (TextView) dialog.findViewById(R.id.text_msg);
        ImageView iv_image = (ImageView) dialog.findViewById(R.id.iv_image);
        iv_image.setImageDrawable(getResources().getDrawable(R.drawable.services));
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text_msg.setText("Are you sure you want to Send Enquiry");
        text.setText("Enquiry..");
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                UserBookEnquriy();

            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void UserBookEnquriy()
    {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> call = apiInterface.Send_Enquriy(Shared_Preferences.getPrefs(SubBusinessActivity.this, Constants.REG_ID),
                Shared_Preferences.getPrefs(SubBusinessActivity.this, "fld_business_id"),
                Shared_Preferences.getPrefs(SubBusinessActivity.this, "business_info_id"),
                Shared_Preferences.getPrefs(SubBusinessActivity.this, "vendor_id"),
                Checkeddata, SuperRech);
        Log.e("vendorid","vendorid:- "+Shared_Preferences.getPrefs(SubBusinessActivity.this, "vendor_id"));
            call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response)
            {
                JSONObject jsonObject = null;
                mDialog.dismiss();
                try {
                    String data = response.body().string();
                    Log.d("BookAppointment", "onResponse: " + data);
                    jsonObject = new JSONObject(data);

                    if (jsonObject != null)
                    {
                        if (jsonObject.getString("ResponseCode").equals("1"))
                        {
                            dialog.dismiss();
                            Shared_Preferences.clearPref1(SubBusinessActivity.this, "fld_business_details_id");


                            startActivity(new Intent(SubBusinessActivity.this, MainActivity.class));
                            finish();

                            Toast.makeText(SubBusinessActivity.this, jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();

                        }
                        else
                            {
                            Toast.makeText(SubBusinessActivity.this, jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                        }
                        mDialog.dismiss();

                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mDialog.dismiss();

            }
        });
    }

    private void SubBusinessListing()
    {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getappintment_varient_List(Shared_Preferences.getPrefs(SubBusinessActivity.this,"business_info_id"));

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    String output = response.body().string();
                    Log.d("Response", "geSubBusiness" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");

                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            subservice_list.add(new SubServiceList(object));

                        }
                        Log.e("Hemantobject",jsonObject.toString());

                        subbusiness_Recyclerview.setLayoutManager(new LinearLayoutManager(SubBusinessActivity.this));
                        madapter = new ServiceSubAdapter(SubBusinessActivity.this, subservice_list);
                        subbusiness_Recyclerview.setAdapter(madapter);
                        //Toast.makeText(SubBusinessActivity.this, reason, Toast.LENGTH_SHORT).show();

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
                //Log.d("Retrofit Error:",t.getMessage());
                mDialog.dismiss();
            }
        });
    }


    private void loadBanner() {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> call = apiInterface.getappintmentsilder(Shared_Preferences.getPrefs(SubBusinessActivity.this,"business_info_id"));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                try {
                    String data = response.body().string();
                    Log.d("sd", "onResponse: " + data);
                    jsonObject = new JSONObject(data);

                    if (jsonObject != null) {
                        if (jsonObject.getString("ResponseCode").equals("1")) {
                            mDialog.dismiss();
                            JSONArray jsonArray = jsonObject.optJSONArray("Data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                homeSliderModelList.add(object.getString("business_photo"));
                                SliderLayout imgSliderLayout = (SliderLayout) findViewById(R.id.imageSlider);
                                DefaultSliderView defaultSliderView = new DefaultSliderView(SubBusinessActivity.this);

                                defaultSliderView
                                        //.description(name)
                                        .image(homeSliderModelList.get(i))
                                        .setScaleType(BaseSliderView.ScaleType.Fit);
                                defaultSliderView.bundle(new Bundle());
                                defaultSliderView.getBundle().putString("extra", homeSliderModelList.get(i));

                                imgSliderLayout.addSlider(defaultSliderView);
                                imgSliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
                                imgSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                                imgSliderLayout.setCustomAnimation(new DescriptionAnimation());
                                imgSliderLayout.setDuration(5000);
                            }
                        }
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onCheckboxdata(Intent intent)
    {
        Checkeddata =intent.getStringExtra("Checkbox");
        Log.e("Checkbox-Activity","Activity:- "+Checkeddata);
    }
}