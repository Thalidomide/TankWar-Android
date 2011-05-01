package com.teamjava.tankwar.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.teamjava.tankwar.ui.ViewCamera;

/**
 * @author Olav Jensen
 * @since 5/1/11
 */
public class SmokeCloud implements Paintable {

	private final int timeToLive;
	private int timeLived;

	private int size;
	private float x;
	private float y;

	private int color = Color.rgb(40, 40, 40);

	public SmokeCloud(int radius, float x, float y) {
		this.size = radius;
		timeToLive = radius + 10;
		this.x = x;
		this.y = y;
	}

	/**
	 * Increases the time the smoke cloud has lived.
	 *
	 * @return true if it still lives.
	 */
	public boolean increaseTimeLived() {
		timeLived++;
		y += 0.1;

		return timeLived <= timeToLive;
	}

	@Override
	public void paint(Canvas canvas, Paint paint) {
		float radius = ViewCamera.getZoomedSize(size);

		float xDraw = ViewCamera.getViewX(x);
		float yDraw = ViewCamera.getViewY(y);

		float alphaMultiplier = (float)(timeToLive - timeLived) / timeToLive;

		paint.setColor(color);
		paint.setAlpha((int) (alphaMultiplier * 255));

		canvas.drawCircle(xDraw, yDraw, radius, paint);
	}
}
