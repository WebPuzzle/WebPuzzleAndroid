package com.webpuzzle.async;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.webpuzzle.R;

public class WebPuzzleAsync extends AsyncTask<String, Integer, String> {
	private Activity mContext=null;

	public WebPuzzleAsync(Activity a) {
		this.mContext = a;
	}

	protected String doInBackground(String... params) {
		StringBuilder builder = null;
		HttpClient client = new DefaultHttpClient();
		
		String action = params[0];
		String url = "";
		
		if("listing".equals(action)){
			String world = params[1];
			url = this.mContext.getResources().getString(R.string.services_endpoint) + String.format(this.mContext.getResources().getString(R.string.services_context_components_belongsto_aworld), world) + ".json";
		}
		
		HttpGet httpGet = new HttpGet(url);
		
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				builder = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e("network error", "Could not retreive data");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	protected void onProgressUpdate(Integer... progress) {

	}
}
