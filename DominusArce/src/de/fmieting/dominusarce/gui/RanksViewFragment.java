/**
 * 
 */
package de.fmieting.dominusarce.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import de.fmieting.dominusarce.Game;
import de.fmieting.dominusarce.R;
import de.fmieting.dominusarce.util.RanksListArrayAdapter;
import de.fmieting.dominusarce.util.RanksListItem;

/**
 * @author fmieting
 * 
 */
public class RanksViewFragment extends Fragment {

	private View ranksView = null;
	private ListView ranksListView = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ranksView = inflater.inflate(R.layout.ranksfragment_layout, container, false);
		return ranksView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		// XXX: Example stats list content
		RanksListItem[] items = new RanksListItem[] {
				new RanksListItem(1, "9001pts", R.drawable.trophy_gold, "Weltimperator"),
				new RanksListItem(2, "666pts", R.drawable.trophy_silver, "AGE AGE AGE"),
				new RanksListItem(3, Game.getPlayer().getScore() + "pts", R.drawable.trophy_bronze, Game.getPlayer()
						.getName(), true), new RanksListItem(4, "MauerBauer"), new RanksListItem(5, "MauerBauer"),
				new RanksListItem(6, "MauerBauer"), new RanksListItem(7, "MauerBauer"),
				new RanksListItem(8, "MauerBauer"), new RanksListItem(9, "MauerBauer"),
				new RanksListItem(10, "MauerBauer"), new RanksListItem(11, "MauerBauer"),
				new RanksListItem(12, "MauerBauer"), new RanksListItem(13, "MauerBauer") };

		ranksListView = (ListView) getActivity().findViewById(R.id.ranksListView);

		ranksListView.setAdapter(new RanksListArrayAdapter(getActivity(), R.layout.rankslistitem_layout, items));

		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void setArguments(Bundle args) {
		super.setArguments(args);
	}
}
