package com.example.karaduser.Fragment;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karaduser.Activity.AboutActivity;
import com.example.karaduser.Activity.ContactAcivity;
import com.example.karaduser.Activity.NotificationActivity;
import com.example.karaduser.Activity.PrivacyActivity;
import com.example.karaduser.Activity.TermsConditionActivity;
import com.example.karaduser.R;
import com.example.karaduser.StartActivity.AddressFilterActivity;
import com.example.karaduser.StartActivity.Login;
import com.example.karaduser.my_library.Shared_Preferences;


public class help extends Fragment {
    private TextView tv_contactus, tv_bookings, tv_man_notification, tv_listlocation, tv_aboutus, tv_ratejustbok, tv_shareoption, tv_faq, tv_term, tv_privacy, tv_logout, tv_callcustomer;

    private View rootView;
    private Dialog dialog;
    private Button btn_yes, btn_no;

    public help() {
        // Required empty public constructor
    }

    public static help newInstance() {
        help fragment = new help();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_help, container, false);
        tv_bookings = rootView.findViewById(R.id.tv_bookings);
        tv_man_notification = rootView.findViewById(R.id.tv_man_notification);
        tv_listlocation = rootView.findViewById(R.id.tv_listlocation);
        tv_aboutus = rootView.findViewById(R.id.tv_aboutus);
        tv_ratejustbok = rootView.findViewById(R.id.tv_ratejustbok);
        tv_shareoption = rootView.findViewById(R.id.tv_shareoption);
        tv_faq = rootView.findViewById(R.id.tv_faq);
        tv_contactus = rootView.findViewById(R.id.tv_contactus);
        tv_term = rootView.findViewById(R.id.tv_term);
        tv_privacy = rootView.findViewById(R.id.tv_privacy);
        tv_logout = rootView.findViewById(R.id.tv_logout);
        tv_callcustomer = rootView.findViewById(R.id.tv_callcustomer);
        ClickListener();
        return rootView;
    }

    private void ClickListener() {
        tv_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ContactAcivity.class);
                startActivity(intent);
            }
        });


        tv_bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Profile.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });
        tv_man_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NotificationActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        tv_listlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddressFilterActivity.class);
                intent.putExtra("back","xyz");
                intent.putExtra("back1",2);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });
        tv_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AboutActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        tv_ratejustbok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RattingDialog();
                final String appPackageName = getActivity().getPackageName();
                try {
                    Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }
            }
        });
        tv_shareoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getActivity().getPackageName();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out the App at: https://play.google.com/store/apps/details?id=" + appPackageName);
                sendIntent.setType("text/plain");
                getContext().startActivity(sendIntent);
            }
        });
        tv_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), FaqActivity.class);
//                startActivity(intent);
            }
        });
        tv_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ContactAcivity.class);
                startActivity(intent);
            }
        });
        tv_term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TermsConditionActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });
        tv_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PrivacyActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });


        tv_callcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("Are you sure you want to Call ?");
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:9763941027"));
                                startActivity(callIntent);
                            }
                        })
                        .setNegativeButton("CANCEL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                android.app.AlertDialog alert = alertDialogBuilder.create();
                alert.show();

            }
        });
    }


    private void showDialog() {
        dialog = new Dialog(getActivity());
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.alert_dialog);
        btn_yes = dialog.findViewById(R.id.btn_logout_yes);
        btn_no = dialog.findViewById(R.id.btn_logout_no);
        btn_yes.setText("Logout");
        btn_no.setText("Cancel");
        TextView text_msg = (TextView) dialog.findViewById(R.id.text_msg);
        ImageView iv_image = (ImageView) dialog.findViewById(R.id.iv_image);
        iv_image.setImageDrawable(getResources().getDrawable(R.drawable.logout_icon));
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text_msg.setText("Are you sure you want to Logout");
        text.setText("Logout...!");
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Login.class);
                Shared_Preferences.clearPref(getActivity());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(getContext(), "Logout Successfully...", Toast.LENGTH_SHORT).show();

            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
