package com.example.testapp999.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private int pageNr = 1;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.act_main_recycler);

        adsViewModel = new ViewModelProvider(requireActivity()).get(AdsViewModel.class);
        adsViewModel.getListAds(1, "ru", 100).observe(getViewLifecycleOwner(), this::onDataAvailable);

        setUpRecyclerView();

        return view;
    }

    public void onDataAvailable(List<Ads> data) {
        List<Ads> temp = new ArrayList<>();;
        int x = 0;

        if (adsList == null)
            adsList = data;
        else {
            while (x < adsList.size())
                temp.add(adsList.get(x++));
            x = 0;
            while (x < data.size())
                temp.add(data.get(x++));
            adsList = temp;
        }
        recyclerViewAdapter.loadNewData(adsList);
    }


    private void setUpRecyclerView() {
        if (recyclerViewAdapter == null) {
            recyclerViewAdapter = new AdsRecyclerAdapter(adClickCallBack);

            layoutManager = new GridLayoutManager(getActivity(), 3);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (recyclerViewAdapter.getItemViewType(position)) {
                        case 1:
                            return 3;
                        default:
                            return 1;
                    }
                }
            });

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(recyclerViewAdapter);

       } else {
            recyclerViewAdapter.notifyDataSetChanged();
       }

    }

    private AdsRecyclerAdapter.OnItemClickListener adClickCallBack = new AdsRecyclerAdapter.OnItemClickListener() {
        @Override
        public void onAdClick(Ads ads) {
            Navigation.findNavController(requireView())
                    .navigate(MainFragmentDirections.actionMainFragmentToAdPreviewFragment(ads));
        }

        @Override
        public void onSaveBtnClick(Ads ads) {
            adsViewModel.insert(ads);
        }

        @Override
        public void loadNextPage() {
            pageNr++;
            adsViewModel.getListAds(pageNr, "ru", 100);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerViewAdapter =null;
    }


}
