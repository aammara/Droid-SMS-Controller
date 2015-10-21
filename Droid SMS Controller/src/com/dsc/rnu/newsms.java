package com.dsc.rnu;

import java.util.ArrayList;
import java.util.HashMap;
import tn.idevelop.rnu.trial.R;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class newsms extends SherlockActivity {
	
	 private ListView maListViewPerso;
	/** Called when the activity is first created. */
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 Resources res = getResources();
		 String action [] = res.getStringArray(R.array.actions);
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.newsms);
	        com.actionbarsherlock.app.ActionBar mActionBar = getSupportActionBar();
	        mActionBar.setHomeButtonEnabled(true);
	        mActionBar.setIcon(R.drawable.home);
	        mActionBar.setTitle("");
	        
	        maListViewPerso = (ListView) findViewById(R.id.commandList);
	        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
	        HashMap<String, String> map;
	        int i;
	        for(i=0;i<action.length;i++)
	        {
	        map = new HashMap<String, String>();
	        map.put("titre", action[i]);
	        map.put("description", "");
	        map.put("img", String.valueOf(R.drawable.newsms));
	        listItem.add(map);
	        }
	        
	        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.row,
	        new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});
	        maListViewPerso.setAdapter(mSchedule);
	         maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
	    @SuppressWarnings("unchecked")
	           public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	        HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
	        Bundle bundle = new Bundle();
	        bundle.putString("value",map.get("titre"));
	        Intent newIntent = new Intent(newsms.this, sendsms.class);
	        newIntent.putExtras(bundle);
	        startActivityForResult(newIntent, 0);
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