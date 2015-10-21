package com.dsc.rnu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;
import tn.idevelop.rnu.trial.R;

public class others extends Activity implements OnClickListener{
	String sound = "";
	ToggleButton tglbutton1;
	ToggleButton tglbutton2;
	Button saveMyNum;
	DbHelper bdd;
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.others);
ArrayAdapter<CharSequence> sounds = ArrayAdapter.createFromResource(
this, R.array.sounds, android.R.layout.simple_spinner_item );
sounds.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
final Spinner s = (Spinner) findViewById( R.id.soundSpinner );
s.setAdapter( sounds );
bdd = new DbHelper(getBaseContext());
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
		tglbutton1 = (ToggleButton) findViewById(R.id.toggleButton1);
		tglbutton1.setChecked(bdd.getAUTHO());
		tglbutton2 = (ToggleButton) findViewById(R.id.toggleButton2);
		tglbutton2.setChecked(bdd.getRecState());
		tglbutton1.setOnClickListener(this);
		tglbutton2.setOnClickListener(this);
		saveMyNum = (Button) findViewById(R.id.saveMyNum);
		saveMyNum.setOnClickListener(this);
		EditText myNum = (EditText) findViewById(R.id.myNum);
		myNum.setText(bdd.getMyNumber());
		bdd.close();
}
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch(v.getId())
    {
	case R.id.saveOthers :
		EditText pswd1 = (EditText)findViewById(R.id.editText1);
		EditText pswd2 = (EditText)findViewById(R.id.editText2);
		if (bdd.getPSWD().equals(pswd1.getText().toString()))
 	   {
			bdd.updatePSWD(pswd2.getText().toString());
 	  Toast.makeText(this, getString(R.string.otherPSWDToastPos), Toast.LENGTH_SHORT).show();
 	 pswd2.setText("");
 	pswd1.setText("");
 	bdd.InsertHistoric(getString(R.string.otherPSWDToastPos));
 	   }
		else
		{
			Toast.makeText(this, getString(R.string.otherPSWDToastNeg), Toast.LENGTH_SHORT).show();
			bdd.InsertHistoric(getString(R.string.otherPSWDToastNeg));
		}
		break;
	case R.id.addOthers :
		EditText num = (EditText)findViewById(R.id.editText3);
		if(bdd.allowedNUMBER(num.getText().toString())>0)
			Toast.makeText(this, getString(R.string.otherNEWnumToastNeg), Toast.LENGTH_SHORT).show();
		else
		{ bdd.insertNUM(num.getText().toString());
		bdd.InsertHistoric(getString(R.string.otherNEWnumToastPos)+" : "+num.getText().toString()); 
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
		else
		mediaPlayer = MediaPlayer.create(getBaseContext(),R.raw.alarmclock);
		mediaPlayer.setLooping(false);
		mediaPlayer.start();
		break;
	case R.id.saveSound :
		bdd.updateSOUND(sound);
		bdd.InsertHistoric(getString(R.string.otherSOUNDToast)+" : "+sound); 
		 Toast.makeText(this, getString(R.string.otherSOUNDToast), Toast.LENGTH_SHORT).show();
		break;
	case R.id.helpButton :
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.otherNEWnumHelpButton));
		builder.setMessage(getString(R.string.otherNEWnumHelpText))
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
			bdd.updateAUTHO("ON");
			bdd.InsertHistoric("authorisation updated : ON");
		}
			else
			{
				bdd.updateAUTHO("OFF");
				bdd.InsertHistoric("authorisation updated : OFF");
			}
		break;
		case R.id.toggleButton2 :
			if(tglbutton2.isChecked()==true)
			{
				bdd.updateRecState("ON");
				bdd.InsertHistoric("SIM Detector updated : ON");
				ComponentName receiver = new ComponentName(getBaseContext(), BootReceiver.class);
				PackageManager pm = getBaseContext().getPackageManager();
				pm.setComponentEnabledSetting(receiver,
				        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
				        PackageManager.DONT_KILL_APP);
			}
				else
				{
					bdd.updateRecState("OFF");
					bdd.InsertHistoric("SIM Detector updated : OFF");
				ComponentName receiver = new ComponentName(getBaseContext(), BootReceiver.class);
				PackageManager pm = getBaseContext().getPackageManager();
				pm.setComponentEnabledSetting(receiver,
				        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
				        PackageManager.DONT_KILL_APP);
				}
			break;
		case R.id.saveMyNum :
			EditText myNum = (EditText) findViewById(R.id.myNum);
			bdd.InsertHistoric(getString(R.string.otherNEWnumToastPos)+" : "+myNum); 
			bdd.updateMyNum(myNum.getText().toString());
			myNum.setText("");
			Toast.makeText(this, getString(R.string.otherNEWnumToastPos), Toast.LENGTH_SHORT).show();
			break;
		}
    }
protected void onResume()
{
super.onResume();
bdd = new DbHelper(getBaseContext());
}

protected void onDestroy() {
	super.onDestroy();
	bdd.close();
	}

	protected void onPause() {
		super.onPause();
		bdd.close();
	}
}
