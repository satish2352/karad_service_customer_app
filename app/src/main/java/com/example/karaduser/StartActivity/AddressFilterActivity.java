package com.example.karaduser.StartActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karaduser.Activity.UpdateProfileActivity;
import com.example.karaduser.MainActivity;
import com.example.karaduser.ModelList.AreaList;
import com.example.karaduser.ModelList.CountryList;
import com.example.karaduser.ModelList.DistrictList;
import com.example.karaduser.ModelList.LandmarkList;
import com.example.karaduser.ModelList.StateList;
import com.example.karaduser.ModelList.TalukaList;
import com.example.karaduser.ModelList.VillageList;
import com.example.karaduser.NetworkController.APIInterface;
import com.example.karaduser.NetworkController.MyConfig;
import com.example.karaduser.R;
import com.example.karaduser.my_library.CheckNetwork;
import com.example.karaduser.my_library.Constants;
import com.example.karaduser.my_library.MyValidator;
import com.example.karaduser.my_library.Shared_Preferences;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressFilterActivity extends AppCompatActivity
{
    private static int SPLASH_TIME_OUT = 2000;
    private Spinner country_spinner, state_spinner, district_spinner, taluka_spinner, landmark_spinner, village_spinner, area_spinner;
    private String district = "1", taluka = "1", village = "1", country_id = "1", state_id = "", area_id = "1", landmark_id = "1";
    private List<CountryList> country = new ArrayList<>();
    private List<StateList> stateList = new ArrayList<>();
    private List<DistrictList> districtLists = new ArrayList<>();
    private List<TalukaList> talukaLists = new ArrayList<>();
    private List<VillageList> villageLists = new ArrayList<>();
    private List<AreaList> areaLists = new ArrayList<>();
    private List<LandmarkList> landmarkLists = new ArrayList<>();
    private SimpleArcDialog mDialog;
    private String spstate,spdist,sptaluka,spvillage,sparea;
    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_filter);

        district_spinner = findViewById(R.id.district_spinner);
        country_spinner = findViewById(R.id.country_spinner);
        state_spinner = findViewById(R.id.state_spinner);
        taluka_spinner = findViewById(R.id.taluka_spinner);
        village_spinner = findViewById(R.id.village_spinner);
        landmark_spinner = findViewById(R.id.landmark_spinner);
        area_spinner = findViewById(R.id.area_spinner);
        btn_next = findViewById(R.id.btn_next);
        mDialog = new SimpleArcDialog(AddressFilterActivity.this);
        mDialog.setCancelable(false);

        getstateData("1");


        btn_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                spstate=state_spinner.getSelectedItem().toString();
                spdist=district_spinner.getSelectedItem().toString();
                sptaluka=taluka_spinner.getSelectedItem().toString();
                spvillage=village_spinner.getSelectedItem().toString();
                sparea=area_spinner.getSelectedItem().toString();


                if(spstate.equals("--- Select State ---"))
                {
                    Toast.makeText(AddressFilterActivity.this, "Please select state", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(spdist.equals("--- Select District ---"))
                {
                    Toast.makeText(AddressFilterActivity.this, "Please select District", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(sptaluka.equals("--- Select Taluka ---"))
                {
                    Toast.makeText(AddressFilterActivity.this, "Please select Taluka", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(spvillage.equals("--- Select City ---"))
                {
                    Toast.makeText(AddressFilterActivity.this, "Please select Village", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(sparea.equals("--- Select Area ---"))
                {
                    Toast.makeText(AddressFilterActivity.this, "Please select Area", Toast.LENGTH_SHORT).show();
                }
                else if(!"--- Select State ---".equals(spstate) || !"--- Select District ---".equals(spdist) || !"--- Select Taluka ---".equals(sptaluka) || !"--- Select City ---".equals(spvillage) || !"--- Select Area ---".equals(sparea))
                {
                    startActivity(new Intent(AddressFilterActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                }

//                if (validateFields()) {
//                    startActivity(new Intent(AddressFilterActivity.this, MainActivity.class));
//                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                }

            }
        });
    }

    private void getstateData(String st_ID) {
        stateList.clear();
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.getstate("1");
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("org", "state: " + output);
                    JSONObject jsonObject = new JSONObject(output);

                    if (jsonObject.getString("result").equals("true")) {

//                        Toast.makeText(EditUserProfile.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray("tbl_state");
                        // Parsing json
                        stateList.add(new StateList("--- Select State ---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                stateList.add(new StateList(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<StateList> dataAdapter = new ArrayAdapter<StateList>(AddressFilterActivity.this, android.R.layout.simple_spinner_item, stateList);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        state_spinner.setAdapter(dataAdapter);
                        String speci = Shared_Preferences.getPrefs(AddressFilterActivity.this, Constants.PROFILE_STATE);
                        for (int i = 0; i < stateList.size(); i++) {
                            if (stateList.get(i).getState_Name().equals(speci)) {
                                state_spinner.setSelection(i);
                                break;
                            }
                        }
                        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //  On selecting a spinner item
                                String item = adapterView.getItemAtPosition(i).toString();
                                state_id = stateList.get(i).getState_Id();
                                getDistData(state_id);
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                    }
                    mDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mDialog.dismiss();
            }
        });
    }

    private void getDistData(String state_id) {
        districtLists.clear();
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.getDistrict("1", state_id);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("org", "district: " + output);
                    JSONObject jsonObject = new JSONObject(output);

                    if (jsonObject.getString("ResponseCode").equals("1")) {
//                        Toast.makeText(EditUserProfile.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        districtLists.add(new DistrictList("--- Select District ---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                districtLists.add(new DistrictList(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<DistrictList> dataAdapter = new ArrayAdapter<DistrictList>(AddressFilterActivity.this, android.R.layout.simple_spinner_item, districtLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        district_spinner.setAdapter(dataAdapter);
                        String speci = Shared_Preferences.getPrefs(AddressFilterActivity.this, Constants.PROFILE_DISTRICT);
                        for (int i = 0; i < districtLists.size(); i++) {
                            if (districtLists.get(i).getDistrict_name().equals(speci)) {
                                district_spinner.setSelection(i);
                                break;
                            }
                        }
                        district_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //  On selecting a spinner item
                                String item = adapterView.getItemAtPosition(i).toString();
                                district = districtLists.get(i).getDistrict_id();
                                gettalukaData();
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                    }

                    mDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mDialog.dismiss();
            }
        });
    }

    private void gettalukaData() {
        talukaLists.clear();
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.gettaluka(district);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                mDialog.dismiss();

                try {
                    output = response.body().string();
                    Log.d("org", "taluka: " + output);
                    JSONObject jsonObject = new JSONObject(output);


                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        talukaLists.add(new TalukaList("--- Select Taluka ---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                talukaLists.add(new TalukaList(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<TalukaList> dataAdapter = new ArrayAdapter<TalukaList>(AddressFilterActivity.this, android.R.layout.simple_spinner_item, talukaLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        taluka_spinner.setAdapter(dataAdapter);
                        String speci = Shared_Preferences.getPrefs(AddressFilterActivity.this, Constants.PROFILE_TALUKA);
                        for (int i = 0; i < talukaLists.size(); i++) {
                            if (talukaLists.get(i).getTaluka_name().equals(speci)) {
                                taluka_spinner.setSelection(i);
                                break;
                            }
                        }
//
                        taluka_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //  On selecting a spinner item
                                String item = adapterView.getItemAtPosition(i).toString();
                                taluka = talukaLists.get(i).getTaluka_id();

                                getvillage();
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                    }
                    mDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mDialog.dismiss();
            }
        });
    }

    private void getvillage() {//city list
        villageLists.clear();
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.getvillage(
                "1",
                state_id,
                district,
                taluka);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("org", "village: " + output);
                    JSONObject jsonObject = new JSONObject(output);

                    if (jsonObject.getString("ResponseCode").equals("1")) {
//                        Toast.makeText(EditUserProfile.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        villageLists.add(new VillageList("--- Select City ---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                villageLists.add(new VillageList(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<VillageList> dataAdapter = new ArrayAdapter<VillageList>(AddressFilterActivity.this, android.R.layout.simple_spinner_item, villageLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        village_spinner.setAdapter(dataAdapter);
                        String speci = Shared_Preferences.getPrefs(AddressFilterActivity.this, Constants.PROFILE_CITY);
                        for (int i = 0; i < villageLists.size(); i++) {
                            if (villageLists.get(i).getVillage_name().equals(speci)) {
                                village_spinner.setSelection(i);
                                break;
                            }
                        }
                        village_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //  On selecting a spinner item
                                String item = adapterView.getItemAtPosition(i).toString();
                                village = villageLists.get(i).getVillage_id();

                                getArea();
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                    }
                    mDialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mDialog.dismiss();
            }
        });
    }

    private void getArea() {
        areaLists.clear();
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.getArea(village);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("org", "village: " + output);
                    JSONObject jsonObject = new JSONObject(output);

                    if (jsonObject.getString("ResponseCode").equals("1")) {
//                        Toast.makeText(EditUserProfile.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        areaLists.add(new AreaList("--- Select Area ---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                areaLists.add(new AreaList(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<AreaList> dataAdapter = new ArrayAdapter<AreaList>(AddressFilterActivity.this, android.R.layout.simple_spinner_item, areaLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        area_spinner.setAdapter(dataAdapter);

                        //String spec = Shared_Preferences.getPrefs(AddressFilterActivity.this,Constants.PRO)




                        area_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //  On selecting a spinner item
                                String item = adapterView.getItemAtPosition(i).toString();
                                area_id = areaLists.get(i).getFld_area_id();
                                Shared_Preferences.setPrefs(AddressFilterActivity.this, Constants.AREA_ID, area_id);

//
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                    }
                    mDialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(AddressFilterActivity.this.getWindow().getDecorView().getRootView(), "No Internet Connection,Please Start The Internet or Wifi", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getArea();
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .setDuration(50000)
                        .show();
                mDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = getIntent();
        //String getName= intent.getStringExtra("back");
        int i = intent.getIntExtra("back",-1);
        Log.d("hemantback","No:-"+i);

        switch (i)
        {
            case 1:
            {
                Toast.makeText(this, "Please select location", Toast.LENGTH_SHORT).show();
                break;
            }
            case 2:
            {
                super.onBackPressed();
            }
            default:
                super.onBackPressed();
        }

    }
}