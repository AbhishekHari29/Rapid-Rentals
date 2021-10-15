package com.example.rapidrentals.Fragment;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rapidrentals.Activity.LoginActivity;
import com.example.rapidrentals.Helper.CategoryAdapter;
import com.example.rapidrentals.Helper.CategoryHelper;
import com.example.rapidrentals.R;
import com.example.rapidrentals.Utility.AppUtility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView categoryRecycler;
    private RecyclerView.Adapter categoryAdapter;
    private ArrayList<CategoryHelper> categoryHelpers;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        initComponents(root);

        return root;
    }

    private void initComponents(View root) {

        GradientDrawable[] gradientDrawables = new GradientDrawable[4];
        gradientDrawables[0] = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradientDrawables[1] = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradientDrawables[2] = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradientDrawables[3] = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});

        // Category Recycler
        categoryRecycler = root.findViewById(R.id.categories_recycler);
        categoryRecycler.setHasFixedSize(true);
        categoryRecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        categoryHelpers = new ArrayList<>();
        categoryHelpers.add(new CategoryHelper(R.drawable.car_category_1,"1","Sports",gradientDrawables[0]));
        categoryHelpers.add(new CategoryHelper(R.drawable.car_category_1,"2","Sedan",gradientDrawables[1]));
        categoryHelpers.add(new CategoryHelper(R.drawable.car_category_1,"3","Luxury",gradientDrawables[2]));
        categoryHelpers.add(new CategoryHelper(R.drawable.car_category_1,"4","Coupe",gradientDrawables[3]));

        categoryAdapter = new CategoryAdapter(categoryHelpers);
        categoryRecycler.setAdapter(categoryAdapter);
    }

}