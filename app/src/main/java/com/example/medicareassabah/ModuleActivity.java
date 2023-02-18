package com.example.medicareassabah;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ModuleActivity extends AppCompatActivity {

    Button imgPatient,imgDoctor,pharmacy_image,delivery_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);

        imgPatient = findViewById(R.id.patient);
        imgDoctor = findViewById(R.id.doctor);
//        pharmacy_image = findViewById(R.id.pharmacy_image);
//        delivery_image = findViewById(R.id.delivery_image);


                        imgPatient.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(ModuleActivity.this,PatientLoginActivity.class);
                                startActivity(i);
                            }

                        });

                                imgDoctor.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(ModuleActivity.this,DoctorLoginActivity.class);
                                        startActivity(i);
                                    }
                                });
    }
}