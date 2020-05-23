package com.example.testapp999.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.testapp999.R;
import com.example.testapp999.model.retrofit.AdObject;
import com.squareup.picasso.Picasso;

public class AdZoomFragment extends Fragment implements View.OnClickListener {
    ImageView imageView;
    private LinearLayout layoutOnboardingIndicators;
    private Button btn1, btn2;
    private View view;
    private int rotate;

    public AdZoomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.ad_preview_container, container, false);

        imageView = view.findViewById(R.id.ad_previuw_imageView);
        layoutOnboardingIndicators = view.findViewById(R.id.liniar_layout);

        setBtns();

        Picasso.get().load(AdObject.id900 + AdZoomFragmentArgs.fromBundle(requireArguments()).getUri())
//         .fit()
//         .centerInside()
                .placeholder(R.drawable.placeholder1)
                .into(imageView);


        return view;
    }

    private void setBtns() {

        btn1 = new Button(requireContext());
        btn1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        btn1.setText("-90degree");
        btn1.setId(R.id.btn1);


        int dimensions = getResources().getDimensionPixelSize(R.dimen.fragment_ad_zoom_view_dimen);
        view = new View(requireContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dimensions, dimensions);
        lp.weight = 1;
        view.setLayoutParams(lp);

        btn2 = new Button(requireContext());
        btn2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        btn2.setText("+90degree");
        btn2.setId(R.id.btn2);

        btn1.setOnClickListener(this::onClick);
        btn2.setOnClickListener(this::onClick);

        layoutOnboardingIndicators.addView(btn1);
        layoutOnboardingIndicators.addView(view);
        layoutOnboardingIndicators.addView(btn2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                rotate -= 90f;
                break;

            case R.id.btn2:
                rotate += 90f;
                break;

            default:
                break;
        }
        Picasso.get().load(AdObject.id900 + AdZoomFragmentArgs.fromBundle(requireArguments()).getUri())
                .rotate(rotate)
                .placeholder(R.drawable.placeholder1)
                .into(imageView);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }
}
