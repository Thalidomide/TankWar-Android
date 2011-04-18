package com.teamjava.tankwar.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.teamjava.tankwar.ui.ViewCamera;

/**
 * @author Olav Jensen
 * @since Jan 19, 2011
 */
public class Bomb implements PhysicalObject {

	private float x, y;
	private float xSpeed, ySpeed;

	private boolean activated;
	private int countDown = 3;

	private final int strength;

	private int bombColor = Color.rgb(80, 80, 50);

	public Bomb(float x, float y, float initialXSpeed, float initialYSpeed, float fireSpeed, float angle, int strength) {
		this.x = x;

		this.y = y;
		this.strength = strength;
		float radAngle = (float) (Math.toRadians(angle) - Math.PI);

		xSpeed = (float) (fireSpeed * Math.cos(radAngle)) + initialXSpeed;
		ySpeed = (float) (fireSpeed * Math.sin(radAngle));//TODO Should be influenced by initialYSpeed, once the tank move more properly up and down
	}

	@Override
	public float getXSpeed() {
		return xSpeed;
	}

	@Override
	public float getYSpeed() {
		return ySpeed;
	}

	@Override
	public void paint(Canvas canvas, Paint paint) {
		//float radius = ViewCamera.getZoomedSize(strength / 4);
		float radius = ViewCamera.getZoomedSize(2);

		float xDraw = ViewCamera.getViewX(x);
		float yDraw = ViewCamera.getViewY(y);

		paint.setColor(bombColor);

		canvas.drawCircle(xDraw, yDraw, radius, paint);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getStrength() {
		return strength;
	}

	public void updateYSpeed(float gravity) {
		ySpeed += gravity;
	}

	public void move() {
		x += xSpeed;
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
