package com.dsc.rnu;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import tn.idevelop.rnu.trial.R;

public class aboutActivity extends SherlockActivity {

	@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.about);

	        com.actionbarsherlock.app.ActionBar mActionBar = getSupportActionBar();
	        mActionBar.setHomeButtonEnabled(true);
	        mActionBar.setIcon(R.drawable.home);
	        mActionBar.setTitle("");
	    }
	    
	 @Override
		 public boolean onOptionsItemSelected(MenuItem item) {
		        // app icon in action bar clicked; go home
		        Intent intentHome = new Intent(this, gridmenu.class);
		        intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(intentHome);
		        return true;
                }
		 
}
