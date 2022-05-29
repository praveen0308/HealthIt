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
import com.jmm.healthit.databinding.FragmentBloodAlcoholContentBinding;


public class BloodAlcoholContent extends Fragment {
    private FragmentBloodAlcoholContentBinding binding;

    private double bacFactor = 0.0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBloodAlcoholContentBinding.inflate(inflater, container, false);
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
            if (binding.rbMale.isChecked()){
                bacFactor = 0.68;
            }else {
                bacFactor = 0.55;
            }
            double alcoholConsumed = Double.parseDouble(binding.etAlcoholConsumed.getText().toString());
            double weight = Double.parseDouble(binding.etBodyWeight.getText().toString());

            double bac= alcoholConsumed/weight*bacFactor*100;
            double rOffBac = Math.round(bac * 100.0) / 100.0;
            binding.tvBac.setText("BAC : "+ rOffBac);
        });
    }
}