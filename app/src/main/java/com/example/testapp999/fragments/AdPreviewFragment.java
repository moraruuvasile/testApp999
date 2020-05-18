package com.example.testapp999.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testapp999.R;
import com.example.testapp999.adapters.AdRecyclerAdapter;
import com.example.testapp999.model.retrofit.AdObject;
import com.example.testapp999.model.retrofit.Ads;
import com.example.testapp999.viewmodel.AdsViewModel;

import java.util.ArrayList;
import java.util.List;


public class AdPreviewFragment extends Fragment {
    private Ads ads;
    private AdsViewModel adsViewModel;
    private List<String> photoList;
    private AdRecyclerAdapter adRecyclerAdapter;
    private ViewPager2 adViewPager;
    private int i = 0;

    public AdPreviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        requireActivity().setTitle("Preview");

        AdPreviewFragmentArgs args = AdPreviewFragmentArgs.fromBundle(requireArguments());
        ads = args.getAds();

        adsViewModel = new ViewModelProvider(requireActivity()).get(AdsViewModel.class);
        photoList = new ArrayList<>();

        return inflater.inflate(R.layout.fragment_ad_preview, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adViewPager = view.findViewById(R.id.adViewPager2);

        adsViewModel.getListAd(Integer.parseInt(ads.get_id()))
                .observe(getViewLifecycleOwner(), (AdObject adObject) -> {

                    photoList.clear();
                    for (AdObject.ObjPhoto photo : adObject.getAd().getExtended_images()) {
                        photoList.add(photo.getFilename());
                        setUpRecyclerView(photoList);
                    }

                });


    }

    private void setUpRecyclerView(List<String> photoList) {
        if (adRecyclerAdapter == null) {
            adRecyclerAdapter = new AdRecyclerAdapter(photoList);
            adViewPager.setAdapter(adRecyclerAdapter);
        } else {
            adRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().setTitle(getResources().getString(R.string.app_name));

    }
}
