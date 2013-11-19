package com.webpuzzle.ui.lazylist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.webpuzzle.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	private JSONArray data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public LazyAdapter(Activity a, JSONArray array) {
		activity = a;
		data = array;

		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.length();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.item, null);

		TextView text = (TextView) vi.findViewById(R.id.title);
		try {
			text.setText(((JSONObject)data.get(position)).getString("name"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		text = (TextView) vi.findViewById(R.id.description);
		try {
			text.setText(((JSONObject)data.get(position)).getString("description"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ImageView image = (ImageView) vi.findViewById(R.id.image);
		try {
			imageLoader.DisplayImage(((JSONObject)data.get(position)).getString("imageLink"), image);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vi;
	}
}