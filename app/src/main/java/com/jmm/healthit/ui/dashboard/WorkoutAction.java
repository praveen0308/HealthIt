package com.jmm.healthit.ui.dashboard;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import com.jmm.healthit.R;
import com.jmm.healthit.databinding.FragmentWorkoutActionBinding;

public class WorkoutAction extends Fragment {
    private FragmentWorkoutActionBinding binding;
    private int workoutRes = 0;
    private String workoutName = "";
    private int reps = 0;
    private int doneReps = 0;
    private NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWorkoutActionBinding.inflate(inflater, container, false);
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
        workoutRes = requireArguments().getInt("resId");
        reps = requireArguments().getInt("reps");
        workoutName = requireArguments().getString("workoutName");
        binding.toolbar.setNavigationOnClickListener(view1 -> navController.navigateUp());
        binding.tvExerciseName.setText(workoutName);


        Uri uri = Uri.parse("android.resource://com.jmm.healthit/"+workoutRes);
        MediaController mc = new MediaController(requireContext());
        binding.videoView.setMediaController(mc);
        binding.videoView.setVideoURI(uri);
        binding.btnStart.setOnClickListener(view1 -> {
            binding.videoView.start();
            binding.btnStart.setVisibility(View.GONE);
        });
        binding.videoView.setOnCompletionListener(mp -> {
            doneReps++;
            binding.progressSpin.setProgress(doneReps*20);
            binding.tvProgress.setText(String.valueOf(doneReps));
            if (doneReps<reps){
                binding.videoView.start();
            }

        });

    }
}