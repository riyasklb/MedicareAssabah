package com.example.medicareassabah;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.medicareassabah.messages.LoginReg.LoginActivity;

public class DoctorHome extends AppCompatActivity {
    CardView Cvbook, Cvpro, Cvqa;
String latitude,longtitude;

private void showExitAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout from your account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DoctorSession(DoctorHome.this).clearData();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menus, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle menu item clicks
        int id = item.getItemId();

//        if (id == R.id.profile) {
//            Intent intent = new Intent(DoctorHome.this, EditProfiledActivity.class);
//            startActivity(intent);
//            //do your function here
//
//        }
        if (id == R.id.logout) {
            showExitAlert();

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        Cvbook=findViewById(R.id.bookings);
        Cvpro=findViewById(R.id.vprofile);
        Cvqa=findViewById(R.id.chat);
    Cvbook.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(DoctorHome.this,BookingList.class);
        startActivity(i);
    }
});
        Cvpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DoctorHome.this,EditProfiledActivity.class);
                startActivity(i);
            }
        });
        Cvqa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DoctorHome.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }
}