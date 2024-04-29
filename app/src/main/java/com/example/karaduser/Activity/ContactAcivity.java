package com.example.karaduser.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.karaduser.R;

public class ContactAcivity extends AppCompatActivity {

    TextView txt_contact,txt_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_acivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Contact me");

        txt_contact=findViewById(R.id.txt_contact);
        txt_email=findViewById(R.id.txt_email);

        txt_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",txt_contact.getText().toString(),null));
                startActivity(i);
            }
        });

        txt_email.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:"+txt_email.getText().toString()));
                i.putExtra(Intent.EXTRA_EMAIL,txt_email.getText().toString());
                i.putExtra(Intent.EXTRA_SUBJECT,"");
                startActivity(i);
                if(i.resolveActivity(getPackageManager()) != null)
                {
                    startActivity(i);
                }
            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
