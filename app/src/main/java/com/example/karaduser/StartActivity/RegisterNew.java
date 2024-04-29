package com.example.karaduser.StartActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.karaduser.ModelList.DistrictList;
import com.example.karaduser.ModelList.StateList;
import com.example.karaduser.ModelList.TalukaList;
import com.example.karaduser.ModelList.VillageList;
import com.example.karaduser.NetworkController.APIInterface;
import com.example.karaduser.NetworkController.MyConfig;
import com.example.karaduser.R;
import com.example.karaduser.my_library.Camera;
import com.example.karaduser.my_library.Constants;
import com.example.karaduser.my_library.MyValidator;
import com.example.karaduser.my_library.Shared_Preferences;
import com.example.karaduser.my_library.UtilityRuntimePermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterNew extends UtilityRuntimePermission implements Camera.AsyncResponse {
    private Camera camera;
    private Button btn_regiter;
    private EditText edt_user_name, edt_email, edt_mobile_no, edt_adsress;
    SimpleArcDialog mDialog;
    private TextView text_upload_image_profile;
    private CircleImageView business_profile;
    private String profile_image_name4 = "";
    private String profile_image_path4 = "";
    private Spinner state_spinner, district_spinner, taluka_spinner, village_spinner;
    private String district = "1", village = "1", taluka = "1", state_id = "";

    private List<StateList> stateList = new ArrayList<>();
    private List<DistrictList> districtLists = new ArrayList<>();
    private List<VillageList> villageLists = new ArrayList<>();
    private List<TalukaList> talukaLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Regiter New");
        mDialog = new SimpleArcDialog(RegisterNew.this);
        mDialog.setCancelable(false);
        getstateData("1");
        findBy();
    }

    private void findBy() {
        camera = new Camera(RegisterNew.this);
        edt_user_name = findViewById(R.id.edt_user_name);
        edt_email = findViewById(R.id.edt_email);
        edt_mobile_no = findViewById(R.id.edt_mobile_no);
        edt_adsress = findViewById(R.id.edt_adsress);
        business_profile = findViewById(R.id.business_profile);
        text_upload_image_profile = findViewById(R.id.text_upload_image_profile);
        btn_regiter = findViewById(R.id.btn_regiter);
        district_spinner = findViewById(R.id.district_spinner);
        state_spinner = findViewById(R.id.state_spinner);
        village_spinner = findViewById(R.id.village_spinner);
        taluka_spinner = findViewById(R.id.taluka_spinner);

        btn_regiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validatefield()) {

                    uploadFile(profile_image_path4, profile_image_name4);

                } else {
                    Toast.makeText(RegisterNew.this, "Fill the Complete Data", Toast.LENGTH_SHORT).show();
                }
            }
        });


        text_upload_image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RegisterNew.super.requestAppPermissions(RegisterNew.this))

                    camera.selectImage(business_profile, 4);

            }
        });
    }

    private boolean validatefield() {
        boolean result = true;
        if (!MyValidator.isValidField(edt_user_name)) {
            result = false;
        }

        if (!MyValidator.isValidEmail(edt_email)) {
            result = false;
        }
        if (!MyValidator.isValidMobile(edt_mobile_no)) {
            result = false;
        }
        if (!MyValidator.isValidField(edt_adsress)) {
            result = false;
        } if (!MyValidator.isValidSpinner(district_spinner)) {
            result = false;
        } if (!MyValidator.isValidSpinner(state_spinner)) {
            result = false;
        } if (!MyValidator.isValidSpinner(taluka_spinner)) {
            result = false;
        }if (!MyValidator.isValidSpinner(village_spinner)) {
            result = false;
        }
        return result;
    }


    @Override
    public void processFinish(String result, int img_no) {
        if (img_no == 4) {
            String[] parts = result.split("/");
            String imagename = parts[parts.length - 1];
            Log.d("pro", "processFinish: " + imagename + "path :-" + result);
            profile_image_name4 = imagename;
            profile_image_path4 = result;

        }
    }

    @Override
    public void onPermissionsGranted(boolean result) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        camera.myActivityResult(requestCode, resultCode, data);
    }

    private void uploadFile(String fileUri4, String filename4) {
        mDialog.show();

        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);

        MultipartBody.Part body4 = null;
        if (!profile_image_path4.equalsIgnoreCase("")) {


            File file4 = new File(fileUri4);
            RequestBody requestFile4 = RequestBody.create(MediaType.parse("image/*"), file4);

            body4 = MultipartBody.Part.createFormData("fld_user_photo", filename4, requestFile4);

        }
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), edt_user_name.getText().toString().trim());
        RequestBody mobile_n = RequestBody.create(MediaType.parse("text/plain"), edt_mobile_no.getText().toString().trim());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), edt_email.getText().toString().trim());
        RequestBody state = RequestBody.create(MediaType.parse("text/plain"), state_id);
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), village);
        RequestBody dist = RequestBody.create(MediaType.parse("text/plain"), district);
        RequestBody talu = RequestBody.create(MediaType.parse("text/plain"), taluka);
        Call<ResponseBody> call;


        call = apiInterface.regiternew(name, mobile_n, email, state, city, dist, talu, body4);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("Response", "response=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        Intent intent = new Intent(RegisterNew.this, Login.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(RegisterNew.this, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterNew.this, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();

                    }
                    mDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                   progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Upload_error:", "");
                mDialog.dismiss();

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
                        ArrayAdapter<StateList> dataAdapter = new ArrayAdapter<StateList>(RegisterNew.this, android.R.layout.simple_spinner_item, stateList);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        state_spinner.setAdapter(dataAdapter);
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
                        ArrayAdapter<DistrictList> dataAdapter = new ArrayAdapter<DistrictList>(RegisterNew.this, android.R.layout.simple_spinner_item, districtLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        district_spinner.setAdapter(dataAdapter);
//                        String speci = Shared_Preferences.getPrefs(EditUserProfile.this, Constants.StateName);
//                        for (int i = 0; i < state.size(); i++) {
//                            if (state.get(i).getFld_state_name().equals(speci)) {
//                                spi_state.setSelection(i);
//                                break;
//                            }
//                        }
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
                        ArrayAdapter<TalukaList> dataAdapter = new ArrayAdapter<TalukaList>(RegisterNew.this, android.R.layout.simple_spinner_item, talukaLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        taluka_spinner.setAdapter(dataAdapter);

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
                        ArrayAdapter<VillageList> dataAdapter = new ArrayAdapter<VillageList>(RegisterNew.this, android.R.layout.simple_spinner_item, villageLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        village_spinner.setAdapter(dataAdapter);

                        village_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //  On selecting a spinner item
                                String item = adapterView.getItemAtPosition(i).toString();
                                village = villageLists.get(i).getVillage_id();
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


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
