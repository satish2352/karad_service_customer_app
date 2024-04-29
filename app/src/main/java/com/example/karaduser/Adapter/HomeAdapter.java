package com.example.karaduser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karaduser.ModelList.HomeDefaultList;
import com.example.karaduser.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>  {

    private Context context;
    private ArrayList<HomeDefaultList>homeDefaultLists;


    public HomeAdapter(Context context, ArrayList<HomeDefaultList> homeDefaultLists) {
        this.context = context;
        this.homeDefaultLists = homeDefaultLists;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rowlayout, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Picasso.with(context).load(homeDefaultLists.get(position).getSubcategoryBannerImage())
                .into(holder.imageView);


    }



    @Override
    public int getItemCount() {
        return homeDefaultLists.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.img_prev_photo);
        }

    }
}
