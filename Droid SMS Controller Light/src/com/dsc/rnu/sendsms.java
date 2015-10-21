package com.dsc.rnu;

import android.app.Activity;
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

public class sendsms extends Activity{
		public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.sendsms);
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
	if(i==0)
    {
		smsPrev.setText(function[0]);
    }
    else if(i==1)
    {
    	smsPrev.setText(function[1]);
    }
    else if(i==2)
    {
    	smsPrev.setText(function[2]);
    	value1.setVisibility(View.VISIBLE);
    	text1.setVisibility(View.VISIBLE);
    	text1.setText(getString(R.string.newSmsNumber));
    }
    else if(i==3)
    {
    	smsPrev.setText(function[3]);
    	value1.setVisibility(View.VISIBLE);
    	text1.setVisibility(View.VISIBLE);
    	text1.setText(getString(R.string.newSmsMode));
    }
    else if(i==4)
    {
    	smsPrev.setText(function[4]);
    	value1.setVisibility(View.VISIBLE);
    	text1.setVisibility(View.VISIBLE);
    	text1.setText(getString(R.string.newSmsName));
    }
    else if(i==5)
    {
    	smsPrev.setText(function[5]);
    	value1.setVisibility(View.VISIBLE);
    	text1.setVisibility(View.VISIBLE);
    	text1.setText(getString(R.string.newSmsNumber));
    }
    else if(i==6)
    {
    	smsPrev.setText(function[6]);
    }
    else if(i==7)
    {
    	smsPrev.setText(function[7]);
    }
    else if(i==8)
    {
    	smsPrev.setText(function[8]);
    	value1.setVisibility(View.VISIBLE);
    	text1.setVisibility(View.VISIBLE);
    	text1.setText(getString(R.string.newSmsNumber));
    	value2.setVisibility(View.VISIBLE);
    	text2.setVisibility(View.VISIBLE);
    	text2.setText(getString(R.string.newSmsText));
    }
    else if(i==9)
    {
    	smsPrev.setText(function[9]);
    	value1.setVisibility(View.VISIBLE);
    	text1.setVisibility(View.VISIBLE);
    	text1.setText(getString(R.string.newSmsNumber));
    	value2.setVisibility(View.VISIBLE);
    	text2.setVisibility(View.VISIBLE);
    	text2.setText(getString(R.string.newSmsName));
    }
    else if(i==10)
    {
    	smsPrev.setText(function[10]);
    }
    else if(i==11)
    {
    	smsPrev.setText(function[10]);
    	value1.setVisibility(View.VISIBLE);
    	text1.setVisibility(View.VISIBLE);
    	text1.setText(getString(R.string.newSmsDuration));
    }
    else if(i==12)
    {
    	smsPrev.setText(function[12]);  	
    }
    else if(i==13)
    {
    	smsPrev.setText(function[13]);
    }
    else if(i==14)
    {
    	smsPrev.setText(function[14]);
    }
    else if(i==15)
    {
    	smsPrev.setText(function[15]);
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
		}
        	else
        		Toast.makeText(getBaseContext(),getString(R.string.newSmsErrorToast), Toast.LENGTH_SHORT).show();
        }
	});
  value1.addTextChangedListener(new TextWatcher() {
  
    public void onTextChanged(CharSequence s, int start, int before,
            int count) {
        // TODO Auto-generated method stub
    	int i;
    	String text = "";
    	String value = smsPrev.getText().toString();
    	for(i=0;value.indexOf("(")>=i;i++)
    	{
    	text = text+value.charAt(i);
    	}
    	if(value2.isShown()==true)
 smsPrev.setText(text+s+",);");
    	else
    		smsPrev.setText(text+s+");");
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
	    	int i;
	    	String text = "";
	    	String value = smsPrev.getText().toString();
	    	for(i=0;value.indexOf(",")>=i;i++)
	    	{
	    	text = text+value.charAt(i);
	    	}
	    	smsPrev.setText(text+s+");");
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
}
