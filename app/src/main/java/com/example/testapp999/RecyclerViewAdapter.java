package com.example.testapp999;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter{

	private List<Ads> adsList;
	private LayoutInflater layoutInflater;
	RecyclerViewClickInterface recyclerViewClickInterface;

	interface RecyclerViewClickInterface{
			void onItemShortClick(int position);
	}

	public RecyclerViewAdapter(Context context) {
		layoutInflater = LayoutInflater.from(context);
		recyclerViewClickInterface = (RecyclerViewClickInterface)context;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

		View view = viewType == 1 ? layoutInflater.inflate(R.layout.recycler_linear, parent, false) :
				layoutInflater.inflate(R.layout.recycler_grid, parent, false);

		return new ViewHolderOne(view);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		ViewHolderOne holder1 =  (ViewHolderOne)holder;
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

			Picasso.get().load(Ads.id160 + ads.getPhotoRaw())
					.into(holder1.thumbnail);
//		}

	}

	@Override
	public int getItemCount() {
		return (adsList != null) ? adsList.size() : 0;
	}

	@Override
	public int getItemViewType(int position) {

		return position % 10 == 9 ? 1 : 0;
	}

	void loadNewData(List<Ads> newAds) {
		adsList = newAds;
		notifyDataSetChanged();
	}

	class ViewHolderOne extends RecyclerView.ViewHolder {
			ImageView thumbnail;
			TextView title, price;
			Button button;

			public ViewHolderOne(View itemView) {
				super(itemView);
				this.thumbnail = itemView.findViewById(R.id.recycle_image);
				this.price = itemView.findViewById(R.id.recycle_price);
				this.title = itemView.findViewById(R.id.recycle_title);
				this.button = itemView.findViewById(R.id.recycle_btnSave);
				itemView.setOnClickListener((v)->
						Toast.makeText(v.getContext(), "To be done with fragment", Toast.LENGTH_SHORT).show());
				this.button.setOnClickListener((v)->
						Toast.makeText(v.getContext(), "To be done with SQLite", Toast.LENGTH_SHORT).show());
				recyclerViewClickInterface.onItemShortClick(getAdapterPosition());
			}
	}

}
