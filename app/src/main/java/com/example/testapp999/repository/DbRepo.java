package com.example.testapp999.repository;

import android.app.Application;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.testapp999.MainActivity;
import com.example.testapp999.model.db.AdsDao;
import com.example.testapp999.model.db.AdsDataBase;
import com.example.testapp999.model.retrofit.Ads;

import java.util.List;

public class DbRepo {
    private AdsDao adsDao;

    public DbRepo(Application application) {
        AdsDataBase database = AdsDataBase.getInstance(application);
        adsDao = database.noteDao();
    }

    public void insert(Ads note) {
        new Thread(()->{
            try {
            adsDao.insert(note);
            } catch (SQLiteException exception) {
                exception.printStackTrace();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }).start();
    }

    public LiveData<List<Ads>> getAllNotes() {
        return adsDao.getAllAds();
    }

}
