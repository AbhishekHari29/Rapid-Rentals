package com.example.rapidrentals.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.rapidrentals.DataModel.Car;
import com.example.rapidrentals.DataModel.CarDao;
import com.example.rapidrentals.R;
import com.example.rapidrentals.Utility.LoadingDialog;
import com.example.rapidrentals.Utility.Validation;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;

public class CarAddActivity extends AppCompatActivity {

    private TextInputLayout brand, model, type, fuel, seat, year, reg;
    private AutoCompleteTextView typeAtv, fuelAtv;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_add);

        initComponents();

    }

    private void initComponents() {

        loadingDialog = new LoadingDialog(CarAddActivity.this);

        brand = findViewById(R.id.car_brand_layout);
        model = findViewById(R.id.car_model_layout);
        type = findViewById(R.id.car_type_layout);
        typeAtv = findViewById(R.id.car_type_atv);
        fuel = findViewById(R.id.car_fuel_layout);
        fuelAtv = findViewById(R.id.car_fuel_atv);
        seat = findViewById(R.id.car_seat_layout);
        year = findViewById(R.id.car_seat_layout);
        reg = findViewById(R.id.car_reg_layout);

        typeAtv.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.car_type)));
        fuelAtv.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.car_fuel)));

    }

    public void onClickAddCar(View view) {
        loadingDialog.startLoadingDialog();

        // Validation
        if (!validateCarDetails()) {
            Toast.makeText(getApplicationContext(), "Validation Failed", Toast.LENGTH_LONG).show();
            loadingDialog.stopLoadingDialog();
            return;
        }

        //Initialize Object
        Car car = new Car();
        car.setId(Car.generateCarId());
        car.setBrand(brand.getEditText().getText().toString().trim());
        car.setModel(model.getEditText().getText().toString().trim());
        car.setFuelType(fuelAtv.getText().toString().trim());
        car.setType(typeAtv.getText().toString().trim());
        car.setRegNumber(reg.getEditText().getText().toString().trim());
        car.setNumOfSeats(Integer.parseInt(seat.getEditText().getText().toString().trim()));
        car.setYear(Integer.parseInt(year.getEditText().getText().toString().trim()));

        // Add to Firebase
        car.addCar(new CarDao() {
            @Override
            public void getBoolean(Boolean result) {
                if (result) {
                    Toast.makeText(getApplicationContext(), "Car Updated", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong. Try again", Toast.LENGTH_LONG).show();
                }
                loadingDialog.stopLoadingDialog();
            }
        });
    }

    public void onClickCancel(View view) {
        onBackPressed();
    }

    private boolean validateCarDetails() {
        return Validation.validateEmpty(brand)
                & Validation.validateEmpty(model)
                & Validation.validateDropDown(type, typeAtv)
                & Validation.validateDropDown(fuel, fuelAtv)
                & Validation.validateEmpty(year)
                & Validation.validateEmpty(reg)
                & Validation.validateEmpty(seat);
    }

}