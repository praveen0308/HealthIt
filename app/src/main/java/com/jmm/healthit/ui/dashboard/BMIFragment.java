package com.jmm.healthit.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmm.healthit.R;
import com.jmm.healthit.databinding.FragmentBMIBinding;
import com.jmm.healthit.databinding.FragmentCalculatorScreenBinding;

public class BMIFragment extends Fragment {
    private FragmentBMIBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBMIBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnCalculate.setOnClickListener(view1 -> {
            double weight = Double.parseDouble(binding.etWeight.getText().toString());
            double height = Double.parseDouble(binding.etHeight.getText().toString());
            double bmi= weight/height;
            double rOffBmi = Math.round(bmi * 100.0) / 100.0;
            binding.tvBmi.setText("BMI : "+ rOffBmi);
        });
    }
}