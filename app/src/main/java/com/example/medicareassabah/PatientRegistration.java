package com.example.medicareassabah;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.droidsonroids.gif.GifImageView;

public class PatientRegistration extends AppCompatActivity {

    EditText etUsername, etPassword, etAge, etPhone, etMail,etBlood,etLocation;
    RadioGroup radioGender;
    Button btnRegister;
    String gender;
    EditText etdisease;
    String status,error,users,location;
    String key;
    String url = Config.baseURL+"patient.php";
    GifImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);

        HashMap<String ,String>user= new SessionManager(PatientRegistration.this).getUserDetails();
        users=user.get("username");

        etUsername = findViewById(R.id.etPusername);
        etPassword = findViewById(R.id.etPpassword);
        etAge = findViewById(R.id.etPage);
        etPhone = findViewById(R.id.etPphone);
        etMail = findViewById(R.id.etPmail);
        etdisease = findViewById(R.id.etdisease);
        etBlood = findViewById(R.id.etblood);
        btnRegister = findViewById(R.id.btnPregister);
        radioGender = findViewById(R.id.radioPgender);
        etLocation = findViewById(R.id.etLocation);
        gifImageView=findViewById(R.id.gii);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPatient();
                key();
            }
        });

    }

    private void key() {
            Random r =new Random();
            int k=r.nextInt(1000);
            key="Aid"+k;
    }

    private void registerPatient() {
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();
        final String age = etAge.getText().toString();
        final String phone = etPhone.getText().toString();
        final String mail = etMail.getText().toString();
        final String disease = etdisease.getText().toString();
        final String bll = etBlood.getText().toString();
        location=etLocation.getText().toString();
        int id = radioGender.getCheckedRadioButtonId();
        if(id == -1) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
        } else {
            RadioButton rb = radioGender.findViewById(id);
             gender = rb.getText().toString();
        }
        if (TextUtils.isEmpty(username.trim())) {
            etUsername.setError("Please enter username");
            etUsername.requestFocus();
            return;
        } else if (TextUtils.isEmpty(password.trim())) {
            etPassword.setError("Please enter password");
            etPassword.requestFocus();
            return;
        } else if (TextUtils.isEmpty(age.trim())) {
            etAge.setError("Please enter your Age");
            etAge.requestFocus();
            return;
        }  else if (TextUtils.isEmpty(disease.trim())) {
                etdisease.setError("Please enter your symptoms");
                etdisease.requestFocus();
                return;
        } else if (TextUtils.isEmpty(phone.trim())) {
            etPhone.setError("Please enter phone no");
            etPhone.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(bll.trim())) {
            etBlood.setError("Please enter your blood group");
            etBlood.requestFocus();
            return;
        }
        else if (!isPhoneValid(phone)) {
            etPhone.setError("Invalid phone no");
            etPhone.requestFocus();
            return;
        } else if (TextUtils.isEmpty(mail.trim())) {
            etMail.setError("Please enter mail id ");
            etMail.requestFocus();
            return;
        } else if (!isEmailValid(mail)) {
            etMail.setError("Invalid mail id");
            etMail.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(location.trim())) {
            etLocation.setError("Please enter location ");
            etLocation.requestFocus();
            return;
        }


        gifImageView.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    try {
                        gifImageView.setVisibility(View.GONE);
                        JSONObject c = new JSONObject(response);
                         status = c.getString("Status");
                         error = c.getString("Error");


                        if (status.equals("0")) {
                            Toast.makeText(PatientRegistration.this, error, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PatientRegistration.this, "Registration successfull", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(PatientRegistration.this,PatientLoginActivity.class);
                                startActivity(i);
                                    finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(PatientRegistration.this, error.toString(), Toast.LENGTH_SHORT).show();
        }
    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> m = new HashMap<>();
            m.put("username", username);
            m.put("mail", mail);
            m.put("password", password);
            m.put("age", age);
            m.put("gender", gender);
            m.put("phone", phone);
            m.put("disease", disease);
            m.put("blood_grp",bll);
            m.put("ekey",key);
            m.put("location",location);

            return m;
        }
    };
        Volley.newRequestQueue(PatientRegistration .this).add(request);

}
    public static boolean isPhoneValid(String s) {
        Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }
}
