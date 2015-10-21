package com.dsc.rnu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class others extends Activity implements OnClickListener{
	String sound = "";
	ToggleButton tglbutton1;
	ToggleButton tglbutton2;
	Button saveMyNum;
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.others);
ArrayAdapter<CharSequence> sounds = ArrayAdapter.createFromResource(
this, R.array.sounds, android.R.layout.simple_spinner_item );
sounds.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
final Spinner s = (Spinner) findViewById( R.id.soundSpinner );
s.setAdapter( sounds );
DbHelper bdd= new DbHelper(getBaseContext());
if(bdd.getSOUND().equals("Danger"))
s.setSelection(0);
else if(bdd.getSOUND().equals("Scream"))
	s.setSelection(1);
else if(bdd.getSOUND().equals("Alarm Clock"))
	s.setSelection(2);
s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		    sound = s.getItemAtPosition(i).toString();
		    }
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
		});
		Button saveOthers = (Button) findViewById( R.id.saveOthers );
		saveOthers.setOnClickListener(this);
		Button addOthers = (Button) findViewById( R.id.addOthers );
		addOthers.setOnClickListener(this);
		Button playSound = (Button) findViewById( R.id.playSound );
		playSound.setOnClickListener(this);
		Button saveSound = (Button) findViewById( R.id.saveSound );
		saveSound.setOnClickListener(this);
		Button help = (Button) findViewById( R.id.helpButton );
		help.setOnClickListener(this);
		final DbHelper db= new DbHelper(getBaseContext());
		tglbutton1 = (ToggleButton) findViewById(R.id.toggleButton1);
		tglbutton1.setChecked(db.getAUTHO());
		tglbutton2 = (ToggleButton) findViewById(R.id.toggleButton2);
		tglbutton2.setChecked(db.getRecState());
		tglbutton1.setOnClickListener(this);
		tglbutton2.setOnClickListener(this);
		saveMyNum = (Button) findViewById(R.id.saveMyNum);
		saveMyNum.setOnClickListener(this);
		EditText myNum = (EditText) findViewById(R.id.myNum);
		myNum.setText(db.getMyNumber());
}
public void onClick(View v) {
	// TODO Auto-generated method stub
	DbHelper db= new DbHelper(getBaseContext());
	switch(v.getId())
    {
	case R.id.saveOthers :
		EditText pswd1 = (EditText)findViewById(R.id.editText1);
		EditText pswd2 = (EditText)findViewById(R.id.editText2);
		if (db.getPSWD().equals(pswd1.getText().toString()))
 	   {
 	   db.updatePSWD(pswd2.getText().toString());
 	  Toast.makeText(this, getString(R.string.otherPSWDToastPos), Toast.LENGTH_SHORT).show();
 	 pswd2.setText("");
 	pswd1.setText("");
 	   }
		else
			Toast.makeText(this, getString(R.string.otherPSWDToastNeg), Toast.LENGTH_SHORT).show();
		break;
	case R.id.addOthers :
		EditText num = (EditText)findViewById(R.id.editText3);
		if(db.allowedNUMBER(num.getText().toString())>0)
			Toast.makeText(this, getString(R.string.otherNEWnumToastNeg), Toast.LENGTH_SHORT).show();
		else
		{ db.insertNUM(num.getText().toString());
	 	  Toast.makeText(this, getString(R.string.otherNEWnumToastPos), Toast.LENGTH_SHORT).show();
	  	 num.setText("");
		}
		break;
	case R.id.playSound :
		MediaPlayer mediaPlayer = null;
		if (sound.equals("Danger"))
	    mediaPlayer = MediaPlayer.create(getBaseContext(),R.raw.danger);
		else if (sound.equals("Scream"))
		mediaPlayer = MediaPlayer.create(getBaseContext(),R.raw.scream);
		else if (sound.equals("Alarm Clock"))
		mediaPlayer = MediaPlayer.create(getBaseContext(),R.raw.alarmclock);
		mediaPlayer.setLooping(false);
		mediaPlayer.start();
		break;
	case R.id.saveSound :
		 db.updateSOUND(sound);
		 Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
		break;
	case R.id.helpButton :
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.otherNEWnumHelpButton));
		builder.setMessage(getString(R.string.otherNEWnumToastNeg))
		       .setCancelable(false)
		       .setPositiveButton(getString(R.string.menuPswdOkButton), new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
		break;
	case R.id.toggleButton1:
		if(tglbutton1.isChecked()==true)
		{
			db.updateAUTHO("ON");
		}
			else
			{
			db.updateAUTHO("OFF");
			}
		break;
		case R.id.toggleButton2 :
			final SpannableString stMyWeb = new SpannableString(getString(R.string.hyperLinkLink));
	        Linkify.addLinks(stMyWeb, Linkify.ALL);
		        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(others.this);
		        TextView Link = new TextView(others.this);
		        Link.setAutoLinkMask(1);
		        Link.setTextColor(Color.RED);
		        Link.setTextSize(20);
		        Link.setGravity(Gravity.CENTER);
		        Link.setText(getString(R.string.hyperLinkLink));
		   	    final LinearLayout layout = new LinearLayout(others.this);
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
		    		        		
			break;
		case R.id.saveMyNum :
			EditText myNum = (EditText) findViewById(R.id.myNum);
			db.updateMyNum(myNum.getText().toString());
			myNum.setText("");
			Toast.makeText(this, getString(R.string.otherNEWnumToastPos), Toast.LENGTH_SHORT).show();
			break;
		}
    }
}
