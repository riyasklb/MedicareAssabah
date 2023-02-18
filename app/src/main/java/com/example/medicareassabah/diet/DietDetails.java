package com.example.medicareassabah.diet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicareassabah.R;

import java.util.HashMap;


public class DietDetails extends AppCompatActivity {

    EditText etAge, etHeight, etWeight;
    TextView tvCal;
    Button btnSave, btnContinue, btnClear;
    Spinner spActivity;
    RadioGroup rgGender;

    boolean saveCounter = false;
    int index;
    double cal;
    double AF; //Activity Factor

    String[] data = {"Choose Activity",
            "Sedentary (little or no exercise)",
            "Lightly active (light exercise/sports 1-3 days/week)",
            "Moderately active (moderate exercise/sports 3-5 days/week)",
            "Very active (hard exercise/sports 6-7 days a week)",
            "Extra active (very hard exercise/sports & physical job or 2x training)" };


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_details);

        etAge = findViewById(R.id.etAge);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        tvCal = findViewById(R.id.tvCal);
        btnSave = findViewById(R.id.btnSave);
        btnContinue = findViewById(R.id.btnContinue);
        btnClear = findViewById(R.id.btnClear);
        spActivity = findViewById(R.id.spActivity);
        rgGender = findViewById(R.id.rgGender);

        rgGender.check(R.id.male);

        setValues();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(DietDetails.this, android.R.layout.simple_dropdown_item_1line, data);
        spActivity.setAdapter(adapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!saveCounter) {
                    Toast.makeText(DietDetails.this, "Save the details before you continue", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(getApplicationContext(), ViewDietActivity.class));
                    finish();
                }
            }
        });


        spActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;

                switch (i) {
                    case 1: AF = 1.2;
                        break;
                    case 2: AF = 1.375;
                        break;
                    case 3: AF = 1.55;
                        break;
                    case 4: AF = 1.725;
                        break;
                    case 5: AF = 1.9;
                        break;
                }
                Toast.makeText(DietDetails.this, ""+AF, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

    }


    private void save() {
        String age, weight, height, gender, calories;

        age = etAge.getText().toString();
        weight = etWeight.getText().toString();
        height = etHeight.getText().toString();

        int g = rgGender.getCheckedRadioButtonId();
        RadioButton r = findViewById(g);
        gender = r.getText().toString();

        if (TextUtils.isEmpty(age)) {
            etAge.setError("Enter age");
            return;
        } else if (TextUtils.isEmpty(weight)) {
            etWeight.setError("Enter weight");
            return;
        } else if (TextUtils.isEmpty(height)) {
            etHeight.setError("Enter height");
            return;
        } else if (TextUtils.isEmpty(gender)) {
            Toast.makeText(this, "Select gender", Toast.LENGTH_SHORT).show();
            return;
        } else if (index == 0) {
            Toast.makeText(this, "Select activity", Toast.LENGTH_SHORT).show();
            return;
        }

        //Converting string values to double for calculation
        double fAge = Double.valueOf(age);
        double fWeight = Double.valueOf(weight);
        double fHeight = Double.valueOf(height);

        //Calculating required calories
        switch (gender) {
            case "Male":
                cal = (66 + (13.7 * fWeight) + (5 * fHeight) - (6.8 * fAge)) * AF;
                break;
            case "Female":
                cal = (655 + (9.6 * fWeight) + (1.8 * fHeight) - (4.7 * fAge)) * AF;
                break;
        }
        double roundedCal = Math.round(cal * 100) / 100;
        calories = String.valueOf(roundedCal);

        tvCal.setText(calories + " KCal");

        new SessionCalorie(this).createSession(age, weight, height, calories);

        saveCounter = true;
    }


    private void setValues() {
        HashMap<String, String> m = new SessionCalorie(this).getUserDetails();
        String age = m.get("age");
        String weight = m.get("weight");
        String height = m.get("height");
        String calories = m.get("cal");

        etAge.setText(age);
        etWeight.setText(weight);
        etHeight.setText(height);
        if (!TextUtils.isEmpty(calories))
            tvCal.setText(calories + " KCal");
        else
            tvCal.setText("0.0 KCal");
    }


    private void clear() {
        etAge.setText("");
        etHeight.setText("");
        etWeight.setText("");
        tvCal.setText("0.0 KCal");
    }
}
