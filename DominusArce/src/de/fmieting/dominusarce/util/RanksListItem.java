/**
 * 
 */
package de.fmieting.dominusarce.util;

/**
 * @author fmieting
 * 
 */
public class RanksListItem {
	public int icon, position;
	public String score, value;
	public boolean emphasize;

	public static final int NO_ICON = 0;

	public RanksListItem() {
		this(0, "Undefined", NO_ICON, "Undefined", false);
	}

	public RanksListItem(int position, String score, int icon, String name, boolean emphasize) {
		this.position = position;
		this.icon = icon;
		this.score = score;
		this.value = name;
		this.emphasize = emphasize;
	}

	public RanksListItem(int position, int icon, String name, boolean emphasize) {
		this(position, "", icon, name, emphasize);
	}

	public RanksListItem(int position, int icon, String name) {
		this(position, "", icon, name, false);
	}

	public RanksListItem(int position, String score, String name, boolean emphasize) {
		this(position, score, NO_ICON, name, emphasize);
	}

	public RanksListItem(int position, String score, int icon, String name) {
		this(position, score, icon, name, false);
	}

	public RanksListItem(int position, String score, String name) {
		this(position, score, NO_ICON, name, false);
	}

	public RanksListItem(int position, String name, boolean emphasize) {
		this(position, "", NO_ICON, name, emphasize);
	}

	public RanksListItem(int position, String name) {
		this(position, "", NO_ICON, name, false);
	}
}