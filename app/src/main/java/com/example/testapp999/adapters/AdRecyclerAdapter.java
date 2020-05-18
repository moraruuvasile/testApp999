package com.example.testapp999.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp999.R;
import com.example.testapp999.model.retrofit.AdObject;
import com.example.testapp999.model.retrofit.Ads;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdRecyclerAdapter extends RecyclerView.Adapter<AdRecyclerAdapter.AdRecyclerViewHolder> {
    private List<String> photoList;

    public AdRecyclerAdapter(List<String> photoList) {
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public AdRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_preview_container, parent, false);
        return new AdRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdRecyclerViewHolder holder, int position) {
        Picasso.get().load(AdObject.id900 + photoList.get(position))
                .placeholder(R.drawable.placeholder1)
                .into(holder.imageView);
        holder.textView.setText("vasea");
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    class AdRecyclerViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textView;
        private ImageView imageView;

        public AdRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.ad_previuw_textView);
            imageView = itemView.findViewById(R.id.ad_previuw_imageView);
        }
    }




}
