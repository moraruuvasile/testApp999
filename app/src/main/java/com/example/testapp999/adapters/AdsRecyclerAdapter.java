package com.example.testapp999.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp999.R;
import com.example.testapp999.model.retrofit.Ads;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdsRecyclerAdapter extends RecyclerView.Adapter {

    private List<Ads> adsList;
    private OnItemClickListener listener;
    private Boolean first99 = false, first199 = false, first299 = false;


    public AdsRecyclerAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = viewType == 1 ? layoutInflater.inflate(R.layout.recycler_linear, parent, false) :
                layoutInflater.inflate(R.layout.recycler_grid, parent, false);

        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderOne holder1 = (ViewHolderOne) holder;
        System.out.println(holder1.toString());
        Ads ads = adsList.get(position);
//		if(position == 0) {
//			holder1.itemView.setVisibility(View.GONE);
//			holder1.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
//		}else {
//			holder1.itemView.setVisibility(View.VISIBLE);
//			holder1.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        holder1.title.setText(ads.getTitle());
        holder1.price.setText(String.valueOf(position));

        Picasso.get().load(Ads.id160 + ads.getImage())
                .placeholder(R.drawable.placeholder1)
                .into(holder1.thumbnail);
//		}

        if (position == 99 && !first99) {
            first99 = true;
            listener.loadNextPage();
        }

        if (position == 199 && !first199) {
            first199 = true;
            listener.loadNextPage();
        }

        if (position == 299 && !first299) {
            first299 = true;
            listener.loadNextPage();
        }
    }

    @Override
    public int getItemCount() {
        return (adsList != null) ? adsList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 10 == 9 ? 1 : 0;
    }

    public void loadNewData(List<Ads> newAds) {
        adsList = newAds;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onAdClick(Ads ads);

        void onSaveBtnClick(Ads ads);

        void loadNextPage();
    }

  class ViewHolderOne extends RecyclerView.ViewHolder {
        int i = 0; // logic to inform if Saved button was pressed more than 2 times
        ImageView thumbnail;
        TextView title, price;
        Button button;


        ViewHolderOne(View itemView) {
            super(itemView);

            this.thumbnail = itemView.findViewById(R.id.recycle_image);
            this.price = itemView.findViewById(R.id.recycle_price);
            this.title = itemView.findViewById(R.id.recycle_title);
            this.button = itemView.findViewById(R.id.recycle_btnSave);

            itemView.setOnClickListener((v) ->
                    listener.onAdClick(adsList.get(getAdapterPosition())));

            this.button.setOnClickListener((v) -> {
                if (i++ > 0) {
                    Toast.makeText(v.getContext(), "Was saved succeseful", Toast.LENGTH_SHORT).show();
                }
                listener.onSaveBtnClick(adsList.get(getAdapterPosition()));
            });
        }
    }

}
