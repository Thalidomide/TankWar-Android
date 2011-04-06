package com.teamjava.tankwar.entities;

/**
 * @author Olav Jensen
 * @since Jan 19, 2011
 */
public class Bomb {

	private float x, y;
	private float ySpeed;

	private boolean activated;
	private int countDown = 3;

	private final int strength;

	public Bomb(float x, float y, int strength) {
		this.x = x;
		this.y = y;
		this.strength = strength;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getySpeed() {
		return ySpeed;
	}

	public int getStrength() {
		return strength;
	}

	public void updateYSpeed(float gravity) {
		ySpeed += gravity;
	}

	public void move() {
		y -= ySpeed;
	}

	public void hitGround() {
		activated = true;
	}

	public boolean isActivated() {
		return activated;
	}

	public boolean blowUpThisRound() {
		if (!activated) {
			return false;
		}

		countDown--;
		return countDown <= 0;
	}
}
