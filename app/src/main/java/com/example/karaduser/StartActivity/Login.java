package com.example.karaduser.StartActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.karaduser.NetworkController.APIInterface;
import com.example.karaduser.NetworkController.MyConfig;
import com.example.karaduser.R;
import com.example.karaduser.my_library.Constants;
import com.example.karaduser.my_library.MyValidator;
import com.example.karaduser.my_library.Shared_Preferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    ImageView btn_login;
    TextView tv_regiter;
    EditText user_mobile;
    private Dialog dialog;
    private SimpleArcDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_2);
        mDialog = new SimpleArcDialog(Login.this);
        mDialog.setCancelable(false);

        FirebaseApp.initializeApp(Login.this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        String token = task.getResult().getToken();
                        Log.d("Notification_Token1", "onComplete: " + token);
                        Shared_Preferences.setPrefs(Login.this, Constants.NOTIFICATION_TOKEN, token);
                    }
                });

        tv_regiter = findViewById(R.id.tv_regiter);
        user_mobile = findViewById(R.id.user_mobile);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    getsend_otp();
                }

            }
        });

        tv_regiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, RegisterNew.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });


    }


    private boolean validateFields() {
        boolean result = true;
        if (!MyValidator.isValidMobile(user_mobile)) {
            result = false;
        }
        return result;
    }

    private void getsend_otp() {
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getlogin(user_mobile.getText().toString(),
                Shared_Preferences.getPrefs(Login.this, Constants.NOTIFICATION_TOKEN));
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    String output = response.body().string();

                    Log.d("Response", "response=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        Shared_Preferences.setPrefs(Login.this, Constants.REG_MOBILE, user_mobile.getText().toString());
                        startActivity(new Intent(Login.this, OTPActivity.class));
                        finish();
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        Toast.makeText(Login.this, reason, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Login.this, reason, Toast.LENGTH_SHORT).show();
                    }
                    mDialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("server_error", "onFailure: " + t.getMessage());
                Toast.makeText(Login.this, "Please Check Internet", Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });
    }

    private void ForgetDialog() {
        dialog = new Dialog(Login.this, R.style.MaterialDialog);
        dialog.setContentView(R.layout.forget_password);
        dialog.setCancelable(false);
        ImageView update = (ImageView) dialog.findViewById(R.id.btn_ok);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String txtValue = edit_review.getText().toString().trim();

//                if (txtValue.length() == 0) {
//                    edit_review.setError("Field required");
//                } else {

//                }


            }
        });

        ImageView cancel = (ImageView) dialog.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //now that the dialog is set up, it's time to show it
        dialog.show();
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

    @Override
    public void onBackPressed() {
        finish();

    }


}
