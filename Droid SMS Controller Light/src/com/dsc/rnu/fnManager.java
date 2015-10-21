package com.dsc.rnu;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class fnManager extends Activity {
 void listFill()
 {
	 Resources res = getResources();
	 String action [] = res.getStringArray(R.array.actions);
        maListViewPerso = (ListView) findViewById(R.id.commandList);
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        int i;
        DbHelper db = new DbHelper(getBaseContext());
        for(i=0;i<action.length;i++)
        {
        map = new HashMap<String, String>();
        map.put("titre", action[i]);
        if((db.GetFuncId(i)!=null)&&(!db.GetFuncId(i).equals("")))
        map.put("description",getString(R.string.fnManagerMsg)+" : "+db.GetFuncId(i));
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
	        listFill();
	         maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
	    @SuppressWarnings("unchecked")
	           public void onItemClick(AdapterView<?> a, View v, final int position, long id) {
	        HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
	        if((position==0)||(position==12)||(position==13)||(position==14))
	        {
	        	Bundle bundle = new Bundle();
	        bundle.putString("value",map.get("titre"));
	        final AlertDialog.Builder alert = new AlertDialog.Builder(fnManager.this);
	   	    final LinearLayout layout = new LinearLayout(fnManager.this);
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
	    		        		        	   if(funid.getText().toString()!="")
	    		        		        	   db.updateFunc(position+"", funid.getText().toString());
	    		        		        	   else
	    		        		        	   db.updateFunc(position+"","NULL");
	    		        		         	   Toast.makeText(getBaseContext(), getString(R.string.fnManagerToast), Toast.LENGTH_SHORT).show();
	    		        		        	   db.close();
	    		        		        	   listFill();
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
	        { final SpannableString stMyWeb = new SpannableString(getString(R.string.hyperLinkLink));
	        Linkify.addLinks(stMyWeb, Linkify.ALL);
		        final AlertDialog.Builder alert = new AlertDialog.Builder(fnManager.this);
		        TextView Link = new TextView(fnManager.this);
		        Link.setAutoLinkMask(1);
		        Link.setTextColor(Color.RED);
		        Link.setTextSize(20);
		        Link.setGravity(Gravity.CENTER);
		        Link.setText(getString(R.string.hyperLinkLink));
		   	    final LinearLayout layout = new LinearLayout(fnManager.this);
		   	    layout.setOrientation(LinearLayout.VERTICAL);
		    	    alert.setView(layout);
		    	    alert.setView(Link);
		    	    alert.setTitle(getString(R.string.hyperLinkTitle))
		    	    .setMessage(getString(R.string.hyperLinkText))
		    		       .setCancelable(false)
		    		        		       .setPositiveButton(getString(R.string.menuExit), new DialogInterface.OnClickListener() {
		    		        		           public void onClick(DialogInterface dialog, int id) {
		    		        		        	   dialog.cancel();
		    		        		        	 }
		    		        		           
		    	          });
		    		        		AlertDialog alertDia = alert.create();
		    		        		alertDia.show();
		    		        		
	        }
	    }
	    });
}
}
