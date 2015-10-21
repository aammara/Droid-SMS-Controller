package com.dsc.rnu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class terminal extends Activity {
	    /** Called when the activity is first created. */
	EditText script;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.terminal);
	       script = (EditText) findViewById(R.id.scriptText);
	        script.setText(getString(R.string.terminalMsg));
	    	        
	 }
	 
	 public boolean onCreateOptionsMenu(Menu menu) {
   	     MenuInflater inflater = getMenuInflater();
   	     inflater.inflate(R.layout.menusettings, menu);
   	     return true;
   	 }

   	 public boolean onOptionsItemSelected(MenuItem item) {
   	        	String scriptLine =  script.getText().toString();
   	        	DbHelper db= new DbHelper(getBaseContext());
   	        	if(scriptLine.contains(""))
   	        	{
   	        	return true;
   	        	}
   	        	else if(scriptLine.contains(""))
   	        	{
   	        	return true;
   	        	}
   	        	else if(scriptLine.contains(""))
   	        	{
   	        	return true;
   	        	}
   	        	else if(scriptLine.contains(""))
   	        	{
   	        	return true;
   	        	}
   	        	else if(scriptLine.contains(""))
   	        	{
   	        	return true;
   	        	}
   	        	else if(scriptLine.contains(""))
   	        	{
   	        	return true;
   	        	}
   	        	else if(scriptLine.contains(""))
   	        	{
   	        	return true;
   	        	}
   	        	else if(scriptLine.contains(""))
   	        	{
   	        	return true;
   	        	}
   	        	else if(scriptLine.contains(""))
   	        	{
   	        	return true;
   	        	}
   	        	else if(scriptLine.contains(""))
   	        	{
   	        	return true;
   	        	}
   	   	  db.close();
   	     return true;
   	 }


}
