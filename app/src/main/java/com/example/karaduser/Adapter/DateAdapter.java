package com.example.karaduser.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.karaduser.Activity.SelectTimeSlotActivity;
import com.example.karaduser.R;
import com.example.karaduser.my_library.Constants;
import com.example.karaduser.my_library.DateTimeFormat;
import com.example.karaduser.my_library.Shared_Preferences;

import java.util.ArrayList;



public class DateAdapter extends RecyclerView.Adapter<DateAdapter.MyHolder> {

    private Context context;
    private ArrayList<String> appointModel;
    public int pos;
    private int row_index;
    private boolean flag = false;
    public DateListener onClickListener;
    private DateinfoListener dateinfoListener;


    public DateAdapter(SelectTimeSlotActivity context, ArrayList<String> appointModel, SelectTimeSlotActivity listener) {
        this.context = context;
        this.appointModel = appointModel;
        this.onClickListener = listener;

        try
        {
            this.dateinfoListener=((DateinfoListener) context);
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(e.getMessage());
        }
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_date_item, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.tv_date.setText(DateTimeFormat.getDate(appointModel.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                SelectTimeSlotActivity.selectedDate = DateTimeFormat.getDateReverse(appointModel.get(position));
                String date=DateTimeFormat.getDateReverse(appointModel.get(position));
                row_index = position;
                notifyDataSetChanged();
                Intent i = new Intent();
                i.putExtra("Date1",date);
                dateinfoListener.dateinfoListener(i);
            }
        });
        if (appointModel.get(position).equals(SelectTimeSlotActivity.selectedDate)) {

            holder.tv_date.setTextColor(context.getResources().getColor(R.color.pin_normal));
            holder.tv_date.setBackground(context.getResources().getDrawable(R.drawable.squer));
        } else {
            holder.tv_date.setTextColor(context.getResources().getColor(R.color.pin_normal));
            holder.tv_date.setBackground(context.getResources().getDrawable(R.drawable.squer));
        }
        if (flag && row_index == position) {
            pos = position;
            onClickListener.iconTextViewOnClick(flag);
            Shared_Preferences.setPrefs(context, Constants.SelectedDate, appointModel.get(pos));
            holder.tv_date.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_date.setBackground(context.getResources().getDrawable(R.drawable.squer_apptheme));
        }

    }


    @Override
    public int getItemCount() {
        return appointModel.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_date;

        public MyHolder(View view) {
            super(view);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_date.setBackgroundColor(context.getResources().getColor(R.color.apptheme));


        }
    }

    public void OnItemClicked(int position) {
        pos = position;
    }

    public interface DateListener {

        void iconTextViewOnClick(boolean flag);

    }
    public interface DateinfoListener
    {
        public void dateinfoListener(Intent intent);
    }

}