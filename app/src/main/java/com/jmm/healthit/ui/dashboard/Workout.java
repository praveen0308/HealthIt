package com.jmm.healthit.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmm.healthit.R;
import com.jmm.healthit.adapters.NewsAdapter;
import com.jmm.healthit.adapters.WorkoutDayAdapter;
import com.jmm.healthit.databinding.FragmentCalculatorScreenBinding;
import com.jmm.healthit.databinding.FragmentWorkoutBinding;
import com.jmm.healthit.model.workout.WorkoutDayModel;

import java.util.ArrayList;
import java.util.List;


public class Workout extends Fragment implements WorkoutDayAdapter.WorkoutDayAdapterInterface {

   private FragmentWorkoutBinding binding;

   private WorkoutDayAdapter workoutDayAdapter;

    private NavController navController;
    public Workout() {
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
        binding = FragmentWorkoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_main_dashboard);
        setupRvDays();
    }

    private void setupRvDays(){
        workoutDayAdapter = new WorkoutDayAdapter(this);
        binding.rvDays.setHasFixedSize(true);
        binding.rvDays.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDays.setAdapter(workoutDayAdapter);
        List<WorkoutDayModel> workoutDays = new ArrayList<>();
        for (int i=1;i<=30;i++){
            workoutDays.add(new WorkoutDayModel(i,0));
        }

        workoutDayAdapter.setItems(workoutDays);
    }

    @Override
    public void onItemClick(WorkoutDayModel item) {
        Bundle bundle = new Bundle();
        bundle.putInt("dayNumber",item.getDayNumber());
        navController.navigate(R.id.action_workout_to_dayWorkout,bundle);
    }
}