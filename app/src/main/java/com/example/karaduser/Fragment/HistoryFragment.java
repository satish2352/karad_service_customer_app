package com.example.karaduser.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.karaduser.Adapter.HistoryAdapter;
import com.example.karaduser.ModelList.HistoryList;
import com.example.karaduser.NetworkController.APIInterface;
import com.example.karaduser.NetworkController.MyConfig;
import com.example.karaduser.R;
import com.example.karaduser.StartActivity.SimpleArcDialog;
import com.example.karaduser.my_library.Constants;
import com.example.karaduser.my_library.Shared_Preferences;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryFragment extends Fragment {
    private View rootView;
    ArrayList<HistoryList> historyLists = new ArrayList<HistoryList>();
    RecyclerView appointment_hist_recyclerView;
    HistoryAdapter mAdapter;
    private SimpleArcDialog mDialog;
    RelativeLayout noRecordLayout,no_internet;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_book, container, false);
        mDialog = new SimpleArcDialog(getActivity());
        mDialog.setCancelable(false);
        noRecordLayout=rootView.findViewById(R.id.noRecordLayout);
        no_internet=rootView.findViewById(R.id.no_internet);
        appointment_hist_recyclerView = (RecyclerView) rootView.findViewById(R.id.appointment_hist_recyclerView);
        BusinessHistoryListting();
        return rootView;
    }


    private void BusinessHistoryListting() {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getappintment_history_List(Shared_Preferences.getPrefs(getContext(), Constants.REG_ID));

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               mDialog.dismiss();
               no_internet.setVisibility(View.GONE);
                try {
                    String output = response.body().string();
                    Log.d("Response", "getAllBusiness" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            Log.e("History","hos:-"+object);
                            historyLists.add(new HistoryList(object));
                        }
                        noRecordLayout.setVisibility(View.GONE);

                        appointment_hist_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        mAdapter = new HistoryAdapter(getActivity(),historyLists);
                        appointment_hist_recyclerView.setAdapter(mAdapter);
                        Toast.makeText(getContext(), reason, Toast.LENGTH_SHORT).show();

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

                no_internet.setVisibility(View.VISIBLE);
                Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), "No Internet Connection,Please Start The Internet or Wifi", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                BusinessHistoryListting();
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
