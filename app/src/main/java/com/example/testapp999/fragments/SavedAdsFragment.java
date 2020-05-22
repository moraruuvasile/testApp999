package com.example.testapp999.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testapp999.R;
import com.example.testapp999.adapters.AdsRecyclerAdapter;
import com.example.testapp999.adapters.SavedAdsRecyclerAdapter;
import com.example.testapp999.model.retrofit.Ads;
import com.example.testapp999.viewmodel.AdsViewModel;

import java.util.List;


public class SavedAdsFragment extends Fragment {

    private AdsViewModel adsViewModel;
    private List<Ads> adsList;
    private GridLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private SavedAdsRecyclerAdapter recyclerViewAdapter;
    private int pageNr = 1;


    private SavedAdsRecyclerAdapter.OnItemClickListener adClickCallBack = new SavedAdsRecyclerAdapter.OnItemClickListener() {
        @Override
        public void onAdClick(Ads ads) {
            Navigation.findNavController(requireView())
                    .navigate(SavedAdsFragmentDirections.actionSavedAdsFragmentToAdPreviewFragment(ads));
        }
    };



    public SavedAdsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.act_main_recycler);

        adsViewModel = new ViewModelProvider(requireActivity()).get(AdsViewModel.class);
        adsViewModel.getAllAdsFromDB().observe(getViewLifecycleOwner(), (this::setUpRecyclerView));
        return view;
    }

    private void setUpRecyclerView(List<Ads> ads) {
        if (recyclerViewAdapter == null) {
            recyclerViewAdapter = new SavedAdsRecyclerAdapter(adClickCallBack, ads);

            layoutManager = new GridLayoutManager(getActivity(), 3);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(recyclerViewAdapter);

        } else {
            recyclerViewAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerViewAdapter = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
}
