package com.teamjava.tankwar.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.teamjava.tankwar.ui.ViewCamera;

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

	private int color = Color.rgb(0, 10, 30);
	private float turretAngle;

	@Override
	public void paint(Canvas canvas, Paint paint) {
		float radius = ViewCamera.getZoomedSize(4);
		float xCam = ViewCamera.getViewX(x);
		float yCam = ViewCamera.getViewX(y);

		paint.setColor(color);
		canvas.drawCircle(xCam, yCam, radius, paint);
	}

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
