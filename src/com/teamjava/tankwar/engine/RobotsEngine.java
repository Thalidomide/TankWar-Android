package com.teamjava.tankwar.engine;

import com.teamjava.tankwar.entities.GlobalSettings;
import com.teamjava.tankwar.entities.Manager;
import com.teamjava.tankwar.entities.Movement;
import com.teamjava.tankwar.entities.Robot;
import com.teamjava.tankwar.entities.World;
import com.teamjava.tankwar.util.Util;

/**
 * @author Olav Jensen
 * @since Jan 25, 2011
 */
public class RobotsEngine {

    private int actionCounter = 0;
    // TODO(raymond) user timed action, not system cpu depended action :D
     private static final int NUMBER_OF_ACTION_BEFORE_ACTION = 60;

	public void updateRobots(World world) {
		for (Robot robot : world.getRobots()) {
			updateRobot(robot, world);
		}
	}

	private void updateRobot(Robot robot, World world) {
		updateIsFalling(robot, world);
		updateMovement(robot);
		updatePosition(robot, world);

        if (robot.isComputer()) {
            actionCounter ++;

            if (actionCounter == NUMBER_OF_ACTION_BEFORE_ACTION) {
                doAI(robot, world);
                actionCounter = 0;
            }
        }
	}


    /**
     * AI for computer Robots.
     *
     * @param robot a Robot that is played by the computer.
     * @param world world
     * */
    private void doAI(Robot robot, World world)
    {
        Robot robotPlayer = world.getRobots().get(0);

        final int nextAction = Util.getRandomNumber(2);
        final boolean move = (nextAction == 0);
        final boolean shoot = (nextAction == 1);

        if (move) {
            aiMove(robot);
        }else if (shoot) {
            aiShoot(robot, robotPlayer);
        }
    }

    /**
     * AI for shooting.
     *
     * @param robot the robot that is going to shoot
     * @param robotPlayer the players robot
     * */
    private void aiShoot(Robot robot, Robot robotPlayer)
    {
        final float playerX = robotPlayer.getX();
        final float robotX = robot.getX();

        boolean isPlayerAtLeftSide = (robotX <= playerX);

        if (isPlayerAtLeftSide) {
            final int minAngle = 90;
            final int randomAngle = Util.getRandomNumber(91);
            final int turretAngle = minAngle + randomAngle;

            robot.setTurretAngle(turretAngle);
        }
        else {
            final int randomNumber = Util.getRandomNumber(91);
            robot.setTurretAngle(randomNumber);
        }

        int randomFirePower = Util.getRandomNumber(5) + 1;

        robot.setBombFirePower(randomFirePower);
        robot.fire();
    }


    /**
     * AI for the movement of the Robot.
     *
     * @param robot the robot to move
     * */
    private void aiMove(Robot robot)
    {
        int moveDirection = Util.getRandomNumber(3);
        Movement movement = robot.getMovement();

        boolean moveLeft = (moveDirection == 0);
        boolean moveRight = (moveDirection == 1);
        boolean moveStop = (moveDirection == 2);

        if (moveLeft) {
            Movement.Walk walking =
                movement.getWalking() == Movement.Walk.left ?
                    Movement.Walk.stop :
                    Movement.Walk.left;

            movement.setWalking(walking);
        } else if (moveRight) { //right
            Movement.Walk walking =
                movement.getWalking() == Movement.Walk.right ?
                    Movement.Walk.stop :
                    Movement.Walk.right;

            movement.setWalking(walking);
        } else if (moveStop) {
             movement.setWalking(Movement.Walk.stop);
        }
    }

    private void updateIsFalling(Robot robot, World world) {
		if (isAboveSurface(robot, world)) {
			robot.setYSpeed(robot.getYSpeed() + GlobalSettings.GRAVITY);
			robot.setY(robot.getY() - robot.getYSpeed());

			if (!isAboveSurface(robot, world)) {
				robot.setYSpeed(0);
			}
		}

		int x = Math.round(robot.getX());
		float worldY = world.getSurface()[x].getTopSurface().getY();

		if (robot.getY() < worldY) {
			robot.setY(worldY);
		}

	}

	private boolean isAboveSurface(Robot robot, World world) {
		int x = Math.round(robot.getX());
		return robot.getY() > world.getSurface()[x].getTopSurface().getY();
	}

	private void updateMovement(Robot robot) {
		robot.updateMoveSpeed();
	}

	private void updatePosition(Robot robot, World world) {
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
