package com.example.karaduser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karaduser.ModelList.HistoryList;
import com.example.karaduser.R;
import com.example.karaduser.my_library.DateTimeFormat;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<HistoryList> homeDefaultLists;


    public HistoryAdapter(Context context, ArrayList<HistoryList> homeDefaultLists) {
        this.context = context;
        this.homeDefaultLists = homeDefaultLists;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list_layout, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.tv_business_name.setText(homeDefaultLists.get(position).getBusiness_info_name());
        holder.tv_ownre_name.setText(homeDefaultLists.get(position).getFld_business_id());
        holder.tv_status.setText(homeDefaultLists.get(position).getStatus());
        holder.tv_date.setText(DateTimeFormat.getDate2(homeDefaultLists.get(position).getFld_service_requested_date()));
        if (!homeDefaultLists.get(position).getFld_actual_booking_slot().equals("null")) {
            holder.tv_address.setText(homeDefaultLists.get(position).getFld_actual_booking_slot());
            holder.tv_address.setVisibility(View.VISIBLE);

        } else {
            holder.tv_address.setVisibility(View.GONE);

        }


    }


    @Override
    public int getItemCount() {
        return homeDefaultLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_business_name, tv_date, tv_status, tv_ownre_name, tv_mobile, tv_address;

        public MyViewHolder(View view) {
            super(view);
            tv_date = view.findViewById(R.id.tv_date);
            tv_business_name = view.findViewById(R.id.tv_business_name);
            tv_ownre_name = view.findViewById(R.id.tv_ownre_name);
            tv_mobile = view.findViewById(R.id.tv_mobile);
            tv_address = view.findViewById(R.id.tv_address);
            tv_status = view.findViewById(R.id.tv_status);
        }

    }
}
