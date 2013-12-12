package com.webpuzzle;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.webpuzzle.async.WebPuzzleAsync;
import com.webpuzzle.ui.lazylist.LazyAdapter;

public class MainActivity extends Activity implements OnItemClickListener {
	ListView list;
	LazyAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		list = (ListView) findViewById(R.id.listWc);
		list.setOnItemClickListener(this);
		new WebPuzzleAsync(this).execute("http://webpuzzlews.herokuapp.com");
		try {
			String result = (new WebPuzzleAsync(this).execute("http://webpuzzlews.herokuapp.com")).get();
			try {
				LazyAdapter adapter = new LazyAdapter(this, new JSONArray(result));
				ListView list = (ListView) this.findViewById(R.id.listWc);
				System.out.println(list);
				list.setAdapter(adapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int itemNumber, long arg3) {
		JSONObject webcomponent = (JSONObject)((LazyAdapter)adapterView.getAdapter()).getItem(itemNumber);
		String id = "";
		try {
			Log.i("tag", webcomponent.getString("id"));
			id = webcomponent.getString("id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		Intent intent = new Intent(this, DetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("id", id);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
