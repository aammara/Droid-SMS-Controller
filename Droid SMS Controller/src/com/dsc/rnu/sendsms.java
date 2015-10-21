package com.dsc.rnu;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import tn.idevelop.rnu.trial.R;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class sendsms extends SherlockActivity{
	String fun = "";
		public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.sendsms);
	    
	    com.actionbarsherlock.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setIcon(R.drawable.home);
        mActionBar.setTitle("");
        
	    Bundle bundle = this.getIntent().getExtras();
	    String action = bundle.getString("value");
	final EditText smsPrev = (EditText)  findViewById(R.id.smsPreview);
	final EditText phoneNum = (EditText)  findViewById(R.id.number);
	final EditText value1 = (EditText)  findViewById(R.id.editText1);
	value1.setVisibility(View.INVISIBLE);
	final EditText value2 = (EditText)  findViewById(R.id.editText2);
	value2.setVisibility(View.INVISIBLE);
	TextView text1 = (TextView)findViewById(R.id.textView1);
	text1.setVisibility(View.INVISIBLE);
	TextView text2 = (TextView)findViewById(R.id.textView2);
	text2.setVisibility(View.INVISIBLE);
	Resources res = getResources();
	String function [] = res.getStringArray(R.array.functions);
	String actions [] = res.getStringArray(R.array.actions);
	int i = 0;
	while(!actions[i].equals(action))
	{i++;};
	fun = function[i];
	smsPrev.setText(function[i]);
	if(i==3)
    {
    	value1.setVisibility(View.VISIBLE);
    	text1.setVisibility(View.VISIBLE);
    	text1.setText(getString(R.string.newSmsNumber));
    }
    else if(i==4)
    {
    	value1.setVisibility(View.VISIBLE);
    	text1.setVisibility(View.VISIBLE);
    	text1.setText(getString(R.string.newSmsMode));
    }
    else if(i==5)
    {
    	value1.setVisibility(View.VISIBLE);
    	text1.setVisibility(View.VISIBLE);
    	text1.setText(getString(R.string.newSmsName));
    }
    else if(i==6)
    {
    	value1.setVisibility(View.VISIBLE);
    	text1.setVisibility(View.VISIBLE);
    	text1.setText(getString(R.string.newSmsNumber));
    }
    else if(i==9)
    {
    	value1.setVisibility(View.VISIBLE);
    	text1.setVisibility(View.VISIBLE);
    	text1.setText(getString(R.string.newSmsNumber));
    	value2.setVisibility(View.VISIBLE);
    	text2.setVisibility(View.VISIBLE);
    	text2.setText(getString(R.string.newSmsText));
    }
    else if(i==10)
    {
    	value1.setVisibility(View.VISIBLE);
    	text1.setVisibility(View.VISIBLE);
    	text1.setText(getString(R.string.newSmsNumber));
    	value2.setVisibility(View.VISIBLE);
    	text2.setVisibility(View.VISIBLE);
    	text2.setText(getString(R.string.newSmsName));
    }
    else if(i==12)
    {
    	value1.setVisibility(View.VISIBLE);
    	text1.setVisibility(View.VISIBLE);
    	text1.setText(getString(R.string.newSmsDuration));
    }
	Button send = (Button)findViewById(R.id.sendButton);
	send.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
        	String num = phoneNum.getText().toString();
        	String text = smsPrev.getText().toString();
        	if(!num.equals(""))
        	{
        	SmsManager sms = SmsManager.getDefault();
        	sms.sendTextMessage(num, null,text, null,null);
        	finish();
		}
        	else
        		Toast.makeText(getBaseContext(),getString(R.string.newSmsErrorToast), Toast.LENGTH_SHORT).show();
        }
	});
  value1.addTextChangedListener(new TextWatcher() {
  
    public void onTextChanged(CharSequence s, int start, int before,
            int count) {
        // TODO Auto-generated method stub
    	if(value2.isShown()==true)
        smsPrev.setText(fun+" "+s+" , "+value2.getText().toString());
    	else
    		smsPrev.setText(fun+" "+s);
    }

	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}
 
});
  
  value2.addTextChangedListener(new TextWatcher() {
	  
	    public void onTextChanged(CharSequence s, int start, int before,
	            int count) {
	        // TODO Auto-generated method stub
	    	smsPrev.setText(fun+" "+value1.getText().toString()+" , "+s);
	    }

		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
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
