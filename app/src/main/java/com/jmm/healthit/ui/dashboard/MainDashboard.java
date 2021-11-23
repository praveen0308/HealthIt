package com.jmm.healthit.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.jmm.healthit.R;
import com.jmm.healthit.databinding.ActivityDiseaseDetailBinding;
import com.jmm.healthit.databinding.ActivityMainDashboardBinding;

public class MainDashboard extends AppCompatActivity {

    private ActivityMainDashboardBinding binding;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this,R.id.nav_host_main_dashboard);
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController);


    }
}