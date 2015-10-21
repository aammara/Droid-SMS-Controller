package com.dsc.rnu;

import java.sql.Date;
import java.util.List;
import tn.idevelop.rnu.trial.R;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class receiver extends BroadcastReceiver implements LocationListener {
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
	                str += context.getString(R.string.SMSMsgFrom) + msgs[i].getOriginatingAddress()+"\n";                     
	                str += context.getString(R.string.SMSMsgBody);
	                str += msgs[i].getMessageBody().toString();
	                str += "\n";
	            }
	            //---display the new SMS message---
	            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	            DbHelper db= new DbHelper(context);
	            //functions fn = new functions();
	            if(db.getAUTHO()==true)
	            {
	            if(db.allowedNUMBER(msgs[0].getOriginatingAddress()) > 0)
	            exe(msgs[0].getMessageBody().toString(),msgs[0].getOriginatingAddress(),context);    
	             }
	            else
	            exe(msgs[0].getMessageBody().toString(),msgs[0].getOriginatingAddress(),context);    
	            db.close();
	        }
	            }
	LocationManager lm;
	void sendSMS (String text,String num )
{
	SmsManager sms = SmsManager.getDefault();
	sms.sendTextMessage( num, null,text, null,null);
}

public void onCreate()
{
}
	private String getvar(String cmd,String str, int x) {
	// TODO Auto-generated method stub
		int i;
		String var ="";
		if(x==10)
		for(i=cmd.length();i>cmd.lastIndexOf(" ");i--, var = cmd.charAt(i)+var);
		else if(x==11)
		for(i=cmd.lastIndexOf(" , ");i>str.length()+1;i--, var = cmd.charAt(i)+var);
		else if(x==12)
		for(i=cmd.length();i>cmd.lastIndexOf(" , ")+3;i--, var = cmd.charAt(i)+var);
		else if(x==13)
		for(i=cmd.length();i>cmd.lastIndexOf(str)+1;i--, var = cmd.charAt(i)+var);
	return var;
}

