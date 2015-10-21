package com.dsc.rnu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import tn.idevelop.rnu.trial.R;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       //TODO   
    	TelephonyManager phoneManager = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
    			String num = phoneManager.getLine1Number();
    			String serial = phoneManager.getSimSerialNumber();
        DbHelper db= new DbHelper(context);
 	    if(!db.getMyNumber().equals(""))
        if (!num.contains(db.getMyNumber()))
 	    {
        	try
        	{
        	SmsManager sms = SmsManager.getDefault();
        	sms.sendTextMessage(db.getMyNumber(), null,"[DSC Number Catcher]\nThis number is using your Smartphone "+num+".\nSIM's Serial Number : "+serial+"\ntake control now ;)", null,null); 
        	}
        	catch(Exception e)
        	{
        		Toast.makeText(context,"Can not send an SMS, Invalid number!", Toast.LENGTH_SHORT).show();
        	}
 	    }
 	    db.close();
    }
}
