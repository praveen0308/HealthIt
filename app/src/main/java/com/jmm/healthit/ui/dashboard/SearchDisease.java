package com.jmm.healthit.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.jmm.healthit.R;
import com.jmm.healthit.databinding.ActivitySearchDiseaseBinding;
import com.jmm.healthit.ui.disease.DiseaseDetail;

public class SearchDisease extends AppCompatActivity {

    private ActivitySearchDiseaseBinding binding;
    private String[] diseaseList;
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchDiseaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        diseaseList = getResources().getStringArray(R.array.diseases);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,diseaseList);
        binding.lvDisease.setAdapter(arrayAdapter);
        binding.lvDisease.setOnItemClickListener((adapterView, view, i, l) -> {
            String diseaseName = adapterView.getItemAtPosition(i).toString();


            Intent intent = new Intent(SearchDisease.this, DiseaseDetail.class);
            intent.putExtra("diseaseName",diseaseName.substring(0, 1).toUpperCase() + diseaseName.substring(1));
            startActivity(intent);
        });
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrayAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}