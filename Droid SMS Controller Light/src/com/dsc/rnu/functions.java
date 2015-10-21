package com.dsc.rnu;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.telephony.SmsManager;
import android.text.format.Time;


public class functions {

void sendSMS (String text,String num )
{
	SmsManager sms = SmsManager.getDefault();
	sms.sendTextMessage( num, null,text, null,null); 	
}



	void exe (String cmd, String num, Context context)
	{  
		Resources res = context.getResources();
		 String func [] = res.getStringArray(R.array.functions);
		DbHelper db = new DbHelper(context);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if( cmd.equals(db.GetFuncId(0)) || func[0].equals(cmd) )//call me
	    {
			String phoneCallUri = "tel:"+num;
			   Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
			   phoneCallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			   phoneCallIntent.setData(Uri.parse(phoneCallUri));
			   context.startActivity(phoneCallIntent);
			   return;
	    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if( cmd.equals(db.GetFuncId(12)) || func[12].equals(cmd) )//Time
    {
    Time today = new Time(Time.getCurrentTimezone());
	today.setToNow();
    sendSMS(today.hour+":"+today.minute+":"+today.second+"\n",num);     
    return;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if( cmd.equals(db.GetFuncId(13)) || func[13].equals(cmd) )//Date
    {    Time today = new Time(Time.getCurrentTimezone());
	     today.setToNow();
		sendSMS(today.year+"-"+today.month+"-"+today.monthDay,num);
		return;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if(cmd.equals(db.GetFuncId(14)) || func[14].equals(cmd) )//sound
	{
		String sound = db.getSOUND();
		MediaPlayer mediaPlayer = null;
		if (sound.equals("Danger"))
	    mediaPlayer = MediaPlayer.create(context,R.raw.danger);
		else if (sound.equals("Scream"))
		mediaPlayer = MediaPlayer.create(context,R.raw.scream);
		else if (sound.equals("Alarm Clock"))
		mediaPlayer = MediaPlayer.create(context,R.raw.alarmclock);
		mediaPlayer.setLooping(true);
		mediaPlayer.start();
		return;
    }

}	
}
