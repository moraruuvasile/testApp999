package com.example.testapp999.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.testapp999.model.retrofit.Ads;
import com.example.testapp999.model.retrofit.ApiClient;
import com.example.testapp999.model.retrofit.ApiInterface;
import com.example.testapp999.model.retrofit.AaObjectAds;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepo {
    private static final String TAG = "ApiRepo";
    ApiInterface apiInterface;

    public MutableLiveData<List<Ads>> getAds(int pageNr, String lang, int pageSize) {
        MutableLiveData<List<Ads>> liveDataAds = new MutableLiveData<>();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AaObjectAds> call = apiInterface.getAdsByQuery(pageNr, lang, pageSize);
        call.enqueue(new Callback<AaObjectAds>() {
            @Override
            public void onResponse(Call<AaObjectAds> call, Response<AaObjectAds> response) {
                if (response.isSuccessful()) {
                    List<Ads> listAds = response.body().getAds();
                    liveDataAds.setValue(listAds);
                }
            }

            @Override
            public void onFailure(Call<AaObjectAds> call, Throwable t) {
                Log.d(TAG, "onFailure: Error Retrofit");
                liveDataAds.setValue(null);
            }
        });
        return liveDataAds;
    }
}
