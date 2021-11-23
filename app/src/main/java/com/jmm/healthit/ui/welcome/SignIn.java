package com.jmm.healthit.ui.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jmm.healthit.R;
import com.jmm.healthit.databinding.FragmentSignInBinding;
import com.jmm.healthit.model.UserModel;
import com.jmm.healthit.ui.dashboard.MainDashboard;
import com.jmm.healthit.utils.PreferenceUtils;
import com.jmm.healthit.utils.ProgressBarHandler;

public class SignIn extends Fragment {

    private FragmentSignInBinding binding;
    private NavController navController;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String TAG = SignIn.class.getSimpleName();
    private ProgressBarHandler progressBar;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        progressBar = new ProgressBarHandler(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);
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
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_main_activity);
        binding.btnLogin.setOnClickListener(view1 -> {

            progressBar.show();

            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            db.collection("users").whereEqualTo("email", email).whereEqualTo("password", password).get().addOnCompleteListener(task -> {
                progressBar.hide();
                if (task.isSuccessful()) {
                    Log.d(TAG, "fetched documents: " + task.getResult().getDocuments().size());
                    int noOfResults = task.getResult().getDocuments().size();
                    if (noOfResults == 0) {
                        Toast.makeText(getContext(), "Invalid email or password !!!", Toast.LENGTH_SHORT).show();
                    } else {
                        DocumentSnapshot userSnap =task.getResult().getDocuments().get(0);
                        UserModel userModel = userSnap.toObject(UserModel.class);
                        Toast.makeText(getContext(), userModel.getFullName(), Toast.LENGTH_SHORT).show();
                        PreferenceUtils.saveUserId(userSnap.getId(), getContext());
                        PreferenceUtils.saveUsername(userModel.getFullName(), getContext());
                        PreferenceUtils.saveEmail(userModel.getEmail(), getContext());
                        PreferenceUtils.savePassword(userModel.getPassword(), getContext());
                        PreferenceUtils.saveAge(userModel.getAge(), getContext());
                        PreferenceUtils.saveGender(userModel.getGender(), getContext());
                        PreferenceUtils.saveCity(userModel.getCity(), getContext());
                        PreferenceUtils.saveUserProfile(userModel.getProfilePic(), getContext());
                        PreferenceUtils.setLoggedIn(true, getContext());
                        Log.d(TAG, "Logged in user id: " + userSnap.getId());
                        Toast.makeText(getContext(), "Logged in successfully!!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), MainDashboard.class));
                        getActivity().finish();

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

        binding.btnNewUser.setOnClickListener(view1 -> {
            navController.navigate(R.id.action_signIn_to_signUp);
        });
    }
}