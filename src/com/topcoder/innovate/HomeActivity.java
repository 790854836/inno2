package com.topcoder.innovate;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class HomeActivity extends Activity {

	private ImageView imageInfo;
	private ImageView imageHome;
	private ImageView imageSpeaker;
	private ImageView imageMap;
	private MyHandler handler;
	private ProgressDialog progressDialog = null;
	private int size;
	private int readLen;
	private int hasReadLen;
	private byte buffer[] = new byte[1024];
	private String readStr;
	private boolean flag;
	private AssetManager assetManager;
	private static boolean hasDownSpeaker = false;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        imageInfo = (ImageView)findViewById(R.id.imageView13);
		imageHome = (ImageView)findViewById(R.id.imageView14);
		imageSpeaker = (ImageView)findViewById(R.id.imageView9);
		imageMap = (ImageView)findViewById(R.id.imageView7);
		handler = new MyHandler();
		
		imageInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(HomeActivity.this, WebViewActivity.class);
				startActivity(intent);
				HomeActivity.this.finish();
				
			}
		});
		
		imageSpeaker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(HomeActivity.this, SpeakerListActivity.class);
				startActivity(intent);
				HomeActivity.this.finish();
			}
		});
		
		imageMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(HomeActivity.this, MapViewActivity.class);
				startActivity(intent);
				HomeActivity.this.finish();
				
			}
		});
		
		if (!hasDownSpeaker) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setTitle("Loading ... ");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setIndeterminate(false);
			progressDialog.show();

			String urlStr = getResources().getString(R.string.feeds_speakers);
			downloadFile(urlStr, "speakers.txt");
			hasDownSpeaker = true;
		}
		
		
    }


	public boolean downloadFile(final String urlStr, final String filename){
    	flag = true;
    	
    	new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				size = 1;
				readLen = hasReadLen = 0;
				readStr = new String();
				
				try {

					URL url = new URL(urlStr);
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					size = connection.getContentLength();
					InputStream input = connection.getInputStream();
					Log.i("input", "input setream success");
					OutputStream outPutStream = getApplication().openFileOutput(
							filename, getApplicationContext().MODE_APPEND);

					while ((readLen = input.read(buffer)) != -1) {
						//readStr += buffer.toString();
						outPutStream.write(buffer);
						hasReadLen += readLen;
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);
						Thread.sleep(100);
						//for (int i=0; i<100000000; i++);
						Log.i("download", String.valueOf(hasReadLen));
					}

					input.close();
				} catch (Exception e) {
					flag = false;
					e.printStackTrace();
				}
			}

    	}).start();
    	
    	return flag;
    }
    
    class MyHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == 1){
				int progress = hasReadLen * 100 / size;
				progressDialog.setProgress(progress);
				Log.i("handler", "setProgress" + progress);
				if (hasReadLen == size){
					progressDialog.dismiss();
				}
			}
			
			super.handleMessage(msg);
		}
    	
    }
}


