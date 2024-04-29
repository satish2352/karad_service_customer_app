package com.example.karaduser.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.karaduser.ModelList.FeatureadsList;
import com.example.karaduser.R;
import com.example.karaduser.StartActivity.SimpleArcDialog;

public class FeaturedAds_Details extends AppCompatActivity
{
    ImageView img;
    TextView name,descp,type;
    FeatureadsList list = null;
    SimpleArcDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_ads_details);

        img=findViewById(R.id.feature_img);
        name=findViewById(R.id.feature_name);
        descp=findViewById(R.id.feature_desc);
        type=findViewById(R.id.feature_type);
        mDialog = new SimpleArcDialog(FeaturedAds_Details.this);
        mDialog.setCancelable(false);

        TextView desc=findViewById(R.id.feature_desc);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            desc.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }


        mDialog.show();
        final Object obj = getIntent().getSerializableExtra("featured");
        if (obj instanceof FeatureadsList)
        {

            list=(FeatureadsList) obj;

        }
        if(list != null)
        {

            Glide.with(getApplicationContext()).load(list.getFeature_image()).into(img);
            name.setText(list.getFld_business_category_name());
            descp.setText(list.getDiscription());
            type.setText(list.getTitle());
            mDialog.dismiss();

        }

    }
}