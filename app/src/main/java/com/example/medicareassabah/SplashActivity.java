package com.example.medicareassabah;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               if (new SessionManager(SplashActivity.this).checkLogin()) {
                   Intent i = new Intent(SplashActivity.this, UserHomeActivity.class);
                   startActivity(i);
                   finish();

               }else if(new DoctorSession(SplashActivity.this).checkLogin()){
                   Intent i = new Intent(SplashActivity.this,DoctorHome.class);
                   startActivity(i);
                   finish();
               }
               else{
                   Intent i = new Intent(SplashActivity.this,ModuleActivity.class);
                   startActivity(i);
                   finish();

               }

           }
       },3000);    }
}