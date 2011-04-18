package com.teamjava.tankwar.entities;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.teamjava.tankwar.ui.ViewCamera;

/**
 * @author Olav Jensen
 * @since Jan 19, 2011
 */
public class EarthSlicePiece implements Comparable<EarthSlicePiece>, Paintable {

	private int x;
	private float y;
	private float depth = -1;//-1 = unlimited depth
	private final static float Y_BOTTOM = 1000;

	private float speed;

	@Override
	public void paint(Canvas canvas, Paint paint) {
		float drawX = ViewCamera.getViewX(x);
		float yTop = ViewCamera.getViewY(y);
		//float yBottom = ViewCamera.getViewY(depth == -1 ? Y_BOTTOM : y + depth);
		//float yBottom = depth == -1 ? Y_BOTTOM : ViewCamera.getViewY(y + depth);
		float yBottom = depth == -1 ? Y_BOTTOM : ViewCamera.getViewY(y - depth);
		float width = ViewCamera.getZoomedSize(1);

		paint.setStrokeWidth(1);

		//System.out.println("Jordstykke x: " + drawX + ", ytop: " + yTop + ", ybot: " + yBottom + ", width: " + width);

		//canvas.drawLine(x, yTop, x, yBottom, paint);
		canvas.drawRect(drawX, yTop, drawX + width, yBottom, paint);
	}

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
