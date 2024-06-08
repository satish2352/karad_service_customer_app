package com.example.karaduser.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karaduser.Adapter.AppointmentAdapter;
import com.example.karaduser.Adapter.ServiceAdapter;
import com.example.karaduser.ModelList.AppointmentList;
import com.example.karaduser.ModelList.CountryList;
import com.example.karaduser.ModelList.StateList;
import com.example.karaduser.NetworkController.APIInterface;
import com.example.karaduser.NetworkController.MyConfig;
import com.example.karaduser.R;
import com.example.karaduser.StartActivity.AddressFilterActivity;
import com.example.karaduser.StartActivity.SimpleArcDialog;
import com.example.karaduser.my_library.Constants;
import com.example.karaduser.my_library.MyValidator;
import com.example.karaduser.my_library.Shared_Preferences;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServiceFragment extends Fragment {
    private View rootView;
    ArrayList<AppointmentList> homeList = new ArrayList<AppointmentList>();
    SimpleArcDialog mDialog;
    RecyclerView recyclerView;
    ServiceAdapter mAdapter;
    RelativeLayout noRecordLayout, no_internet;
    private List<CountryList> country = new ArrayList<>();
    private Spinner categ_spinner;
    private Button filter;
    private String cate_id = "0";

    public ServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_appointment, container, false);

        mDialog = new SimpleArcDialog(getActivity());
        mDialog.setCancelable(false);
        noRecordLayout = rootView.findViewById(R.id.noRecordLayout);
        no_internet = rootView.findViewById(R.id.no_internet);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.appointment_recyclerView);
        categ_spinner = rootView.findViewById(R.id.cate_spinner);
        filter = rootView.findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (validateFields()) {
                    BusinessListting(cate_id);
                //}else{
                    //BusinessListting("0");
                //}
            }
        });
        getcategoryData();
        BusinessListting(cate_id);
        return rootView;
    }

    private boolean validateFields() {
        boolean result = true;
        if (!MyValidator.isValidSpinner(categ_spinner)) {
            result = false;
        }
        return result;
    }

    private void getcategoryData() {
        country.clear();
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.getservice_category();
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("org", "state: " + output);
                    JSONObject jsonObject = new JSONObject(output);

                    if (jsonObject.getString("ResponseCode").equals("1")) {


                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        country.add(new CountryList("-Select Category-"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                country.add(new CountryList(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<CountryList> dataAdapter = new ArrayAdapter<CountryList>(getContext(), android.R.layout.simple_spinner_item, country);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        categ_spinner.setAdapter(dataAdapter);
                        categ_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                String item = adapterView.getItemAtPosition(i).toString();
                                cate_id = country.get(i).getFld_business_category_id();
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                BusinessListting("0");
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

    private void BusinessListting(final String cate_id) {
        mDialog.show();
        homeList.clear();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getServiceList("1",
                Shared_Preferences.getPrefs(getContext(), Constants.STATE_ID),
                Shared_Preferences.getPrefs(getContext(), Constants.DISRICT_ID),
                Shared_Preferences.getPrefs(getContext(), Constants.TALUKA_ID),
                Shared_Preferences.getPrefs(getContext(), Constants.CITY_ID),
                Shared_Preferences.getPrefs(getContext(), Constants.AREA_ID),
                cate_id);



        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                no_internet.setVisibility(View.GONE);
                try {
                    String output = response.body().string();
                    Log.d("Response", "getAllBusinessSErvice" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Log.e("AppObject",""+object);
                            homeList.add(new AppointmentList(object));
                            Log.e("Service","service:- "+homeList.toString()+homeList.size());
                        }
                        noRecordLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        mAdapter = new ServiceAdapter(getActivity(), homeList);
                        recyclerView.setAdapter(mAdapter);


                    } else {
                        mDialog.dismiss();
                        noRecordLayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
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
                no_internet.setVisibility(View.VISIBLE);
                Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), "No Internet Connection,Please Start The Internet or Wifi", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                BusinessListting(cate_id);
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .setDuration(50000)
                        .show();
                mDialog.dismiss();
            }
        });
    }

}