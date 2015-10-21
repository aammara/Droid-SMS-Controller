package com.dsc.rnu;

import java.util.ArrayList;
import java.util.HashMap;
import tn.idevelop.rnu.trial.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class historic extends Activity{
	ListView list;
	DbHelper db;
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
    setContentView(R.layout.historic);
    list = (ListView) findViewById(R.id.ListView02);
	}
	
void fillList()
{
    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
    final ListView list = (ListView)findViewById(R.id.ListView02);
    Cursor c = db.getHistoric();
    startManagingCursor(c);
    if(c.getCount()>0)
    {
   if(c!=null)
   {
    do
    {
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("action", c.getString(0));
    	map.put("date",  c.getString(1));
    	map.put("time",c.getString(2));
    	mylist.add(map);
    }
    while(c.moveToNext());
   }
}
    SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), mylist, R.layout.historicrow,
    new String[] {"action", "date", "time"}, new int[] { R.id.action, R.id.date, R.id.time});
    list.setAdapter(mSchedule);
    c.close();
}
protected void onResume()
{
super.onResume();
db = new DbHelper(getBaseContext());
fillList();
}

protected void onDestroy() {
    super.onDestroy();
	db.close();
}

protected void onPause() {
	super.onPause();
	db.close();
}
}
