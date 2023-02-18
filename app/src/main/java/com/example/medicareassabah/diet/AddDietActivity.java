package com.example.medicareassabah.diet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medicareassabah.R;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddDietActivity extends AppCompatActivity implements NutritionixAdapter.ClickListener{

    EditText etFood, etCal;
    Button btnSearch, btnAdd;
    ProgressBar p;
    String url, query, dietType;

    private RecyclerView recyclerView;
    private NutritionixAdapter rvAdapter;
    private ArrayList<NutritionixDataModel> dataModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet);

        etFood = findViewById(R.id.etQuery);
        etCal = findViewById(R.id.etCal);
        btnSearch = findViewById(R.id.btnSearch);
        btnAdd = findViewById(R.id.btnAdd);
        p = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recycler);

        //Getting diet-type...
        Intent i = getIntent();
        dietType = i.getStringExtra("type");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDiet();
            }
        });

    }



    private void addDiet() {

        String food = etFood.getText().toString();
        String cal = etCal.getText().toString();

        if (TextUtils.isEmpty(food)) {
            etFood.setError("Enter food name");
            etFood.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etCal.getText().toString())){
            new AlertDialog.Builder(this)
                    .setTitle("Info")
                    .setMessage("Please search for the required food and select one from the list to get calorie data")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    }).show();
            return;
        }

        double calories = Double.valueOf(cal);

        String breakfast, lunch, snacks, dinner, requiredCal, totalCal;
        double tCal, tCal2;

        HashMap<String, String> diet = new SessionDiet(this).getUserDetails();
        breakfast = diet.get("breakfast");
        lunch = diet.get("lunch");
        snacks = diet.get("snacks");
        dinner = diet.get("dinner");
        requiredCal = diet.get("requiredCal");
        totalCal = diet.get("totalCal");

        tCal = Double.valueOf(totalCal);

        ArrayList<String> data = new ArrayList<>();

        //Breakfast
        if (dietType.equals("breakfast")) {
            Gson gson = new Gson();
            if (TextUtils.isEmpty(breakfast)) {
                data.add(food+", "+cal);
            } else {
                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();
                data = gson.fromJson(breakfast, type);
                data.add(food+", "+cal);
            }
            Gson g = new Gson();
            String json = g.toJson(data);
            tCal2 = tCal + calories;
            new SessionDiet(this).createSession(json, lunch, snacks, dinner,
                    requiredCal, Double.toString(tCal2));
        }

        //Lunch
        if (dietType.equals("lunch")) {
            Gson gson = new Gson();
            if (TextUtils.isEmpty(lunch)) {
                data.add(food+", "+cal);
            } else {
                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();
                data = gson.fromJson(lunch, type);
                data.add(food+", "+cal);
            }
            Gson g = new Gson();
            String json = g.toJson(data);
            tCal2 = tCal + calories;
            new SessionDiet(this).createSession(breakfast, json, snacks, dinner,
                    requiredCal, Double.toString(tCal2));
        }

        //Snacks
        if (dietType.equals("snacks")) {
            Gson gson = new Gson();
            if (TextUtils.isEmpty(snacks)) {
                data.add(food+", "+cal);
            } else {
                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();
                data = gson.fromJson(snacks, type);
                data.add(food+", "+cal);
            }
            Gson g = new Gson();
            String json = g.toJson(data);
            tCal2 = tCal + calories;
            new SessionDiet(this).createSession(breakfast, lunch, json, dinner,
                    requiredCal, Double.toString(tCal2));
        }

        //Dinner
        if (dietType.equals("dinner")) {
            Gson gson = new Gson();
            if (TextUtils.isEmpty(dinner)) {
                data.add(food+", "+cal);
            } else {
                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();
                data = gson.fromJson(dinner, type);
                data.add(food+", "+cal);
            }
            Gson g = new Gson();
            String json = g.toJson(data);
            tCal2 = tCal + calories;
            new SessionDiet(this).createSession(breakfast, lunch, snacks, json,
                    requiredCal, Double.toString(tCal2));
        }

        startActivity(new Intent(getApplicationContext(), ViewDietActivity.class));
        finish();
    }




    private void search() {
        query = etFood.getText().toString();
        if (TextUtils.isEmpty(query)) {
            Toast.makeText(this, "Enter query", Toast.LENGTH_SHORT).show();
            return;
        }

        url = "https://nutritionix-api.p.rapidapi.com/v1_1/search/" + query + "?fields=item_name%2Citem_id%2Cbrand_name%2Cnf_calories%2Cnf_total_fat";

        p.setVisibility(View.VISIBLE);
        StringRequest s = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        p.setVisibility(View.INVISIBLE);
                        dataModelArrayList = new ArrayList<>();

                        try {
                            JSONObject c = new JSONObject(response);
                            JSONArray hits = c.getJSONArray("hits");

                            for (int i = 0; i < hits.length(); i++) {

                                JSONObject hit = hits.getJSONObject(i);
                                JSONObject fields = hit.getJSONObject("fields");

                                dataModelArrayList.add(new NutritionixDataModel(
                                        fields.getString("item_id"),
                                        fields.getString("item_name"),
                                        fields.getString("brand_name"),
                                        fields.getString("nf_calories"),
                                        fields.getString("nf_total_fat")
                                ));
                            }
                            setupRecycler();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                p.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("x-rapidapi-host", "nutritionix-api.p.rapidapi.com");
                params.put("x-rapidapi-key", "5fa7a566famshd071d2d65338d57p1f2162jsna480a9cf2bba");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(s);
    }



    private void setupRecycler(){
        rvAdapter = new NutritionixAdapter(this, dataModelArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }



    @Override
    public void onClick(int position) {
        NutritionixDataModel p = dataModelArrayList.get(position);
        String calorie = p.getCal();
//        etCal.setVisibility(View.VISIBLE);
        etCal.setText(calorie);
    }

}
