/**
 * 
 */
package de.fmieting.dominusarce.gui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import de.fmieting.dominusarce.Game;
import de.fmieting.dominusarce.R;
import de.fmieting.dominusarce.util.Pair;
import de.fmieting.dominusarce.util.StatsListArrayAdapter;
import de.fmieting.dominusarce.util.StatsListItem;

/**
 * @author fmieting
 * 
 */
public class StatsViewFragment extends Fragment {

	private ListView statsListView = null;

	public StatsViewFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View statsView = inflater.inflate(R.layout.statsfragment_layout, container, false);
		return statsView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		TextView playerNameView = (TextView) getActivity().findViewById(R.id.playerNameTextView);
		playerNameView.setText(Game.getPlayer().getName());

		TextView playerTitleView = (TextView) getActivity().findViewById(R.id.playerTitleTextView);
		playerTitleView.setText("»" + Game.getPlayer().getTitle() + "«");

		List<Pair<String, String>> list = new ArrayList<Pair<String, String>>();
		list.add(new Pair<String, String>("Total points:", "" + Game.getPlayer().getScore()));
		list.add(new Pair<String, String>("Name:", Game.getPlayer().getName()));
		list.add(new Pair<String, String>("Title:", Game.getPlayer().getTitle()));

		StatsListItem[] items = { new StatsListItem("General", R.drawable.ic_menu_refresh, list),
				new StatsListItem("Unimportant", null) };

		statsListView = (ListView) getActivity().findViewById(R.id.statsListView);
		statsListView.setAdapter(new StatsListArrayAdapter(getActivity(), R.layout.statslistitem_layout, items));

		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void setArguments(Bundle args) {
		super.setArguments(args);
	}
}
