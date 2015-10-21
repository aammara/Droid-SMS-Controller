package com.dsc.rnu;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import tn.idevelop.rnu.trial.R;
import com.actionbarsherlock.app.SherlockActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;
 
public class gridmenu extends SherlockActivity implements Runnable{
	GridView gridView;
	ProgressDialog ProgDialog;
	Boolean stat = false;
	protected boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected())
	    return true;
		else 
	    return false;
	}
	
int error = 0;	
CustomHttpClient client = new CustomHttpClient();

	 boolean ServerIsAvailable() {
		try{
	        URL myUrl = new URL(getString(R.string.URL)+"test.php");
	        URLConnection connection = myUrl.openConnection();
	        connection.setConnectTimeout(10000);
	        connection.connect();
	        if(connection.getContent() != null)
	        {
	        	String response = "";
	        	ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        	postParameters.add(new BasicNameValuePair("empty","empty"));
	    		try { response = client.executeHttpPost(getString(R.string.URL)+"test.php", postParameters); }
	    		catch (Exception e) {  }
	    		response.toString();
	    		Log.i("xxxxxxxxxxxxx",""+response.toString());
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
	
	 DbHelper db;
	@Override
	public void onCreate(Bundle savedInstanceState) {
 
		super.onCreate(savedInstanceState);
		int ort = getResources().getConfiguration().orientation;
		com.actionbarsherlock.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
    	if(ort==1)
    	setContentView(R.layout.gridmenu); 
    	else if(ort==2)
        setContentView(R.layout.gridmenulandscape);
    	
		gridView = (GridView) findViewById(R.id.gridView1);
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		 
        HashMap<String, String> map;
        
        map = new HashMap<String, String>();
        map.put("titre", getString(R.string.menuNew));
        map.put("img", String.valueOf(R.drawable.newsms));
        listItem.add(map);
 
        map = new HashMap<String, String>();
        map.put("titre", getString(R.string.menuFunctionsManager));
        map.put("img", String.valueOf(R.drawable.functions_manager));
        listItem.add(map);
        
        map = new HashMap<String, String>();
        map.put("titre", getString(R.string.menuAbout));
        map.put("img", String.valueOf(R.drawable.about_ic));
        listItem.add(map);
        
        map = new HashMap<String, String>();
        map.put("titre", getString(R.string.menuSettings));
        map.put("img", String.valueOf(R.drawable.settings));
        listItem.add(map);
        
        map = new HashMap<String, String>();
        map.put("titre", getString(R.string.menuUpdate));
        map.put("img", String.valueOf(R.drawable.update));
        listItem.add(map);
                
        map = new HashMap<String, String>();
        map.put("titre", getString(R.string.menuStatistics));
        map.put("img", String.valueOf(R.drawable.ic_statistics));
        listItem.add(map);
        
        map = new HashMap<String, String>();
        map.put("titre", getString(R.string.menuHelp));
        map.put("img", String.valueOf(R.drawable.help));
        listItem.add(map);
        
        map = new HashMap<String, String>();
        map.put("titre", getString(R.string.menuExit));
        map.put("img", String.valueOf(R.drawable.exit));
        listItem.add(map);
        
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.gridviewlayout,
               new String[] {"img", "titre"}, new int[] {R.id.img, R.id.title});
        gridView.setAdapter(mSchedule);
        gridView.setOnItemClickListener(new OnItemClickListener() {
        @SuppressWarnings("unchecked")
        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
     HashMap<String, String> map = (HashMap<String, String>) gridView.getItemAtPosition(position);
     if(map.get("titre").equals(getString(R.string.menuNew)))
     {    
  	   Intent myIntent = new Intent (gridmenu.this, newsms.class);
         startActivity(myIntent);
     }
     else if (map.get("titre").equals(getString(R.string.menuAbout))) 
     {
  	   Intent myIntent = new Intent (gridmenu.this, aboutActivity.class);
     startActivity(myIntent);
     }
     else if (map.get("titre").equals(getString(R.string.menuFunctionsManager)))
     {
  	   Intent myIntent = new Intent (gridmenu.this, fnManager.class);
     startActivity(myIntent);
     }
     else if (map.get("titre").equals(getString(R.string.menuSettings)))
     {
  	   if (!db.getPSWD().equals(""))
  	   {
  	 final AlertDialog.Builder alert = new AlertDialog.Builder(gridmenu.this);
 	    final LinearLayout layout = new LinearLayout(gridmenu.this);
 	    layout.setOrientation(LinearLayout.VERTICAL);
 	    final EditText pswd = new EditText(gridmenu.this);
 	    pswd.setGravity(Gravity.CENTER);
 	    pswd.setHint("Password");
 	    pswd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
 	    pswd.setWidth(200);
  	    layout.addView(pswd);
  	    alert.setView(layout);
  		alert.setMessage(getString(R.string.menuPswd))
  		       .setCancelable(false)

  		        		       .setPositiveButton(getString(R.string.menuPswdOkButton), new DialogInterface.OnClickListener() {
  		        		           public void onClick(DialogInterface dialog, int id) {
  		        		        	   DbHelper db = new DbHelper(getBaseContext());
  		        		        	   if (db.getPSWD().equals(pswd.getText().toString()))
  		        		        	   {
  		        		        	   Intent myIntent = new Intent (gridmenu.this, settings.class);
  		        		           startActivity(myIntent);
  		        		        	   }
  		        		        	   else
  		        		        	   {
  		        		        	   Toast.makeText(gridmenu.this,getString(R.string.menuPswdError), Toast.LENGTH_LONG).show();
  		        		        	   }
  		        		        	   dialog.cancel();
  		        		        	   db.close(); 
  		        		           }
  		        		           })
  		        		           .setNegativeButton(getString(R.string.menuPswdCancelButton), new DialogInterface.OnClickListener() {
  	              public void onClick(DialogInterface dialog, int id) {
  	            	  dialog.cancel();
  	              }
  	          });
  		        		AlertDialog alertDia = alert.create();
  		        		alertDia.show();
     	   }
  	   else
  	   {
  		   Intent myIntent = new Intent (gridmenu.this, settings.class);
	           startActivity(myIntent);
  	   }
     }
     else if (map.get("titre").equals(getString(R.string.menuHelp)))
     {Intent myIntent = new Intent (gridmenu.this, help.class);
     startActivity(myIntent);
     }
     else if (map.get("titre").equals(getString(R.string.menuUpdate)))
     {
  	   if(isOnline())   
     	{
  		 ProgDialog = ProgressDialog.show(gridmenu.this, "", 
		  	  	  "Loading. Please wait...", true);
		stat = true;
		 Thread thread = new Thread(gridmenu.this);
		   thread.start();
     	}
     	else
     		Toast.makeText(gridmenu.this,getString(R.string.connectionErrorMsgSta),Toast.LENGTH_LONG).show();
     }
     else if (map.get("titre").equals(getString(R.string.menuStatistics)))
     {
  	if(isOnline())   
  	{
  		
			ProgDialog = ProgressDialog.show(gridmenu.this, "", 
			  	  	  "Loading. Please wait...", true);
			stat = false;
			 Thread thread = new Thread(gridmenu.this);
			   thread.start();

     }
  	else
  		Toast.makeText(gridmenu.this,getString(R.string.connectionErrorMsgSta),Toast.LENGTH_LONG).show();
     }
     else if (map.get("titre").equals(getString(R.string.menuExit)))
     {
  	   AlertDialog.Builder builder = new AlertDialog.Builder(gridmenu.this);
    		builder.setMessage(getString(R.string.menuExitDialog))
    		       .setCancelable(false)
    		       .setPositiveButton(getString(R.string.menuExitDialogYB), new DialogInterface.OnClickListener() {
    		           public void onClick(DialogInterface dialog, int id) {
    		        	 dialog.cancel();
    		        	 finish();
    	           	   Intent myIntent = new Intent (gridmenu.this, exit.class);
    	                  startActivity(myIntent);
    		        	         		           }
    		           })
    		           .setNegativeButton(getString(R.string.menuExitDialogNB), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
          	  finish();
         	   Intent myIntent = new Intent (gridmenu.this, exit.class);
                startActivity(myIntent);
                stopService(new Intent(gridmenu.this, smsreciver.class)); 
          	  dialog.cancel();
            }
        });
    		AlertDialog alert = builder.create();
    		alert.show();
  	    }
 }
       });    

}
protected void onResume()
{
super.onResume();
db = new DbHelper(getBaseContext());
}

protected void onDestroy() {
	super.onDestroy();
	db.close();
	}

	protected void onPause() {
		super.onPause();
		db.close();
	}


	public void run() {
      ServerIsAvailable();
       handler.sendEmptyMessage(0);

}

   private Handler handler = new Handler() {
       @Override
       public void handleMessage(Message msg) {
   
               ProgDialog.dismiss();
               if(ServerIsAvailable())
               {
            	  if (stat){
            		    Intent myIntent = new Intent (gridmenu.this, update.class);
                 	     startActivity(myIntent);  
                 	     stat=false;
            	  }
            	  else{
               	     Intent myIntent = new Intent (gridmenu.this, statistics.class);
              	     startActivity(myIntent);
            	  }
     
               }
               else 
            	   Toast.makeText(getApplicationContext(), getString(R.string.serverMsg), Toast.LENGTH_LONG).show();
               

       }
};
	
	}
