package com.dsc.rnu;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import tn.idevelop.rnu.trial.R;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class statistics extends SherlockActivity{
	CustomHttpClient client ;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        
        com.actionbarsherlock.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setIcon(R.drawable.home);
        mActionBar.setTitle("");
        
        client = new CustomHttpClient();
        final ListView list= (ListView)findViewById(R.id.statistics);
        Resources res = getResources();
		 String sta [] = res.getStringArray(R.array.statistics);
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        int i;
        for(i=0;i<sta.length;i++)
        {
        	ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        	postParameters.add(new BasicNameValuePair("act",i+""));
        	String resp = null;
        	try {resp =  client.executeHttpPost(getString(R.string.URL)+"statistics.php", postParameters); }
        	catch (Exception e) {}
        map = new HashMap<String, String>();
        map.put("titre", sta[i]+" "+resp);
        map.put("description", "");
        map.put("img", String.valueOf(R.drawable.ic_statistics));
        listItem.add(map);
        }
        
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.row,
        new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});
        list.setAdapter(mSchedule);
        }
	
	 public boolean onOptionsItemSelected(MenuItem item) {
	        // app icon in action bar clicked; go home
	        Intent intentHome = new Intent(this, gridmenu.class);
	        intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        startActivity(intentHome);
	        return true;
	        }
}
