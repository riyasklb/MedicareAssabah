package com.example.medicareassabah.diet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicareassabah.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;


public class ViewDietActivity extends AppCompatActivity {

    TextView tvHeadBreakfast, tvBreakfast1, tvBreakfast2,
            tvHeadLunch, tvLunch1, tvLunch2, tvHeadSnacks, tvSnacks1, tvSnacks2,
            tvHeadDinner, tvDinner1, tvDinner2;

    TextView tvCal, tvCalTotal, tvClear;

    String calories;
    double dCal;
    String breakfast, lunch, snacks, dinner, requiredCal, totalCal;
    int breakfastCount = 1, lunchCount = 1, snackCount = 1, dinnerCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diet);

        tvHeadBreakfast = findViewById(R.id.tvHeadBreakfast);
        tvBreakfast1 = findViewById(R.id.tvBreakfast1);
        tvBreakfast2 = findViewById(R.id.tvBreakfast2);
        tvHeadLunch = findViewById(R.id.tvHeadLunch);
        tvLunch1 = findViewById(R.id.tvLunch1);
        tvLunch2 = findViewById(R.id.tvLunch2);
        tvHeadSnacks = findViewById(R.id.tvHeadSnacks);
        tvSnacks1 = findViewById(R.id.tvSnacks1);
        tvSnacks2 = findViewById(R.id.tvSnacks2);
        tvHeadDinner = findViewById(R.id.tvHeadDinner);
        tvDinner1 = findViewById(R.id.tvDinner1);
        tvDinner2 = findViewById(R.id.tvDinner2);
        tvCal = findViewById(R.id.tvCal);
        tvCalTotal = findViewById(R.id.tvCalTotal);
        tvClear = findViewById(R.id.tvClear);
