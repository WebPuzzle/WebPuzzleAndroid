package com.webpuzzle.ui.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.webpuzzle.R;
import com.webpuzzle.ui.lazylist.util.ImageLoader;

public class DetailActivity extends Activity implements OnClickListener{

	ImageLoader imageLoader;
	DownloadManager dm;
	Button downloadButton;
	private JSONObject webcomponent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_layout);
		Bundle bundle = getIntent().getExtras();
		String webcomponentString = bundle.getString("webcomponent");
		try {
			webcomponent = new JSONObject(webcomponentString);

			TextView titleView = ((TextView) findViewById(R.id.textViewTitle));
			titleView.setText(webcomponent.getString("name"));

			TextView descriptionView = ((TextView) findViewById(R.id.textViewDescription));
			descriptionView.setText(webcomponent.getString("description"));

			ImageView image = (ImageView) findViewById(R.id.imageViewWCImage);
			imageLoader = new ImageLoader(this);
			imageLoader.DisplayImage(webcomponent.getString("imageLink"), image);

			WebView webView = (WebView) findViewById(R.id.webView);
			webView.getSettings().setJavaScriptEnabled(true);

			if(webcomponent.get("demoLink").toString().matches(".*codepen.*")){
				Pattern pattern = Pattern.compile(".*codepen\\.io/(.*)\\/pen\\/(.*)");
				Matcher matcher = pattern.matcher(webcomponent.get("demoLink").toString());
				String user = "";
				String slug_hash = "";
				if(matcher.matches()) {
					user=matcher.group(1);
					slug_hash=matcher.group(2);
				}
				webView.loadUrl(String.format("http://codepen.io/%s/embed/%s",user, slug_hash));
			}
			else{
				webView.loadData(getDemoHTML(webcomponent.get("demoLink").toString()), "text/html", "UTF-8");
			}

		} catch (Exception e) {e.printStackTrace();}

		downloadButton = (Button) findViewById(R.id.ButtonLayout);
		downloadButton.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.ButtonLayout:
			String url;
			try {
				url = getResources().getString(R.string.github_endpoint)+"/"+webcomponent.getString("githubLink")+getResources().getString(R.string.github_repo_archive);
				DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
				request.setDescription("Archive of "+webcomponent.getString("name")+ " component");
				request.setTitle(webcomponent.getString("name")+" download");

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					request.allowScanningByMediaScanner();
					request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				}
				request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, webcomponent.getString("name")+".zip");

				DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
				manager.enqueue(request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

			break;
		}
	}	

	private String getDemoHTML(String url){
		String html="";
		if(url.matches(".*jsfiddle.*")){
			url += "embedded/result,js,html,css/";

			html = "<iframe width=\"100%\" height=\"100%\" src=\""+ url +"\" allowfullscreen=\"allowfullscreen\" frameborder=\"0\"></iframe>";
		}
		else if(url.matches(".*codepen.*")){
			String slug_hash = "";
			String user = "";
			Pattern pattern = Pattern.compile(".*codepen\\.io/(.*)\\/pen\\/(.*)");
			Matcher matcher = pattern.matcher(url);
			if(matcher.matches()) {
				user=matcher.group(1);
				slug_hash=matcher.group(2);
			}

			html = "<html><body><p data-height=\"268\" data-theme-id=\"0\" data-slug-hash=\""+ slug_hash + "\" data-user=\""+user+"\" data-default-tab=\"result\" class=\"codepen\"></p>";
			html += "<script async src=\"//codepen.io/assets/embed/ei.js\"></script></body></html>";
		}
		return html;
	}
}