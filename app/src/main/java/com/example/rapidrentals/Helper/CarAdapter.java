package com.example.rapidrentals.Helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rapidrentals.R;

import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    ArrayList<CarHelper> carHelpers;

    public CarAdapter(ArrayList<CarHelper> carHelpers) {
        this.carHelpers = carHelpers;
    }

    @NonNull
    @Override
    public CarAdapter.CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_design_card, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.CarViewHolder holder, int position) {
        CarHelper carHelper = carHelpers.get(position);

        holder.image.setImageResource(carHelper.getImage());
        holder.brand_model.setText(carHelper.getBrand_model());
        holder.year_type.setText(carHelper.getYear_type());
        holder.location.setText(carHelper.getLocation());
        holder.price.setText(carHelper.getPrice());
        holder.fuel.setText(carHelper.getFuel());
    }

    @Override
    public int getItemCount() {
        return carHelpers.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {

        TextView brand_model;
        TextView year_type;
        TextView location;
        TextView price;
        TextView fuel;
        ImageView image;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            brand_model = itemView.findViewById(R.id.car_brand_model);
            year_type = itemView.findViewById(R.id.car_year_type);
            location = itemView.findViewById(R.id.car_location);
            price = itemView.findViewById(R.id.car_rent);
            fuel = itemView.findViewById(R.id.car_fuel);
            image = itemView.findViewById(R.id.car_image);
        }
    }

}
