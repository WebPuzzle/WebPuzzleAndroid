package com.webpuzzle;

import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import com.webpuzzle.async.WebPuzzleAsync;
import com.webpuzzle.ui.lazylist.ImageLoader;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity {

	ImageLoader imageLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_layout);
		Bundle bundle = getIntent().getExtras();
		String id = bundle.getString("id");
		Log.i("detail activity", id);
		String result = "";
		JSONObject data = new JSONObject();
		try {
			result = (new WebPuzzleAsync(this).execute("http://webpuzzlews.herokuapp.com/web_components/" + id + ".json")).get();
			Log.i("detail result", result);
			data = new JSONObject(result);
			TextView titleView = ((TextView) findViewById(R.id.textViewTitle));
			titleView.setText(data.getString("name"));
			TextView descriptionView = ((TextView) findViewById(R.id.textViewDescription));
			descriptionView.setText(data.getString("description"));
			ImageView image = (ImageView) findViewById(R.id.imageViewWCImage);
			imageLoader = new ImageLoader(this);
			imageLoader.DisplayImage(data.getString("imageLink"), image);
		} catch (Exception e) {e.printStackTrace();}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

}
