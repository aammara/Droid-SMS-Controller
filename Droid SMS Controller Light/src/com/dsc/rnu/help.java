package com.dsc.rnu;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class help extends Activity {
	void dialog(String msg)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg)
		       .setCancelable(false)
		       .setPositiveButton(getString(R.string.menuPswdOkButton), new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	
}
/** Called when the activity is first created. */
	private ListView maListViewPerso;
	/** Called when the activity is first created. */
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 Resources res = getResources();
		 final String action [] = res.getStringArray(R.array.actions);
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.help);
	        maListViewPerso = (ListView) findViewById(R.id.helplist);
	        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
	        HashMap<String, String> map;
	        int i;
	        for(i=0;i<action.length;i++)
	        {
	        map = new HashMap<String, String>();
	        map.put("titre", action[i]);
	        map.put("description", "");
	        map.put("img", String.valueOf(R.drawable.help));
	        listItem.add(map);
	        }
	        
	        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.row,
	        new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});
	        maListViewPerso.setAdapter(mSchedule);
	         maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
	    @SuppressWarnings("unchecked")
	           public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	    	Resources res = getResources();
			String desc [] = res.getStringArray(R.array.descriptions);
	        HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
	    if(map.get("titre").equals(action[0]))
	    {
	    	dialog(desc[0]);
	    }
	    else if(map.get("titre").equals(action[1]))
	    {
	    	dialog(desc[1]);
	    }
	    else if(map.get("titre").equals(action[2]))
	    {
	    	dialog(desc[2]);
	    }
	    else if(map.get("titre").equals(action[3]))
	    {
	    	dialog(desc[3]);
	    }
	    else if(map.get("titre").equals(action[4]))
	    {
	    	dialog(desc[4]);
	    }
	    else if(map.get("titre").equals(action[5]))
	    {
	    	dialog(desc[5]);
	    }
	    else if(map.get("titre").equals(action[6]))
	    {
	    	dialog(desc[6]);
	    }
	    else if(map.get("titre").equals(action[7]))
	    {
	    	dialog(desc[7]);
	    }
	    else if(map.get("titre").equals(action[8]))
	    {
	    	dialog(desc[8]);
	    }
	    else if(map.get("titre").equals(action[9]))
	    {
	    	dialog(desc[9]);
	    }
	    else if(map.get("titre").equals(action[10]))
	    {
	    	dialog(desc[10]);
	    }
	    else if(map.get("titre").equals(action[11]))
	    {
	    	dialog(desc[11]);
	    }
	    else if(map.get("titre").equals(action[12]))
	    {
	    	dialog(desc[12]);
	    }
	    else if(map.get("titre").equals(action[13]))
	    {
	    	dialog(desc[13]);
	    }
	    else if(map.get("titre").equals(14))
	    {
	    	dialog(desc[14]);
	    }
	    else if(map.get("titre").equals(action[15]))
	    {
	    	dialog(desc[15]);
	    }
	    else if(map.get("titre").equals(action[16]))
	    {
	    	dialog(desc[16]);
	    }
	    }
	    });
}
}
