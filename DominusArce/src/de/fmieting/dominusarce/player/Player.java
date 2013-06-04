/**
 * 
 */
package de.fmieting.dominusarce.player;

/**
 * @author fmieting
 *
 */
public class Player {
	private String name = "Undefined";
	private int score = 0;
	
	public Player() {
		// TODO Auto-generated constructor stub
	}
	
	public Player(String name){
		this();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Player setName(String name) {
		this.name = name;
		return this;
	}

	public int getScore() {
		return score;
	}

	public Player setScore(int score) {
		this.score = score;
		return this;
	}
}
