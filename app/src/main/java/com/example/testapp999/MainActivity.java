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
import androidx.navigation.ui.NavigationUI;

import com.example.testapp999.adapters.AdsRecyclerAdapter;
import com.example.testapp999.fragments.MainFragmentDirections;
import com.example.testapp999.model.retrofit.Ads;
import com.example.testapp999.viewmodel.AdsViewModel;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    AdsViewModel viewModel;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(AdsViewModel.class);

        navController= Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        if (viewModel.getAscOrDesc() == 10) {
            menu.findItem(R.id.mnu_AZ).setChecked(true);
        } else {
            menu.findItem(R.id.mnu_ZA).setChecked(true);
        }
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mnu_save:
                navController.navigate(R.id.action_mainFragment_to_savedAdsFragment);
                break;

            case android.R.id.home:
                navController.navigateUp();
                break;

            case R.id.mnu_AZ:
            case R.id.mnu_ZA:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    viewModel.inverseAscOrDesc();
                    break;
                }
        }
        return true;
    }

}