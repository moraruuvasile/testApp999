package com.example.testapp999.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testapp999.R;
import com.example.testapp999.model.retrofit.AdObject;
import com.example.testapp999.model.retrofit.Ads;
import com.example.testapp999.viewmodel.AdsViewModel;

import java.util.ArrayList;
import java.util.List;


public class AdPreviewFragment extends Fragment {
    private AdPreviewFragmentArgs args;
    private Ads ads;
    private AdsViewModel adsViewModel;
    private List<String> photoList;

    public AdPreviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        requireActivity().setTitle("Preview");

        args = AdPreviewFragmentArgs.fromBundle(requireArguments());
        ads = args.getAds();

        adsViewModel = new ViewModelProvider(requireActivity()).get(AdsViewModel.class);
        photoList = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_ad_preview, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adsViewModel.getListAd(Integer.parseInt(ads.get_id()))
                    .observe(getViewLifecycleOwner(), (AdObject adObject) -> {

                        for(AdObject.ObjPhoto photo: adObject.getAd().getExtended_images()){
                            photoList.add(photo.getFilename());
                        }

        });


    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().setTitle(getResources().getString(R.string.app_name));

    }
}
