package com.example.medicareassabah.bmi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicareassabah.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class BmiActivity extends AppCompatActivity {

    EditText age, etHeight, etWeight;
    TextView tvBMI, tvLevel;
    Button calculate;
    RadioGroup rd;
    FloatingActionButton fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

//        age=findViewById(R.id.bmiage);
        etHeight = findViewById(R.id.height);
        etWeight = findViewById(R.id.weight);
//        rd=findViewById(R.id.bmiradio);
        tvBMI = findViewById(R.id.tvBMI);
        tvLevel = findViewById(R.id.tvLevel);
        calculate= findViewById(R.id.calculate);
        fl=findViewById(R.id.graphss);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmi();
            }
        });

        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nn=new Intent(BmiActivity.this, GraphActivity.class);
                startActivity(nn);
            }
        });

    }


    private void bmi(){
        String height, weight;
        float h, w, bmi;

        height = etHeight.getText().toString();
        weight = etWeight.getText().toString();

        if (TextUtils.isEmpty(height)) {
            etHeight.setError("Enter height");
            return;
        } else if (TextUtils.isEmpty(weight)) {
            etWeight.setError("Enter weight");
            return;
        }

        h = Float.valueOf(height) / 100;
        w = Float.valueOf(weight);

        bmi = w / (h * h);
        tvBMI.setText("" + bmi);

        if (bmi < 15)
            tvLevel.setText("Very severely underweight");
        else if (bmi > 15 && bmi < 16)
            tvLevel.setText("Severely underweight");
        else if (bmi > 16 && bmi < 18.5)
            tvLevel.setText("Underweight");
        else if (bmi > 18.5 && bmi < 25)
            tvLevel.setText("Normal (healthy weight)");
        else if (bmi > 25 && bmi < 30)
            tvLevel.setText("Overweight");
        else if (bmi > 30 && bmi < 35)
            tvLevel.setText("Moderately obese");
        else if (bmi > 35 && bmi < 40)
            tvLevel.setText("Severely obese");
        else if (bmi > 40)
            tvLevel.setText("Very severely obese");
    }

}
