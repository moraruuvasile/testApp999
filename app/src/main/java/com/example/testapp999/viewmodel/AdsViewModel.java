package com.example.testapp999.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.testapp999.model.retrofit.AdObject;
import com.example.testapp999.model.retrofit.Ads;
import com.example.testapp999.repository.ApiRepo;
import com.example.testapp999.repository.DbRepo;

import java.util.List;

public class AdsViewModel extends AndroidViewModel {
    private final ApiRepo apiRepo;
    private final DbRepo dbRepo;
    private MutableLiveData<List<Ads>> adsLiveData;
    private MutableLiveData<AdObject> adLiveData;


    public AdsViewModel(@NonNull Application application) {
        super(application);

        apiRepo = ApiRepo.getInstance();
        dbRepo = new DbRepo(application);
    }

    /// Comunication with API I
    public LiveData<List<Ads>> getListAds(int pageNr, String lang, int pageSize) {
        return apiRepo.getAds(pageNr, lang, pageSize);
    }

    /// Comunication with API II
    public LiveData<AdObject> getListAd(int id) {
        return apiRepo.getAd(id);
    }


    /// Comunication with DB
    public void insert(Ads ads) {
        dbRepo.insert(ads);
    }

    public LiveData<List<Ads>> getAllAdsFromDB() {
        return dbRepo.getAllNotes();
    }
}

