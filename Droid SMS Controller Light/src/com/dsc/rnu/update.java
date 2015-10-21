package com.dsc.rnu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class update extends Activity {
	    /** Called when the activity is first created. */
	 
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.update);
	        Button send = (Button)findViewById(R.id.update);
	    	send.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	          		        	 final SpannableString stMyWeb = new SpannableString(getString(R.string.hyperLinkLink));
	          			        Linkify.addLinks(stMyWeb, Linkify.ALL);
	          				        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(update.this);
	          				        TextView Link = new TextView(update.this);
	          				        Link.setAutoLinkMask(1);
	          				        Link.setTextColor(Color.RED);
	          				        Link.setTextSize(20);
	          				        Link.setGravity(Gravity.CENTER);
	          				        Link.setText(getString(R.string.hyperLinkLink));
	          				   	    final LinearLayout layout = new LinearLayout(update.this);
	          				   	    layout.setOrientation(LinearLayout.VERTICAL);
	          				   	 alertDialog.setView(layout);
	          				   	alertDialog.setView(Link);
	          				   	alertDialog.setTitle(getString(R.string.hyperLinkTitle))
	          				    	    .setMessage(getString(R.string.hyperLinkText))
	          				    		       .setCancelable(false)
	          				    		        		       .setPositiveButton(getString(R.string.menuExit), new DialogInterface.OnClickListener() {
	          				    		        		           public void onClick(DialogInterface dialog, int id) {
	          				    		        		        	   dialog.cancel();
	          				    		        		        	 }
	          				    		        		           
	          				    	          });
	          				    		        		AlertDialog alertDia = alertDialog.create();
	          				    		        		alertDia.show();
	          	
	            }	});
}
}