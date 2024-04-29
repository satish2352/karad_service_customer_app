package com.example.karaduser.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karaduser.Activity.UpdateProfileActivity;
import com.example.karaduser.R;
import com.example.karaduser.my_library.Constants;
import com.example.karaduser.my_library.Shared_Preferences;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class Profile extends AppCompatActivity {
    private View rootView;
    private AppBarLayout app_bar;
    private ImageView iv_back;
    private CircleImageView iv_profile;
    private ImageView iv_update_profile;
    private TextView tv_user_name, tv_mobile, tv_email, tv_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        iv_update_profile = findViewById(R.id.iv_update_profile);
        iv_profile = findViewById(R.id.iv_profile);



        String path=Shared_Preferences.getPrefs(Profile.this, Constants.REG_IMAGE);

        Picasso.with(Profile.this).load(path)
                .placeholder(R.drawable.no_image_available)
                .into(iv_profile);

        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_name.setText(Shared_Preferences.getPrefs(Profile.this, Constants.REG_NAME));
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_mobile.setText(Shared_Preferences.getPrefs(Profile.this, Constants.REG_MOBILE));
        tv_email = findViewById(R.id.tv_email);
        tv_email.setText(Shared_Preferences.getPrefs(Profile.this, Constants.REG_EMAIL));
        tv_address = findViewById(R.id.tv_address);


        iv_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, UpdateProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

