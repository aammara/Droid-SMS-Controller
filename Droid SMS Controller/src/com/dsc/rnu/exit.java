package com.dsc.rnu;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import tn.idevelop.rnu.trial.R;

public class exit extends Activity {
	Handler h = new Handler();
    Run run = new Run();
    
    class Run implements Runnable {

        public void run() {
            // TODO Auto-generated method stub
            finish();
        }
    }
    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
    	int ort = getResources().getConfiguration().orientation;
    	if(ort==1)
    	setContentView(R.layout.exit); 
    	else if(ort==2)
        setContentView(R.layout.exitlandscape);  
    	super.onConfigurationChanged(newConfig);
    //Do nothing here
    } 
   
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	int ort = getResources().getConfiguration().orientation;
    	if(ort==1)
    	setContentView(R.layout.exit); 
    	else if(ort==2)
        setContentView(R.layout.exitlandscape); 

    	sleep(3000);
    }
    public void sleep(long delayMillis) 
    {
        h.postDelayed(run, delayMillis);
    }            
}