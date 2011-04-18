package com.teamjava.tankwar.engine;

import com.teamjava.tankwar.entities.GlobalSettings;
import com.teamjava.tankwar.entities.Manager;
import com.teamjava.tankwar.entities.Robot;
import com.teamjava.tankwar.entities.World;

/**
 * @author Olav Jensen
 * @since Jan 25, 2011
 */
public class RobotsEngine {

	public static void updateRobots(World world) {
		for (Robot robot : world.getRobots()) {
			updateRobot(robot, world);
		}
	}

	private static void updateRobot(Robot robot, World world) {
		updateIsFalling(robot, world);
		updateMovement(robot);
		updatePosition(robot, world);
	}

	private static void updateIsFalling(Robot robot, World world) {
		if (isAboveSurface(robot, world)) {
			robot.setYSpeed(robot.getYSpeed() + GlobalSettings.GRAVITY);
			robot.setY(robot.getY() - robot.getYSpeed());
		}

		int x = Math.round(robot.getX());
		float worldY = world.getSurface()[x].getTopSurface().getY();

		if (robot.getY() < worldY) {
			robot.setY(worldY);
		}

	}

	private static boolean isAboveSurface(Robot robot, World world) {
		int x = Math.round(robot.getX());
		return robot.getY() > world.getSurface()[x].getTopSurface().getY();
	}

	private static void updateGravity(Robot robot, World world) {
		//TODO Implement
	}

	private static void updateMovement(Robot robot) {
		robot.updateMoveSpeed();
	}

	private static void updatePosition(Robot robot, World world) {
		float newX = robot.getX() + robot.getXSpeed();
		float maxX = Manager.getSettings().getWorldWidth() - 1;

		if (newX < 0) {
			newX = 0;
		} else if (newX > maxX) {
			newX = maxX;
		}

		robot.setX(newX);
	}
}
