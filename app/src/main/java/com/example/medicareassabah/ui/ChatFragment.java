package com.example.medicareassabah.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicareassabah.R;
import com.example.medicareassabah.messages.ForgotPassword.ForgotPassActivity;
import com.example.medicareassabah.messages.Home.MActivity;
import com.example.medicareassabah.messages.LoginReg.LoginActivity;
import com.example.medicareassabah.messages.LoginReg.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;


public class ChatFragment extends Fragment {

    private static final String TAG = "LoginActivity";

    private EditText userEmail, userPassword;
    private Button loginButton;
    private TextView linkSingUp, linkForgotPassword, copyrightTV;


    private ProgressDialog progressDialog;

    //Firebase Auth
    private FirebaseAuth mAuth;
    private FirebaseUser user, currentUser;

    private DatabaseReference userDatabaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        userEmail =view. findViewById(R.id.inputEmail);
        userPassword =view.findViewById(R.id.inputPassword);
        loginButton = view. findViewById(R.id.loginButton);
        linkSingUp =view.  findViewById(R.id.linkSingUp);
        linkForgotPassword =view.  findViewById(R.id.linkForgotPassword);
        progressDialog = new ProgressDialog(getActivity());

        //Copyright text
//        copyrightTV = findViewById(R.id.copyrightTV);
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        copyrightTV.setText("NSS © " + year);

        //redirect to FORGOT PASS activity
        linkForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( TAG, "onClick: go to FORGOT Activity");
                Intent intent = new Intent(getActivity(), ForgotPassActivity.class);
                startActivity(intent);

            }
        });

        //redirect to register activity
        linkSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d( TAG, "onClick: go to Register Activity");
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);

            }
        });


        /**
         * Login Button with Firebase
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();

                loginUserAccount(email, password);
            }
        });
        return view;
    }
    private void loginUserAccount(String email, String password) {
        //just validation
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(), "Email is required", Toast.LENGTH_SHORT).show();
//            SweetToast.error(this, "Email is required");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(getActivity(), "Your email is not valid.", Toast.LENGTH_SHORT).show();
//            SweetToast.error(this, "Your email is not valid.");
        } else if(TextUtils.isEmpty(password)){
            Toast.makeText(getActivity(), "Password is required", Toast.LENGTH_SHORT).show();
//            SweetToast.error(this, "Password is required");
        } else if (password.length() < 6){
            Toast.makeText(getActivity(), "May be your password had minimum 6 numbers of character.", Toast.LENGTH_SHORT).show();
//            SweetToast.error(this, "May be your password had minimum 6 numbers of character.");
        } else {
            //progress bar
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);

            // after validation checking, log in user a/c
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                // these lines for taking DEVICE TOKEN for sending device to device notification
                                String userUID = mAuth.getCurrentUser().getUid();
                                Task<String> userDeiceToken = FirebaseMessaging.getInstance().getToken();
                                userDatabaseReference.child(userUID).child("device_token").setValue(userDeiceToken)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                checkVerifiedEmail();
                                            }
                                        });

                            } else {
                                Toast.makeText(getActivity(), "Your email and password may be incorrect. Please check & try again.", Toast.LENGTH_SHORT).show();
//                                SweetToast.error(LoginActivity.this, "Your email and password may be incorrect. Please check & try again.");
                            }

                            progressDialog.dismiss();

                        }
                    });
        }
    }

    /** checking email verified or NOT */
    private void checkVerifiedEmail() {
        user = mAuth.getCurrentUser();
        boolean isVerified = false;
        if (user != null) {
            isVerified = user.isEmailVerified();
        }
        if (isVerified){
            String UID = mAuth.getCurrentUser().getUid();
            userDatabaseReference.child(UID).child("verified").setValue("true");

            Intent intent = new Intent(getActivity(), MActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(), "Email is not verified. Please verify first", Toast.LENGTH_SHORT).show();
//            SweetToast.info(LoginActivity.this, "Email is not verified. Please verify first");
            mAuth.signOut();
        }

    }
}