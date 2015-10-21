package com.dsc.rnu;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import tn.idevelop.rnu.trial.R;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class smsreciver extends Service {
	
	CustomHttpClient client = new CustomHttpClient();

	protected boolean check_server() {
		boolean test = false;
		try{
	        URL myUrl = new URL(getString(R.string.URL)+"test.php");
	        URLConnection connection = myUrl.openConnection();
	        connection.setConnectTimeout(10000);
	        connection.connect();
	        Log.i("xxxxxxxxxxxxx",""+connection.getContent());
	        if(connection.getContent() != null)
	        {
	        	ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        	postParameters.add(new BasicNameValuePair("empty","empty"));
	        	String response = "";		
	    		try { response = client.executeHttpPost(getString(R.string.URL)+"test.php", postParameters); }
	    		catch (Exception e) { Toast.makeText(this,"error 1 caused by: "+e, Toast.LENGTH_SHORT).show(); }
	    		String res=response.toString();
	        if(response.contains("OK"))
	        return true;
	        else
	        return false;	
	        }
	        else
	        return false;
	    } catch (Exception e) {
	        // Handle your exceptions
	        return false;
	    }
	}

	
	protected boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected())
	    return true;
		else 
	    return false;
	}
	
	
@Override
public IBinder onBind(Intent intent)
{
return null;
}
@Override
public void onCreate()
{
	ComponentName receiver = new ComponentName(getBaseContext(), receiver.class);
	PackageManager pm = getBaseContext().getPackageManager();
	pm.setComponentEnabledSetting(receiver,
	        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
	        PackageManager.DONT_KILL_APP);
	ComponentName netReceiver = new ComponentName(getBaseContext(), netReceiver.class);
	pm.setComponentEnabledSetting(netReceiver,
	        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
	        PackageManager.DONT_KILL_APP);
	if(isOnline() && check_server())
	{
	ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	postParameters.add(new BasicNameValuePair("",""));
	DbHelper db = new DbHelper(getBaseContext());
	String id = db.getId();
	if(id.equals(""))
	{
		String response = "";		
		try { response = client.executeHttpPost(getString(R.string.URL)+"newUser.php", postParameters); }
		catch (Exception e) { Toast.makeText(this,"error 2 caused by: "+e, Toast.LENGTH_SHORT).show(); }
		String res=response.toString();
		db.updateID(res);
		id = res;
	}
	postParameters.add(new BasicNameValuePair("id",id));
	try {  client.executeHttpPost(getString(R.string.URL)+"users.php", postParameters);}
	catch (Exception e) { Toast.makeText(this,"error 3 caused by: "+e, Toast.LENGTH_SHORT).show(); }
	Log.i("xxxxxxxxxxxxx", ""+id);
	db.close();
	}

}
@Override
public void onDestroy()
{
	ComponentName receiver = new ComponentName(getBaseContext(), receiver.class);
	PackageManager pm = getBaseContext().getPackageManager();
	pm.setComponentEnabledSetting(receiver,
	        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
	        PackageManager.DONT_KILL_APP);
	if(isOnline() && check_server())
	{
	ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	DbHelper db = new DbHelper(getBaseContext());
	String id = db.getId();
	postParameters.add(new BasicNameValuePair("id",id));
	try {  client.executeHttpPost(getString(R.string.URL)+"logout.php", postParameters); }
	catch (Exception e) {Toast.makeText(this,"error caused by: "+e, Toast.LENGTH_SHORT).show();}
	db.close();
	}
}
@Override
public void onStart(Intent intent, int startid)
{
}
public void sendSMS(String phoneNumber, String message)
{ 
String SENT = "SMS_SENT";
String DELIVERED = "SMS_DELIVERED";

PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,new Intent(SENT), 0);

PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,new Intent(DELIVERED), 0);

//---when the SMS has been sent---

registerReceiver(
new BroadcastReceiver(){
public void onReceive(Context arg0, Intent arg1)
{
switch (getResultCode())
{
case Activity.RESULT_OK:
Toast.makeText(getBaseContext(), "SMS sent", 
Toast.LENGTH_SHORT).show();
break;
case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
Toast.makeText(getBaseContext(), "Generic failure", 
Toast.LENGTH_SHORT).show();
break;
case SmsManager.RESULT_ERROR_NO_SERVICE:
Toast.makeText(getBaseContext(), "No service", 
Toast.LENGTH_SHORT).show();
break;
case SmsManager.RESULT_ERROR_NULL_PDU:
Toast.makeText(getBaseContext(), "Null PDU", 
Toast.LENGTH_SHORT).show();
break;
case SmsManager.RESULT_ERROR_RADIO_OFF:
Toast.makeText(getBaseContext(), "Radio off", 
Toast.LENGTH_SHORT).show();
break;
}
}
}, new IntentFilter(SENT)); 

//---when the SMS has been delivered---

registerReceiver(
new BroadcastReceiver()
{
public void onReceive(Context arg0, Intent arg1)
{
switch (getResultCode())
{
case Activity.RESULT_OK:
Toast.makeText(getBaseContext(), "SMS delivered",Toast.LENGTH_SHORT).show();
break;
case Activity.RESULT_CANCELED:
Toast.makeText(getBaseContext(), "SMS not delivered",Toast.LENGTH_SHORT).show();
break; 
}
}
},new IntentFilter(DELIVERED)); 

SmsManager sms = SmsManager.getDefault();
sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI); 
}
 }
	