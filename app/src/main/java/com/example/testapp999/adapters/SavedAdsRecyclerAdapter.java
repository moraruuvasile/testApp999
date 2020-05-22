package com.example.testapp999.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp999.R;
import com.example.testapp999.model.retrofit.Ads;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SavedAdsRecyclerAdapter extends RecyclerView.Adapter<SavedAdsRecyclerAdapter.SavedAdsRecyclerViewHolder>{

    private List<Ads> adsList;
    private OnItemClickListener listener;



    public SavedAdsRecyclerAdapter(OnItemClickListener listener, List<Ads> adsList) {
        this.listener = listener;
        this.adsList = adsList;
    }


    @NonNull
    @Override
    public SavedAdsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SavedAdsRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAdsRecyclerViewHolder holder, int position) {
        Ads ads = adsList.get(position);

        holder.title.setText(ads.getTitle());
        holder.price.setText(String.valueOf(position));

        Picasso.get().load(Ads.id160 + ads.getImage())
                .placeholder(R.drawable.placeholder1)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {return (adsList != null) ? adsList.size() : 0;}


    public interface OnItemClickListener {
        void onAdClick(Ads ads);
    }

    class SavedAdsRecyclerViewHolder extends RecyclerView.ViewHolder
    {
        ImageView thumbnail;
        TextView title, price;
        Button button;


        public SavedAdsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            this.thumbnail = itemView.findViewById(R.id.recycle_image);
            this.price = itemView.findViewById(R.id.recycle_price);
            this.title = itemView.findViewById(R.id.recycle_title);
            this.button = itemView.findViewById(R.id.recycle_btnSave);
            this.button.setVisibility(View.GONE);

            itemView.setOnClickListener((v) ->
                    listener.onAdClick(adsList.get(getAdapterPosition())));
        }
    }
}
