/**
 * 
 */
package de.fmieting.dominusarce.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.fmieting.dominusarce.R;
import de.fmieting.dominusarce.player.PlayerRanks;

/**
 * @author fmieting
 *
 */
public class StatsViewFragment extends Fragment {
	
	private String playerName, playerTitle;
	
	public StatsViewFragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View statsView = inflater.inflate(R.layout.statsfragment_layout,container, false);
		return statsView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		TextView playerNameView = (TextView) getActivity().findViewById(R.id.playerNameTextView);
		playerNameView.setText(playerName);
		TextView playerTitleView = (TextView) getActivity().findViewById(R.id.playerTitleTextView);
		playerTitleView.setText(playerTitle);
		
		super.onViewCreated(view, savedInstanceState);
	}
	
	@Override
	public void setArguments(Bundle args) {
		super.setArguments(args);
		
		playerName = args.getString(MainActivity.PLAYERNAME,"UNDEFINED");
		
		playerTitle = PlayerRanks.getRank(args.getInt(MainActivity.PLAYERSCORE,0));
		if(playerTitle != null){
			playerTitle = "»" + playerTitle + "«";
		}		
	}
}
