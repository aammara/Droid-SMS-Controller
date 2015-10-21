package com.dsc.rnu;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class exit extends Activity{
	Handler h = new Handler();
    Run run = new Run();
    
    class Run implements Runnable {

        public void run() {
            // TODO Auto-generated method stub
            finish();
        }
    }
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exit);
        DbHelper db= new DbHelper(getBaseContext());
        db.close();
		sleep(5000);
    }
    public void sleep(long delayMillis) {
        h.postDelayed(run, delayMillis);
    }
}
