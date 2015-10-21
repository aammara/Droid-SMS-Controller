package com.dsc.rnu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import tn.idevelop.rnu.trial.R;

public class intro extends Activity {
	Handler h = new Handler();
    Run run = new Run();
    
    class Run implements Runnable {

        public void run() {
            // TODO Auto-generated method stub
        	Intent myIntent = new Intent
                    (intro.this, start.class);
                    startActivityForResult(myIntent, 0);       
            finish();
        }
    }
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.intro); 
    		sleep(3000);
    }
    
    public void sleep(long delayMillis) {
        h.postDelayed(run, delayMillis);
    }
}