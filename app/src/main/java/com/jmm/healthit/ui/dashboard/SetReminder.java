package com.jmm.healthit.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.jmm.healthit.R;
import com.jmm.healthit.databinding.ActivityMainDashboardBinding;
import com.jmm.healthit.databinding.ActivitySetReminderBinding;

public class SetReminder extends AppCompatActivity {

    private ActivitySetReminderBinding binding;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetReminderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


    }
}