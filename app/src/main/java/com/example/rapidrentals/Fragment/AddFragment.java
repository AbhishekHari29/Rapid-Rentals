package com.example.rapidrentals.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.rapidrentals.Activity.CarAddActivity;
import com.example.rapidrentals.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class AddFragment extends Fragment {

    FloatingActionButton addCarFab;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add, container, false);

        addCarFab = root.findViewById(R.id.add_car_button);
        addCarFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAddCar(view);
            }
        });

        return root;
    }

    public void onClickAddCar(View view) {
        Intent intent = new Intent(getActivity().getApplicationContext(), CarAddActivity.class);
        startActivity(intent);
    }

}