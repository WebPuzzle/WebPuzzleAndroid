package com.webpuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.webpuzzle.async.WebPuzzleAsync;
import com.webpuzzle.ui.lazylist.LazyAdapter;

public class MainActivity extends Activity {
	ListView list;
	LazyAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		new WebPuzzleAsync(this).execute("http://webpuzzlews.herokuapp.com");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
