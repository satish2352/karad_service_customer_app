package com.example.karaduser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.karaduser.ModelList.NotificationList;
import com.example.karaduser.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class NotificationAdapter extends ArrayAdapter<NotificationList>
{
    public NotificationAdapter(@NonNull Context context, ArrayList<NotificationList> notificationListArrayList)
    {
        super(context, R.layout.row_notification_list_layout,notificationListArrayList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        NotificationList list=getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_notification_list_layout,parent,false);
        }

            TextView user =convertView.findViewById(R.id.tv_notification_title);
            TextView noti =convertView.findViewById(R.id.tv_notification_message);
            TextView date =convertView.findViewById(R.id.notification_date_time);
            TextView type =convertView.findViewById(R.id.tv_notification_type);

            ImageView image =convertView.findViewById(R.id.profile);

            if(list.getFld_business_id().equals("1"))
            {
                type.setText("(Appointment)");
            }
            if(list.getFld_business_id().equals("2"))
            {
                type.setText("(Service)");
            }

            user.setText(list.getBusiness_info_name());
            noti.setText(list.getNote_text());
            date.setText(list.getCreated_at());

            Picasso.with(getContext()).load(list.getBusiness_image())
                .placeholder(R.drawable.applogop)
                .error(R.drawable.applogop)
                .into(image);

            return convertView;
    }
}