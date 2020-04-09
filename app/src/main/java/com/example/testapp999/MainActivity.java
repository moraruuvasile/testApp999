package com.example.testapp999;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity implements JsonData.OnDataAvailable, RecyclerViewAdapter.RecyclerViewClickInterface {
	private String feedUrl = "https://999.md/api/v3/categories/real-estate/apartments-and-rooms?page=%d&lang=ru&page_size=%d";
	private String page = "1";
	private String feedLimit = "100";
	private GridLayoutManager layoutManager;

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
		jsonData.execute(page, feedLimit);
	}

	@Override
	public void onDataAvailable(List<Ads> data, DownloadStatus status) {
		recyclerViewAdapter.loadNewData(data);
	}

	@Override
	public void onItemShortClick(int position) {

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
