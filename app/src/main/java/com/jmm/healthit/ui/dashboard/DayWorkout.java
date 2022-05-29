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
import com.jmm.healthit.adapters.WorkoutAdapter;
import com.jmm.healthit.adapters.WorkoutDayAdapter;
import com.jmm.healthit.databinding.FragmentDayWorkoutBinding;
import com.jmm.healthit.model.workout.WorkoutDayModel;
import com.jmm.healthit.model.workout.WorkoutModel;

import java.util.ArrayList;
import java.util.List;

public class DayWorkout extends Fragment implements WorkoutAdapter.WorkoutAdapterInterface {


    private FragmentDayWorkoutBinding binding;
    private int dayNumber=0;
    private NavController navController;
    private WorkoutAdapter workoutAdapter;
    public DayWorkout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDayWorkoutBinding.inflate(inflater, container, false);
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
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_main_dashboard);
        dayNumber = requireArguments().getInt("dayNumber");
        binding.ivBack.setOnClickListener(view1 -> {
            navController.navigateUp();
        });
        binding.tvWorkoutDay.setText(String.format("Day %d Workout",dayNumber));
        setupRvDays();
    }

    private void setupRvDays(){
        workoutAdapter = new WorkoutAdapter(this);
        binding.rvWorkouts.setHasFixedSize(true);
        binding.rvWorkouts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvWorkouts.setAdapter(workoutAdapter);
        List<WorkoutModel> workouts = new ArrayList<>();


        workouts.add(new WorkoutModel(1,"Dry Land Swimming",5,R.raw.dry_land_swim));
        workouts.add(new WorkoutModel(2,"Pelvic shift",5,R.raw.pelvic_shift));
        workouts.add(new WorkoutModel(3,"Triangle pose",5,R.raw.triangle_pose));
        workouts.add(new WorkoutModel(4,"Pilates roll over",5,R.raw.pilates_roll_over));
        workouts.add(new WorkoutModel(5,"Wall stretch",5,R.raw.wall_stretch));

        workoutAdapter.setItems(workouts);
    }

    @Override
    public void onItemClick(WorkoutModel item) {
        Bundle bundle = new Bundle();
        bundle.putInt("resId",item.getResId());
        bundle.putInt("reps",item.getReps());
        bundle.putString("workoutName",item.getName());
        navController.navigate(R.id.action_dayWorkout_to_workoutAction,bundle);
    }
}