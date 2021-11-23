package com.jmm.healthit.ui.disease;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmm.healthit.R;
import com.jmm.healthit.databinding.FragmentDiseaseDescriptionDialogBinding;
import com.jmm.healthit.databinding.FragmentMedicineTipsBinding;
import com.jmm.healthit.viewmodel.DiseaseDetailViewModel;

public class DiseaseDescriptionDialog extends DialogFragment {

    private FragmentDiseaseDescriptionDialogBinding binding;
    private DiseaseDetailViewModel viewModel;
    public DiseaseDescriptionDialog() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentDiseaseDescriptionDialogBinding.inflate(inflater, container, false);
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
        viewModel = new ViewModelProvider(requireActivity()).get(DiseaseDetailViewModel.class);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        viewModel.disease.observe(requireActivity(),disease -> {
            binding.tvTitle.setText(disease.getDiseaseName());
            binding.tvDescription.setText(disease.getDiseaseDescription());
        });
    }
}