package com.dsc.rnu;

import java.util.ArrayList;
import java.util.HashMap;
import tn.idevelop.rnu.trial.R;


import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class fnManager extends SherlockActivity {
	String action [];
	void listFill()
 {
	 Resources res = getResources();
	 DbHelper db = new DbHelper(getBaseContext());
	 action = res.getStringArray(R.array.actions);
	 
        maListViewPerso = (ListView) findViewById(R.id.commandList);
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        int i;
        for(i=0;i<action.length;i++)
        {
        map = new HashMap<String, String>();
        map.put("titre", action[i]);
        if((db.GetFuncId(i)!=null)&&(!db.GetFuncId(i).equals("null")))
        map.put("description",getString(R.string.fnManagerDesc)+" : "+db.GetFuncId(i));
        else
        map.put("description", getString(R.string.fnManagerMsgNonId));
        map.put("img", String.valueOf(R.drawable.functions_manager));
        listItem.add(map);
        }
        db.close();	 
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.row,
		        new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});
		        maListViewPerso.setAdapter(mSchedule); 
 }
	 private ListView maListViewPerso;
	/** Called when the activity is first created. */
	 
	    public void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	        setContentView(R.layout.newsms);
	        
	        com.actionbarsherlock.app.ActionBar mActionBar = getSupportActionBar();
	        mActionBar.setHomeButtonEnabled(true);
	        mActionBar.setIcon(R.drawable.home);
	        mActionBar.setTitle("");
	   	 
	        listFill();
	         maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
	    @SuppressWarnings("unchecked")
	           public void onItemClick(AdapterView<?> a, View v, final int position, long id) {
	        HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
	        Bundle bundle = new Bundle();
	        bundle.putString("value",map.get("titre"));
	        final AlertDialog.Builder alert = new AlertDialog.Builder(fnManager.this);
	   	    final LinearLayout layout = new LinearLayout(fnManager.this);
	   	    layout.setPadding(10, 10, 10, 10);
	   	    layout.setOrientation(LinearLayout.VERTICAL);
	   	    final EditText funid = new EditText(fnManager.this);
	   	    funid.setGravity(Gravity.CENTER);
	   	    funid.setWidth(200);
	    	    layout.addView(funid);
	    	    alert.setView(layout);
	    	    alert.setTitle(getString(R.string.fnManagerTitle));
	    		alert.setMessage(map.get("titre")+" : "+getString(R.string.fnManagerMsg))
	    		       .setCancelable(false)
	    		        		       .setPositiveButton(getString(R.string.menuPswdOkButton), new DialogInterface.OnClickListener() {
	    		        		           public void onClick(DialogInterface dialog, int id) {
	    		        		        	   DbHelper db = new DbHelper(getBaseContext());
	    		        		        	   if(!funid.getText().toString().equals(""))
	    		        		        	   { int j = 0;
	    		        		        	   int i = 0;
	    		        		        	   while(j<16)
    		        		        		   {   
	    		        		        	if((db.GetFuncId(j)!=null)&&(!db.GetFuncId(j).equals("null")))
    		        		        		   {
    		        		        			   if(db.GetFuncId(j).equals(funid.getText().toString()))
    		        		        	 	   { i=j;
    		        		        				   j=16;
    		        		        			   }
    		        		        			}
	    		        		        		   j++;}
	    		        		        		   if((j==16)||(position==j))
	    		        		        		   {
	    		        		        			   db.updateFunc(position+"", funid.getText().toString());
	    		        		        			   Toast.makeText(getBaseContext(), getString(R.string.fnManagerToast), Toast.LENGTH_LONG).show();
	    		        		        			   listFill();
	    	    		        		        	   dialog.cancel();
	    		        		        		   }
	    		        		        		   else
	    		        		        		   Toast.makeText(getBaseContext(), getString(R.string.fnManagerToastError)+" "+action[i], Toast.LENGTH_LONG).show();
	    		        		        	   }
	    		        		        	   else
	    		        		        	   {
	    		        		        	   db.updateFunc(position+"",null);
	    		        		         	   Toast.makeText(getBaseContext(), getString(R.string.fnManagerToast), Toast.LENGTH_LONG).show();
	    		        		         	  listFill();
	    		        		        	   dialog.cancel();
	    		        		        	   }
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
	    });
}
	    protected void onDestroy() {        
	        super.onDestroy();
	        DbHelper db = new DbHelper(getBaseContext());
	        db.close();
	    }
	    
	    protected void onPause() {        
	    	super.onPause();
	    	DbHelper db = new DbHelper(getBaseContext());
	    	db.close();
	    }
	    
	    @Override 
	    public  boolean onCreateOptionsMenu(Menu menu) {
	     
	     	   menu.add(0, 0, 0, "HELP").setIcon(R.drawable.help_actionbar).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	         return true;

	     }
	    
	    void helpDialog ()
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(R.string.menuHelp));
			builder.setMessage(getString(R.string.menuHelpText))
			       .setCancelable(false)
			       .setPositiveButton("  OK  ", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {

			        	   dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
	    
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // app icon in action bar clicked; go home
	    	if(item.getTitle().equals("HELP"))
	    	{
	        helpDialog();
	    	}
	    	else
	    	{
	        Intent intentHome = new Intent(this, gridmenu.class);
	        intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        startActivity(intentHome);
	    	}
	        return true;
	        }
}
