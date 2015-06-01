package com.topcoder.innovate.util;

import java.io.IOException;
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
import com.topcoder.innovate.model.PosMsg;
import com.topcoder.innovate.model.Speaker;

import android.app.Activity;

public class PositionRetriever {
	public List<PosMsg> retrieveAllPos(Activity activity) {
		List<PosMsg> posArrayList = new ArrayList<PosMsg>();
		
		String url = activity.getResources().getString(R.string.web_position);
        HttpClient httpClient=new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        
		try {

			HttpResponse response = httpClient.execute(get);
			HttpEntity httpEntity = response.getEntity();
			String retStr = EntityUtils.toString(httpEntity);

			JSONArray jsonArray = new JSONArray(retStr);
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObj = jsonArray.getJSONObject(i);

				JSONObject jsonObjField = jsonObj.getJSONObject("fields");
				
				PosMsg msg = new PosMsg();
				msg.setName(jsonObjField.getString("name"));
				msg.setAddress(jsonObjField.getString("address"));
				msg.setLatitude(jsonObjField.getDouble("latitude"));
				msg.setLongitude(jsonObjField.getDouble("longitude"));

				posArrayList.add(msg);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return posArrayList;
	}

}
