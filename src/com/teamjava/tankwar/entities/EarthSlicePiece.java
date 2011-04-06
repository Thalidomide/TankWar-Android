package com.teamjava.tankwar.entities;

/**
 * @author Olav Jensen
 * @since Jan 19, 2011
 */
public class EarthSlicePiece implements Comparable<EarthSlicePiece> {

	private int x;
	private float y;
	private float depth = -1;//-1 = unlimited depth

	private float speed;

	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void increaseSpeed(float inc) {
		speed += inc;
	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	/**
	 * @param distance the target distance to move.
	 * @return true if the target distance was reached.
	 */
	public boolean moveDistance(float distance) {
		boolean reachedDistance = distance <= speed;

		float moveDistance = reachedDistance ? distance : speed;
		y -= moveDistance;
		
		return reachedDistance;
	}

	public float getBottomYPosition() {
		if (depth == -1) {
			return -1;
		}

		return y - depth;
	}

	public int compareTo(EarthSlicePiece o) {
		return Math.round(y - o.y);
	}
}
