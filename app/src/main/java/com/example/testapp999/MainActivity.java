package com.example.testapp999;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements JsonData.OnDataAvailable, RecyclerViewAdapter.RecyclerViewClickInterface {
	private String feedUrl = "https://999.md/api/v3/categories/real-estate/apartments-and-rooms?page=%d&lang=ru&page_size=%d";
	private String page = "1";
	private int i=1;
	private String feedLimit = "100";
	private GridLayoutManager layoutManager;
	private List<Ads> adsList;

	private RecyclerView recyclerView;
	private RecyclerViewAdapter recyclerViewAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpRecyclerView();


	}

	@Override
	protected void onResume() {
		super.onResume();

		JsonData jsonData = new JsonData(this, feedUrl);
		jsonData.execute(String.valueOf(page), feedLimit);
	}

	@Override
	public void onDataAvailable(List<Ads> data, DownloadStatus status) {
		List<Ads> temp;
		int x = 0;

		if(adsList == null)
			adsList =data;
		else{
			temp = new ArrayList<>();
			while(x < adsList.size())
				temp.add(adsList.get(x++));
			x=0;
			while(x < data.size())
				temp.add(data.get(x++));
			adsList = temp;
		}

		recyclerViewAdapter.loadNewData(adsList);

//		recyclerViewAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemShortClick(int position) {
		Snackbar.make(recyclerView, "Saved", Snackbar.LENGTH_SHORT)
				.setAction("UNDO", new View.OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				}).show();
	}

	@Override
	public void loadNextPage() {
		i++;
		JsonData jsonData = new JsonData(this, feedUrl);
		jsonData.execute(String.valueOf(i), feedLimit);
	}

	private void setUpRecyclerView(){
		recyclerView = findViewById(R.id.act_main_recycler);
		recyclerViewAdapter = new RecyclerViewAdapter(this);

		layoutManager = new GridLayoutManager(this, 3);
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
	}
}
