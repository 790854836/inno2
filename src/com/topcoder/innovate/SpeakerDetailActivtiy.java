package com.topcoder.innovate;

import com.topcoder.innovate.model.Speaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SpeakerDetailActivtiy extends Activity {

	private ImageView imageInfo;
	private ImageView imageHome;
	private ImageView speakImg;
	private TextView speakName;
	private TextView speakTitle;
	private TextView speakDetails;
	private Button retButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speakerdetail);

		imageInfo = (ImageView) findViewById(R.id.imageView13);
		imageHome = (ImageView) findViewById(R.id.imageView14);
		speakImg = (ImageView) findViewById(R.id.img);
		speakName = (TextView) findViewById(R.id.name);
		speakTitle = (TextView) findViewById(R.id.title);
		speakDetails = (TextView) findViewById(R.id.details);
		retButton = (Button) findViewById(R.id.button1);
		
		Speaker speaker = (Speaker)getIntent().getExtras().get("speaker");
		
		speakName.setText(speaker.getName());
		speakTitle.setText(speaker.getTitle());
		speakDetails.setText(speaker.getDetails());
		
		String pictureName = getPictureName(speaker.getPicture());
		//Log.i("ning", pictureName);
		int indentify = getResources().getIdentifier(pictureName, "drawable",
				getPackageName());
		speakImg.setImageResource(indentify);

		imageInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SpeakerDetailActivtiy.this,
						WebViewActivity.class);
				startActivity(intent);
				finish();

			}
		});

		imageHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SpeakerDetailActivtiy.this, HomeActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		retButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SpeakerDetailActivtiy.this, SpeakerListActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
	}
	
	private String getPictureName(String str) {
		// TODO Auto-generated method stub
		str.trim();
		String[] temp = str.split("/");
		String wholeName = temp[temp.length - 1];
		String ret = wholeName.split("[.]")[0];
		ret = ret.toLowerCase();
		ret.replace('-', '_');
		
		if (ret.charAt(0) >= '0' && ret.charAt(0) <= '9'){
			ret = "x" + ret;
		}
		
		return ret;
	}

}
