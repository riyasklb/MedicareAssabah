package com.example.medicareassabah.MedicalChart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicareassabah.Config;
import com.example.medicareassabah.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MedicalAdapter extends RecyclerView.Adapter<MedicalAdapter.MyViewHolder>implements Filterable {
    private LayoutInflater inflater;
    private ArrayList<MedicalData> medicalData;
    private ArrayList<MedicalData> medicalDataArrayList;
    Context c;

    public MedicalAdapter(Context ctx, ArrayList<MedicalData> medicalData) {
        c = ctx;
        inflater = LayoutInflater.from(ctx);
        this.medicalData = medicalData;
        this.medicalDataArrayList = medicalData;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.medical_chart, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.bookdate.setVisibility(View.VISIBLE);
        final MedicalData product = medicalDataArrayList.get(position);
        holder.bookdate.setText("Last booking date :"+product.getBookdate());
        holder.dna.setText("Doctor Name :"+product.getDoctorname());
        holder.dph.setText("Doctor Phone No :"+product.getDoctorphn());
        holder.ddpt.setText("Department :"+product.getDepartment());
        holder.dhos.setText("Hospital Name :"+product.getDoctorhospital());
        holder.ddis.setText("Disease Info :"+product.getDiseaseInfo());

        if(!medicalDataArrayList.get(position).getFiles().equals("")){
            Picasso.get().load(Config.imageURL + product.getFiles()).into(holder.dfile);
        }
//        holder.dfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MedicalData p = medicalDataArrayList.get(position);
//                String image = p.getFiles();
//                Intent i = new Intent(c, ZoomActivity.class);
//                i.putExtra("img",image);
//                c.startActivity(i);
//            }
//        });
//        holder.btnPharmacy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(c, PharmacyListActivity.class);
//                c.startActivity(intent);
//            }
//        });




//        holder.dfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MedicalData p = medicalDataArrayList.get(position);
//                String image = p.getFiles();
//                Intent i = new Intent();
//                i.setPackage("com.android.chrome");
//                i.setAction(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(Config.imageURL + image));
//                c.startActivity(i);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return medicalDataArrayList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    medicalDataArrayList = medicalData;
                } else {
                    ArrayList<MedicalData> filteredList = new ArrayList<>();
                    for (MedicalData row : medicalDataArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getBookdate().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getBookdate().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    medicalDataArrayList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = medicalDataArrayList;
                return filterResults;
            }

        @Override
        protected void publishResults (CharSequence constraint, FilterResults results){
                notifyDataSetChanged();
        }
    };


}


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bookdate,dna,dph,dhos,ddpt,ddis;
        ImageView dfile;
         CardView cardView;
         Button btnPharmacy;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookdate= itemView.findViewById(R.id.bookdate);
            dna= itemView.findViewById(R.id.dname);
            dph= itemView.findViewById(R.id.dphoneno);
            dhos= itemView.findViewById(R.id.dhospital);
            ddpt= itemView.findViewById(R.id.ddept);
            ddis= itemView.findViewById(R.id.diseaseInfo);
            dfile= itemView.findViewById(R.id.files);
            cardView=itemView.findViewById(R.id.dcardview);
        }
    }
}

