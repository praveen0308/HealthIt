package com.jmm.healthit.ui.welcome;

import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jmm.healthit.R;
import com.jmm.healthit.databinding.FragmentSignInBinding;
import com.jmm.healthit.databinding.FragmentSignUpBinding;
import com.jmm.healthit.model.UserModel;
import com.jmm.healthit.utils.PreferenceUtils;
import com.jmm.healthit.utils.ProgressBarHandler;

import java.lang.reflect.Array;

public class SignUp extends Fragment {


    private FragmentSignUpBinding binding;
    private NavController navController;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final String TAG = SignUp.class.getSimpleName();
    private ProgressBarHandler progressBar;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        progressBar = new ProgressBarHandler(context);
    }
    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
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
        populateCitiesDdl();
        populateGenderDdl();
        navController = Navigation.findNavController(getActivity(),R.id.nav_host_main_activity);
        binding.btnNext.setOnClickListener(view1 -> {

            progressBar.show();

            int age = 0;
            if (!binding.etAge.getText().toString().isEmpty()){
                age = Integer.parseInt(binding.etAge.getText().toString());
            }else {
                Toast.makeText(getContext(), "Enter age!!!", Toast.LENGTH_SHORT).show();
            }

            UserModel userModel = new UserModel();
            userModel.setFullName(binding.etFullName.getText().toString().trim());
            userModel.setEmail(binding.etEmail.getText().toString().trim());
            userModel.setPassword(binding.etPassword.getText().toString().trim());
            userModel.setGender(binding.actvGender.getText().toString().trim());
            userModel.setCity(binding.actvCity.getText().toString().trim());
            userModel.setAge(age);


            db.collection("users").add(userModel).addOnCompleteListener(task -> {
                progressBar.hide();
                if (task.isSuccessful()){

                    Toast.makeText(getContext(), "Registered successfully!!!", Toast.LENGTH_SHORT).show();
                    PreferenceUtils.saveUserId(task.getResult().getId(),getContext());
                    PreferenceUtils.saveUsername(userModel.getFullName(),getContext());
                    PreferenceUtils.saveEmail(userModel.getEmail(),getContext());
                    PreferenceUtils.savePassword(userModel.getPassword(),getContext());
                    PreferenceUtils.saveAge(userModel.getAge(),getContext());
                    PreferenceUtils.saveGender(userModel.getGender(),getContext());
                    PreferenceUtils.saveCity(userModel.getCity(),getContext());
                    Log.d(TAG,"Added user id: "+task.getResult().getId());
                    navController.navigate(R.id.action_signUp_to_uploadProfile);
                }
            }).addOnFailureListener(e -> {
                progressBar.hide();
                Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"Error : "+e.toString());
            });

        });

        binding.btnBack.setOnClickListener(view1 -> {
            navController.navigateUp();
        });
    }

    private void populateCitiesDdl(){
        String[] cities = getResources().getStringArray(R.array.cities);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,cities);
        binding.actvCity.setThreshold(1);
        binding.actvCity.setAdapter(arrayAdapter);
    }

    private void populateGenderDdl(){
        String[] gender = getResources().getStringArray(R.array.gender);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,gender);
        binding.actvGender.setThreshold(1);
        binding.actvGender.setAdapter(arrayAdapter);
    }




}