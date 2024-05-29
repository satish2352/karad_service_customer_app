package com.example.karaduser;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.karaduser.Activity.NotificationActivity;
import com.example.karaduser.Activity.UpdateProfileActivity;
import com.example.karaduser.Fragment.AppointmentFragment;
import com.example.karaduser.Fragment.HomeFragment;
import com.example.karaduser.Fragment.Profile;
import com.example.karaduser.Fragment.ServiceFragment;
import com.example.karaduser.Fragment.help;
import com.example.karaduser.NetworkController.APIInterface;
import com.example.karaduser.NetworkController.MyConfig;
import com.example.karaduser.my_library.Constants;
import com.example.karaduser.my_library.Shared_Preferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public BottomNavigationView navigation;
    private ImageView iv_notification;
    private ImageView helpbar;
    private TextView notification_text_count;

    CircleImageView userprofileImg;
    TextView username,userlocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username=findViewById(R.id.username);
        userlocation=findViewById(R.id.location);
        userprofileImg=findViewById(R.id.userprofile);
        userprofileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
            }
        });

        noti_count();

        userdetail();

        navigation = findViewById(R.id.bottom_navigation);

        notification_text_count = findViewById(R.id.notification_text_count);
        iv_notification = findViewById(R.id.iv_notification);

        helpbar=findViewById(R.id.iv_help);

        iv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                getNotificationSeen();
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);

            }
        });

        helpbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(v.getId()==R.id.iv_help)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new help()).commit();
                }
            }
        });



        HomeFragment HomeFragment = new HomeFragment();
        if (HomeFragment == null)
        {
            HomeFragment = new HomeFragment();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        HomeFragment home = new HomeFragment();


        fragmentTransaction.add(R.id.main_frame, home, "F");
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
        HomeFragment = new HomeFragment();


        fragmentTransaction.add(R.id.main_frame, HomeFragment, "F");
        fragmentTransaction.addToBackStack(null);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                int flag = 0;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new HomeFragment();

                        flag = 0;
                        break;

                    case R.id.action_appointment:
                        fragment = new AppointmentFragment();

                        flag = 1;
                        break;

                    case R.id.action_service:
                        fragment = new ServiceFragment();

                        flag = 1;
                        break;

//                    case R.id.action_offer:
//
//                        flag = 1;
//                        break;
                }
                return loadFragment(fragment, flag);
            }
        });


    }

    private void getNotificationSeen()
    {
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getnoti_countszero(Shared_Preferences.getPrefs(MainActivity.this, Constants.REG_ID),"1");
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                try {
                    String output = response.body().string();
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {

            }
        });
    }

    private void userdetail()
    {
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> call = apiInterface.getuser(Shared_Preferences.getPrefs(MainActivity.this, Constants.REG_ID));
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                try {
                    String output = response.body().string();
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String s= object.getString("user_name");
                            username.setText(object.getString("user_name"));
                            userlocation.setText(object.getString("fld_taluka_name"));
                            Picasso.with(MainActivity.this)
                                    .load(object.getString("fld_user_photo"))
                                    .error(R.drawable.no_image_available)
                                    .into(userprofileImg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {

            }
        });

    }

    private boolean loadFragment(Fragment fragment, int flag) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        navigation.setSelectedItemId(R.id.action_home);
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


    private void noti_count() {
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getnoti_counts(Shared_Preferences.getPrefs(MainActivity.this, Constants.REG_ID));

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String output = response.body().string();
                    Log.d("Response", "gBusiness" + output);
                    JSONObject jsonObject = new JSONObject(output);
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        notification_text_count.setText(jsonObject.getString("Data"));
                    }
                    else
                    {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
