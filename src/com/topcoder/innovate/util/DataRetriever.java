package com.topcoder.innovate.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.topcoder.innovate.R;
import com.topcoder.innovate.model.Speaker;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

public class DataRetriever {
	public List<Speaker> retrieveAllSpeakers(Activity activity) {
    	List<Speaker> speakerArrayList = new ArrayList<Speaker>();
    	
    	byte buffer[] = new byte[1024];
		String retStr = new String();
		StringBuilder stb = new StringBuilder();
		try {
			InputStream input = activity.getApplication().openFileInput("speakers.txt");
			StringBuffer out = new StringBuffer(); 
			int len = 0;

			while ((len = input.read(buffer)) != -1) {
				out.append(new String(buffer, 0, len));
				
			}
			
			retStr = out.toString();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Log.i("load speaker", "have read " + retStr);
    	
    	/*String url = activity.getResources().getString(R.string.feeds_speakers);
        HttpClient httpClient=new DefaultHttpClient();
        HttpGet get = new HttpGet(url);*/
        
		try {

			/*HttpResponse response = httpClient.execute(get);
			HttpEntity httpEntity = response.getEntity();
			String retStr = EntityUtils.toString(httpEntity);*/

			//Log.i("ning", retStr);
			
			JSONArray jsonArray = new JSONArray(retStr);
			
			//Log.i("ning", "jsonarray finished and lenth = " + jsonArray.length());

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				
				//Log.i("ning", i + "th element");
				
				JSONObject jsonObjField = jsonObj.getJSONObject("fields");
				
				//Log.i("ning", i + "th fields element");

				Speaker speaker = new Speaker();
				speaker.setName(jsonObjField.getString("name"));
				
				//Log.i("ning", jsonObjField.getString("name"));
				
				speaker.setPicture(jsonObjField.getString("picture"));
				speaker.setDetails(jsonObjField.getString("details"));
				speaker.setTitle(jsonObjField.getString("title"));

				List<String> sessions = new ArrayList<String>();
				JSONArray jsonArraySessions = jsonObjField.getJSONArray("sessions");
				//Log.i("ning", "jsonArraySessions have length" + jsonArraySessions.length());
				for (int j = 0; j < jsonArraySessions.length(); j++) {
					sessions.add(jsonArraySessions.getString(j));
				}

				speaker.setSessionIds(sessions);
				speakerArrayList.add(speaker);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

        return speakerArrayList;
    }

	// check the Internet connection
	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
}