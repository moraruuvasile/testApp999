package com.example.testapp999;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

enum DownloadStatus {IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK}



class GetRawData {
	private static final String TAG = "GetRawData";

	private DownloadStatus mDownloadStatus;
	private final OnDownloadComplete callBack;

	interface OnDownloadComplete {
		void onDownloadComplete(String data, DownloadStatus status);
	}

	public GetRawData(OnDownloadComplete callback) {
		this.mDownloadStatus = DownloadStatus.IDLE;
		callBack = callback;
	}

	void runInSameThread(String s) {
		if (callBack != null)
			callBack.onDownloadComplete(doInBackground(s), mDownloadStatus);
	}


	protected String doInBackground(String... strings) {
		HttpURLConnection connection = null;
		BufferedReader reader = null;

		if (strings == null) {
			mDownloadStatus = DownloadStatus.NOT_INITIALISED;
			return null;
		}

		try {
			mDownloadStatus = DownloadStatus.PROCESSING;
			URL url = new URL(strings[0]);

			connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			Log.d(TAG, "doInBackground: " + connection.getResponseCode());
			StringBuilder result = new StringBuilder();

			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String line;
			while (null != (line = reader.readLine())) {
				result.append(line).append("\n");
			}

			mDownloadStatus = DownloadStatus.OK;
			return result.toString();


		} catch (MalformedURLException e) {
			Log.e(TAG, "doInBackground: Invalid URL " + e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, "doInBackground: IO Exception reading data: " + e.getMessage());
		} catch (SecurityException e) {
			Log.e(TAG, "doInBackground: Security Exception. Needs permission? " + e.getMessage());
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					Log.e(TAG, "doInBackground: Error closing stream " + e.getMessage());
				}
			}
		}

		mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
		return null;
	}

}
