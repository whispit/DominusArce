package de.fmieting.dominusarce.gui;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import de.fmieting.dominusarce.Game;
import de.fmieting.dominusarce.R;
import de.fmieting.dominusarce.player.Player;

public class MainActivity extends FragmentActivity implements ActionBar.OnNavigationListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private Fragment mvf = null, svf = null, rvf = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_activity_layout);
		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(actionBar.getThemedContext(), android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] { getString(R.string.title_map),
								getString(R.string.title_stats), getString(R.string.title_ranks), }), this);

		// XXX: Restore Settings and similar stuff
		Player player = new Player("MauerBauer").setScore(100).setColor(Color.MAGENTA);
		Game.setPlayer(player);

// Bundle gameSettings = Game.getSettings();
// gameSettings.putString(Game.PLAYERNAME, player.getName());
// gameSettings.putInt(Game.PLAYERSCORE, player.getScore());

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previous dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar().getSelectedNavigationIndex());

		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maps_test, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String text = "";
		switch (item.getItemId()) {
		case R.id.action_settings:
			text = "Not Implemented";
			break;
		default:
			return false;
		}

		Context context = this.getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view. Create new if somehow failed
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = (mvf != null) ? mvf : new ArceMapFragment();
			break;
		case 1:
			fragment = (svf != null) ? svf : new StatsViewFragment();
			break;
		case 2:
			fragment = (rvf != null) ? rvf : new RanksViewFragment();
			break;
		default:
			return false;
		}

		try {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.container, fragment);

			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		} catch (NullPointerException npe) {
			Log.d("MainActivity",
					"onNavigationItemSelected: NullPointerExceptoin: getSupportFragmentManager returned null: "
							+ (getSupportFragmentManager() == null));
			return false;
		}

		return true;
	}

}
