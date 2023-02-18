package com.example.medicareassabah.Pill;

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

public class PillAdapter extends RecyclerView.Adapter<PillAdapter.MyViewHolder>implements Filterable {
    private LayoutInflater inflater;
    private ArrayList<PillData> medicalData;
    private ArrayList<PillData> medicalDataArrayList;
    Context c;

    public PillAdapter(Context ctx, ArrayList<PillData> medicalData) {
        c = ctx;
        inflater = LayoutInflater.from(ctx);
        this.medicalData = medicalData;
        this.medicalDataArrayList = medicalData;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.pillchart, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.name.setVisibility(View.VISIBLE);
        final PillData product = medicalDataArrayList.get(position);
        holder.name.setText(product.getMedname());
        holder.dose.setText(product.getDose());

        Picasso.get().load(Config.imageURL + product.getImage()).into(holder.pill);
    holder.cardView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i=new Intent(c,Viewmedicine.class);
            i.putExtra("name",product.getMedname());
            i.putExtra("dose",product.getDose());
            i.putExtra("presday",product.getPrescribedays());
            i.putExtra("date",product.getDate());
            i.putExtra("img",product.getImage());
            c.startActivity(i);
        }
    });

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
                    ArrayList<PillData> filteredList = new ArrayList<>();
                    for (PillData row : medicalDataArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getMedname().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getMedname().toLowerCase().contains(charString.toLowerCase())) {
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
        TextView name,dose;
        ImageView pill;
         CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.pmname);
            dose= itemView.findViewById(R.id.pdose);
            pill= itemView.findViewById(R.id.pimage);
            cardView=itemView.findViewById(R.id.cardview);
        }
    }
}

