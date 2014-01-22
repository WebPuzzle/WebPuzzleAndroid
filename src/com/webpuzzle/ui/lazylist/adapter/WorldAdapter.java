package com.webpuzzle.ui.lazylist.adapter;

import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.webpuzzle.R;
import com.webpuzzle.ui.lazylist.util.ImageLoader;

public class WorldAdapter extends BaseAdapter {

	private Activity activity;
	private List<String> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public WorldAdapter(Activity a, List<String> collection) throws JSONException {
		activity = a;
		data = collection;
		
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.item_world_layout, null);

		String name = data.get(position);



				TextView text = (TextView) vi.findViewById(R.id.title);
				text.setText(name);

				//text = (TextView) vi.findViewById(R.id.description);
				//text.setText(object.getString("description"));

				//ImageView image = (ImageView) vi.findViewById(R.id.image);
				//imageLoader.DisplayImage(object.getString("imageLink"), image);

		
		return vi;
	}
}