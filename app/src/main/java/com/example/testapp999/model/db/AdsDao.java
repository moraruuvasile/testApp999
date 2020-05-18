package com.example.testapp999.model.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testapp999.model.retrofit.Ads;

import java.util.List;

@Dao
public interface AdsDao {
	@Insert
	void insert(Ads ads);


	@Query("SELECT * FROM Ads_table")
    LiveData<List<Ads>> getAllAds();
}
