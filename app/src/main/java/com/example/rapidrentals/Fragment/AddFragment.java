package com.example.rapidrentals.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.rapidrentals.Activity.CarAddActivity;
import com.example.rapidrentals.Activity.MainActivity;
import com.example.rapidrentals.DataModel.Car;
import com.example.rapidrentals.DataModel.CarDao;
import com.example.rapidrentals.Helper.CarHelper;
import com.example.rapidrentals.Helper.CarRentAdapter;
import com.example.rapidrentals.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class AddFragment extends Fragment {

    FloatingActionButton addCarFab;
    GridView carGridView;
    LinearLayout emptyLayout;

    ArrayList<Car> carArrayList;
    ArrayList<CarHelper> carHelpers;
    CarRentAdapter carRentAdapter;

    FirebaseUser currentUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add, container, false);

        initComponents(root);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        retrieveCarInformation();
    }

    private void initComponents(View root) {
        carGridView = root.findViewById(R.id.car_grid_view);
        emptyLayout = root.findViewById(R.id.no_record_found_layout);
        addCarFab = root.findViewById(R.id.add_car_button);

        carGridView.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);

        carArrayList = new ArrayList<>();
        carHelpers = new ArrayList<>();
        carRentAdapter = new CarRentAdapter(getActivity().getApplicationContext(), R.layout.car_design_1_card, carHelpers);
        carGridView.setAdapter(carRentAdapter);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        addCarFab.setOnClickListener(view -> onClickAddCar(view));

        retrieveCarInformation();

    }

    public void onClickAddCar(View view) {
        Intent intent = new Intent(getActivity().getApplicationContext(), CarAddActivity.class);
        startActivity(intent);
    }

    private void retrieveCarInformation() {
        Car.getCarsByOwner(currentUser.getUid(), new CarDao() {
            @Override
            public void getCarList(List<Car> carList) {
                if (carList != null && carList.size() > 0) {
                    emptyLayout.setVisibility(View.GONE);
                    carGridView.setVisibility(View.VISIBLE);

                    carHelpers.clear();
                    for (Car car : carList) {
                        carHelpers.add(new CarHelper(0, car.getBrand() + ", " + car.getModel(), car.getYear() + " • " + car.getType(), null, car.getFuel(), car.getRentPerDay()));
                    }

                } else {
                    emptyLayout.setVisibility(View.VISIBLE);
                    carGridView.setVisibility(View.GONE);
                }
            }
        });
    }

}