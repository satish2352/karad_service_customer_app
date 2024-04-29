package com.example.karaduser.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.karaduser.NetworkController.APIInterface;
import com.example.karaduser.NetworkController.MyConfig;
import com.example.karaduser.R;
import com.example.karaduser.StartActivity.SimpleArcDialog;
import com.example.karaduser.my_library.Constants;
import com.example.karaduser.my_library.Shared_Preferences;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.EmojiTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateUsDialog  extends Dialog
{
    private float s=0;
    String comment="";

    public RateUsDialog(@NonNull Context context)
    {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_us_dialog_layout);

        final AppCompatButton ratenowBtn= findViewById(R.id.ratenowBtn);
        final AppCompatButton laterBtn= findViewById(R.id.laterBtn);
        final RatingBar ratingBar= findViewById(R.id.ratingBar);
        final ImageView ratingimg = findViewById(R.id.ratingimg);
        final EditText comm=findViewById(R.id.comment);


        ratenowBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                s = ratingBar.getRating();
                String userRate = String.valueOf(s);
                if(comm.getText().toString().length()>0)
                {
                    comment = comm.getText().toString();
                }
                else
                {
                    comment = " ";
                }
                String date = new SimpleDateFormat("yyyymmdd", Locale.getDefault()).format(new Date());
                String businessid=Shared_Preferences.getPrefs(getContext(), Constants.CommentBusinessId);
                String fldBusinessid=Shared_Preferences.getPrefs(getContext(), Constants.CommentFLDBusinessid);
                String vendorid=Shared_Preferences.getPrefs(getContext(), Constants.CommentVendorId);

                APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
                Call<ResponseBody> result = apiInterface.ratingadd(Shared_Preferences.getPrefs(getContext(), Constants.REG_ID),
                        fldBusinessid,businessid,vendorid, userRate,comment,date);
                result.enqueue(new Callback<ResponseBody>()
                {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                    {
                        dismiss();
                        comm.setText("");
                        Toast.makeText(getContext(), "Comment Added Successfully", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t)
                    {

                    }
                });



            }
        });

        laterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
            {
                if(rating <=1)
                {
                    ratingimg.setImageResource(R.drawable.emoone);
                }
                else if(rating <=2)
                {
                    ratingimg.setImageResource(R.drawable.emotwo);
                }
                else if(rating <=3)
                {
                    ratingimg.setImageResource(R.drawable.emothree);
                }
                else if(rating <=4)
                {
                    ratingimg.setImageResource(R.drawable.emofour);
                }
                else if(rating <=5)
                {
                    ratingimg.setImageResource(R.drawable.emofive);
                }
                //animate emoji image
                animateImag(ratingimg);
            }
        });
    }

    private void animateImag(ImageView ratingImage)
    {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1f,0,1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.startAnimation(scaleAnimation);

    }
}
