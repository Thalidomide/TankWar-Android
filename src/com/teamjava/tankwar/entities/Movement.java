package com.teamjava.tankwar.entities;

/**
 * @author Olav Jensen
 * @since Jan 25, 2011
 */
public class Movement {

	public enum Walk {left, right, stop}

	private Walk walking = Walk.stop;
	private boolean jump;

	public Walk getWalking() {
		return walking;
	}

	public void setWalking(Walk walking) {
		this.walking = walking;
	}

	public boolean isJump() {
		return jump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}
}
