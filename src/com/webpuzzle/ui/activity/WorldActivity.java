package com.webpuzzle.ui.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.webpuzzle.R;
import com.webpuzzle.ui.lazylist.adapter.WorldAdapter;

public class WorldActivity extends Activity implements OnItemClickListener {
	ListView list;
	WorldAdapter adapter;
	List<String> worlds;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.worlds);
		
		worlds = new ArrayList<String>() {
			private static final long serialVersionUID = -6264569531467215972L;
			{
				add("AngularJsWc");
				add("AngularDartWc");
				add("JqueryWc");
				add("EmberWc");
				add("PolymerJsWc");
				add("PolymerDartWc");
			}
		};

		list = (ListView) findViewById(R.id.listWorlds);
		list.setOnItemClickListener(this);

		try {
			try {
				adapter = new WorldAdapter(this, worlds);
				ListView list = (ListView) this.findViewById(R.id.listWorlds);
				System.out.println(list);
				list.setAdapter(adapter);
			} catch (JSONException e) {
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
		String worldSelected = this.adapter.getItem(itemNumber).toString();
		
		Intent intent = new Intent(this, ListWCActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("world", worldSelected);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
