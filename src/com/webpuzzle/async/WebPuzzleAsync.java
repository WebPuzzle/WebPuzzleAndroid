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
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.webpuzzle.R;
import com.webpuzzle.ui.lazylist.LazyAdapter;

public class WebPuzzleAsync extends AsyncTask<String, Integer, String> {
	private Activity mContext=null;

	public WebPuzzleAsync(Activity a) {
		this.mContext = a;
	}

	protected String doInBackground(String... urls) {
		StringBuilder builder = null;
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(urls[0]);

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

	protected void onPostExecute(String result) {
		try {
			LazyAdapter adapter = new LazyAdapter(mContext, new JSONArray(result));
			ListView list = (ListView) mContext.findViewById(R.id.listWc);
			System.out.println(list);
			list.setAdapter(adapter);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
