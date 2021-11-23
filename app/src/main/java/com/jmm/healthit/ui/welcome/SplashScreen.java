package com.jmm.healthit.ui.welcome;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.jmm.healthit.R;
import com.jmm.healthit.databinding.FragmentSignInBinding;
import com.jmm.healthit.databinding.FragmentSplashScreenBinding;
import com.jmm.healthit.ui.MainActivity;
import com.jmm.healthit.ui.dashboard.MainDashboard;
import com.jmm.healthit.utils.PreferenceUtils;

public class SplashScreen extends Fragment {

    private FragmentSplashScreenBinding binding;

    private NavController navController;

    private static int SPLASH_SCREEN_TIME_OUT=2000;
    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false);
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

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.



        navController = Navigation.findNavController(getActivity(),R.id.nav_host_main_activity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startNavigation();
            }
        }, SPLASH_SCREEN_TIME_OUT);

    }

    private void startNavigation() {
        if (PreferenceUtils.isLoggedIn(getContext())){
            startActivity(new Intent(getActivity(), MainDashboard.class));
            getActivity().finish();
        }
        else {
            navController.navigate(R.id.action_splashScreen_to_signIn);
        }


    }
}