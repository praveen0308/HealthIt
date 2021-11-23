package com.jmm.healthit.ui.welcome;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jmm.healthit.R;
import com.jmm.healthit.databinding.FragmentSignInBinding;
import com.jmm.healthit.databinding.FragmentUploadProfileBinding;
import com.jmm.healthit.ui.dashboard.MainDashboard;
import com.jmm.healthit.utils.PreferenceUtils;
import com.jmm.healthit.utils.ProgressBarHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadProfile extends Fragment {

    private FragmentUploadProfileBinding binding;
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String fileName = "";
    private Uri mContentUri;
    private ProgressBarHandler progressBar;
    private final String TAG = UploadProfile.class.getSimpleName();
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        progressBar = new ProgressBarHandler(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUploadProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {
                // Handle the returned Uri
                if (uri != null) {
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(uri);
                    binding.ivProfile.setImageURI(uri);
                    fileName = imageFileName;
                    mContentUri = uri;
                }


            });


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.ivProfile.setOnClickListener(view12 -> mGetContent.launch("image/*"));

        binding.btnUpload.setOnClickListener(view1 -> uploadImageToFirebase(fileName,mContentUri));
    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = requireActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

    private void uploadImageToFirebase(String name, Uri uri) {
        StorageReference storageReference = storage.getReference().child("profile_pictures/" + name);
        progressBar.show();
        storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
            progressBar.hide();
            Log.d(TAG, "OnSuccess : Profile picture successfully uploaded on firebase...");

            storageReference.getDownloadUrl().addOnSuccessListener(imageUrl -> {
                Toast.makeText(getContext(), "Profile picture uploaded successfully !!!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "OnSuccess : Profile picture url >>> "+imageUrl);
                saveProfileUrlInDb(imageUrl.toString());

            });
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Something went wrong!! Please try again!!!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "OnFailure : Profile picture uploading task failed!!!");
            Log.e(TAG, "Error : "+e.toString());
            progressBar.hide();
        });
    }

    private void saveProfileUrlInDb(String url){
        progressBar.show();
        db.collection("users").document(PreferenceUtils.getUserID(getContext())).update("profilePic",url).addOnCompleteListener(task -> {
            progressBar.hide();
            if (task.isSuccessful()){

                Toast.makeText(getContext(), "Updated successfully!!!", Toast.LENGTH_SHORT).show();
                PreferenceUtils.saveUserProfile(url, getContext());
                PreferenceUtils.setLoggedIn(true,getContext());
                startActivity(new Intent(requireActivity(), MainDashboard.class));
                requireActivity().finish();

            }
        }).addOnFailureListener(e -> {
            progressBar.hide();
            Toast.makeText(getContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Error : "+e.toString());
        });
    }

}