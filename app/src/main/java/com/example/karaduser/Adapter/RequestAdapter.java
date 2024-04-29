package com.example.karaduser.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karaduser.Activity.RequestActivity;
import com.example.karaduser.Activity.SubBusinessActivity;
import com.example.karaduser.MainActivity;
import com.example.karaduser.ModelList.RequestList;
import com.example.karaduser.NetworkController.APIInterface;
import com.example.karaduser.NetworkController.MyConfig;
import com.example.karaduser.R;
import com.example.karaduser.StartActivity.SimpleArcDialog;
import com.example.karaduser.my_library.Constants;
import com.example.karaduser.my_library.Shared_Preferences;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder>
{


    private Context context;
    ArrayList<RequestList>list;

    String hemant;
    SimpleArcDialog mDialog;
    RequestActivity requestActivity = new RequestActivity();

    private Dialog dialog;
    private Button btn_yes;
    private Button btn_no;


    public RequestAdapter(Context context, ArrayList<RequestList> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requestlayout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.businessname.setText(list.get(position).getBusiness_info_name());
        holder.bookingDate.setText(list.get(position).getFld_service_requested_date());
        holder.type.setText(list.get(position).getFld_business_name());
        holder.slot.setText(list.get(position).getFld_actual_booking_slot());
        holder.slot.setText(list.get(position).getFld_actual_booking_slot());
        holder.mobile.setText(list.get(position).getMobile());
        Picasso.with(context).load(list.get(position).getBusiness_image())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.no_image_available)
                .into(holder.imageView);

        if(list.get(position).getFld_actual_booking_slot().equals("null"))
        {
            holder.slot.setText("");
        }



        hemant=list.get(position).getFld_service_issuedorreturned();
        Log.e("fgd", ""+ hemant);
        if (hemant.equals("3")){

            holder.delete.setVisibility(View.INVISIBLE);
        }
        else if (hemant.equals("1")){

            holder.delete.setVisibility(View.INVISIBLE);
        }
        else if (hemant.equals("4")){

            holder.delete.setVisibility(View.INVISIBLE);
        }
        else if (hemant.equals("0")){

            holder.delete.setVisibility(View.INVISIBLE);
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showdialog(list.get(position));

            }
        });

    }

    private void showdialog(RequestList position)
    {
        dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.alert_dialog);
        btn_yes = dialog.findViewById(R.id.btn_logout_yes);
        btn_no = dialog.findViewById(R.id.btn_logout_no);
        btn_yes.setText("Yes");
        btn_no.setText("No");
        TextView text_msg = (TextView) dialog.findViewById(R.id.text_msg);
        ImageView iv_image = (ImageView) dialog.findViewById(R.id.iv_image);
        iv_image.setImageResource(R.drawable.services);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text_msg.setText("Are you sure you want to Delete Request");
        text.setText("Delete..");
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();
                String vendorid=position.getVendor_id();
                String bsinessid=position.getBusiness_info_id();
                String issuedservice=position.getFld_user_issued_servicesApp();

                APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
                Call<ResponseBody> result = apiInterface.cancelstatus(Shared_Preferences.getPrefs(context, Constants.REG_ID),
                        vendorid,bsinessid,"4",issuedservice);
                result.enqueue(new Callback<ResponseBody>()
                {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                    {
                        try {
                            String output = response.body().string();
                            Log.d("Response2", "GetComments:-" + output);
                            JSONObject jsonObject = new JSONObject(output);

                            if (jsonObject.getString("result").equals("true"))
                            {
                                Toast.makeText(context, "Your "+position.getFld_business_name()+" Cancelled Successfully..!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(context,MainActivity.class);
                                context.startActivity(i);
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
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView bookingDate,businessname,type,slot,appointmentdata,mobile;
        CircleImageView imageView;
        Button delete;

        public ViewHolder(View view)
        {
            super(view);
            bookingDate= view.findViewById(R.id.req_bookingdate);
            businessname= view.findViewById(R.id.req_businessname);
            mobile= view.findViewById(R.id.tv_mobile);
            type= view.findViewById(R.id.req_type);
            slot= view.findViewById(R.id.req_slot);
            appointmentdata= view.findViewById(R.id.tv_AppointmentData);
            imageView=view.findViewById(R.id.req_img);
            delete=view.findViewById(R.id.req_cancelBtn);

        }
    }
}
