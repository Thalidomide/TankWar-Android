package com.teamjava.tankwar.entities;

/**
 * @author Olav Jensen
 * @since Jan 23, 2011
 */
public class Robot implements PhysicalObject {

	private final float speed = 2;

	private float x;
	private float y;

	private float xSpeed;
	private float ySpeed;

	private boolean falling;

	private Movement movement = new Movement();

	private float turretAngle;

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getXSpeed() {
		return xSpeed;
	}

	public void setXSpeed(float xSpeed) {
		this.xSpeed = xSpeed;
	}

	public float getYSpeed() {
		return ySpeed;
	}

	public void setYSpeed(float ySpeed) {
		this.ySpeed = ySpeed;
	}

	public Movement getMovement() {
		return movement;
	}

	public void setMovement(Movement movement) {
		this.movement = movement;
	}

	public float getTurretAngle() {
		return turretAngle;
	}

	public void setTurretAngle(float turretAngle) {
		this.turretAngle = turretAngle;
	}

	public float getSpeed() {
		return speed;
	}

	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}
}
