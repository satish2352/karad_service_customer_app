package com.example.karaduser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karaduser.ModelList.TermsCList;
import com.example.karaduser.R;

import java.util.ArrayList;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<TermsCList> homeDefaultLists;


    public TermsAdapter(Context context, ArrayList<TermsCList> homeDefaultLists) {
        this.context = context;
        this.homeDefaultLists = homeDefaultLists;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.terms_list_layout, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.tv_notification_title.setText(homeDefaultLists.get(position).getTerm_condition_heading());
        holder.tv_notification_message.setText(homeDefaultLists.get(position).getTerm_condition_desc());



    }


    @Override
    public int getItemCount() {
        return homeDefaultLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_notification_title,tv_notification_message;

        public MyViewHolder(View view) {
            super(view);
            tv_notification_title = view.findViewById(R.id.tv_notification_title);
            tv_notification_message = view.findViewById(R.id.tv_notification_message);

        }

    }
}
