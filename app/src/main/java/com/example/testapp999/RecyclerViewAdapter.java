package com.example.testapp999;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter{

	private List<Ads> adsList;
	private LayoutInflater layoutInflater;


	public RecyclerViewAdapter( Context context) {
		layoutInflater = LayoutInflater.from(context);
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = layoutInflater.inflate(R.layout.recycler_grid, parent, false);
		return new ViewHolderOne(view);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		ViewHolderOne holder1 =  (ViewHolderOne)holder;
		Ads ads = adsList.get(position);

		holder1.title.setText(ads.getTitle());
		holder1.price.setText(ads.getPrice());

		Picasso.get().load(Ads.id160 + ads.getPhotoRaw())
				.into(holder1.thumbnail);

	}

	@Override
	public int getItemCount() {
		return (adsList != null) ? adsList.size() : 0;
	}

	void loadNewData(List<Ads> newAds) {
		adsList = newAds;
		notifyDataSetChanged();
	}

	class ViewHolderOne extends RecyclerView.ViewHolder {
		ImageView thumbnail = null;
		TextView title, price;

		public ViewHolderOne(View itemView) {
			super(itemView);
			this.thumbnail = itemView.findViewById(R.id.recycle_grid_image);
			this.price = itemView.findViewById(R.id.recycle_grid_price);
			this.title = itemView.findViewById(R.id.recycle_grid_title);
		}
	}
}
