/**
 * 
 */
package de.fmieting.dominusarce;

import android.os.Bundle;
import de.fmieting.dominusarce.player.Player;

/**
 * @author fmieting
 * 
 */
public class Game {

	public static final String PLAYERNAME = "Playername";
	public static final String PLAYERSCORE = "Playertitle";

	private static Player player;
	private static Bundle settings = new Bundle();

	private static Game game = new Game();

	public static Game getInstance() {
		return game;
	}

// ------- Getters and Setters --------------------------//

	/**
	 * @return the settings
	 */
	public static Bundle getSettings() {
		return settings;
	}

	/**
	 * @return the player
	 */
	public static Player getPlayer() {
		return player;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public static void setPlayer(Player p) {
		player = p;
	}

}
