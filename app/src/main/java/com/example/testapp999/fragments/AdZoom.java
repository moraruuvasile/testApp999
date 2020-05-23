package com.example.testapp999.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.testapp999.R;
import com.example.testapp999.model.retrofit.AdObject;
import com.squareup.picasso.Picasso;

public class AdZoom extends Fragment {

    public AdZoom() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ad_preview_container, container, false);
        ImageView imageView = view.findViewById(R.id.ad_previuw_imageView);

        Picasso.get().load(AdObject.id900 + "AA3A0881-92A0-E466-927C-6EB589EC3694.jpg")
//         .fit()
//         .centerInside()
                .placeholder(R.drawable.placeholder1)
                .into(imageView);

        return view;
    }
}
