package com.dsc.rnu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class receiver extends BroadcastReceiver {
	@Override
	 public void onReceive(Context context, Intent intent)
	    {   //---get the SMS message passed in---
	        Bundle bundle = intent.getExtras();       
	        SmsMessage[] msgs = null;  
	        String str = "";           
	        if (bundle != null)
	        {
	            //---retrieve the SMS message received---
	            Object[] pdus = (Object[]) bundle.get("pdus");
	            msgs = new SmsMessage[pdus.length];           
	            for (int i=0; i<msgs.length; i++){
	                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);               
	                str += "from : " + msgs[i].getOriginatingAddress()+"\n";                     
	                str += "Text :";
	                str += msgs[i].getMessageBody().toString();
	                str += "\n";
	            }
	            //---display the new SMS message---
	            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	            DbHelper db= new DbHelper(context);
	            functions fn = new functions();
	            if(db.getAUTHO()==true)
	            {
	            if(db.allowedNUMBER(msgs[0].getOriginatingAddress())>0)
	            fn.exe(msgs[0].getMessageBody().toString(),msgs[0].getOriginatingAddress(),context);    
	             }
	            else
	            fn.exe(msgs[0].getMessageBody().toString(),msgs[0].getOriginatingAddress(),context);    
	        }
	            }
	    }