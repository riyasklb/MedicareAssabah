package com.example.medicareassabah.diet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicareassabah.R;

import java.util.ArrayList;

public class NutritionixAdapter extends RecyclerView.Adapter<NutritionixAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<NutritionixDataModel> dataModelArrayList;
    private Context c;
    ClickListener clickListener;

    public NutritionixAdapter(Context ctx, ArrayList<NutritionixDataModel> dataModelArrayList){
        c = ctx;
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
        clickListener = (ClickListener) ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_nutritionix_items, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.tvName.setText(dataModelArrayList.get(position).getName());
        holder.tvBrand.setText(dataModelArrayList.get(position).getBrand());
        holder.tvCal.setText("Calories: " + dataModelArrayList.get(position).getCal());
        holder.tvFat.setText("Total fat: " + dataModelArrayList.get(position).getFat());

        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvBrand, tvCal, tvFat;
        CardView rootLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvBrand = itemView.findViewById(R.id.tvBrand);
            tvCal = itemView.findViewById(R.id.tvCal);
            tvFat = itemView.findViewById(R.id.tvFat);
            rootLayout = itemView.findViewById(R.id.rootLayout);
        }

    }


    public interface ClickListener
    {
        void onClick(int position);
    }

}
