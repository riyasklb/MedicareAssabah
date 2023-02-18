package com.example.medicareassabah;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class doctorlistadpter extends RecyclerView.Adapter<doctorlistadpter.MyViewHolder>implements Filterable {
    private LayoutInflater inflater;
    private ArrayList<doctorlistmodel> dataModelArrayList;
    private ArrayList<doctorlistmodel> dataModelArrayListFiltered;
    Context c;
    TextView dcardview;
    public doctorlistadpter(Context ctx, ArrayList<doctorlistmodel> dataModelArrayList) {
        c = ctx;
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
        this.dataModelArrayListFiltered = dataModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.doctoritem, parent, false);
        MyViewHolder holder = new MyViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.name.setVisibility(View.VISIBLE);
        holder.name.setText("Doctor Name: "+ dataModelArrayListFiltered.get(position).getUsername());
        holder.dept.setText("Department : "+ dataModelArrayListFiltered.get(position).getDepartment());
        holder.quali.setText("Qualification : "+ dataModelArrayListFiltered.get(position).getQualification());
        holder.exp.setText("Experience: "+ dataModelArrayListFiltered.get(position).getExperience());
        holder.hos.setText("Hospital Name : "+ dataModelArrayListFiltered.get(position).getHospital());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctorlistmodel w = dataModelArrayList.get(position);
                Intent i = new Intent(c, DlistActivity.class);
                i.putExtra("username", w.getUsername());
                i.putExtra("department", w.getDepartment());
                i.putExtra("qualification", w.getQualification());
                i.putExtra("experience", w.getExperience());
                i.putExtra("phone",w.getPhone());
                i.putExtra("hospital", w.getHospital());
                i.putExtra("stime", w.getStime());
                i.putExtra("etime", w.getEtime());
                i.putExtra("available_day", w.getAvailable_day());
                i.putExtra("payment", w.getPayment());
                Toast.makeText(c, w.getQualification(), Toast.LENGTH_SHORT).show();

                c.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModelArrayListFiltered.size();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dataModelArrayListFiltered = dataModelArrayList;
                } else {
                    ArrayList<doctorlistmodel> filteredList = new ArrayList<>();
                    for (doctorlistmodel row : dataModelArrayList) {
                        if (row.getDepartment().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getUsername().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    dataModelArrayListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataModelArrayListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }


        };

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, dept, exp, hos, quali;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.user);
            dept = itemView.findViewById(R.id.dept);
            quali = itemView.findViewById(R.id.quali);
            exp = itemView.findViewById(R.id.exp);
            hos = itemView.findViewById(R.id.hos);

            cardView = itemView.findViewById(R.id.dcardview);

        }
    }
}





