package com.topcoder.innovate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.topcoder.innovate.R.drawable;
import com.topcoder.innovate.model.Speaker;
import com.topcoder.innovate.util.DataRetriever;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SpeakerListActivity extends Activity {

	private ImageView imageInfo;
	private ImageView imageHome;
	private ListView mListView;
	private List<Speaker> listSpeaker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speakerlist);
		
		imageInfo = (ImageView)findViewById(R.id.imageView13);
		imageHome = (ImageView)findViewById(R.id.imageView14);
		mListView = (ListView)findViewById(R.id.listView1);
		
		imageInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SpeakerListActivity.this, WebViewActivity.class);
				startActivity(intent);
				finish();

			}
		});
		
		imageHome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SpeakerListActivity.this, HomeActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		
		DataRetriever dataRetriver = new DataRetriever();
		
		Log.i("speaker list activity", "use dataretriever");
		listSpeaker = dataRetriver.retrieveAllSpeakers(this);
		Log.i("speaker list activity", "use dataretriever success");
		
		//Log.i("ning", "go to adapter");
		SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.speakerlist_raw,
				new String[] {"img", "name", "title"},
				new int[] {R.id.img, R.id.name, R.id.title});
		
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("speaker", listSpeaker.get(arg2));
				intent.setClass(SpeakerListActivity.this, SpeakerDetailActivtiy.class);
				startActivity(intent);
				finish();
				
			}
		});
	}
	
	private List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		//Log.i("ning", "list is empty? + " + list.isEmpty() + " status ");
		for (Iterator<Speaker> it=listSpeaker.iterator(); it.hasNext(); ){
			Map<String, Object> tmp = new HashMap<String, Object>();
			Speaker spk = it.next();
			//Log.i("ning", spk.getPicture());
			String pictureName = getPictureName(spk.getPicture());
			//Log.i("ning", pictureName);
			int indentify = getResources().getIdentifier(pictureName, "drawable",
					getPackageName());
			if (indentify > 0){
				tmp.put("img", indentify);
			}
			else {
				Log.e("ning", "img id not found");
			}
			
			tmp.put("name", spk.getName());
			tmp.put("title", spk.getTitle());
			list.add(tmp);
		}

		return list;
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
