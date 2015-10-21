package com.dsc.rnu;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
public class menu extends Activity {
 
 private ListView maListViewPerso;
    /** Called when the activity is first created. */
 
 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);        
        
        maListViewPerso = (ListView) findViewById(android.R.id.list);
 
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
 
        HashMap<String, String> map;
        
        map = new HashMap<String, String>();
        map.put("titre", getString(R.string.menuNew));
        map.put("description", "");
        map.put("img", String.valueOf(R.drawable.newsms));
        listItem.add(map);
 
        map = new HashMap<String, String>();
        map.put("titre", getString(R.string.menuTerminal));
        map.put("description", "");
        map.put("img", String.valueOf(R.drawable.terminal));
        listItem.add(map);
 
        map = new HashMap<String, String>();
        map.put("titre", getString(R.string.menuFunctionsManager));
        map.put("description", "");
        map.put("img", String.valueOf(R.drawable.functions_manager));
        listItem.add(map);
        
        map = new HashMap<String, String>();
        map.put("titre", getString(R.string.menuSettings));
        map.put("description", "");
        map.put("img", String.valueOf(R.drawable.settings));
        listItem.add(map);
        
        map = new HashMap<String, String>();
        map.put("titre", getString(R.string.menuUpdate));
        map.put("description", "");
        map.put("img", String.valueOf(R.drawable.update));
        listItem.add(map);
                
        map = new HashMap<String, String>();
        map.put("titre", getString(R.string.menuHelp));
        map.put("description", "");
        map.put("img", String.valueOf(R.drawable.help));
        listItem.add(map);
        
        map = new HashMap<String, String>();
        map.put("titre", getString(R.string.menuExit));
        map.put("description", "");
        map.put("img", String.valueOf(R.drawable.exit));
        listItem.add(map);
        
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.row,
               new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});
        maListViewPerso.setAdapter(mSchedule);
        maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
   @SuppressWarnings("unchecked")
          public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	   ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
       HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
       String str = "";
       if(map.get("titre").equals(getString(R.string.menuNew)))
       {    
    	   Intent myIntent = new Intent (menu.this, newsms.class);
           startActivity(myIntent);
       }
       else if (map.get("titre").equals(getString(R.string.menuTerminal))) 
       {
    	   Intent myIntent = new Intent (menu.this, terminal.class);
       startActivity(myIntent);
       }
       else if (map.get("titre").equals(getString(R.string.menuFunctionsManager)))
       {
    	   Intent myIntent = new Intent (menu.this, fnManager.class);
       startActivity(myIntent);
       }
       else if (map.get("titre").equals(getString(R.string.menuSettings)))
       {   DbHelper db= new DbHelper(getBaseContext());
    	   if (!db.getPSWD().equals(""))
    	   {  
    	 final AlertDialog.Builder alert = new AlertDialog.Builder(menu.this);
   	    final LinearLayout layout = new LinearLayout(menu.this);
   	    layout.setOrientation(LinearLayout.VERTICAL);
   	    final EditText pswd = new EditText(menu.this);
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
    		        		        	   DbHelper db= new DbHelper(getBaseContext());
    		        		        	   if (db.getPSWD().equals(pswd.getText().toString()))
    		        		        	   {
    		        		        	   Intent myIntent = new Intent (menu.this, settings.class);
    		        		           startActivity(myIntent);
    		        		        	   }
    		        		        	   else
    		        		        	   Toast.makeText(menu.this,getString(R.string.menuPswdError), Toast.LENGTH_LONG).show();
    		        		        	   dialog.cancel();
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
    		   Intent myIntent = new Intent (menu.this, settings.class);
	           startActivity(myIntent);
    	   }
       }
       else if (map.get("titre").equals(getString(R.string.menuHelp)))
       {Intent myIntent = new Intent (menu.this, help.class);
       startActivity(myIntent);
       }
       else if (map.get("titre").equals(getString(R.string.menuUpdate)))
       {
    	   Intent myIntent = new Intent (menu.this, update.class);
       startActivity(myIntent);
       }
       else if (map.get("titre").equals(getString(R.string.menuExit)))
       {
    	   AlertDialog.Builder builder = new AlertDialog.Builder(menu.this);
      		builder.setMessage(getString(R.string.menuExitDialog))
      		       .setCancelable(false)
      		       .setPositiveButton(getString(R.string.menuExitDialogYB), new DialogInterface.OnClickListener() {
      		           public void onClick(DialogInterface dialog, int id) {
      		        	 dialog.cancel();
      		        	 finish();
      	           	   Intent myIntent = new Intent (menu.this, exit.class);
      	                  startActivity(myIntent);
      		        	         		           }
      		           })
      		           .setNegativeButton(getString(R.string.menuExitDialogNB), new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
            	  finish();
           	   Intent myIntent = new Intent (menu.this, exit.class);
                  startActivity(myIntent);
                  stopService(new Intent(menu.this, smsreciver.class)); 
            	  dialog.cancel();
              }
          });
      		AlertDialog alert = builder.create();
      		alert.show();
    	    }
	   postParameters.add(new BasicNameValuePair("action", str));
	   //String response = null;
   	try {
   	    
   	   //String res=response.toString();
   	   // res = res.trim();
   	   //res= res.replaceAll("\\s+","");         	              	 
   	} 
   	catch (Exception e) {}
   }
         });    
    
    }
}