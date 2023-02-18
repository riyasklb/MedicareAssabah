package com.example.medicareassabah;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.medicareassabah.bmi.BmiActivity;
import com.example.medicareassabah.diet.DietDetails;
import com.example.medicareassabah.diet.SessionCalorie;
import com.example.medicareassabah.diet.ViewDietActivity;
import com.example.medicareassabah.step_counter.FootstepActivity;


public class HomeActivity extends AppCompatActivity {

    CardView card1, card2, card3, card4, card5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        card2=findViewById(R.id.bmi);
        card3=findViewById(R.id.diet);
        card4=findViewById(R.id.foot);



//        card1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(HomeActivity.this,ViewProfile.class);
//                startActivity(i);
//            }
//        });


        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n=new Intent(HomeActivity.this, BmiActivity.class);
                startActivity(n);
            }
        });


        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (new SessionCalorie(HomeActivity.this).checkSavedState()) {
                    startActivity(new Intent(getApplicationContext(), ViewDietActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), DietDetails.class));
                }
            }
        });


        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e=new Intent(HomeActivity.this, FootstepActivity.class);
                startActivity(e);
            }
        });


//        card5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in=new Intent(HomeActivity.this, ReminderActivity.class);
//                startActivity(in);
//            }
//        });


    }
}