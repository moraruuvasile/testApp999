package com.example.testapp999.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testapp999.model.retrofit.Ads;
import com.example.testapp999.repository.ApiRepo;

import java.util.List;

public class AdsViewModel extends ViewModel {
    private MutableLiveData<List<Ads>> mutableLiveData;
    private ApiRepo apiRepo;

    public void init(int pageNr, String lang, int pageSize){
        if (mutableLiveData != null){
            return;
        }
        apiRepo = new ApiRepo();
        mutableLiveData = apiRepo.getAds(pageNr, lang, pageSize);
    }

    public LiveData<List<Ads>> getListAds() {
        return mutableLiveData;
    }


    private MutableLiveData<CharSequence> text = new MutableLiveData<>();
    public void setText(CharSequence input) {
        text.setValue(input);
    }
    public LiveData<CharSequence> getText() {
        return text;
    }
}

