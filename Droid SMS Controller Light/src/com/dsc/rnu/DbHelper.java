package com.dsc.rnu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DbHelper extends SQLiteOpenHelper {
final static String dbName="dsc";
final String numCOLUMN="number";
final String deptTable="numbers";
final String pswdTable="password";
final String soundTable="sound";
final String authoTable="autho";
final String myNumTable="mynumber";
final String myFunTable="myfunc";
private SQLiteDatabase bdd = getReadableDatabase();
public DbHelper(Context context) {
super(context, dbName, null,2); 
}
@Override
public void onCreate(SQLiteDatabase db) 
{
// TODO Auto-generated method stub   	  
db.execSQL("CREATE TABLE IF NOT EXISTS "+deptTable+" (number TEXT PRIMARY KEY )");
db.execSQL("CREATE TABLE IF NOT EXISTS "+pswdTable+" (pswd TEXT PRIMARY KEY )");
ContentValues val=new ContentValues();
val.put("pswd", "");
db.insert(pswdTable, null, val); 
db.execSQL("CREATE TABLE IF NOT EXISTS "+soundTable+" (snd TEXT PRIMARY KEY )");
val.clear();
val.put("snd", "Danger");
db.insert(soundTable, null, val);
db.execSQL("CREATE TABLE IF NOT EXISTS "+authoTable+" (status TEXT PRIMARY KEY )");
val.clear();
val.put("status", "OFF");
db.insert(authoTable, null, val);
db.execSQL("CREATE TABLE IF NOT EXISTS "+myNumTable+" (num TEXT PRIMARY KEY,  rec TEXT)");
val.clear();
val.put("num", "");
val.put("rec", "OFF");
db.insert(myNumTable, null, val);
db.execSQL("CREATE TABLE IF NOT EXISTS "+myFunTable+" (fun TEXT PRIMARY KEY,  funid TEXT)");
val.clear();
val.put("fun", "0");
db.insert(myFunTable, null, val);
val.put("fun", "1");
db.insert(myFunTable, null, val);
val.put("fun", "2");
db.insert(myFunTable, null, val);
val.put("fun", "3");
db.insert(myFunTable, null, val);
val.put("fun", "4");
db.insert(myFunTable, null, val);
val.put("fun", "5");
db.insert(myFunTable, null, val);
val.put("fun", "6");
db.insert(myFunTable, null, val);
val.put("fun", "7");
db.insert(myFunTable, null, val);
val.put("fun", "8");
db.insert(myFunTable, null, val);
val.put("fun", "9");
db.insert(myFunTable, null, val);
val.put("fun", "10");
db.insert(myFunTable, null, val);
val.put("fun", "11");
db.insert(myFunTable, null, val);
val.put("fun", "12");
db.insert(myFunTable, null, val);
val.put("fun", "13");
db.insert(myFunTable, null, val);
val.put("fun", "14");
db.insert(myFunTable, null, val);
val.put("fun", "15");
db.insert(myFunTable, null, val);
//Inserts pre-defined departments  
}
 
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
{
// TODO Auto-generated method stub   	  
db.execSQL("DROP TABLE IF EXISTS "+deptTable);
db.execSQL("DROP TABLE IF EXISTS "+pswdTable);
db.execSQL("DROP TABLE IF EXISTS "+soundTable);
db.execSQL("DROP TABLE IF EXISTS "+deptTable);
db.execSQL("DROP TABLE IF EXISTS "+myNumTable);
db.execSQL("DROP TABLE IF EXISTS "+myFunTable);
onCreate(db);
}
    
public void insertNUM(String num)
{
SQLiteDatabase db=this.getWritableDatabase();
ContentValues numToAdd=new ContentValues();
numToAdd.put("number", num);
db.insert(deptTable, null, numToAdd);
}

public void updatePSWD(String pswd)
{
bdd.execSQL("UPDATE password SET pswd = '"+pswd+"';");
bdd.close();
}

public void updateSOUND(String snd)
{
bdd.execSQL("UPDATE sound SET snd = '"+snd+"';");
bdd.close();
}
 
public void remove(String num)
{
bdd.execSQL("DELETE FROM numbers WHERE number = '"+num+"';");
bdd.close();
}
     

String getPSWD()
{
Cursor cur=bdd.rawQuery("SELECT * from password",new String [] {});
if(cur.moveToFirst())
return cur.getString(cur.getColumnIndex("pswd"));
bdd.close();
return null;
}

String getSOUND()
{
Cursor cur=bdd.rawQuery("SELECT * from sound",new String [] {});
if(cur.moveToFirst())
return cur.getString(cur.getColumnIndex("snd"));
bdd.close();
return null;
}

boolean getAUTHO()
{
Cursor cur=bdd.rawQuery("SELECT * from autho",new String [] {});
if(cur.moveToFirst())
{
if (cur.getString(cur.getColumnIndex("status")).equals("ON"))
return true;
else
return false;
}
bdd.close();
return false;
}

public void updateAUTHO(String stts)
{
bdd.execSQL("UPDATE autho SET status = '"+stts+"';");
return;
}

public Cursor getNUMBERS() {
	// TODO Auto-generated method stub
	Cursor cur=bdd.rawQuery("SELECT * FROM numbers",new String [] {});
	cur.moveToFirst();
	return cur;
}

public int allowedNUMBER(String num) {
	// TODO Auto-generated method stub
	Cursor dataCount = bdd.rawQuery("select count(*) from numbers WHERE number = "+num, null);
    dataCount.moveToFirst();
    int jcount = dataCount.getInt(0);
    dataCount.close();
    bdd.close();
    return jcount;
    
}

public String getMyNumber() {
	Cursor cur=bdd.rawQuery("SELECT * from mynumber",new String [] {});
	if(cur.moveToFirst())
	return cur.getString(cur.getColumnIndex("num"));
	return "";
	// TODO Auto-generated method stub
}
public boolean getRecState() {
	Cursor cur=bdd.rawQuery("SELECT * from mynumber",new String [] {});
	if(cur.moveToFirst())
	{
	if (cur.getString(cur.getColumnIndex("rec")).equals("ON"))
	return true;
	else
	return false;
	}
	bdd.close();
	return false;
	// TODO Auto-generated method stub
}
public void updateRecState(String string) {
	// TODO Auto-generated method stub
	bdd.execSQL("UPDATE mynumber SET rec = '"+string+"';");
	return;
}
public void updateMyNum(String string) {
	// TODO Auto-generated method stub
	bdd.execSQL("UPDATE mynumber SET num = '"+string+"';");
	return;
}
public void updateFunc(String fun, String funid) {
	// TODO Auto-generated method stub
	bdd.execSQL("UPDATE myfunc SET funid = '"+funid+"' WHERE fun = "+fun+";");
	return;
}
public void close()
{
    super.close();
    if ( bdd != null )
    {
        bdd.close();
    }
}

/**
 * Closes cursor without closing database.
 */
public void closeForReuse()
{
    super.close();
}
public String GetFuncId(int i) {
	Cursor cur=bdd.rawQuery("SELECT * from myfunc WHERE fun = "+i,new String [] {});
	if(cur.moveToFirst())
	return cur.getString(cur.getColumnIndex("funid"));
	return "";
	// TODO Auto-generated method stub
}
}