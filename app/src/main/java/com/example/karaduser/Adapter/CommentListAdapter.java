package com.example.karaduser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.karaduser.ModelList.CommentList;
import com.example.karaduser.R;

import java.util.ArrayList;

public class CommentListAdapter extends ArrayAdapter<CommentList>
{

    public CommentListAdapter(@NonNull Context context, ArrayList<CommentList> commentListArrayList)
    {
        super(context, R.layout.customcommentlist,commentListArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        CommentList list=getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.customcommentlist,parent,false);
        }

        TextView name = convertView.findViewById(R.id.namecomm);
        TextView comment =convertView.findViewById(R.id.comments);
        TextView date =convertView.findViewById(R.id.date);
        RatingBar star=convertView.findViewById(R.id.ratingbarr);

        name.setText(list.getUser_id());
        comment.setText(list.getReview_text());
        date.setText(list.getReview_date());
        star.setRating(Float.parseFloat(list.getReview_star()));

        return convertView;
    }
}
