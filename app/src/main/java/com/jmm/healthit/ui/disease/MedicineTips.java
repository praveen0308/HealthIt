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
import com.jmm.healthit.adapters.ExerciseTipsAdapter;
import com.jmm.healthit.adapters.MedicineTipsAdapter;
import com.jmm.healthit.databinding.FragmentFoodTipsBinding;
import com.jmm.healthit.databinding.FragmentMedicineTipsBinding;
import com.jmm.healthit.databinding.FragmentSignInBinding;
import com.jmm.healthit.model.ExerciseModel;
import com.jmm.healthit.model.MedicineModel;
import com.jmm.healthit.utils.ProgressBarHandler;
import com.jmm.healthit.viewmodel.DiseaseDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class MedicineTips extends Fragment implements MedicineTipsAdapter.MedicineTipsAdapterInterface {


    private FragmentMedicineTipsBinding binding;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String TAG = FoodTips.class.getSimpleName();
    private ProgressBarHandler progressBar;
    private DiseaseDetailViewModel viewModel;

    private MedicineTipsAdapter rvAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        progressBar = new ProgressBarHandler(context);
    }



    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentMedicineTipsBinding.inflate(inflater, container, false);
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
            Log.d(TAG, "onViewCreated: printing from medicinetips >>>"+id);
            progressBar.show();
            db.collection("disease").document(id).collection("medicines").get().addOnCompleteListener(task -> {
                progressBar.hide();
                if (task.isSuccessful()) {
                    Log.d(TAG, "fetched documents: " + task.getResult().getDocuments().size());
                    int noOfResults = task.getResult().getDocuments().size();
                    if (noOfResults == 0) {
                        Toast.makeText(getContext(), "Unknown disease", Toast.LENGTH_SHORT).show();
                    } else {
                        List<DocumentSnapshot> results =task.getResult().getDocuments();
                        List<MedicineModel> items = new ArrayList<>();
                        for (int i=0;i<results.size();i++){
                            items.add(results.get(i).toObject(MedicineModel.class));
                        }
                        rvAdapter.setMedicineTips(items);

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
        rvAdapter = new MedicineTipsAdapter(this);
        binding.rvData.setHasFixedSize(true);
        binding.rvData.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvData.setAdapter(rvAdapter);
    }

    @Override
    public void onItemClick(MedicineModel item) {

    }
}