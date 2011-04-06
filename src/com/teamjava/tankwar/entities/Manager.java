package com.teamjava.tankwar.entities;

/**
 * @author Olav Jensen
 * @since Jan 25, 2011
 */
public class Manager {

	private static Manager instance;

	private World world;
	private GameSettings settings;

	private Manager() {
	}

	private synchronized static Manager getInstance() {
		if (instance == null) {
			instance = new Manager();
		}

		return instance;
	}

	public static World getWorld() {
		return getInstance().world;
	}

	public static void setWorld(World world) {
		getInstance().world = world;
	}

	public static GameSettings getSettings() {
		return getInstance().settings;
	}

	public static void setSettings(GameSettings settings) {
		getInstance().settings = settings;
	}
}
