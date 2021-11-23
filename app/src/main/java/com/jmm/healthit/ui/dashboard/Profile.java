package com.jmm.healthit.ui.dashboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jmm.healthit.R;
import com.jmm.healthit.databinding.FragmentProfileBinding;
import com.jmm.healthit.databinding.FragmentSignInBinding;
import com.jmm.healthit.model.UserModel;
import com.jmm.healthit.ui.MainActivity;
import com.jmm.healthit.ui.welcome.SignIn;
import com.jmm.healthit.utils.PreferenceUtils;
import com.jmm.healthit.utils.ProgressBarHandler;


public class Profile extends Fragment {


    private FragmentProfileBinding binding;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String TAG = SignIn.class.getSimpleName();
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
        binding = FragmentProfileBinding.inflate(inflater, container, false);
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

        binding.etFullName.setText(PreferenceUtils.getUsername(getContext()));
        binding.etEmail.setText(PreferenceUtils.getEmail(getContext()));
        binding.etAge.setText(String.valueOf(PreferenceUtils.getAge(getContext())));
        binding.actvCity.setText(PreferenceUtils.getCity(getContext()));
        binding.actvGender.setText(PreferenceUtils.getGender(getContext()));

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_insert_photo_24)
                .error(R.drawable.ic_baseline_error_24);

        Glide.with(binding.getRoot().getContext()).
                load(PreferenceUtils.getUserProfile(getContext())).
                apply(options).
                into(binding.ivProfile);
        populateCitiesDdl();
        populateGenderDdl();
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                userModel.setGender(binding.actvGender.getText().toString().trim());
                userModel.setCity(binding.actvCity.getText().toString().trim());
                userModel.setAge(age);
                userModel.setPassword(PreferenceUtils.getPassword(getContext()));
                userModel.setProfilePic(PreferenceUtils.getUserProfile(getContext()));



                db.collection("users").document(PreferenceUtils.getUserID(getContext())).set(userModel).addOnCompleteListener(task -> {
                    progressBar.hide();
                    if (task.isSuccessful()){

                        Toast.makeText(getContext(), "Updated successfully!!!", Toast.LENGTH_SHORT).show();

                        PreferenceUtils.saveUsername(userModel.getFullName(), getContext());
                        PreferenceUtils.saveEmail(userModel.getEmail(), getContext());
                        PreferenceUtils.saveAge(userModel.getAge(), getContext());
                        PreferenceUtils.saveGender(userModel.getGender(), getContext());
                        PreferenceUtils.saveCity(userModel.getCity(), getContext());
                        PreferenceUtils.savePassword(userModel.getPassword(), getContext());

                    }
                }).addOnFailureListener(e -> {
                    progressBar.hide();
                    Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"Error : "+e.toString());
                });
            }
        });

        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Choose");
                builder.setMessage("Do you really want to log out ??");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    PreferenceUtils.clearUserData(getContext());
                    dialog.dismiss();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                });

                builder.setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
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