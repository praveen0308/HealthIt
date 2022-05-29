package com.jmm.healthit.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmm.healthit.R;
import com.jmm.healthit.adapters.CalculatorItemsAdapter;
import com.jmm.healthit.adapters.NewsAdapter;
import com.jmm.healthit.databinding.FragmentCalculatorScreenBinding;
import com.jmm.healthit.databinding.FragmentNewsBinding;
import com.jmm.healthit.model.WidgetModel;
import com.jmm.healthit.utils.CalculatorOption;

import java.util.ArrayList;
import java.util.List;

public class CalculatorScreen extends Fragment implements CalculatorItemsAdapter.CalculatorItemsAdapterInterface {

    private FragmentCalculatorScreenBinding binding;

    private CalculatorItemsAdapter calculatorItemsAdapter;

    private NavController navController;
    public CalculatorScreen() {
        // Required empty public constructor
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCalculatorScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_main_dashboard);

        setupRvItems();
    }

    private void setupRvItems(){
        calculatorItemsAdapter = new CalculatorItemsAdapter(this);
        binding.rvItems.setHasFixedSize(true);
        binding.rvItems.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.rvItems.setAdapter(calculatorItemsAdapter);
        calculatorItemsAdapter.setItems(getItems());
    }

    private List<WidgetModel> getItems(){
        List<WidgetModel> items = new ArrayList<>();

        items.add(new WidgetModel("Blood Alcohol Content",R.drawable.ic_blood_acohol, CalculatorOption.bloodAlcoholContent));
        items.add(new WidgetModel("Body Mass Index",R.drawable.ic_bmi, CalculatorOption.bmi));
        /*items.add(new WidgetModel("Blood Pressure",R.drawable.ic_blood_pressure, CalculatorOption.bloodPressure));
        items.add(new WidgetModel("Body Fat",R.drawable.ic_body_fat, CalculatorOption.bodyFat));
        items.add(new WidgetModel("Blood Volume",R.drawable.ic_blood_volume, CalculatorOption.bloodVolume));
        items.add(new WidgetModel("Heart Rate",R.drawable.ic_baseline_favorite_24, CalculatorOption.heartRate));
        items.add(new WidgetModel("Blood Sugar",R.drawable.ic_blood_sugar, CalculatorOption.bloodSugar));*/
        return items;

    }

    @Override
    public void onItemClick(WidgetModel item) {
        switch (item.getType()){
            case bloodAlcoholContent:
                navController.navigate(CalculatorScreenDirections.actionCalculatorScreenToBloodAlcoholContent());
                break;
            case bloodPressure:
                break;
            case bmi:
                navController.navigate(CalculatorScreenDirections.actionCalculatorScreenToBMIFragment());
                break;
            case bodyFat:
                break;
            case bloodVolume:
                break;
            case heartRate:
                break;
            case bloodSugar:
                break;
        }
    }
}