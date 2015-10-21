package com.dsc.rnu;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import tn.idevelop.rnu.trial.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class netReceiver extends BroadcastReceiver {

    Context context;
    CustomHttpClient client = new CustomHttpClient();
    @Override
    public void onReceive(Context ctx, Intent intent) {
        if(isNetworkAvailable(ctx))
        {
    	ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
    	postParameters.add(new BasicNameValuePair("",""));
    	DbHelper db = new DbHelper(ctx);
    	String id = db.getId();
    	if(id.equals(""))
    	{
    		String response = "";
    		try {response = client.executeHttpPost("http://10.0.2.2/DSC/newUser.php", postParameters);}
    		catch (Exception e) {}
    		String res=response.toString();	
    		db.updateID(res);
    		id = res;
    	}
    	postParameters.add(new BasicNameValuePair("id",id));
    	try {  client.executeHttpPost("http://10.0.2.2/DSC/users.php", postParameters); }
    	catch (Exception e) {}

        // Check if the network is connected, and if so, then restart the timer
    	 db.close();
        }       
    }

	public static boolean isNetworkAvailable(Context context) {
	    ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
	    NetworkInfo info = mgr.getActiveNetworkInfo();
	    if (info != null && info.isConnected()) {
	        return true;
	    }
	    else {
	        return false;
	    }
	}

}
