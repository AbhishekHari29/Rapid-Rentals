package com.example.rapidrentals.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rapidrentals.R;

import java.util.List;

public class CarRentAdapter extends ArrayAdapter<CarHelper> {

    List<CarHelper> carHelpers;
    int customLayoutId;

    public CarRentAdapter(@NonNull Context context, int resource, @NonNull List<CarHelper> objects) {
        super(context, resource, objects);
        this.carHelpers = objects;
        this.customLayoutId = resource;
    }

    @Override
    public int getCount() {
        return carHelpers.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(customLayoutId, null);
        }

        CarHelper carHelper = carHelpers.get(position);

        ImageView carImage = view.findViewById(R.id.car_image);
        TextView carBrandModel = view.findViewById(R.id.car_brand_model);
        TextView carYearType = view.findViewById(R.id.car_year_type);
        TextView rent = view.findViewById(R.id.car_rent);
        ImageView fuel = view.findViewById(R.id.car_fuel);

        if (carHelper.getImage() != 0)
            carImage.setImageResource(carHelper.getImage());
        carBrandModel.setText(carHelper.getBrand_model());
        carYearType.setText(carHelper.getYear_type());
        rent.setText(String.format("Rs.%.2f/day", carHelper.getRentPerDay()));

        return view;
    }
}