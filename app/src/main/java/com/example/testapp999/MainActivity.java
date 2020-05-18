package com.example.testapp999;

import android.app.Activity;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.testapp999.adapters.AdsRecyclerAdapter;
import com.example.testapp999.fragments.MainFragmentDirections;
import com.example.testapp999.model.retrofit.Ads;
import com.example.testapp999.viewmodel.AdsViewModel;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    AdsViewModel viewModel;
    int pageNr = 1;
    int ifAZorZA = 10;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(AdsViewModel.class);
        navController= Navigation.findNavController(this,R.id.nav_host_fragment);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (ifAZorZA == 10) {
            menu.findItem(R.id.mnu_AZ).setChecked(true);
        } else {
            menu.findItem(R.id.mnu_ZA).setChecked(true);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.mnu_AZ:
            case R.id.mnu_ZA:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    ifAZorZA = 35 - ifAZorZA;
                    Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.action_mainFragment_to_adPreviewFragment);
                    break;
                }
        }
        return true;
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}