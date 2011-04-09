package com.teamjava.tankwar.engine;

import java.util.Iterator;

import com.teamjava.tankwar.entities.Bomb;
import com.teamjava.tankwar.entities.EarthSlice;
import com.teamjava.tankwar.entities.GlobalSettings;
import com.teamjava.tankwar.entities.World;

/**
 * @author Olav Jensen
 * @since Jan 19, 2011
 */
public class WorldEngine {

	private final World world;
	private long lastUpdateTime;

	public WorldEngine(World world) {
		this.world = world;
	}

	public void updateWorld() {
		long startTime = System.currentTimeMillis();

		updateBombs();
		updateFallingPieces();
		updateRobots();
		
		lastUpdateTime = System.currentTimeMillis() - startTime;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	private void updateBombs() {
		synchronized (world.getBombs()) {
			for (Iterator<Bomb> it = world.getBombs().iterator(); it.hasNext();) {
				Bomb bomb = it.next();

				if (bomb.getX() < 0 || bomb.getX() > world.getSurface().length) {
					it.remove();
					continue;
				}

				bomb.updateYSpeed(GlobalSettings.GRAVITY);
				bomb.move();

				if (!bomb.isActivated()) {
					checkIfBombHitGround(bomb);
				}

				if (bomb.blowUpThisRound()) {
					bombExplodes(bomb);
					it.remove();
				}
			}
		}
	}

	private void checkIfBombHitGround(Bomb bomb) {
		EarthSlice slice = world.getSurface()[Math.round(bomb.getX())];
		//TODO Also check if between sub surfaces
		if (bomb.getY() < slice.getTopSurface().getY()) {
			bomb.hitGround();
		}
	}

	private void bombExplodes(Bomb bomb) {
		world.addExplossion(bomb);
	}

	private void updateFallingPieces() {
		for (EarthSlice earthSlice : world.getSurface()) {
			if (!earthSlice.getSubSurfaces().isEmpty()) {
				FallingEarthSlicePiecesUpdates.updateFallingPieces(earthSlice);
			} else {
//				System.out.printf("Ingen subsurfs, topp y=%s, depth=%s%n", earthSlice.getTopSurface().getY(), earthSlice.getTopSurface().getDepth());
			}
		}
	}

	private void updateRobots() {
		RobotsEngine.updateRobots(world);
	}
}
