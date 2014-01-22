package com.webpuzzle.ui.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.webpuzzle.R;
import com.webpuzzle.async.WebPuzzleAsync;
import com.webpuzzle.ui.lazylist.adapter.ComponentAdapter;

public class ListWCActivity extends Activity implements OnItemClickListener {
	ListView list;
	ComponentAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Bundle bundle = getIntent().getExtras();
		String worldSelected = bundle.getString("world");
		
		list = (ListView) findViewById(R.id.listWc);
		list.setOnItemClickListener(this);

		try {
			String result = (new WebPuzzleAsync(this).execute("listing",worldSelected)).get();
			try {
				this.adapter = new ComponentAdapter(this, new JSONArray(result));
				ListView list = (ListView) this.findViewById(R.id.listWc);
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
		JSONObject webcomponent = (JSONObject)this.adapter.getItem(itemNumber);
		
		Intent intent = new Intent(this, DetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("webcomponent", webcomponent.toString());
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
