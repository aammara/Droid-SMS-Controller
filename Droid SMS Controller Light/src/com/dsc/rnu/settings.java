package com.dsc.rnu;


import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class settings extends TabActivity {
        @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.settings);
	        Resources res = getResources(); // Resource object to get Drawables
	        TabHost tabHost = getTabHost();  // The activity TabHost
	        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	        Intent intent;  // Reusable Intent for each tab

	        // Create an Intent to launch an Activity for the tab (to be reused)
	        intent = new Intent().setClass(this, list.class);

	        // Initialize a TabSpec for each tab and add it to the TabHost
	        spec = tabHost.newTabSpec("List").setIndicator(getString(R.string.settingsAutho),
	                          res.getDrawable(R.drawable.list))
	                      .setContent(intent);
	        tabHost.addTab(spec);

	        // Do the same for the other tabs
	        intent = new Intent().setClass(this, others.class);
	        spec = tabHost.newTabSpec("Others").setIndicator(getString(R.string.settingsSOthers),
	                          res.getDrawable(R.drawable.other))
	                      .setContent(intent);
	        tabHost.addTab(spec);
	        tabHost.setCurrentTab(2);
}
        protected void OnActivityDestroyListener()
        {
        	DbHelper db= new DbHelper(getBaseContext());
        	db.close();
        }
}