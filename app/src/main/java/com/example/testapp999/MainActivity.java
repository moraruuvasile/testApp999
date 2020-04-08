package com.example.testapp999;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity implements JsonData.OnDataAvailable {
	private String feedUrl = "https://999.md/api/v3/categories/real-estate/apartments-and-rooms?page=%d&lang=ru&page_size=%d";
	private String page = "1";
	private String feedLimit = "40";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onResume() {
		super.onResume();
		JsonData jsonData = new JsonData(this, feedUrl);
		jsonData.execute(page, feedLimit);
	}

	@Override
	public void onDataAvailable(List<Ads> data, DownloadStatus status) {
	int i = 0;
	}
}
