package com.example.testapp999.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.simpals.map.md.MapMd;
import com.simpals.map.md.MapMdView;
import com.simpals.map.md.listener.OnMapMdReadyCallback;

import java.util.ArrayList;
import java.util.List;


public class AdPreviewFragment extends Fragment implements OnMapMdReadyCallback {
    private static final String TAG = "AdPreviewFragment";
    private static final String ID_ICON_APARTMENT = "apartment";
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
    private MapMdView mapView;
    private MapMd mapMd;
    private SymbolManager symbolManager;
    private AdObject.Value coordValues;


    public AdPreviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        MapMd.getInstanceMap(requireContext(), "99b67d4e-d0f8-4dda-afa4-7e909e4d5e49");

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
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);


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
                if (adObject.getAd().getLocation_coordinates() != null) {
                    coordValues = adObject.getAd().getLocation_coordinates().getValue();
                }
                adRecyclerAdapter = new AdRecyclerAdapter(photoList);
                adViewPager.setAdapter(adRecyclerAdapter);

                Log.d(TAG, "onViewCreated: " + observerCall);
                if (observerCall == 1 || observerCall % 2 == 0) {
                    setupOnboardingIndicators();
                }
                if (observerCall < 3) {
                    setCurrentOnboardIndicator(0);
                }

                adViewPager.setCurrentItem(adsViewModel.getViewPagerPosition() - 1, false);

                mapView.initMap(requireContext(), this);
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


        //

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
        adsViewModel.setViewPagerPosition(adViewPager.getCurrentItem() + 1);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapMd) {
        mapView.setLogoTopLeft();

        mapMd.getStyle(style -> {

            addApartmentImageToStyle(style);

            if (coordValues != null) {
                if (symbolManager == null) {
                    symbolManager = new SymbolManager(mapView, mapMd, style);
                }


                Symbol symbol = symbolManager.create(new SymbolOptions()
                        .withLatLng(new LatLng(coordValues.getLat(), coordValues.getLon()))
                        .withIconImage(ID_ICON_APARTMENT)
                        .withIconSize(1.5f));
            }

        });

    }


    private void addApartmentImageToStyle(Style style) {
        style.addImage(ID_ICON_APARTMENT,
                BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.ic_location_24dp)),
                true);
    }

}

