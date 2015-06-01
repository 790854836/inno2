package com.topcoder.innovate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;

public class WebViewActivity extends Activity {

	private ImageView imageInfo;
	private ImageView imageHome;
	private WebView webView; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		
		imageInfo = (ImageView)findViewById(R.id.imageView13);
		imageHome = (ImageView)findViewById(R.id.imageView14);
		webView = (WebView)findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		String webUrl = (String)getResources().getText(R.string.web_baidu);
		webView.loadUrl(webUrl);
		webView.setScrollbarFadingEnabled(false);
		
		imageHome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(WebViewActivity.this, HomeActivity.class);
				startActivity(intent);
				finish();
			}
		});

	}
	
	

}
