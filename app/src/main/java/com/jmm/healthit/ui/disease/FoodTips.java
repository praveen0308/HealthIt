package com.jmm.healthit.ui.disease;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jmm.healthit.R;
import com.jmm.healthit.adapters.FoodTipsAdapter;
import com.jmm.healthit.adapters.NewsAdapter;
import com.jmm.healthit.databinding.FragmentFoodTipsBinding;
import com.jmm.healthit.databinding.FragmentSignInBinding;
import com.jmm.healthit.model.Disease;
import com.jmm.healthit.model.FoodModel;
import com.jmm.healthit.ui.welcome.SignIn;
import com.jmm.healthit.utils.ProgressBarHandler;
import com.jmm.healthit.viewmodel.DiseaseDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class FoodTips extends Fragment implements FoodTipsAdapter.FoodTipsAdapterInterface {

    private FragmentFoodTipsBinding binding;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String TAG = FoodTips.class.getSimpleName();
    private ProgressBarHandler progressBar;
    private DiseaseDetailViewModel viewModel;

    private FoodTipsAdapter rvAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        progressBar = new ProgressBarHandler(context);
    }


    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentFoodTipsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        progressBar.hide();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(DiseaseDetailViewModel.class);
        setupRvData();
        progressBar.show();
        viewModel.diseaseId.observe(requireActivity(),id->{
            Log.d(TAG, "onViewCreated: printing from foodtips >>>"+id);
            progressBar.show();
            db.collection("disease").document(id).collection("foods").get().addOnCompleteListener(task -> {
                progressBar.hide();
                if (task.isSuccessful()) {
                    Log.d(TAG, "fetched documents: " + task.getResult().getDocuments().size());
                    int noOfResults = task.getResult().getDocuments().size();
                    if (noOfResults == 0) {
                        Toast.makeText(getContext(), "Unknown disease", Toast.LENGTH_SHORT).show();
                    } else {
                        List<DocumentSnapshot> results =task.getResult().getDocuments();
                        List<FoodModel> foodModels = new ArrayList<>();
                        for (int i=0;i<results.size();i++){
                            foodModels.add(results.get(i).toObject(FoodModel.class));
                        }
                        rvAdapter.setFoodTips(foodModels);

                    }


                } else {
                    Log.d(TAG, "Cached get failed: ", task.getException());
                }
            }).addOnFailureListener(e -> {
                progressBar.hide();
                Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Error : " + e.toString());
            });


        });
    }


    private void setupRvData(){
        rvAdapter = new FoodTipsAdapter(this);
        binding.rvData.setHasFixedSize(true);
        binding.rvData.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvData.setAdapter(rvAdapter);
    }

    @Override
    public void onItemClick(FoodModel item) {

    }
}