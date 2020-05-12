package com.example.testapp999.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testapp999.R;
import com.example.testapp999.adapters.AdsRecyclerAdapter;
import com.example.testapp999.model.retrofit.Ads;
import com.example.testapp999.viewmodel.AdsViewModel;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {
    AdsViewModel adsViewModel;

    private List<Ads> adsList;

    private GridLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private AdsRecyclerAdapter recyclerViewAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        String vasea = "VVVVVV";
       TextView v = view.findViewById(R.id.test);
       v.setTextColor(getResources().getColor(R.color.colorPrimary));
       v.setText(vasea);

        recyclerView = view.findViewById(R.id.act_main_recycler);

        adsViewModel = new ViewModelProvider(getActivity()).get(AdsViewModel.class);
        adsViewModel.init(1, "ru", 20);
        adsViewModel.getListAds().observe(getViewLifecycleOwner(), (List<Ads> ads) -> {
            onDataAvailable(ads);
        });
        setUpRecyclerView();
        return view;
    }

    public void onDataAvailable(List<Ads> data) {
        List<Ads> temp;
        int x = 0;

        if (adsList == null)
            adsList = data;
        else {
            temp = new ArrayList<>();
            while (x < adsList.size())
                temp.add(adsList.get(x++));
            x = 0;
            while (x < data.size())
                temp.add(data.get(x++));
            adsList = temp;
        }

        recyclerViewAdapter.loadNewData(adsList);

//		recyclerViewAdapter.notifyDataSetChanged();
    }


    private void setUpRecyclerView() {
        if (recyclerViewAdapter == null) {
            recyclerViewAdapter = new AdsRecyclerAdapter();

            layoutManager = new GridLayoutManager(getActivity(), 3);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (recyclerViewAdapter.getItemViewType(position)) {
                        case 0:
                            return 1;
                        case 1:
                            return 3;
                        default:
                            return 1;
                    }
                }
            });

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(recyclerViewAdapter);
        }else {
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }
}
