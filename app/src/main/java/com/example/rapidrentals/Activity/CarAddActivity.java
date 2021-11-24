package com.example.rapidrentals.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.rapidrentals.DataModel.Car;
import com.example.rapidrentals.DataModel.CarDao;
import com.example.rapidrentals.R;
import com.example.rapidrentals.Utility.LoadingDialog;
import com.example.rapidrentals.Utility.Validation;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CarAddActivity extends AppCompatActivity {

    private TextInputLayout brand, model, type, fuel, transmission, seat, year, reg, rent;
    private AutoCompleteTextView typeAtv, fuelAtv, transmissionAtv;
    private SwitchMaterial carAvailableSwitch, driverAvailableSwitch, locationSwitch;

    private LoadingDialog loadingDialog;

    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_add);

        initComponents();

    }

    private void initComponents() {

        loadingDialog = new LoadingDialog(CarAddActivity.this);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        brand = findViewById(R.id.car_brand_layout);
        model = findViewById(R.id.car_model_layout);
        type = findViewById(R.id.car_type_layout);
        typeAtv = findViewById(R.id.car_type_atv);
        fuel = findViewById(R.id.car_fuel_layout);
        fuelAtv = findViewById(R.id.car_fuel_atv);
        transmission = findViewById(R.id.car_transmisison_layout);
        transmissionAtv = findViewById(R.id.car_transmission_atv);
        seat = findViewById(R.id.car_seat_layout);
        year = findViewById(R.id.car_year_layout);
        reg = findViewById(R.id.car_reg_layout);
        rent = findViewById(R.id.car_rent_layout);
        carAvailableSwitch = findViewById(R.id.car_available_switch);
        driverAvailableSwitch = findViewById(R.id.driver_available_switch);
        locationSwitch = findViewById(R.id.car_location_switch);

        typeAtv.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.car_type)));
        fuelAtv.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.car_fuel)));
        transmissionAtv.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.car_transmission)));
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
        car.setOwner(currentUser.getUid());
        car.setBrand(brand.getEditText().getText().toString().trim());
        car.setModel(model.getEditText().getText().toString().trim());
        car.setFuel(fuelAtv.getText().toString().trim());
        car.setType(typeAtv.getText().toString().trim());
        car.setTransmission(transmissionAtv.getText().toString().trim());
        car.setRegNumber(reg.getEditText().getText().toString().trim());
        car.setNumOfSeats(Integer.parseInt(seat.getEditText().getText().toString().trim()));
        car.setYear(Integer.parseInt(year.getEditText().getText().toString().trim()));
        car.setRentPerDay(Float.parseFloat(rent.getEditText().getText().toString().trim()));
        car.setCarAvailable(carAvailableSwitch.isChecked());
        car.setDriverAvailable(driverAvailableSwitch.isChecked());

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
                & Validation.validateDropDown(transmission, transmissionAtv)
                & Validation.validateEmpty(year)
                & Validation.validateEmpty(reg)
                & Validation.validateEmpty(seat)
                & Validation.validateEmpty((rent));
    }

}