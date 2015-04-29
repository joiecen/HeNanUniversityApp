package com.example.henanuniversityapp;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ych.http.AsyncHttpClient;
import com.ych.http.JsonHttpResponseHandler;
import com.ych.http.PersistentCookieStore;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings.Global;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private AsyncHttpClient asyncHttpClient;
	private Button getdatabutton;
	private TextView datafromnet;
	private String text = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		asyncHttpClient = new AsyncHttpClient();
		PersistentCookieStore persistentCookieStore = new PersistentCookieStore(this);
		asyncHttpClient.setCookieStore(persistentCookieStore);
		
		getdatabutton = (Button)findViewById(R.id.getdatabutton);
		getdatabutton.setOnClickListener(onClickListener);
		datafromnet = (TextView)findViewById(R.id.datafromnet);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			asyncHttpClient.post("http://121.40.61.76:8080/parkManagementSystem/park/search/", jsonHttpResponseHandler);
		}
	};
	
	private JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler("utf-8"){
		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
			System.out.println(statusCode);
			for(int i=0; i<response.length(); i++){
				try {
					JSONObject jsonObject = response.getJSONObject(0);
					String address = jsonObject.getString("address");
					System.out.println(address);
					text += address+"/";
					datafromnet.setText(text);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			super.onSuccess(statusCode, headers, response);
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
			super.onFailure(statusCode, headers, responseString, throwable);
		}
	};
		
}
