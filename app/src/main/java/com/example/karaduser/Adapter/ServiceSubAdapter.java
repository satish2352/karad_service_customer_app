package com.example.karaduser.Adapter;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.karaduser.R;
import com.example.karaduser.ModelList.SubServiceList;

import java.util.ArrayList;

public class ServiceSubAdapter extends RecyclerView.Adapter<ServiceSubAdapter.MyViewHolder>
{

    private ArrayList<SubServiceList> homeDefaultLists;
    private Context context;

    ArrayList<String> ids = new ArrayList<>();

    private OnCheckboxdata onCheckboxdata;

    public ServiceSubAdapter(Context context, ArrayList<SubServiceList> homeDefaultLists)
    {
        this.context = context;
        this.homeDefaultLists = homeDefaultLists;

        try{
            this.onCheckboxdata =((OnCheckboxdata)context);
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(e.getMessage());
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selectbusinessitem, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.tv_business_name.setText(homeDefaultLists.get(position).getFld_business_details_name());
        holder.tv_description.setText(homeDefaultLists.get(position).getFld_business_details_size());
        holder.tv_rate.setText("Rs "+homeDefaultLists.get(position).getFld_business_details_rate()+"/-");
        holder.tv_business_name.setTag(position);

        holder.tv_business_name.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Integer pos =(Integer) holder.tv_business_name.getTag();

                if(homeDefaultLists.get(pos).getSelected())
                {
                    homeDefaultLists.get(pos).setSelected(false);

                    ids.remove(homeDefaultLists.get(position).getFld_business_details_id());


                }
                else
                {
                    homeDefaultLists.get(pos).setSelected(true);

                    ids.add(homeDefaultLists.get(position).getFld_business_details_id());

                }

                String abc= ids.toString();
                abc=abc.replace("[", "").replace("]","").replace(" ","");
                for (int i =0; i<ids.size(); i++)
                {
                    Log.e("Checkbox","IDS "+abc);
                    Log.e("CheckboxSize","IDS "+ids.size());
                }

                Intent intent = new Intent();
                intent.putExtra("Checkbox",abc); //transfer to SubBusinessActivity
                onCheckboxdata.onCheckboxdata(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return homeDefaultLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView  tv_description, tv_rate;
        CheckBox tv_business_name;

        public MyViewHolder(View view) {
            super(view);
            tv_business_name = (CheckBox) view.findViewById(R.id.tv_business_name);
            tv_description = view.findViewById(R.id.tv_description);
            tv_rate = view.findViewById(R.id.tv_rate);
        }

    }

    public interface OnCheckboxdata
    {
        public void onCheckboxdata(Intent intent);
    }
}