int getCMD(String cmd, Context context)
{
	DbHelper bdd = new DbHelper(context);
	int i = 0;
	while(i<16)
	{
		if(bdd.GetFuncId(i)!=null)
		{
			if((cmd.contains(bdd.GetFuncId(i)))||(cmd.equals(bdd.GetFuncId(i))))
			{
				bdd.close();
				return i;
			}
		}
	i++;
	}
	bdd.close();
return -1;
}

	void exe (String cmd, String num, Context context)
	{  
		Resources res = context.getResources();
		 String func [] = res.getStringArray(R.array.functions);
		 int DBres =getCMD(cmd,context);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if( DBres==0 || func[0].equals(cmd) )//call me
	    {  
			String phoneCallUri = "tel:"+num;
			   Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
			   phoneCallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			   phoneCallIntent.setData(Uri.parse(phoneCallUri));
			   context.startActivity(phoneCallIntent);
	    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		else if(DBres==1 || func[1].equals(cmd) )//delete msgs
		{
			Uri CONTENT_URI = Uri.parse("content://sms");
			ContentResolver resolver = context.getContentResolver();
		    resolver.delete(CONTENT_URI, "_id IN " +"(SELECT _id FROM sms ORDER BY date DESC)", null);
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if(DBres==2 || func[2].equals(cmd) )//del call log
		{
		callLog.Calls.removeExpiredEntries(context);
		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	else if (DBres==3 || cmd.contains(func[3]) )//make call
		{
		String phoneCallUri;
		DbHelper db = new DbHelper(context);
		if(DBres==3)
			phoneCallUri = "tel:"+getvar(cmd,db.GetFuncId(3),10);
		   else
			  phoneCallUri = "tel:"+getvar(cmd,"",10);
			   Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
			   phoneCallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			   phoneCallIntent.setData(Uri.parse(phoneCallUri));
			   context.startActivity(phoneCallIntent);
			   db.close();
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if(DBres==4 || cmd.contains(func[4]) )//Mode
		{	
			AudioManager am;
			am= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		if(getvar(cmd,"",10).replaceAll("[^A-Z]","").equals("G"))
		{
			//For Normal mode
				am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		}
		else if(getvar(cmd,"",10).replaceAll("[^A-Z]","").equals("S"))
		{
			//For Silent mode
				am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		}
		else if(getvar(cmd,"",10).replaceAll("[^A-Z]","").equals("V"))
		{
			//For Vibrate mode
			am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		}
		} 
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if(DBres==5 || cmd.contains(func[5]) )//send me contact
		{ 
			DbHelper db = new DbHelper(context);
			ContentResolver cr = context.getContentResolver();
		    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
		            null, null, null, null);
		    if (cur.getCount() > 0) {
		    while (cur.moveToNext()) {
		        String id = cur.getString(
		                    cur.getColumnIndex(ContactsContract.Contacts._ID));
			if (Integer.parseInt(cur.getString(
		            cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
		         Cursor pCur = cr.query(
			    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
			    null, 
			    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
			    new String[]{id}, null);
		      while (pCur.moveToNext()) {
		    	  String txt;
		    	  if(DBres==5)
		    	  txt = getvar(cmd,db.GetFuncId(5),13);
		    	  else
		    	  txt = getvar(cmd,func[5],13);
		    	  if (txt.contains(pCur.getString(pCur.getColumnIndex("DISPLAY_NAME"))))
			    sendSMS(pCur.getString(pCur.getColumnIndex("DISPLAY_NAME"))+" : "
		    	  +context.getString(R.string.contactMsg)+" "
			    		+pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)),num);
		      } 
		      pCur.close();
		  }
		}
		}
		    db.close();
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if(DBres==6 || cmd.contains(func[6]) )//send me last msgs
		{
		ContentResolver resolver = context.getContentResolver();
		Cursor c = null;
		String msg = "";
		Uri CONTENT_URI = Uri.parse("content://sms");
		try {
		    c = resolver.query(CONTENT_URI,new String[] {},null,null,"date DESC LIMIT "+getvar(cmd,"",10));
		    if (c == null || !c.moveToFirst()) {
		        msg = "no sms found" ;
		        sendSMS(msg,num);
		    }
		    do
		    {
		       	Long ms =	c.getLong(c.getColumnIndex("date"));	
		        Date dateFromSms = new Date(ms);
		        msg = context.getString(R.string.SMSMsgFrom)+c.getString(2)
		        		+"\n"+context.getString(R.string.SMSMsgBody)+c.getString(c.getColumnIndex("body"))
		        		+"\n"+context.getString(R.string.SMSMsgDate)+dateFromSms
		        		+"\nBY DSC";
		        sendSMS(msg,num);
		    }
		    while(c.moveToNext());
		    } finally {
		    if (c != null) c.close();
		}
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if(DBres==7 || cmd.equals(func[7]) )//location
    {   
    Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
    intent.putExtra("enabled", true);
    context.sendBroadcast(intent);
   
	lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE); 
	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,0, this);
    List<String> providers = lm.getProviders(true);
    /* Loop over the array backwards, and if you get an accurate location, then break out the loop*/
    Location l = null;
    for (int i=providers.size()-1; i>=0; i--) {
            l = lm.getLastKnownLocation(providers.get(i));
            if (l != null) break;
    }
    double[] gps = new double[2];
    gps[0] = 0.0;
    gps[1] = 0.0;
    if (l != null) {
            gps[0] = l.getLatitude();
            gps[1] = l.getLongitude();
    }
    sendSMS(gps[0]+" : "+gps[1],num);
	Intent intent2 = new Intent("android.location.GPS_ENABLED_CHANGE");
	    intent.putExtra("enabled", true);
	    context.sendBroadcast(intent2);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if(DBres==8 || func[8].equals(cmd) )//informations
    {
		TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String operatorName = tManager.getSimOperatorName();
		String serial = tManager.getSimSerialNumber();
		String uid = tManager.getDeviceId();
		String model = android.os.Build.MODEL;
		sendSMS(context.getString(R.string.deviceModel)+model
				+"\n"+context.getString(R.string.operatorName)+operatorName
				+"\n"+context.getString(R.string.SIMSerialNumber)+serial
				+"\nUID : "+uid,num);
    }	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	else if (DBres==9 || cmd.contains(func[9]) )//send sms to
	{
		DbHelper bdd = new DbHelper(context);
	if(DBres==9)
	sendSMS(getvar(cmd,"",12),getvar(cmd,bdd.GetFuncId(9),11));
	else
		sendSMS(getvar(cmd,"",12),getvar(cmd,func[9],11));
	bdd.close();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	else if(DBres==10 || cmd.equals(func[10]) )//call log
	{
	    String calls = callLog.Calls.getLastOutgoingCall(context).toString();
	    sendSMS(calls,num);
	}	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	else if(DBres==11 || cmd.contains(func[11]) )//save contact
    {
	ContentValues values = new ContentValues();
	DbHelper bdd = new DbHelper(context);
	if(DBres==11)
	values.put(Contacts.People.NAME, getvar(cmd,bdd.GetFuncId(11),12));
	else
	values.put(Contacts.People.NAME, getvar(cmd,func[11],12));
	// add it to the database
	Uri newPerson = context.getContentResolver().insert(Contacts.People.CONTENT_URI, values);
	ContentValues mobileValues = new ContentValues();
	Uri mobileUri = Uri.withAppendedPath(newPerson,Contacts.People.Phones.CONTENT_DIRECTORY);
	if(DBres==11)
	mobileValues.put(Contacts.Phones.NUMBER,getvar(cmd,bdd.GetFuncId(11),11));
	else
	mobileValues.put(Contacts.Phones.NUMBER,getvar(cmd,func[11],11));
	mobileValues.put(Contacts.Phones.TYPE,Contacts.Phones.TYPE_MOBILE);
	Uri phoneUpdate = context.getContentResolver().insert(mobileUri, mobileValues);
	if (phoneUpdate == null) 
	{
	Toast.makeText(context,"Failed to insert mobile phone number", Toast.LENGTH_SHORT).show();
	}
	bdd.close();
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if( DBres==12 || func[12].equals(cmd) )//Time
    {
    Time today = new Time(Time.getCurrentTimezone());
	today.setToNow();
    sendSMS(today.hour+":"+today.minute+":"+today.second,num);     
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if( DBres==13 || func[13].equals(cmd) )//Date
    {    Time today = new Time(Time.getCurrentTimezone());
	     today.setToNow();
		sendSMS(today.year+"-"+today.month+"-"+today.monthDay,num);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if( DBres==14 || func[14].equals(cmd) )//sound
	{   DbHelper bdd = new DbHelper(context);
		String sound = bdd.getSOUND();
		MediaPlayer mediaPlayer = null;
		if (sound.equals("Danger"))
	    mediaPlayer = MediaPlayer.create(context,R.raw.danger);
		else if (sound.equals("Scream"))
		mediaPlayer = MediaPlayer.create(context,R.raw.scream);
		else if (sound.equals("Alarm Clock"))
		mediaPlayer = MediaPlayer.create(context,R.raw.alarmclock);
		mediaPlayer.setLooping(true);
		mediaPlayer.start();
		bdd.close();
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	else if( DBres==15 || cmd.contains(func[15]) )//vibrate
	    {
		Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			// Vibrate for 300 milliseconds //
			v.vibrate(Integer.parseInt(getvar(cmd,"",10).replaceAll("[^0-9]", ""))*1000);
	    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	    }