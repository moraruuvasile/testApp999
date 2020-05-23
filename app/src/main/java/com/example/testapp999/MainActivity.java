package com.example.testapp999;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.testapp999.fragments.MainFragment;
import com.example.testapp999.model.retrofit.Ads;
import com.example.testapp999.viewmodel.AdsViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
            case R.id.mnu_bytitle:
                sortAdsByTitle();
                break;

            case R.id.mnu_byprice:
                sortAdsByPrice();
                break;

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

    private void sortAdsByPrice() {
        List<Ads> adsList = new ArrayList<>();
        MainFragment mainFragment = (MainFragment) getFunctionalFragment();
        adsList = mainFragment.getAdsList();

        if(viewModel.getAscOrDesc() == 10){
            Ads.PriceCompare nameCompare = new Ads().new PriceCompare();
            Collections.sort(adsList, nameCompare);
        } else {
            Ads.ReversPriceCompare nameCompare = new Ads().new ReversPriceCompare();
            Collections.sort(adsList, nameCompare);
        }
        mainFragment.setAdsList(adsList);

    }

    private void sortAdsByTitle() {
        List<Ads> adsList = new ArrayList<>();
//        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment).getChildFragmentManager().getFragments().get(0);

        MainFragment mainFragment = (MainFragment) getFunctionalFragment();
        adsList = mainFragment.getAdsList();

        if(viewModel.getAscOrDesc() == 10){
            Ads.NameCompare nameCompare = new Ads().new NameCompare();
            Collections.sort(adsList, nameCompare);
        } else {
            Ads.ReversNameCompare nameCompare = new Ads().new ReversNameCompare();
            Collections.sort(adsList, nameCompare);
        }
        mainFragment.setAdsList(adsList);
    }

    public Fragment getFunctionalFragment ()
    {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        FragmentManager navHostManager = Objects.requireNonNull(navHostFragment).getChildFragmentManager();

        Fragment fragment=null;
        List fragment_list = navHostManager.getFragments();

        for (int i=0; i < fragment_list.size() ; i ++ )
        {
            if ( fragment_list.get(i) instanceof MainFragment) {
                fragment = (MainFragment) fragment_list.get(i);
                break;
            }
        }

        return fragment;
    }
}