//        tvCalRemaining = findViewById(R.id.tvCalRemaining);


        //Getting calorie value
        HashMap<String, String> m = new SessionCalorie(this).getUserDetails();
        calories = m.get("cal");
        new SessionDiet(this).createSession(null, null, null, null,
                calories, null);


        //Getting diet data
        HashMap<String, String> diet = new SessionDiet(this).getUserDetails();
        breakfast = diet.get("breakfast");
        lunch = diet.get("lunch");
        snacks = diet.get("snacks");
        dinner = diet.get("dinner");
        requiredCal = diet.get("requiredCal");
        totalCal = diet.get("totalCal");

        dCal = Double.valueOf(totalCal);

        setDietData();


        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SessionCalorie(ViewDietActivity.this).clear();
                new SessionDiet(ViewDietActivity.this).clear();
                Toast.makeText(ViewDietActivity.this, "Data cleared successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        tvHeadBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (breakfastCount == 3) {
                    showInfo();
                } else {
                    Intent i = new Intent(getApplicationContext(), AddDietActivity.class);
                    i.putExtra("type", "breakfast");
                    startActivity(i);
                    finish();
                }
            }
        });

        tvHeadLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lunchCount == 3) {
                    showInfo();
                } else {
                    Intent i = new Intent(getApplicationContext(), AddDietActivity.class);
                    i.putExtra("type", "lunch");
                    startActivity(i);
                    finish();
                }
            }
        });

        tvHeadSnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (snackCount == 3) {
                    showInfo();
                } else {
                    Intent i = new Intent(getApplicationContext(), AddDietActivity.class);
                    i.putExtra("type", "snacks");
                    startActivity(i);
                    finish();
                }
            }
        });

        tvHeadDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dinnerCount == 3) {
                    showInfo();
                } else {
                    Intent i = new Intent(getApplicationContext(), AddDietActivity.class);
                    i.putExtra("type", "dinner");
                    startActivity(i);
                    finish();
                }
            }
        });


        tvBreakfast1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = tvBreakfast1.getText().toString();
                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>() {
                }.getType();
                List<String> b = gson.fromJson(breakfast, type);
                b.remove(data);
                String json = gson.toJson(b);

                String[] split = data.split(",");
                double c = Double.valueOf(split[1]);
                dCal = dCal - c;

                new SessionDiet(ViewDietActivity.this).createSession(json, lunch, snacks,
                        dinner, requiredCal, String.valueOf(dCal));
                startActivity(new Intent(getApplicationContext(), ViewDietActivity.class));
                finish();
            }
        });


        tvBreakfast2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = tvBreakfast2.getText().toString();
                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>() {
                }.getType();
                List<String> b = gson.fromJson(breakfast, type);
                b.remove(data);
                String json = gson.toJson(b);

                String[] split = data.split(",");
                double c = Double.valueOf(split[1]);
                dCal = dCal - c;

                new SessionDiet(ViewDietActivity.this).createSession(json, lunch, snacks,
                        dinner, requiredCal, String.valueOf(dCal));
                startActivity(new Intent(getApplicationContext(), ViewDietActivity.class));
                finish();
            }
        });


        tvLunch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = tvLunch1.getText().toString();
                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>() {
                }.getType();
                List<String> b = gson.fromJson(lunch, type);
                b.remove(data);
                String json = gson.toJson(b);

                String[] split = data.split(",");
                double c = Double.valueOf(split[1]);
                dCal = dCal - c;

                new SessionDiet(ViewDietActivity.this).createSession(breakfast, json, snacks,
                        dinner, requiredCal, String.valueOf(dCal));
                startActivity(new Intent(getApplicationContext(), ViewDietActivity.class));
                finish();
            }
        });


        tvLunch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = tvLunch2.getText().toString();
                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>() {
                }.getType();
                List<String> b = gson.fromJson(lunch, type);
                b.remove(data);
                String json = gson.toJson(b);

                String[] split = data.split(",");
                double c = Double.valueOf(split[1]);
                dCal = dCal - c;

                new SessionDiet(ViewDietActivity.this).createSession(breakfast, json, snacks,
                        dinner, requiredCal, String.valueOf(dCal));
                startActivity(new Intent(getApplicationContext(), ViewDietActivity.class));
                finish();
            }
        });


        tvSnacks1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = tvSnacks1.getText().toString();
                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>() {
                }.getType();
                List<String> b = gson.fromJson(snacks, type);
                b.remove(data);
                String json = gson.toJson(b);

                String[] split = data.split(",");
                double c = Double.valueOf(split[1]);
                dCal = dCal - c;

                new SessionDiet(ViewDietActivity.this).createSession(breakfast, lunch, json,
                        dinner, requiredCal, String.valueOf(dCal));
                startActivity(new Intent(getApplicationContext(), ViewDietActivity.class));
                finish();
            }
        });


        tvSnacks2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = tvSnacks2.getText().toString();
                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>() {
                }.getType();
                List<String> b = gson.fromJson(snacks, type);
                b.remove(data);
                String json = gson.toJson(b);

                String[] split = data.split(",");
                double c = Double.valueOf(split[1]);
                dCal = dCal - c;

                new SessionDiet(ViewDietActivity.this).createSession(breakfast, lunch, json,
                        dinner, requiredCal, String.valueOf(dCal));
                startActivity(new Intent(getApplicationContext(), ViewDietActivity.class));
                finish();
            }
        });


        tvDinner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = tvDinner1.getText().toString();
                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>() {
                }.getType();
                List<String> b = gson.fromJson(dinner, type);
                b.remove(data);
                String json = gson.toJson(b);

                String[] split = data.split(",");
                double c = Double.valueOf(split[1]);
                dCal = dCal - c;

                new SessionDiet(ViewDietActivity.this).createSession(breakfast, lunch, snacks,
                        json, requiredCal, String.valueOf(dCal));
                startActivity(new Intent(getApplicationContext(), ViewDietActivity.class));
                finish();
            }
        });


        tvDinner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = tvDinner2.getText().toString();
                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>() {
                }.getType();
                List<String> b = gson.fromJson(dinner, type);
                b.remove(data);
                String json = gson.toJson(b);

                String[] split = data.split(",");
                double c = Double.valueOf(split[1]);
                dCal = dCal - c;

                new SessionDiet(ViewDietActivity.this).createSession(breakfast, lunch, snacks,
                        json, requiredCal, String.valueOf(dCal));
                startActivity(new Intent(getApplicationContext(), ViewDietActivity.class));
                finish();
            }
        });

    }



    private void setDietData() {

        /*String requiredCal, totalCal, remainingCal;

        HashMap<String, String> diet = new SessionDiet(this).getUserDetails();
        requiredCal = diet.get("requiredCal");
        totalCal = diet.get("totalCal");*/
//        remainingCal = diet.get("remainingCal");

        tvCal.setText(requiredCal + " KCal");
        tvCalTotal.setText(totalCal + " KCal");
//        tvCalRemaining.setText(remainingCal + " KCal");

        //Breakfast
        Gson b = new Gson();
//        String breakfast = diet.get("breakfast");
        if (TextUtils.isEmpty(breakfast)) {
//            Toast.makeText(getApplicationContext(), "There is something error", Toast.LENGTH_LONG).show();
        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            List<String> arrPackageData = b.fromJson(breakfast, type);
            for (String data : arrPackageData) {
                if (breakfastCount == 1) {
                    tvBreakfast1.setVisibility(View.VISIBLE);
                    tvBreakfast1.setText(data);
                } else if (breakfastCount == 2) {
                    tvBreakfast2.setVisibility(View.VISIBLE);
                    tvBreakfast2.setText(data);
                }
                breakfastCount++;
            }
        }

        //Lunch
        Gson l = new Gson();
//        String lunch = diet.get("lunch");
        if (TextUtils.isEmpty(lunch)) {
//            Toast.makeText(getApplicationContext(), "There is something error", Toast.LENGTH_LONG).show();
        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            List<String> arrPackageData = l.fromJson(lunch, type);
            for (String data : arrPackageData) {
                if (lunchCount == 1) {
                    tvLunch1.setVisibility(View.VISIBLE);
                    tvLunch1.setText(data);
                } else if (lunchCount == 2) {
                    tvLunch2.setVisibility(View.VISIBLE);
                    tvLunch2.setText(data);
                }
                lunchCount++;
            }
        }

        //Snacks
        Gson s = new Gson();
//        String snacks = diet.get("snacks");
        if (TextUtils.isEmpty(snacks)) {
//            Toast.makeText(getApplicationContext(), "There is something error", Toast.LENGTH_LONG).show();
        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            List<String> arrPackageData = s.fromJson(snacks, type);
            for (String data : arrPackageData) {
                if (snackCount == 1) {
                    tvSnacks1.setVisibility(View.VISIBLE);
                    tvSnacks1.setText(data);
                } else if (snackCount == 2) {
                    tvSnacks2.setVisibility(View.VISIBLE);
                    tvSnacks2.setText(data);
                }
                snackCount++;
            }
        }

        //Dinner
        Gson d = new Gson();
//        String dinner = diet.get("dinner");
        if (TextUtils.isEmpty(dinner)) {
//            Toast.makeText(getApplicationContext(), "There is something error", Toast.LENGTH_LONG).show();
        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            List<String> arrPackageData = d.fromJson(dinner, type);
            for (String data : arrPackageData) {
                if (dinnerCount == 1) {
                    tvDinner1.setVisibility(View.VISIBLE);
                    tvDinner1.setText(data);
                } else if (dinnerCount == 2) {
                    tvDinner2.setVisibility(View.VISIBLE);
                    tvDinner2.setText(data);
                }
                dinnerCount++;
            }
        }

    }


    private void showInfo() {
        new AlertDialog.Builder(ViewDietActivity.this)
                .setTitle("Info")
                .setMessage("You can add only two food items under one category")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                }).show();
    }

}
