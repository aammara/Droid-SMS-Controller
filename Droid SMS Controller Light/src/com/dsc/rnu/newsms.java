package com.dsc.rnu;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class newsms extends Activity {
	
	 private ListView maListViewPerso;
	/** Called when the activity is first created. */
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 Resources res = getResources();
		 String action [] = res.getStringArray(R.array.actions);
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.newsms);
	        maListViewPerso = (ListView) findViewById(R.id.commandList);
	        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
	        HashMap<String, String> map;
	        int i;
	        for(i=0;i<action.length;i++)
	        {
	        map = new HashMap<String, String>();
	        map.put("titre", action[i]);
	        map.put("description", "");
	        map.put("img", String.valueOf(R.drawable.newsms));
	        listItem.add(map);
	        }
	        
	        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.row,
	        new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});
	        maListViewPerso.setAdapter(mSchedule);
	         maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
	    @SuppressWarnings("unchecked")
	           public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	        HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
	        if((position==0)||(position==12)||(position==13)||(position==14))
	        {
	        Bundle bundle = new Bundle();
	        bundle.putString("value",map.get("titre"));
	        Intent newIntent = new Intent(newsms.this, sendsms.class);
	        newIntent.putExtras(bundle);
	        startActivityForResult(newIntent, 0);
	        }
	        else
	        { final SpannableString stMyWeb = new SpannableString(getString(R.string.hyperLinkLink));
	        Linkify.addLinks(stMyWeb, Linkify.ALL);
		        final AlertDialog.Builder alert = new AlertDialog.Builder(newsms.this);
		        TextView Link = new TextView(newsms.this);
		        Link.setAutoLinkMask(1);
		        Link.setTextColor(Color.RED);
		        Link.setTextSize(20);
		        Link.setGravity(Gravity.CENTER);
		        Link.setText(getString(R.string.hyperLinkLink));
		   	    final LinearLayout layout = new LinearLayout(newsms.this);
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