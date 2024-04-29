package com.example.karaduser.Activity;

import static java.security.AccessController.getContext;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.example.karaduser.MainActivity;
import com.example.karaduser.ModelList.DistrictList;
import com.example.karaduser.ModelList.StateList;
import com.example.karaduser.ModelList.TalukaList;
import com.example.karaduser.ModelList.VillageList;
import com.example.karaduser.NetworkController.APIInterface;
import com.example.karaduser.NetworkController.MyConfig;
import com.example.karaduser.R;
import com.example.karaduser.StartActivity.SimpleArcDialog;
import com.example.karaduser.my_library.Camera;
import com.example.karaduser.my_library.Constants;
import com.example.karaduser.my_library.MyValidator;
import com.example.karaduser.my_library.Shared_Preferences;
import com.example.karaduser.my_library.UtilityRuntimePermission;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends UtilityRuntimePermission implements Camera.AsyncResponse {
    private Camera camera;
    private Button btn_regiter;
    private EditText edt_user_name, edt_email, edt_mobile_no, edt_adsress;
    SimpleArcDialog mDialog;
    private TextView text_upload_image_profile;
    private CircleImageView business_profile;
    ImageView iv_back;
    private Spinner state_spinner, district_spinner, taluka_spinner, village_spinner;
    private String district = "1", village = "1", taluka = "1", state_id = "";

    private List<StateList> stateList = new ArrayList<>();
    private List<DistrictList> districtLists = new ArrayList<>();
    private List<VillageList> villageLists = new ArrayList<>();
    private List<TalukaList> talukaLists = new ArrayList<>();


    private CharSequence[] options = {"camera","Gallery","Cancel"};
    String filepath ="";
    File imageFile;
    Uri uri;

    String Date = new SimpleDateFormat("yyyymmdd", Locale.getDefault()).format(new Date());
    String Time = new SimpleDateFormat("HHmmss",Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mDialog = new SimpleArcDialog(UpdateProfileActivity.this);
        mDialog.setCancelable(false);
        findBy();
        getstateData("1");


        Log.e("userprofile",""+Shared_Preferences.getPrefs(UpdateProfileActivity.this, Constants.REG_IMAGE));
    }

    private void findBy() {
        camera = new Camera(UpdateProfileActivity.this);
        edt_user_name = findViewById(R.id.edt_user_name);
        edt_email = findViewById(R.id.edt_email);
        edt_mobile_no = findViewById(R.id.edt_mobile_no);
        edt_adsress = findViewById(R.id.edt_adsress);
        district_spinner = findViewById(R.id.district_spinner);
        state_spinner = findViewById(R.id.state_spinner);
        village_spinner = findViewById(R.id.village_spinner);
        taluka_spinner = findViewById(R.id.taluka_spinner);

        edt_user_name.setText(Shared_Preferences.getPrefs(UpdateProfileActivity.this, Constants.REG_NAME));
        edt_email.setText(Shared_Preferences.getPrefs(UpdateProfileActivity.this, Constants.REG_EMAIL));
        edt_mobile_no.setText(Shared_Preferences.getPrefs(UpdateProfileActivity.this, Constants.REG_MOBILE));
        edt_adsress.setText("Address"/*Shared_Preferences.getPrefs(UpdateProfileActivity.this,Constants.REG_EMAIL)*/);
        business_profile = findViewById(R.id.business_profile);

        Picasso.with(UpdateProfileActivity.this).load(Shared_Preferences.getPrefs(UpdateProfileActivity.this, Constants.REG_IMAGE))
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.no_image_available)
                .into(business_profile);

        text_upload_image_profile = findViewById(R.id.text_upload_image_profile);

        btn_regiter = findViewById(R.id.btn_regiter);

        btn_regiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validatefield())
                {

                    uploadFile();

                } else
                {
                    Toast.makeText(UpdateProfileActivity.this, "Fill the Complete Data", Toast.LENGTH_SHORT).show();
                }
            }
        });


        text_upload_image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfileActivity.this);
                builder.setTitle("Select Image");
                builder.setItems(options, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int i)
                    {
                        if(options[i].equals("camera"))
                        {
                            Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takepic,0);
                        }
                        else if(options[i].equals("Gallery"))
                        {
                            Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(gallery,1);
                        }
                        else
                        {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
    }


    private void uploadFile( )
    {
        if(filepath.equals(""))
        {
            Toast.makeText(this, "Please Select Profile Image", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mDialog.show();
            RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(UpdateProfileActivity.this, Constants.REG_ID));
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), edt_user_name.getText().toString().trim());
            RequestBody mobile_n = RequestBody.create(MediaType.parse("text/plain"), edt_mobile_no.getText().toString().trim());
            RequestBody email = RequestBody.create(MediaType.parse("text/plain"), edt_email.getText().toString().trim());
            RequestBody state = RequestBody.create(MediaType.parse("text/plain"), state_id);
            RequestBody city = RequestBody.create(MediaType.parse("text/plain"), village);
            RequestBody dist = RequestBody.create(MediaType.parse("text/plain"), district);
            RequestBody talu = RequestBody.create(MediaType.parse("text/plain"), taluka);

            imageFile = new File(filepath);

            Log.e("profileimg", "" + imageFile);
            Log.e("profileimg", "" + filepath);

            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
            MultipartBody.Part fld_user_photo = MultipartBody.Part.createFormData("fld_user_photo", imageFile.getName(), reqBody);

            APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
            Call<ResponseBody> result = apiInterface.updateprofile(user_id, name, mobile_n, email,state, city, dist, talu, fld_user_photo);
            result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("Response", "update=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        JSONArray jb = jsonObject.getJSONArray("Data");
                        JSONObject user = jb.getJSONObject(0);
                        Shared_Preferences.setPrefs(UpdateProfileActivity.this, Constants.REG_ID, user.getString("user_id"));
                        Shared_Preferences.setPrefs(UpdateProfileActivity.this, Constants.REG_MOBILE, user.getString("user_mobile"));
                        Shared_Preferences.setPrefs(UpdateProfileActivity.this, Constants.REG_NAME, user.getString("user_name"));
                        Shared_Preferences.setPrefs(UpdateProfileActivity.this, Constants.REG_EMAIL, user.getString("user_email"));
//
                        Shared_Preferences.setPrefs(UpdateProfileActivity.this, Constants.REG_IMAGE, user.getString("fld_user_photo"));
                        startActivity(new Intent(UpdateProfileActivity.this, MainActivity.class));
                        finish();
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        Toast.makeText(UpdateProfileActivity.this, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();

                    } else
                    {
                        Toast.makeText(UpdateProfileActivity.this, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();

                    }
                    mDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                   progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                Log.d("Upload_error:", "");
                mDialog.dismiss();

            }
            });

        }
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
        if (!MyValidator.isValidSpinner(district_spinner)) {
            result = false;
        }
        if (!MyValidator.isValidSpinner(state_spinner)) {
            result = false;
        }
        if (!MyValidator.isValidSpinner(taluka_spinner)) {
            result = false;
        }
        if (!MyValidator.isValidSpinner(village_spinner)) {
            result = false;
        }
        return result;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_CANCELED)
        {
            switch (requestCode)
            {
                case 0:
                    if(resultCode == RESULT_OK && data !=null)
                    {
                        Bitmap image =(Bitmap) data.getExtras().get("data");
                        filepath = com.example.karaduser.FileUtils.getPath(getApplicationContext(),getImageUri(UpdateProfileActivity.this,image));
                        business_profile.setImageBitmap(image);

                    }
                    break;

                case 1:
                {
                    if(resultCode == RESULT_OK && data !=null)
                    {
                        uri = data.getData();
                        Bitmap bitmap = null;
                        try {
                            bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        filepath = com.example.karaduser.FileUtils.getPath(getApplicationContext(),getImageUri(UpdateProfileActivity.this,bitmap));
                        Picasso.with(UpdateProfileActivity.this).load(uri)
                                .error(R.drawable.no_image_available)
                                .into(business_profile);
                    }
                }
            }

        }
    }


    public Uri getImageUri(Context context, Bitmap bitmap)
    {
        String profile = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap,"ProfileImg"+Time+Date,"");

        return Uri.parse(profile);
    }

    public void requiredPermission()
    {
        ActivityCompat.requestPermissions(UpdateProfileActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

                        JSONArray jsonArray = jsonObject.getJSONArray("tbl_state");
                        // Parsing json
                        stateList.add(new StateList("--- Select State ---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                stateList.add(new StateList(obj));
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<StateList> dataAdapter = new ArrayAdapter<StateList>(UpdateProfileActivity.this, android.R.layout.simple_spinner_item, stateList);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        state_spinner.setAdapter(dataAdapter);
                        String speci = Shared_Preferences.getPrefs(UpdateProfileActivity.this, Constants.PROFILE_STATE);
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

                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        districtLists.add(new DistrictList("--- Select District ---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                districtLists.add(new DistrictList(obj));
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<DistrictList> dataAdapter = new ArrayAdapter<DistrictList>(UpdateProfileActivity.this, android.R.layout.simple_spinner_item, districtLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        district_spinner.setAdapter(dataAdapter);
                        String speci = Shared_Preferences.getPrefs(UpdateProfileActivity.this, Constants.PROFILE_DISTRICT);
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
                        ArrayAdapter<TalukaList> dataAdapter = new ArrayAdapter<TalukaList>(UpdateProfileActivity.this, android.R.layout.simple_spinner_item, talukaLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        taluka_spinner.setAdapter(dataAdapter);
                        String speci = Shared_Preferences.getPrefs(UpdateProfileActivity.this, Constants.PROFILE_TALUKA);
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
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        villageLists.add(new VillageList("--- Select City ---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                villageLists.add(new VillageList(obj));
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<VillageList> dataAdapter = new ArrayAdapter<VillageList>(UpdateProfileActivity.this, android.R.layout.simple_spinner_item, villageLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        village_spinner.setAdapter(dataAdapter);
                        String speci = Shared_Preferences.getPrefs(UpdateProfileActivity.this, Constants.PROFILE_CITY);
                        for (int i = 0; i < villageLists.size(); i++) {
                            if (villageLists.get(i).getVillage_name().equals(speci)) {
                                village_spinner.setSelection(i);
                                break;
                            }
                        }
                        village_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

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
    public void processFinish(String result, int img_no)
    {

    }

    @Override
    public void onPermissionsGranted(boolean result) {

    }

}
