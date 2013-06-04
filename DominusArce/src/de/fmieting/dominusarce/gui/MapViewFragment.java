/**
 * 
 */
package de.fmieting.dominusarce.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import de.fmieting.dominusarce.R;

/**
 * @author fmieting
 *
 */

@Deprecated
public final class MapViewFragment extends Fragment {
	
	private View mapView = null;
	private GoogleMap map = null;
	
	//Saving instance states:
	private static final String LONGITUDE = "mapviewfragment_longitude", 
								LATITUDE = "mapviewfragment_latitude",	
								ZOOM = "mapviewfragment_zoom";
	
	//Debugging
	private static final LatLng RR41 = new LatLng(51.04532, 13.693635),
						 		ECKE_WEITER = new LatLng(51.046419,13.693219),
						 		CONERT = new LatLng(51.045681,13.691443);
	
	private Bitmap playerTower = null;
	
	private Bundle destroyBundle = null;
	
	public MapViewFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Paint p = new Paint(Color.RED);
		ColorFilter filter = new LightingColorFilter(0xffaaaa, 0x220000);	//tint the tower red
		p.setColorFilter(filter);
		Bitmap tmp = BitmapFactory.decodeResource(getResources(), R.drawable.tower);
		playerTower = tmp.copy(Config.ARGB_8888, true);
		Canvas cv = new Canvas(playerTower);
		cv.drawBitmap(tmp, 0, 0, p);
		
		setRetainInstance(true);
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		mapView = inflater.inflate(R.layout.mapviewfragment_layout,container, false);
		//Get the map, add a (test-)marker
		try {
			map = ((SupportMapFragment) this.getFragmentManager()
					.findFragmentById(R.id.map)).getMap();
		} catch (NullPointerException npe) {
			Log.e("MapViewFragment","Google Services (and thus Google Maps) not available");
			return null;
		}
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		
		
		// Add some towers & a first city
		PolygonOptions city = new PolygonOptions();
			city.add(RR41,ECKE_WEITER,CONERT,RR41);
			city.strokeColor(Color.RED - 0x00220000);	//not-so-bright red
			city.fillColor(Color.RED - 0xaa220000);		//not-so-bright, transparent red
		
		map.addPolygon(city);
		
		MarkerOptions opt = new MarkerOptions();
			opt.title("Home Sweet Home").snippet("Here thou shall find the lion's den");
			opt.icon(BitmapDescriptorFactory.fromBitmap(playerTower)).anchor(0.5f, 0.85f);
		
			opt.position(RR41);
		map.addMarker(opt);
			opt.title("Eine Ecke weiter").snippet("");
			opt.position(ECKE_WEITER);
		map.addMarker(opt);
			opt.title("Alle guten Dinge sind drei");
			opt.position(CONERT);
		map.addMarker(opt);
		
		map.setMyLocationEnabled(true);
		
		//Disable rotation and legacy zoom buttons
		UiSettings uiset = map.getUiSettings();
		uiset.setCompassEnabled(false);
		uiset.setRotateGesturesEnabled(false);
		uiset.setZoomControlsEnabled(false);
		
		if(destroyBundle != null){
			CameraUpdate update = CameraUpdateFactory
			.newLatLngZoom(new LatLng(	destroyBundle.getDouble(LATITUDE), 
										destroyBundle.getDouble(LONGITUDE)),
										destroyBundle.getFloat(ZOOM));
			map.moveCamera(update);
		} else {
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(51.051107,13.73909),12.0f);	//Dresden, zentriert auf Stadtmitte
			map.moveCamera(update);
		}
		
		Log.d("MapViewFragment","savedInstanceState was null:"+(savedInstanceState==null));
		
		return mapView;
	}
	
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
	
	
	@Override
	public void onDestroyView() {
		//Needed for proper switching back to the maps fragment
		try {
			destroyBundle = new Bundle();
			destroyBundle.putDouble(LATITUDE, map.getCameraPosition().target.latitude);
			destroyBundle.putDouble(LONGITUDE, map.getCameraPosition().target.longitude);
			destroyBundle.putFloat(ZOOM, map.getCameraPosition().zoom);
			
			SupportMapFragment fragment = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map));
			FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.remove(fragment);
			ft.commit();
		} catch (Exception e) {
		}
		super.onDestroyView();
	}
}
