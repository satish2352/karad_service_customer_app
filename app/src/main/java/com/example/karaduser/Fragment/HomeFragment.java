package com.example.karaduser.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.karaduser.Activity.RequestAccepted;
import com.example.karaduser.Activity.RequestActivity;
import com.example.karaduser.Activity.RequestCancelActivity;
import com.example.karaduser.Activity.RequestComplete;
import com.example.karaduser.Activity.RequestRejectedActivity;
import com.example.karaduser.Adapter.FeaturesAdsAdapter;
import com.example.karaduser.Adapter.RequestAdapter;
import com.example.karaduser.ModelList.AppointmentList;
import com.example.karaduser.ModelList.CountryList;
import com.example.karaduser.ModelList.FeatureadsList;
import com.example.karaduser.NetworkController.APIInterface;
import com.example.karaduser.NetworkController.MyConfig;
import com.example.karaduser.R;
import com.example.karaduser.StartActivity.AddressFilterActivity;
import com.example.karaduser.StartActivity.SimpleArcDialog;
import com.example.karaduser.my_library.Constants;
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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private View rootView;
    ArrayList<String> homeSliderModelList = new ArrayList<String>();
    private List<FeatureadsList>list =new ArrayList<>();
    SimpleArcDialog mDialog;
    TextView tv_request, tv_accpts, tv_complete, tv_cancel;
    CardView cardcomplete,cardaccept,cardrequested,cardcancel;

    RecyclerView featureRec;
    FeaturesAdsAdapter featuresAdsAdapter;

    ImageView requestBook, AcceptBook, CompleteBook, cancelBook, rejBook;
    ImageView bkappointment, bkservice, managelocation;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mDialog = new SimpleArcDialog(getActivity());
        mDialog.setCancelable(false);
        featureRec= rootView.findViewById(R.id.rec_featureAds);
        tv_request = rootView.findViewById(R.id.tv_request);
        tv_accpts = rootView.findViewById(R.id.tv_accpts);
        tv_complete = rootView.findViewById(R.id.tv_complete);
        tv_cancel = rootView.findViewById(R.id.tv_cancel);

        cardcomplete=rootView.findViewById(R.id.cardComplete);
        cardaccept=rootView.findViewById(R.id.cardAccept);
        cardrequested=rootView.findViewById(R.id.cardRequest);
        cardcancel=rootView.findViewById(R.id.cardCancelled);

        CompleteBook=rootView.findViewById(R.id.compBook);
        AcceptBook=rootView.findViewById(R.id.ActepBook);
        requestBook=rootView.findViewById(R.id.reqBook);
        cancelBook=rootView.findViewById(R.id.cancelBook);
        rejBook=rootView.findViewById(R.id.Reject);

        managelocation=rootView.findViewById(R.id.managelocation);
        bkappointment=rootView.findViewById(R.id.appointmentimg);
        bkservice=rootView.findViewById(R.id.serviceimg);


        CompleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getContext(), RequestComplete.class);
                startActivity(i);
            }
        });

        AcceptBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getContext(), RequestAccepted.class);
                startActivity(i);
            }
        });

        requestBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getContext(), RequestActivity.class);
                startActivity(i);
            }
        });

        cancelBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getContext(), RequestCancelActivity.class);
                startActivity(i);
            }
        });

        rejBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getContext(), RequestRejectedActivity.class);
                startActivity(i);
            }
        });

        managelocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AddressFilterActivity.class);
                startActivity(i);
            }
        });

        bkappointment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment fragment = new Fragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame,new AppointmentFragment())
                        .addToBackStack(null).commit();

            }
        });

        bkservice.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment fragment = new Fragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame,new ServiceFragment())
                        .addToBackStack(null).commit();
            }
        });


        getcounts();
        loadBanner();
        getfeatureAds();

        Log.d("User_id", "user_id=: "+Shared_Preferences.getPrefs(getContext(), Constants.REG_ID));
        return rootView;
    }

    private void getfeatureAds()
    {
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.getfeatureads();
        result.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                try{
                    String output = response.body().string();
                    //Log.d("Feature", "Feature: " + output);
                    JSONObject jsonObject = new JSONObject(output);

                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++)
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                list.add(new FeatureadsList(obj));
                                Log.d("Feature", "Feature: " + obj);
                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        featureRec.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false));
                        featuresAdsAdapter= new FeaturesAdsAdapter(getActivity(),list);
                        featureRec.setAdapter(featuresAdsAdapter);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {

            }
        });
    }


    private void loadBanner() {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> call = apiInterface.getslider("3");
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
                                homeSliderModelList.add(object.getString("image"));
//                                homeSliderModelList.add("https://images.unsplash.com/photo-1497215842964-222b430dc094?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80");

                                SliderLayout imgSliderLayout = (SliderLayout) rootView.findViewById(R.id.imageSlider);
                                DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());

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


    private void getcounts() {
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getcounts(Shared_Preferences.getPrefs(getContext(), Constants.REG_ID));
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String output = response.body().string();

                    Log.d("Response", "Counts" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("0")) {
                        int a= Integer.parseInt(jsonObject.getString("Requeste_count"));
                        tv_request.setText(String.format("%02d", a));

                        int b= Integer.parseInt(jsonObject.getString("Accept_count"));
                        tv_accpts.setText(String.format("%02d", b));

                        int c= Integer.parseInt(jsonObject.getString("Complete_count"));
                        tv_complete.setText(String.format("%02d",c));
//                                tv_cancel.setText(""+jsonObject.getString("Requeste_count"));
                    } else {
                        tv_request.setText("00");
                        tv_accpts.setText("00");
                        tv_complete.setText("00");
                        tv_cancel.setText("00");
                    }
                    mDialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("server_error", "onFailure: " + t.getMessage());
                Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), "No Internet Connection,Please Start The Internet or Wifi", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                //loadBanner();
                                getcounts();
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
    public void onResume() {
        super.onResume();
        getcounts();
    }
}

