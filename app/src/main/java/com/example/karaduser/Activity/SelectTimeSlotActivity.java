package com.example.karaduser.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karaduser.Adapter.AdapterMorning;
import com.example.karaduser.ModelList.AppointModel;
import com.example.karaduser.Adapter.DateAdapter;
import com.example.karaduser.MainActivity;
import com.example.karaduser.NetworkController.APIInterface;
import com.example.karaduser.NetworkController.MyConfig;
import com.example.karaduser.R;
import com.example.karaduser.StartActivity.SimpleArcDialog;
import com.example.karaduser.ModelList.TimeList;
import com.example.karaduser.my_library.Constants;
import com.example.karaduser.my_library.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectTimeSlotActivity extends AppCompatActivity implements DateAdapter.DateListener, AdapterMorning.MorningListener, DateAdapter.DateinfoListener {
    private RecyclerView rec_datelist, rv_morning_list;
    SimpleArcDialog mDialog;
    private DateAdapter datelistadapter;
    private AdapterMorning adapterMorning;
    private List<TimeList> timeLists = new ArrayList<>();
    private List<AppointModel> appointModel = new ArrayList<>();

    ArrayList<String> listdata = new ArrayList<String>();
    private String slotNo="";
    public static String selectedDate = "";
    public static String selectedTime = "";
    public static String curDate="";
    public static String curTime ="";
    boolean flag = false;
    Button btn_appoinext;
    String SelectedDate="";

    private Dialog dialog;
    private Button btn_yes;
    private Button btn_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Select Time");

        curDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        curTime = new SimpleDateFormat("HH:mm:ss",Locale.getDefault()).format(new Date());

        mDialog = new SimpleArcDialog(SelectTimeSlotActivity.this);
        mDialog.setCancelable(false);
        GetApppointment();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//2021-03-29
        String formattedDate = df.format(c.getTime());
        getMorningList(formattedDate);

        rec_datelist = (RecyclerView) findViewById(R.id.rec_datelist);
        rv_morning_list = (RecyclerView) findViewById(R.id.rv_morning_list);

        btn_appoinext = findViewById(R.id.btn_appoinext);
        btn_appoinext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedTime.isEmpty() && !selectedDate.isEmpty())
                {
                    showDialog();
                } else {
                    Toast.makeText(getApplicationContext(), "Select Date/Time", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void showDialog()
    {
        dialog = new Dialog(SelectTimeSlotActivity.this);
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
        text_msg.setText("Are you sure you want to Take Appointment");
        text.setText("Enquiry");
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                UserBookAppointment();

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


    private void UserBookAppointment() {
        Log.d("UserBookAppointment:", "fld_business_id" + Shared_Preferences.getPrefs(SelectTimeSlotActivity.this, "fld_business_id"));
        Log.d("UserBookAppointment:", "business_info_id " + Shared_Preferences.getPrefs(SelectTimeSlotActivity.this, "business_info_id"));
        Log.d("UserBookAppointment:", "vendor_id" + Shared_Preferences.getPrefs(SelectTimeSlotActivity.this, "vendor_id"));
        Log.d("UserBookAppointment:", "date " + SelectedDate);
        Log.d("UserBookAppointment:", "time " + SelectTimeSlotActivity.selectedTime);
        Log.d("UserBookAppointment: ", "slotNo" + slotNo);

        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.book_Appointment(
                Shared_Preferences.getPrefs(SelectTimeSlotActivity.this, Constants.REG_ID),
                Shared_Preferences.getPrefs(SelectTimeSlotActivity.this, "fld_business_id"),
                Shared_Preferences.getPrefs(SelectTimeSlotActivity.this, "business_info_id"),
                Shared_Preferences.getPrefs(SelectTimeSlotActivity.this, "vendor_id"),
                SelectedDate,
                SelectTimeSlotActivity.selectedTime,
                slotNo);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                mDialog.dismiss();
                try {
                    String output = response.body().string();
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        SelectTimeSlotActivity.selectedDate = "";
                        SelectTimeSlotActivity.selectedTime = "";

                        Toast.makeText(SelectTimeSlotActivity.this, "Request Send Successfully", Toast.LENGTH_SHORT).show();

                        Shared_Preferences.clearPref1(SelectTimeSlotActivity.this, "fld_business_id");
                        Shared_Preferences.clearPref1(SelectTimeSlotActivity.this, "business_info_id");
                        Shared_Preferences.clearPref1(SelectTimeSlotActivity.this, "vendor_id");
                        Shared_Preferences.clearPref1(SelectTimeSlotActivity.this, "fld_business_details_id");

                        startActivity(new Intent(SelectTimeSlotActivity.this, MainActivity.class));
                        finish();
                    }
                    mDialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                mDialog.dismiss();
            }
        });

    }

    private void GetApppointment() {

        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> call = apiInterface.getDays();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                JSONObject jsonObject = null;

                try {
                    String data = response.body().string();
                    jsonObject = new JSONObject(data);

                    if (jsonObject != null) {
                        if (jsonObject.getString("ResponseCode").equals("1"))
                        {
                            JSONArray jsonArray = jsonObject.optJSONArray("Data");
                            appointModel = new ArrayList<>();
                            if (jsonArray != null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
//
                                    listdata.add(String.valueOf(jsonArray.get(i)));
                                    Log.d("sdetetrt", "onResponse: " + listdata);
                                }
                                appointModel.add(new AppointModel(listdata));
                                datelistadapter = new DateAdapter(SelectTimeSlotActivity.this, listdata, SelectTimeSlotActivity.this);
                                rec_datelist.setAdapter(datelistadapter);
                                rec_datelist.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
                            } else {
                                Toast.makeText(SelectTimeSlotActivity.this, jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {


            }
        });
    }


    private void getMorningList(String date) {
        mDialog.show();
        Log.d("idd", "getMorningList: " + Shared_Preferences.getPrefs(SelectTimeSlotActivity.this, "business_info_id"));
        Log.d("idd", "getMorningList: " + date);
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> call = apiInterface.getMorningList(Shared_Preferences.getPrefs(SelectTimeSlotActivity.this, "business_info_id"),
                date);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    String data = response.body().string();
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.getString("result").equals("Ture."))
                    {
                        JSONArray jsonArray = jsonObject.optJSONArray("Data");
                        timeLists = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            timeLists.add(new TimeList(object));
                            slotNo = object.getString("no_of_people");
                        }
                        adapterMorning = new AdapterMorning(SelectTimeSlotActivity.this, timeLists, SelectTimeSlotActivity.this);
                        rv_morning_list.setAdapter(adapterMorning);
                        rv_morning_list.setLayoutManager(new LinearLayoutManager(SelectTimeSlotActivity.this, LinearLayoutManager.VERTICAL, false));
                    }
                    else if (jsonObject.getString("result").equals("False."))
                    {
                        timeLists.clear();
                        Toast.makeText(SelectTimeSlotActivity.this, "" + jsonObject.getString("SystemErrorMessage"), Toast.LENGTH_SHORT).show();
                        adapterMorning = new AdapterMorning(SelectTimeSlotActivity.this, timeLists, SelectTimeSlotActivity.this);
                        rv_morning_list.setAdapter(adapterMorning);
                        rv_morning_list.setLayoutManager(new LinearLayoutManager(SelectTimeSlotActivity.this, LinearLayoutManager.VERTICAL, false));
                    }
                    else
                    {

                    }
                    mDialog.dismiss();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                mDialog.dismiss();
            }
        });


    }


    @Override
    public void iconTextViewOnClick(boolean flag1)
    {
        if (flag1) {
            flag = flag1;
            getMorningList(selectedDate);
        } else {
            flag = false;
        }

    }

    @Override
    public void morningItemClick(boolean flag1) {
        Log.e("morningItemClick", "=");
        if (flag1) {

        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void dateinfoListener(Intent intent)
    {
        SelectedDate=intent.getStringExtra("Date1");
    }
}