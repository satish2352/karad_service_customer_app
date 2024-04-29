package com.example.karaduser.StartActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karaduser.AutoDetectSMS.OtpReceivedInterface;
import com.example.karaduser.AutoDetectSMS.SmsBroadcastReceiver;
import com.example.karaduser.MainActivity;
import com.example.karaduser.NetworkController.APIInterface;
import com.example.karaduser.NetworkController.MyConfig;
import com.example.karaduser.R;
import com.example.karaduser.my_library.CheckNetwork;
import com.example.karaduser.my_library.Constants;
import com.example.karaduser.my_library.MyValidator;
import com.example.karaduser.my_library.Shared_Preferences;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OTPActivity extends AppCompatActivity implements OtpReceivedInterface {
    private ImageView verify;
    private EditText tv_otp1, tv_otp2, tv_otp3, tv_otp4;
    private String otp = "";
    private TextView resendotp;
    private SimpleArcDialog mDialog;
    SmsBroadcastReceiver mySMSBroadCastReceiver;
    private int RESOLVE_HINT = 2;
    private static final int REQ_USER_CONSENT = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        verify = findViewById(R.id.verify);
        tv_otp1 = findViewById(R.id.tv_otp1);
        tv_otp2 = findViewById(R.id.tv_otp2);
        tv_otp3 = findViewById(R.id.tv_otp3);
        tv_otp4 = findViewById(R.id.tv_otp4);
        mDialog = new SimpleArcDialog(OTPActivity.this);
        mDialog.setCancelable(false);
        startSmsUserConsent();
        mySMSBroadCastReceiver = new SmsBroadcastReceiver();
        check_connection();

    }

    public void check_connection() {
        if (CheckNetwork.isInternetAvailable(this))  //if connection available
        {

            init();
            setCountDown();
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(), getResources().getString(R.string.no_internet), Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            check_connection();
                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_green_dark))
                    .setDuration(50000)
                    .show();
        }
    }

    private void init() {
        resendotp = findViewById(R.id.resendotp);
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOTP(); //comment on 01-03-2021
            }
        });
        tv_otp1.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() == 1) {
                    tv_otp2.requestFocus();
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });
        tv_otp2.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() == 1) {
                    tv_otp3.requestFocus();
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });
        tv_otp3.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() == 1) {
                    tv_otp4.requestFocus();
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });
        tv_otp4.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() == 1) {
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        verify = findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if (validateFields())
                {

                    otp = tv_otp1.getText().toString().trim() + "" + tv_otp2.getText().toString().trim() + "" + tv_otp3.getText().toString().trim() + "" + tv_otp4.getText().toString().trim();
                    CheckOTP(otp);
                } else {
                    Toast.makeText(OTPActivity.this, "Fill The Complete Data", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void setCountDown() {
        resendotp.setEnabled(false);
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {

                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                if (seconds < 10) {
                    resendotp.setText(minutes + ":0" + seconds);
                    //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                } else {
                    resendotp.setText(minutes + ":" + seconds);
                    //mTimeLabel.setText("" + minutes + ":" + seconds);
                }
            }

            public void onFinish() {
                //mTextField.setText("done!");
                // Log.d("run", "run: " + "done!");
                resendotp.setText("Resend OTP");
                resendotp.setEnabled(true);
            }
        }.start();
    }


    private boolean validateFields() {
        boolean result = true;
        if (!MyValidator.isValidField(tv_otp1)) {
            result = false;
        }
        if (!MyValidator.isValidField(tv_otp2)) {
            result = false;
        }
        if (!MyValidator.isValidField(tv_otp3)) {
            result = false;
        }
        if (!MyValidator.isValidField(tv_otp4)) {
            result = false;
        }
        return result;
    }

    private void CheckOTP(String otp)
    {
//        Log.d("token", "CheckOTP: " + Shared_Preferences.getPrefs(OTPActivity.this, Constants.NOTIFICATION_TOKEN));
//        try {
//            mDialog.dismiss();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> call = apiInterface.getotp(Shared_Preferences.getPrefs(OTPActivity.this, Constants.REG_MOBILE), otp);
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response)
            {
//                try {
//                    mDialog.dismiss();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                String output = "";
                try {

                    output = response.body().string();
                    JSONObject jsonObject = new JSONObject(output);
                    Log.d("OTP", "onResponse: " + output);

                    if (jsonObject.getString("ResponseCode").equals("1"))
                    {
                        mDialog.dismiss();
                        JSONArray jb = jsonObject.getJSONArray("Data");
                        JSONObject user = jb.getJSONObject(0);
                        Shared_Preferences.setPrefs(OTPActivity.this, Constants.REG_ID, user.getString("user_id"));
                        Shared_Preferences.setPrefs(OTPActivity.this, Constants.REG_MOBILE, user.getString("user_mobile"));
                        Shared_Preferences.setPrefs(OTPActivity.this, Constants.REG_NAME, user.getString("user_name"));
                        Shared_Preferences.setPrefs(OTPActivity.this, Constants.REG_EMAIL, user.getString("user_email"));
//                        Shared_Preferences.setPrefs(OTPActivity.this, Constants.ADDRESS, user.getString("user_address"));
                        Shared_Preferences.setPrefs(OTPActivity.this, Constants.PROFILE_CITY, user.getString("city"));
                        Shared_Preferences.setPrefs(OTPActivity.this, Constants.PROFILE_STATE, user.getString("state"));
                        Shared_Preferences.setPrefs(OTPActivity.this, Constants.PROFILE_DISTRICT, user.getString("district"));
                        Shared_Preferences.setPrefs(OTPActivity.this, Constants.PROFILE_TALUKA, user.getString("taluka"));
                        Shared_Preferences.setPrefs(OTPActivity.this, Constants.REG_IMAGE, user.getString("fld_user_photo"));
//
                        //startActivity(new Intent(OTPActivity.this, AddressFilterActivity.class));
                        Intent intent = new Intent(OTPActivity.this, AddressFilterActivity.class);
                        intent.putExtra("back",1);
                        startActivity(intent);
                        //finish();
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        Toast.makeText(getApplicationContext(), jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        mDialog.dismiss();
                        tv_otp1.setText("");
                        tv_otp1.requestFocus();
                        tv_otp2.setText("");
                        tv_otp3.setText("");
                        tv_otp4.setText("");
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    mDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                mDialog.dismiss();
            }
        });
    }

    private void resendOTP() {
        setCountDown();
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> call = apiInterface.getlogin(
                Shared_Preferences.getPrefs(OTPActivity.this, Constants.REG_MOBILE),
                Shared_Preferences.getPrefs(OTPActivity.this, Constants.NOTIFICATION_TOKEN));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                mDialog.dismiss();
                String output = "";
                try {
                    output = response.body().string();
                    JSONObject jsonObject = new JSONObject(output);
                    boolean res = jsonObject.getBoolean("result");

                    if (res) {
                        tv_otp1.requestFocus();
                        resendotp.setEnabled(false);
                        setCountDown();
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    mDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                mDialog.dismiss();
            }
        });
    }


    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("SMS Retriever started", "=");
                // Toast.makeText(getApplicationContext(), "On Success", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getApplicationContext(), "On OnFailure", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onOtpReceived(String otp) {
        Log.e("onOtpReceived", "=" + otp);
    }

    @Override
    public void onOtpTimeout() {
        Log.e("onOtpTimeout", "=");
    }

    private void registerBroadcastReceiver() {
        mySMSBroadCastReceiver = new SmsBroadcastReceiver();
        mySMSBroadCastReceiver.smsBroadcastReceiverListener =
                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, REQ_USER_CONSENT);
                    }

                    @Override
                    public void onFailure() {
                    }
                };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(mySMSBroadCastReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mySMSBroadCastReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                //That gives all message to us.
                // We need to get the code from inside with regex
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                textViewMessage.setText(
//                        String.format("%s - %s", getString(R.string.received_message), message));
                Log.e("receivedMsg", "=" + message);
                setMessage(message);

//                getOtpFromMessage(message);
            }
        }
    }

    private void setMessage(String message) {
        String str = message;
        String otp = str.replaceAll("[^0-9]", "");
//        String otp = message.substring(5, 58);
        Log.d("dsfs", "setMessage: " + otp);
        if (otp.length() == 4) {
            for (int i = 0; i < otp.length(); i++) {
                switch (i) {
                    case 0:
                        tv_otp1.setText(String.valueOf(otp.charAt(i)));
                    case 1:
                        tv_otp2.setText(String.valueOf(otp.charAt(i)));
                    case 2:
                        tv_otp3.setText(String.valueOf(otp.charAt(i)));
                    case 3:
                        tv_otp4.setText(String.valueOf(otp.charAt(i)));
                }
            }
        }
    }


}