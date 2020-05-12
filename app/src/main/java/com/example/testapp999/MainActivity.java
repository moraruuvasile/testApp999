package com.example.testapp999;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapp999.adapters.AdsRecyclerAdapter;
import com.example.testapp999.viewmodel.AdsViewModel;

public class MainActivity extends AppCompatActivity implements AdsRecyclerAdapter.OnItemClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public void onItemShortClick(int position) {

    }

    @Override
    public void loadNextPage() {

    }
}