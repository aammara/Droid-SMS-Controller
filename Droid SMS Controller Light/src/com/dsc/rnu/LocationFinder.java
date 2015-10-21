package com.dsc.rnu;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationFinder extends Activity{
	double longitude;
	double latitude;
	public void onCreate()
	{
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
		Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		longitude = location.getLongitude();
		latitude = location.getLatitude();
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, locationListener);
	}
	String getPos ()	{	 return "Longitude : "+longitude;	}
	private final LocationListener locationListener = new LocationListener() {
	    public void onLocationChanged(Location location) {
	        longitude = location.getLongitude();
	        latitude = location.getLatitude();
	    }

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
	
	};
}