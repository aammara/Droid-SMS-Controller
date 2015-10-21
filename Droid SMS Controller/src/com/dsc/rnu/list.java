package com.dsc.rnu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import tn.idevelop.rnu.trial.R;

public class list extends Activity{
	ListView list;
	DbHelper db;
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
    setContentView(R.layout.list);
    list = (ListView) findViewById(R.id.ListView01);
    fillList();
    list.setOnItemLongClickListener(new OnItemLongClickListener() 
    {
public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
{
deleteChoice(list.getItemAtPosition(arg2).toString());
	return false;
}
  		});
	}
	void deleteChoice(final String num)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.deleteTitle));
		builder.setMessage(getString(R.string.deleteMsg)+" "+num+"?")
		       .setCancelable(false)
		       .setPositiveButton(getString(R.string.deleteButton), new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	    db = new DbHelper(getBaseContext());
		        	    db.InsertHistoric("Delete / effacer : "+num); 
		        	   db.remove(num);
		        	   fillList();
		        	   dialog.cancel();
		        	   db.close();
		           }
		       })
		       .setNegativeButton(getString(R.string.menuPswdCancelButton), new DialogInterface.OnClickListener() {
 	              public void onClick(DialogInterface dialog, int id) {
 	            	  dialog.cancel();
 	              }
 	           });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
void fillList()
{
    db = new DbHelper(getBaseContext());
    Cursor c = db.getNUMBERS();
   startManagingCursor(c);
   String numbers[]=new String[c.getCount()];
   if(c.getCount()>0)
   {
  int i = 0;
  if(c!=null)
  {
   do
   {
	   String str = c.getString(0);
	   numbers[i] = str;
	   i++;
   }
   while(c.moveToNext());
  }
  TextView text = (TextView) findViewById(R.id.noNUM);
  if(c.getCount()>1)
  text.setText(c.getCount()+" "+getString(R.string.numFound));
  else
  text.setText(getString(R.string.oneNumFound));
  list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , numbers));
   }
   else
   {
	   TextView text = (TextView) findViewById(R.id.noNUM);
   text.setText(getString(R.string.noNumFound));
   list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , numbers));
   }
db.close();
c.close();
}
protected void onResume()
{
super.onResume();
fillList();
}

protected void onDestroy() {
    db.close();
    super.onDestroy();
}

protected void onPause() {
	db.close();
	super.onPause();
}
}
