package com.jmm.healthit.ui.disease;

import static com.jmm.healthit.utils.Extensions.fromListToSet;
import static com.jmm.healthit.utils.Extensions.fromSetToList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jmm.healthit.R;
import com.jmm.healthit.databinding.ActivityDiseaseDetailBinding;
import com.jmm.healthit.databinding.ActivityMainBinding;
import com.jmm.healthit.model.Disease;
import com.jmm.healthit.model.UserModel;
import com.jmm.healthit.ui.dashboard.MainDashboard;
import com.jmm.healthit.ui.welcome.UploadProfile;
import com.jmm.healthit.utils.PreferenceUtils;
import com.jmm.healthit.utils.ProgressBarHandler;
import com.jmm.healthit.viewmodel.DiseaseDetailViewModel;

import java.util.List;
import java.util.Set;

public class DiseaseDetail extends AppCompatActivity {

    private ActivityDiseaseDetailBinding binding;

    private DiseaseDetailViewModel viewModel;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressBarHandler progressBar;
    private final String TAG = DiseaseDetail.class.getSimpleName();

    private boolean isWishListed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar = new ProgressBarHandler(this);
        viewModel = new ViewModelProvider(this).get(DiseaseDetailViewModel.class);
        viewModel.diseaseName = getIntent().getStringExtra("diseaseName");


        binding = ActivityDiseaseDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupViewPagerWithTabs();

        binding.ivDiseaseInfo.setOnClickListener(view -> {
            DiseaseDescriptionDialog dialog = new DiseaseDescriptionDialog();
            dialog.show(getSupportFragmentManager(),dialog.getTag());
        });

        binding.ivDiseaseWatchlist.setOnClickListener(view -> {
            if (isWishListed){
                removeFromWatchList(viewModel.diseaseId.getValue());
            }
            else {
                addIntoWatchList(viewModel.diseaseId.getValue());

            }
        });


        viewModel.isWishListed.observe(this,isWishlist->{
            isWishListed = isWishlist;
            if (isWishlist){
                binding.ivDiseaseWatchlist.setImageResource(R.drawable.ic_baseline_star_24);
            }else {
                binding.ivDiseaseWatchlist.setImageResource(R.drawable.ic_baseline_star_border_24);
            }
        });


        progressBar.show();
        db.collection("disease").whereEqualTo("diseaseName", viewModel.diseaseName).get().addOnCompleteListener(task -> {
            progressBar.hide();
            if (task.isSuccessful()) {
                Log.d(TAG, "fetched documents: " + task.getResult().getDocuments().size());
                int noOfResults = task.getResult().getDocuments().size();
                if (noOfResults == 0) {
                    Toast.makeText(this, "Unknown disease", Toast.LENGTH_SHORT).show();
                } else {
                    DocumentSnapshot diseaseSnap =task.getResult().getDocuments().get(0);
                    Disease disease = diseaseSnap.toObject(Disease.class);
                    disease.setDiseaseId(diseaseSnap.getId());
                    viewModel.diseaseId.postValue(disease.getDiseaseId());
                    viewModel.disease.postValue(disease);
                    binding.tvDiseaseName.setText(disease.getDiseaseName());
                    checkWishListed(disease.getDiseaseId());


                }


            } else {
                Log.d(TAG, "Cached get failed: ", task.getException());
            }
        }).addOnFailureListener(e -> {
            progressBar.hide();
            Toast.makeText(this, "Something went wrong!!!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Error : " + e.toString());
        });




}

    private void setupViewPagerWithTabs(){
        binding.vpDiseaseInfos.setAdapter(new MyViewPagerAdapter(this));
        new TabLayoutMediator(binding.tabLayout, binding.vpDiseaseInfos,
                (tab, position) -> {
                    switch (position){
                        case 0:{
                            tab.setText("Exercise");
                            tab.setIcon(R.drawable.img_yoga);
                        }
                        break;
                        case 1:{
                            tab.setText("Food");
                            tab.setIcon(R.drawable.ic_round_restaurant_menu_24);
                        }
                        break;
                        case 2:{
                            tab.setText("Medicine");
                            tab.setIcon(R.drawable.ic_baseline_medication_liquid_24);
                        }
                        break;
                    }

                }).attach();
    }
    static class MyViewPagerAdapter extends FragmentStateAdapter{

        public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:return new Exercise();
                case 1:return new FoodTips();
                default:
                    return new MedicineTips();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    private void addIntoWatchList(String id){
        Set<String> watchListItems = PreferenceUtils.getUserWatchList(getApplicationContext());
        watchListItems.add(id);
        PreferenceUtils.saveUserWatchList(watchListItems,getApplicationContext());
        db.collection("users").document(PreferenceUtils.getUserID(getApplicationContext())).update("watchList",fromSetToList(watchListItems)).addOnCompleteListener(task -> {
            progressBar.hide();
            if (task.isSuccessful()) {
                Log.d(TAG, "result : " + task.getResult());
                Toast.makeText(this, "Added into watchlist!!!", Toast.LENGTH_SHORT).show();

            } else {
                Log.d(TAG, "Cached get failed: ", task.getException());
            }
        }).addOnFailureListener(e -> {
            progressBar.hide();
            Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Error : " + e.toString());
        });
        checkWishListed(viewModel.diseaseId.getValue());
    }

    private void removeFromWatchList(String id){
        Set<String> watchListItems = PreferenceUtils.getUserWatchList(getApplicationContext());
        watchListItems.remove(id);
        PreferenceUtils.saveUserWatchList(watchListItems,getApplicationContext());
        db.collection("users").document(PreferenceUtils.getUserID(getApplicationContext())).update("watchList",fromSetToList(watchListItems)).addOnCompleteListener(task -> {
            progressBar.hide();
            if (task.isSuccessful()) {
                Log.d(TAG, "result : " + task.getResult());
                Toast.makeText(this, "Removed from watchlist!!!", Toast.LENGTH_SHORT).show();

            } else {
                Log.d(TAG, "Cached get failed: ", task.getException());
            }
        }).addOnFailureListener(e -> {
            progressBar.hide();
            Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Error : " + e.toString());
        });
        checkWishListed(viewModel.diseaseId.getValue());
    }

    private void checkWishListed(String diseaseId){
        Set<String> watchListItems = PreferenceUtils.getUserWatchList(getApplicationContext());
        viewModel.isWishListed.postValue(watchListItems.contains(diseaseId));

    }



}