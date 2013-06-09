/**
 * 
 */
package de.fmieting.dominusarce.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fmieting
 * 
 */
public class StatsListItem {
	public int icon;
	public String title, content;
	public List<Pair<String, String>> items;

	public static final int NO_ICON = 0;

	public StatsListItem() {
		this("Undefined", NO_ICON, new ArrayList<Pair<String, String>>());
	}

	public StatsListItem(String key, int icon, List<Pair<String, String>> items) {
		this.icon = icon;
		this.title = key;
		this.items = items;
	}

	public StatsListItem(int icon, List<Pair<String, String>> items) {
		this("", icon, items);
	}

	public StatsListItem(String key, int icon) {
		this(key, icon, new ArrayList<Pair<String, String>>());
	}

	public StatsListItem(String key, List<Pair<String, String>> items) {
		this(key, NO_ICON, items);
	}

}