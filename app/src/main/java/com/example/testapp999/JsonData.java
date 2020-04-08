package com.example.testapp999;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class JsonData extends AsyncTask<String, Void, List<Ads>> implements GetRawData.OnDownloadComplete {

	private String baseUrl;
	private List<Ads> adsList = null;
	private final OnDataAvailable mCallBack;

	interface OnDataAvailable {
		void onDataAvailable(List<Ads> data, DownloadStatus status);
	}

	public JsonData(OnDataAvailable callBack, String baseURL) {
		mCallBack = callBack;
		this.baseUrl = baseURL;
	}

	@Override
	protected void onPostExecute(List<Ads> photos) {


		if (mCallBack != null) {
			mCallBack.onDataAvailable(adsList, DownloadStatus.OK);
		}
	}

	@Override
	protected List<Ads> doInBackground(String... params) {
		int page = Integer.valueOf(params[0]);
		int feedLimit = Integer.valueOf(params[1]);

		GetRawData getRawData = new GetRawData(this);
		getRawData.runInSameThread(String.format(baseUrl, page, feedLimit));
		return adsList;
	}


	@Override
	public void onDownloadComplete(String data, DownloadStatus status) {

		if(status == DownloadStatus.OK) {
			adsList = new ArrayList<>();

			try {
				JSONObject jsonData = new JSONObject(data);
				JSONArray itemsArray = jsonData.getJSONArray("ads");

				for(int i=0; i<itemsArray.length(); i++) {
					JSONObject jsonAd = itemsArray.getJSONObject(i);
					String title = jsonAd.getString("title");
					String price = jsonAd.getString("price");
					String image = jsonAd.getString("image");


					Ads adsObject = new Ads(title, price, image);
					adsList.add(adsObject);

				}
			} catch(JSONException jsone) {
				jsone.printStackTrace();
				status = DownloadStatus.FAILED_OR_EMPTY;
			}
		}


	}
}