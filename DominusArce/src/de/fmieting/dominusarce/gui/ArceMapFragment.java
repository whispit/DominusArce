/**
 * ArceMapFragment:
 * 
 * Extension of the SupportMapFragment for use with the "Dominum Arce" application.
 * Enables keeping / saving map zoom and position between fragment or app changes. 
 * 
 * Fabian Mieting, 2013-04-11
 * 
 */
package de.fmieting.dominusarce.gui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import de.fmieting.dominusarce.R;

/**
 * @author fmieting
 *
 */
public class ArceMapFragment extends SupportMapFragment {

	private LatLng currentPosition = new LatLng(51.051107,13.73909);	//Defaults to Dresden,
	private float currentZoom = 12.0f;									//Complete overview
	private float currentTilt = 0.0f;
	private float currentBearing = 0.0f;
	
	private static final String LONGITUDE = "arcemapfragment_longitude", 
								LATITUDE = "arcemapfragment_latitude",	
								ZOOM = "arcemapfragment_zoom",
								TILT = "arcemapfragment_tilt",
								BEARING = "arcemapfragment_bearing";
	
	//XXX:
	private static final LatLng RR41 = new LatLng(51.04532, 13.693635),
								ECKE_WEITER = new LatLng(51.046419,13.693219),
								CONERT = new LatLng(51.045681,13.691443);

	private Bitmap playerTower = null;
	
//-----------INITIALISATION--------------------------------//
	
	public ArceMapFragment() {
		super();
	}
	
	public static ArceMapFragment newInstance(LatLng startPosition, float startZoom, float startTilt, float startBearing){
		ArceMapFragment fragment = new ArceMapFragment();
		fragment.currentPosition = startPosition;
		fragment.currentZoom = startZoom;
		fragment.currentTilt = startTilt;
		fragment.currentBearing = startBearing;
		return fragment;		
	}
	
//-----------LIFECYCLE MANAGEMENT--------------------------------//
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRetainInstance(true);
		this.setHasOptionsMenu(true);
		
		//Create color
		Paint p = new Paint(Color.RED);
		ColorFilter filter = new LightingColorFilter(0xffaaaa, 0x220000);	//tint the tower red
		p.setColorFilter(filter);
		Bitmap tmp = BitmapFactory.decodeResource(getResources(), R.drawable.tower);
		playerTower = tmp.copy(Config.ARGB_8888, true);
		Canvas cv = new Canvas(playerTower);
		cv.drawBitmap(tmp, 0, 0, p);
		
		//Read settings & last position
		SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
		double lat = Double.longBitsToDouble(prefs.getLong(LATITUDE, Double.doubleToLongBits(currentPosition.latitude)));
		double lon = Double.longBitsToDouble(prefs.getLong(LONGITUDE, Double.doubleToLongBits(currentPosition.longitude)));
		
		currentPosition = new LatLng(lat, lon);
		currentZoom = prefs.getFloat(ZOOM, 12.0f);
		currentTilt = prefs.getFloat(TILT, 0.0f);
		currentBearing = prefs.getFloat(BEARING, 0.0f);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		setupMap();
		getMap().setMyLocationEnabled(true);
		
		//Restore last camera position
		if(savedInstanceState != null){
			CameraUpdate update = CameraUpdateFactory
			.newCameraPosition(new CameraPosition(	new LatLng(	savedInstanceState.getDouble(LATITUDE), savedInstanceState.getDouble(LONGITUDE)),
													savedInstanceState.getFloat(ZOOM),
													savedInstanceState.getFloat(TILT),
													savedInstanceState.getFloat(BEARING)));
			getMap().moveCamera(update);
			Log.d("ArceMapFragment","Restoring instance state...");
		} else {
			//Testing
			addExampleTowers();
		}
		
		return view;
	}
	
	@Override
	public void onResume() {
		CameraUpdate update = CameraUpdateFactory.newCameraPosition(new CameraPosition(currentPosition,currentZoom,currentTilt,currentBearing));
		getMap().moveCamera(update);		
		
		super.onResume();
		Log.d("ArceMapFragment","Resuming... with " + currentPosition.latitude + " " + currentPosition.longitude + " " + currentZoom + " " + currentTilt + " " + currentBearing);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putDouble(LATITUDE, getMap().getCameraPosition().target.latitude);
		outState.putDouble(LONGITUDE, getMap().getCameraPosition().target.longitude);
		outState.putFloat(ZOOM, getMap().getCameraPosition().zoom);
		outState.putFloat(TILT, getMap().getCameraPosition().tilt);
		outState.putFloat(BEARING, getMap().getCameraPosition().bearing);
		Log.d("ArceMapFragment","Saving instance state...");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor ed = prefs.edit();
		ed.putLong(LATITUDE, Double.doubleToLongBits(getMap().getCameraPosition().target.latitude));
		ed.putLong(LONGITUDE, Double.doubleToLongBits(getMap().getCameraPosition().target.longitude));
		ed.putFloat(ZOOM, getMap().getCameraPosition().zoom);
		ed.putFloat(TILT, getMap().getCameraPosition().tilt);
		ed.putFloat(BEARING, getMap().getCameraPosition().bearing);
		ed.commit();
		
		Log.d("ArceMapFragment","Stopping... put " 	+ getMap().getCameraPosition().target.latitude + " " 
													+ getMap().getCameraPosition().target.longitude + " "
													+ getMap().getCameraPosition().zoom + " "
													+ getMap().getCameraPosition().tilt + " "
													+ getMap().getCameraPosition().bearing);
	}
	
	
//-----------MENU HANDLERS ETC.--------------------------------//
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		//Add options menu entry
		inflater.inflate(R.menu.mapviewfragment_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String text = "";
		switch(item.getItemId()){
		case R.id.action_addtower:
				text = "Building Tower...";
			break;
		case R.id.action_sync:
				text = "Synchronizing...";
			break;
		default:
			return false;
		}
		
		Context context = getActivity().getApplicationContext();
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
		return super.onOptionsItemSelected(item);
	}
	
	
//-----------AUXILIARY METHODS--------------------------------//

	private void setupMap() {
		
		getMap().setMapType(GoogleMap.MAP_TYPE_HYBRID);
		
		UiSettings uiset = getMap().getUiSettings();
			uiset.setCompassEnabled(false);
			uiset.setRotateGesturesEnabled(false);
			uiset.setZoomControlsEnabled(false);			
	}
	
	//XXX:
	private void addExampleTowers(){
		// Add some towers & a first city
		PolygonOptions city = new PolygonOptions();
			city.add(RR41,ECKE_WEITER,CONERT,RR41);
			city.strokeColor(Color.RED - 0x00220000);	//not-so-bright red
			city.fillColor(Color.RED - 0xaa220000);		//not-so-bright, transparent red
		
		getMap().addPolygon(city);
		
		MarkerOptions opt = new MarkerOptions();
			opt.title("Home Sweet Home").snippet("Here thou shall find the lion's den");
			opt.icon(BitmapDescriptorFactory.fromBitmap(playerTower)).anchor(0.5f, 0.85f);
		
			opt.position(RR41);
		getMap().addMarker(opt);
			opt.title("Eine Ecke weiter").snippet("");
			opt.position(ECKE_WEITER);
		getMap().addMarker(opt);
			opt.title("Alle guten Dinge sind drei");
			opt.position(CONERT);
		getMap().addMarker(opt);
		
		Log.d("ArceMapFragment", "Added example towers");
	}

}
