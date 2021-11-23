package com.jmm.healthit.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jmm.healthit.model.Disease;

public class DiseaseDetailViewModel extends ViewModel {

    public String diseaseName = "";
    public MutableLiveData<String> diseaseId = new MutableLiveData<>();
    public MutableLiveData<Boolean> isWishListed = new MutableLiveData<>(false);
    public MutableLiveData<Disease> disease = new MutableLiveData<>();
}
