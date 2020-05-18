package com.example.testapp999.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.testapp999.model.retrofit.AdObject;
import com.example.testapp999.model.retrofit.Ads;
import com.example.testapp999.model.retrofit.ApiClient;
import com.example.testapp999.model.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepo {
    private static final String TAG = "ApiRepo";
    private static ApiRepo apiRepo;
    private ApiInterface apiInterface;
    private MutableLiveData<List<Ads>> liveDataAds;
    private MutableLiveData<AdObject> liveDataAdObject;

    private ApiRepo() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        liveDataAds = new MutableLiveData<>();
        liveDataAdObject = new MutableLiveData<>();
    }

    public static ApiRepo getInstance() {
        if (apiRepo == null) {
            apiRepo = new ApiRepo();
        }
        return apiRepo;
    }

    public MutableLiveData<List<Ads>> getAds(int pageNr, String lang, int pageSize) {
        Call<Ads.ObjAds> call = apiInterface.getAdsByQuery(pageNr, lang, pageSize);
        call.enqueue(new Callback<Ads.ObjAds>() {
            @Override
            public void onResponse(Call<Ads.ObjAds> call, Response<Ads.ObjAds> response) {
                if (response.isSuccessful()) {
                    List<Ads> listAds = response.body().getAds();
                    liveDataAds.setValue(listAds);
                }
            }

            @Override
            public void onFailure(Call<Ads.ObjAds> call, Throwable t) {
                Log.d(TAG, "onFailure from getAds: " + t);
            }
        });
        return liveDataAds;
    }

    public MutableLiveData<AdObject> getAd(int id) {
        Call<AdObject> call = apiInterface.getAdById(id);
        call.enqueue(new Callback<AdObject>() {
            @Override
            public void onResponse(Call<AdObject> call, Response<AdObject> response) {
                if (response.isSuccessful()) {
                    AdObject adObject = response.body();
                    liveDataAdObject.setValue(adObject);
                }
            }

            @Override
            public void onFailure(Call<AdObject> call, Throwable t) {
                Log.d(TAG, "onFailure: from getAd" + t);
            }
        });
        return liveDataAdObject;
    }
}
