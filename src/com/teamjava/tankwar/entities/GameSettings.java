package com.teamjava.tankwar.entities;

/**
 * @author Olav Jensen
 * @since Jan 19, 2011
 */
public class GameSettings {

	private final int worldWidth;

	public GameSettings(int worldWidth) {
		this.worldWidth = worldWidth;
	}

	public int getWorldWidth() {
		return worldWidth;
	}
}
