package com.webpuzzle.ui.lazylist.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.webpuzzle.R;
import com.webpuzzle.ui.lazylist.util.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ComponentAdapter extends BaseAdapter {

	private Activity activity;
	private JSONArray data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public ComponentAdapter(Activity a, JSONArray array) {
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
		try {
			return data.get(position);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.item, null);
		
		JSONObject object = (JSONObject) getItem(position);

		if(object != null) {
			try {
				TextView text = (TextView) vi.findViewById(R.id.title);
				text.setText(object.getString("name"));
			
				text = (TextView) vi.findViewById(R.id.description);
				text.setText(object.getString("description"));
	
				ImageView image = (ImageView) vi.findViewById(R.id.image);
				imageLoader.DisplayImage(object.getString("imageLink"), image);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return vi;
	}
}