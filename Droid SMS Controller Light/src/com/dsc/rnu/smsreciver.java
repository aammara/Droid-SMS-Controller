package com.dsc.rnu;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

public class smsreciver extends Service
{
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
}
@Override
public void onDestroy()
{
	ComponentName receiver = new ComponentName(getBaseContext(), receiver.class);
	PackageManager pm = getBaseContext().getPackageManager();
	pm.setComponentEnabledSetting(receiver,
	        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
	        PackageManager.DONT_KILL_APP);	
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
	