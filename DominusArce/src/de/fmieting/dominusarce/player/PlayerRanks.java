/**
 * 
 */
package de.fmieting.dominusarce.player;

import java.util.TreeMap;

/**
 * @author fmieting
 *
 */
public final class PlayerRanks {
	
	private static final TreeMap<Integer,String> ranks;
	
	public static String getRank(int score){
		return ranks.get(ranks.floorKey(score));
	}
	
		
	static{
		
		TreeMap<Integer,String> map = new TreeMap<Integer, String>();
		map.put(0, "Peasant");
		map.put(100, "Builder");
		map.put(1000, "Architect");
		map.put(2000, "Mayor");
		
		ranks = map;
		
	}
}
