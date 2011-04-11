package com.teamjava.tankwar.engine;

/**
 * A interface for the Robot(s).
 * Game buttons/slider etc can communicate with the Robots through this
 * interface.
 *
 * TODO(raymond) Consider using one class all of this events. Movements?
 * If we have a lot of this interfaces the GameView will a HUGE class in
 * no time :(
 *
 * This class could be a class called RobotEvent/RobotLogic of something like
 * that.
 * How to handle this events should be the RobotsEvents implementation details,
 * not GameView or the Robots (Robots should be dumb, just draw Robot and
 * turret with the provided bitmaps and values).
 *
 * @author raymond
 */
public interface RobotCommunicator
{
    /**
     * Tell the robot to move left.
     */
    void robotMoveLeft();

    /**
     * Tell the robot to move right.
     */
    void robotMoveRight();

    /**
     * The angle for the turret is changed, time to update the robot.
     *
     * @param degrees new angle for turret
     */
    void robotTurretAngleChanged(float degrees);

    /**
     * Robot has fired its gun.
     */
    void robotFire();

    /**
     *  Update the robot fire power.
     * @param newFirePower the new fire power
     */
    void robotSetFirePower(int newFirePower);

    void setWorldZoomLevel(float zoomLevel);

}
