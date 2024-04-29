package com.example.karaduser.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karaduser.Activity.SelectTimeSlotActivity;
import com.example.karaduser.R;
import com.example.karaduser.ModelList.TimeList;
import com.example.karaduser.my_library.Constants;
import com.example.karaduser.my_library.DateTimeFormat;
import com.example.karaduser.my_library.Shared_Preferences;

import java.util.List;


/**
 * Created by ${Hemant Pillai} on 22/02/2022.
 */

public class AdapterMorning extends RecyclerView.Adapter<AdapterMorning.MyHolder> {

    private Context context;
    private List<TimeList> timeModelList;
    public String pos;
    private int row_index;
    public boolean morningFlag = false;


    public MorningListener morningListener;

    public AdapterMorning(Context context, List<TimeList> timeModelList, MorningListener morningListener) {
        this.context = context;
        this.timeModelList = timeModelList;
        this.morningListener = morningListener;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_morning_time, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder,  int position) {
        holder.tv_time_m1.setText(DateTimeFormat.getTime(timeModelList.get(position).getFrom())+"-"+DateTimeFormat.getTime(timeModelList.get(position).getTo()));
        int a= Integer.parseInt(timeModelList.get(position).getCount());
        holder.tv_count.setText(String.format("%02d", a));

        if (timeModelList.get(position).getTo().equals(SelectTimeSlotActivity.selectedTime))
        {
            Log.e("CheckTime", "=" + SelectTimeSlotActivity.selectedTime + " " + timeModelList.get(position).getTo());
            holder.tv_time_m1.setTextColor(context.getResources().getColor(R.color.pin_normal));
            holder.tv_time.setTextColor(context.getResources().getColor(R.color.pin_normal));
            holder.tv_count.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_count.setBackground(context.getResources().getDrawable(R.drawable.circle_apptheme));
            holder.rel_time.setBackground(context.getResources().getDrawable(R.drawable.squer));

        } else {
            holder.tv_time_m1.setTextColor(context.getResources().getColor(R.color.pin_normal));
            holder.tv_time.setTextColor(context.getResources().getColor(R.color.pin_normal));
            holder.tv_count.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_count.setBackground(context.getResources().getDrawable(R.drawable.circle_apptheme));
            holder.rel_time.setBackground(context.getResources().getDrawable(R.drawable.squer));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morningFlag = true;
                morningListener.morningItemClick(true);
                row_index = position;
                SelectTimeSlotActivity.selectedTime = timeModelList.get(position).getTo();
                notifyDataSetChanged();
            }
        });
        if (morningFlag && row_index == position) {
            pos = timeModelList.get(position).getTo();
            Shared_Preferences.setPrefs(context, Constants.SelectedTime, pos);
            holder.tv_time_m1.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_time.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_count.setTextColor(context.getResources().getColor(R.color.apptheme));
            holder.tv_count.setBackground(context.getResources().getDrawable(R.drawable.circle_white));
            holder.rel_time.setBackground(context.getResources().getDrawable(R.drawable.squer_apptheme));
        }

        if(a<=0)
        {
            holder.itemView.setClickable(false);
            holder.itemView.setFocusable(false);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "No Slot Available Here", Toast.LENGTH_SHORT).show();
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return timeModelList.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_time_m1, tv_time, tv_count;
        RelativeLayout rel_time;

        public MyHolder(View view) {
            super(view);
            tv_count = (TextView) view.findViewById(R.id.tv_count);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_time_m1 = (TextView) view.findViewById(R.id.tv_time_m1);
            rel_time = (RelativeLayout) view.findViewById(R.id.rel_time);
        }
    }


    public interface MorningListener {
        void morningItemClick(boolean flag);
    }
}