package com.example.karaduser.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karaduser.Activity.SelectTimeSlotActivity;

import com.example.karaduser.Activity.ViewComments;
import com.example.karaduser.ModelList.AppointmentList;
import com.example.karaduser.R;
import com.example.karaduser.my_library.Constants;
import com.example.karaduser.my_library.Shared_Preferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<AppointmentList> homeDefaultLists;


    public AppointmentAdapter(Context context, ArrayList<AppointmentList> homeDefaultLists) {
        this.context = context;
        this.homeDefaultLists = homeDefaultLists;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_list_layout, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position)
    {
        Picasso.with(context).load(homeDefaultLists.get(position).getBusiness_image())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.no_image_available)
                .into(holder.imageView);
        holder.tv_business_name.setText(homeDefaultLists.get(position).getBusiness_info_name());
        holder.tv_ownre_name.setText(homeDefaultLists.get(position).getFld_business_name());
        holder.tv_mobile.setText("mobile");
        holder.tv_address.setText(homeDefaultLists.get(position).getAddress());

        holder.ratingbar.setRating(homeDefaultLists.get(position).getAvg_rating());

        holder.viewComment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(context, ViewComments.class);
                i.putExtra("business_info_id",homeDefaultLists.get(position).getBusiness_info_id());
                i.putExtra("fld_business_id",homeDefaultLists.get(position).getFld_business_id());
                context.startActivity(i);
            }
        });

        holder.rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                RateUsDialog rateUsDialog = new RateUsDialog(context);
                rateUsDialog.setCancelable(false);
                Shared_Preferences.setPrefs(context, Constants.CommentBusinessId,homeDefaultLists.get(position).getBusiness_info_id());
                Shared_Preferences.setPrefs(context, Constants.CommentFLDBusinessid,homeDefaultLists.get(position).getFld_business_id());
                Shared_Preferences.setPrefs(context, Constants.CommentVendorId,homeDefaultLists.get(position).getVendor_id());
                rateUsDialog.show();
            }
        });




//        int i=homeDefaultLists.get(position).getVariant_cnt();
//        if (i>0)
//        {
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    context.startActivity(new Intent(context,SelectTimeSlotActivity.class));
                    Shared_Preferences.setPrefs(context, "business_info_id", homeDefaultLists.get(position).getBusiness_info_id());
                    Shared_Preferences.setPrefs(context, "business_Profile", homeDefaultLists.get(position).getBusiness_image());
                    Shared_Preferences.setPrefs(context, "fld_business_id", homeDefaultLists.get(position).getFld_business_id());
                    Shared_Preferences.setPrefs(context, "vendor_id", homeDefaultLists.get(position).getVendor_id());
                    Shared_Preferences.setPrefs(context, "business_vendor", homeDefaultLists.get(position).getBusiness_info_name());
                    Shared_Preferences.setPrefs(context, "business_Adrs", homeDefaultLists.get(position).getAddress());
                    Shared_Preferences.setPrefs(context, "business_type", homeDefaultLists.get(position).getFld_business_name());
                    ((Activity) context).overridePendingTransition(R.anim.right_in, R.anim.left_out);

            Log.d("hemant",homeDefaultLists.get(position).getBusiness_info_id());
                }
            });
//        }
//        else
//            {
//            holder.layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Toast.makeText(context, "Business is Out Of service", Toast.LENGTH_SHORT).show();
////                    context.startActivity(new Intent(context, ServiceDetailsActivity.class));
////                    Shared_Preferences.setPrefs(context, "business_info_id", homeDefaultLists.get(position).getBusiness_info_id());
////                    Shared_Preferences.setPrefs(context, "fld_business_id", homeDefaultLists.get(position).getFld_business_id());
////                    Shared_Preferences.setPrefs(context, "business_Profile", homeDefaultLists.get(position).getBusiness_image());
////                    Shared_Preferences.setPrefs(context, "business_vendor", homeDefaultLists.get(position).getBusiness_info_name());
////                    Shared_Preferences.setPrefs(context, "business_Adrs", homeDefaultLists.get(position).getAddress());
////                    Shared_Preferences.setPrefs(context, "business_type", homeDefaultLists.get(position).getFld_business_name());
////                    Shared_Preferences.setPrefs(context, "vendor_id", homeDefaultLists.get(position).getVendor_id());
////                    ((Activity) context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
////                    Log.d("hemant1",homeDefaultLists.get(position).getBusiness_info_id());
//                }
//            });
//         }

    }


    @Override
    public int getItemCount() {
        return homeDefaultLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView tv_business_name, tv_ownre_name, tv_mobile, tv_address, rateus, viewComment;
        RatingBar ratingbar;
        LinearLayout layout;
        public MyViewHolder(View view) {
            super(view);
            ratingbar = view.findViewById(R.id.ratingbarr);
            imageView = view.findViewById(R.id.im_photo);
            rateus = view.findViewById(R.id.rateus);
            viewComment=view.findViewById(R.id.viewcomment);

            tv_business_name = view.findViewById(R.id.tv_business_name);
            tv_ownre_name = view.findViewById(R.id.tv_ownre_name);
            tv_mobile = view.findViewById(R.id.tv_mobile);
            tv_address = view.findViewById(R.id.tv_address);
            layout=view.findViewById(R.id.touch);
        }

    }
}
