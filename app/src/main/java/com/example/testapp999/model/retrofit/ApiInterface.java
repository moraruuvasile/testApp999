package com.example.testapp999.model.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/api/v3/categories/real-estate/apartments-and-rooms")
    Call<Ads.ObjAds> getAdsByQuery(@Query("page") int pageNr, @Query("lang") String lang, @Query("page_size") int pageSize);

    @GET("/api/v3/ads/{id}")
    Call<AdObject> getAdById(@Path("id") int id);

}
