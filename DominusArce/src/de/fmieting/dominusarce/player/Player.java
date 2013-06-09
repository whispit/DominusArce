/**
 * 
 */
package de.fmieting.dominusarce.player;

import android.graphics.Color;

/**
 * @author fmieting
 * 
 */
public class Player {
	private String name = "Undefined";
	private String title = "";
	private int score = 0;
	private int color = Color.BLACK;

	public Player() {
		// TODO Auto-generated constructor stub
	}

	public Player(String name) {
		this();
		this.name = name;
	}

// ------- Getters and Setters --------------------------//
	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public Player setColor(int color) {
		this.color = color;
		return this;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public Player setTitle(String title) {
		this.title = title;
		return this;
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

		title = PlayerRanks.getRank(score);
		if (title == null) {
			title = "";
		}

		return this;
	}
}
