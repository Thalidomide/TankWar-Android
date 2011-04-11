package com.teamjava.tankwar.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.teamjava.tankwar.engine.GapCreator;

/**
 * @author Olav Jensen
 * @since Jan 19, 2011
 */
public class World {

	private final GameSettings settings;

	private EarthSlice[] surface;
	private List<Robot> robots = new ArrayList<Robot>();

	private List<Bomb> bombs = new ArrayList<Bomb>();

	public World(GameSettings settings) {
		this(settings, createSurface(settings));
	}

	public World(GameSettings settings, EarthSlice[] surface) {
		this.settings = settings;
		this.surface = surface;
	}

	public void addRobot(Robot robot) {
		this.robots.add(robot);
	}

	private static EarthSlice[] createSurface(GameSettings settings) {
		EarthSlice[] surface = new EarthSlice[settings.getWorldWidth()];
		int y = 0;
		Random random = new Random();

		for (int i = 0; i < surface.length; i++) {
			EarthSlicePiece earthSlicePiece = new EarthSlicePiece();

			y = getNextY(y, random);

			earthSlicePiece.setX(i);
			earthSlicePiece.setY(y);

			surface[i] = new EarthSlice(earthSlicePiece);
		}

		return surface;
	}

	public synchronized void addBomb(Bomb bomb) {
		bombs.add(bomb);
	}

	private static int getNextY(int y, Random random) {
		return y + random.nextInt(3) - 1;
	}

	public void addExplossion(Bomb bomb) {
		int width = bomb.getStrength();
		int start = (int) (bomb.getX() - width/2);
		int end = start + width;
		int y = (int) (bomb.getY());

		if (end >= settings.getWorldWidth()) {
			end = settings.getWorldWidth() - 1;
		}

		for (int x = start; x <= end; x++) {
			if (x < 0) {
				continue;
			} else if (end >= settings.getWorldWidth()) {
				break;
			}
			double value = Math.sin((double) (x - start) / (width / 3));
			int depth = (int) Math.round(width / 2 * value);
			addGap(x, y + depth, depth * 2);
		}
	}

	public void addGap(int x, float y, int height) {
		GapCreator.createGap(this, x, y, height);
	}

	public EarthSlice[] getSurface() {
		return surface;
	}

	public List<Bomb> getBombs() {
		return bombs;
	}

	public List<Robot> getRobots() {
		return robots;
	}
}
