package com.example.testapp999.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testapp999.R;
import com.example.testapp999.adapters.AdRecyclerAdapter;
import com.example.testapp999.model.retrofit.AdObject;
import com.example.testapp999.model.retrofit.Ads;
import com.example.testapp999.viewmodel.AdsViewModel;

import java.util.ArrayList;
import java.util.List;


public class AdPreviewFragment extends Fragment {
    private static final String TAG = "AdPreviewFragment";
    private static int FIRST_RUN = 0;
    private int observerCall = 0;
    private Ads ads;
    private AdsViewModel adsViewModel;
    private AdRecyclerAdapter adRecyclerAdapter;
    private ViewPager2 adViewPager;
    private LinearLayout layoutOnboardingIndicators;
    private TextView pageNr;
    private TextView title;
    private TextView price;


    public AdPreviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        AdPreviewFragmentArgs args = AdPreviewFragmentArgs.fromBundle(requireArguments());
        ads = args.getAds();

        adsViewModel = new ViewModelProvider(this).get(AdsViewModel.class);
        adsViewModel.initAd(Integer.parseInt(ads.get_id()));

        return inflater.inflate(R.layout.fragment_ad_preview, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        adViewPager = view.findViewById(R.id.adViewPager2);
        layoutOnboardingIndicators = view.findViewById(R.id.layoutOfIndicator);
        pageNr = view.findViewById(R.id.ad_preview_page_nr);
        title = view.findViewById(R.id.ad_preview_title);
        price = view.findViewById(R.id.ad_preview_price);

        title.setText(ads.getTitle());
        price.setText(String.valueOf(ads.getPrice()) + ads.getCurrency());

        adsViewModel.getListAd().observe(getViewLifecycleOwner(), (AdObject adObject) -> {
            FIRST_RUN++;
            observerCall++;
            if (FIRST_RUN == 1 || observerCall > 1) {
                List<String> photoList = new ArrayList<>();

                for (AdObject.ObjPhoto photo : adObject.getAd().getExtended_images()) {
                    photoList.add(photo.getFilename());
                }

                adRecyclerAdapter = new AdRecyclerAdapter(photoList);
                adViewPager.setAdapter(adRecyclerAdapter);

                Log.d(TAG, "onViewCreated: " + observerCall);
                if (observerCall==1 || observerCall%2 == 0) {
                    setupOnboardingIndicators();
                }
                if (observerCall < 3) {
                    setCurrentOnboardIndicator(0);
                }

                adViewPager.setCurrentItem(adsViewModel.getViewPagerPosition()-1);
            }
        });


        adViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardIndicator(position);
                pageNr.setText(String.valueOf(position + 1));
            }
        });
    }


    private void setupOnboardingIndicators() {
        ImageView[] indicators = new ImageView[adRecyclerAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(7, 0, 7, 0);
        layoutParams.gravity = Gravity.CENTER;
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(requireContext());
            indicators[i].setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), R.drawable.indicator_inactive)
            );
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }

    private void setCurrentOnboardIndicator(int index) {
        int childCount = layoutOnboardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), R.drawable.indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), R.drawable.indicator_inactive)
                );
            }
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        adsViewModel.setViewPagerPosition(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adsViewModel.setViewPagerPosition(adViewPager.getCurrentItem()+1);
    }
}

