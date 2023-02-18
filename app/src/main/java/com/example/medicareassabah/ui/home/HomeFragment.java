package com.example.medicareassabah.ui.home;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.medicareassabah.Config;
import com.example.medicareassabah.DoctorListActivity;
import com.example.medicareassabah.HomeActivity;
import com.example.medicareassabah.MedicalChart.MedicalList;
import com.example.medicareassabah.Pill.Pillremainder;
import com.example.medicareassabah.SessionManager;
import com.example.medicareassabah.Vaccineportal;
import com.example.medicareassabah.bookings.PatientBookingListActivity;
import com.example.medicareassabah.databinding.FragmentHomeBinding;

import java.util.HashMap;

public class HomeFragment extends Fragment {
    TextView tvCovid, tvNearby;
    ImageView imgCovid, imgConsult, imgHospital;
    String latitude, longtitude;
    TextView tvConsult, tvHospital;
    CardView cvHospital, cvPharmacy, cvConsult, bookingsP, pill, Cvfile, Cvmedical, cardschedule, cvlocation;
    //    RecyclerView recyclerView;
    static ProgressDialog progressDialog;
    String url = Config.baseURL + "tiplist.php";
    //    tipAdapter tip;
    String diseases;
    EditText editTextSearch;
    String edsearch;
    //    ArrayList<tipData> arrayList;
    Context c;
    Button ss;
    String query;


    private void showExitAlert() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout from your account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new SessionManager(getActivity()).clearData();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }


    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tvCovid = binding.tvCovid;
        tvConsult =binding.tvConsult;
        imgConsult = binding.imgConsult;
        cvConsult = binding.cvConsult;
        Cvfile = binding.vaccination;
        Cvmedical = binding.medical;
        cardschedule = binding.cardschedule;
        cvlocation = binding.nears;
        bookingsP =binding.bookingsP;
        editTextSearch =binding.search;
        ss = binding.btnsearch;
        pill = binding.pillreminder;


pill.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), Pillremainder.class));
    }
});
        cardschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });

        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searches();
            }
        });


        HashMap<String,String> user=new SessionManager(getActivity()).getUserDetails();
        diseases=user.get("disease");


                bookingsP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PatientBookingListActivity.class);
                startActivity(i);
            }
        });

//        cardChat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), MainActivity2.class);
//                startActivity(i);
//            }
//        });
        Cvmedical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MedicalList.class);
                startActivity(i);
            }
        });


        cvlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fetchLocation();
                Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longtitude + "0,0?q=doctors");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        cvConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DoctorListActivity.class);
                startActivity(i);
            }

        });
        Cvfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Vaccineportal.class);
                startActivity(i);
            }

        });



        return root;
    }
    private void searches() {

        edsearch=editTextSearch.getText().toString();
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, edsearch); // query contains search string
        startActivity(intent);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}