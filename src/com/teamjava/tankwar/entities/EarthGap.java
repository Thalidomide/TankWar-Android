package com.teamjava.tankwar.entities;

/**
 * @author Olav Jensen
 * @since Jan 19, 2011
 */
public class EarthGap {
	
	private int x;
	private float y;
	private float height;
	private final EarthSlicePiece aboveSlicePiece;

	public EarthGap(int x, float y, int height, EarthSlicePiece aboveSlicePiece) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.aboveSlicePiece = aboveSlicePiece;
	}

	public int getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getHeight() {
		return height;
	}

	public EarthSlicePiece getAboveSurface() {
		return aboveSlicePiece;
	}

	public void decreaseHeight(float speed) {
		height -= speed;
	}

	public boolean isFilled() {
		return height <= 0;
	}
}
