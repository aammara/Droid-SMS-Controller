package com.dsc.rnu;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tn.idevelop.rnu.trial.R;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class update extends SherlockActivity {
	    /** Called when the activity is first created. */
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.update);
	        
	        com.actionbarsherlock.app.ActionBar mActionBar = getSupportActionBar();
	        mActionBar.setHomeButtonEnabled(true);
	        mActionBar.setIcon(R.drawable.home);
	        mActionBar.setTitle("");
	        
	            	JSON jsn = new JSON();
	            	ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	            		   try{
	            			   JSONObject json = jsn.getJSONfromURL(getString(R.string.URL)+"updates.php");
	            		JSONArray  updates = json.getJSONArray("updates");
	            	        for(int i=0;i < updates.length();i++){						

	            	        	HashMap<String, String> map = new HashMap<String, String>();
	            	        	JSONObject e = updates.getJSONObject(i);
	            	        	
	            	        	map.put("title",  e.getString("title"));
	            	        	map.put("com",  e.getString("com"));
	            	        	map.put("link",e.getString("link"));
	            	        	map.put("date", e.getString("date"));
	            	        	map.put("icon", String.valueOf(R.drawable.icon));
	            	        	mylist.add(map);
	            		}
	            	       }catch(JSONException e)        {
	            	    	   TextView txt = (TextView) findViewById(R.id.updatesNotFound);
	   	            		txt.setText(getString(R.string.updateNotFound));
	            	       }
	            	       final ListView list = (ListView)findViewById(R.id.updates);
	            	       SimpleAdapter mSchedule = new SimpleAdapter (getBaseContext(), mylist, R.layout.updatesrow,
	            	               new String[] {"title", "com", "link", "date", "icon"}, new int[] { R.id.title, R.id.com, R.id.link, R.id.date, R.id.updateIcon });
	            	        list.setAdapter(mSchedule);
	            	        list.setOnItemClickListener(new OnItemClickListener() {
	            	        	 //  @SuppressWarnings("unchecked")
	            	        	          public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	            	        	       @SuppressWarnings("unchecked")
									HashMap<String, String> map = (HashMap<String, String>) list.getItemAtPosition(position);
	            	        	       Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(map.get("link")));
	            	        	       startActivity(browserIntent);            	        	   
	            	        	          }
	            	        	   });
	            }
	 
	 public boolean onOptionsItemSelected(MenuItem item) {
	        // app icon in action bar clicked; go home
	        Intent intentHome = new Intent(this, gridmenu.class);
	        intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        startActivity(intentHome);
	        return true;
	        }

}
