package com.example.karaduser.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karaduser.Activity.FeaturedAds_Details;
import com.example.karaduser.ModelList.FeatureadsList;
import com.example.karaduser.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeaturesAdsAdapter extends RecyclerView.Adapter<FeaturesAdsAdapter.ViewHolder>
{
    private Context context;
    private List<FeatureadsList> featureadsLists;

    public FeaturesAdsAdapter(Context context, List<FeatureadsList> featureadsLists)
    {
        this.context = context;
        this.featureadsLists = featureadsLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView =LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_ads,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        final FeatureadsList list = featureadsLists.get(position);
        holder.name.setText(list.getFld_business_category_name());
        holder.type.setText(list.getTitle());
        holder.description.setText(list.getDiscription());
        Picasso.with(context)
                .load(list.getFeature_image())
                .error(R.drawable.no_image_available)
                .into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //tranfering data to FeaturedDetailsActivity
                Intent intent = new Intent(context, FeaturedAds_Details.class);
                intent.putExtra("featured",featureadsLists.get(position));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return featureadsLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView name,type,description;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            img= itemView.findViewById(R.id.feature_img);
            name= itemView.findViewById(R.id.feature_name);
            type= itemView.findViewById(R.id.feature_type);
            description= itemView.findViewById(R.id.feature_desc);
        }
    }
}
































































































































































