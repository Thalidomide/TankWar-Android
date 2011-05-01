package com.teamjava.tankwar.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.teamjava.tankwar.R;
import com.teamjava.tankwar.ui.ViewCamera;
import com.teamjava.tankwar.util.Util;

/**
 * @author Olav Jensen
 * @since Jan 23, 2011
 */
public class Robot implements PhysicalObject {

    private final static float BOMB_SHOOT_HEIGHT_ABOVE_TANK = 3;
    private static final float MAX_SPEED = 2f;

    private float x;
	private float y;

	private float xSpeed;
	private float ySpeed;

    private final float acceleration = 0.1f;
    private float turretAngle = 0;

    // Robot is a computer or a human.
    private boolean isComputer = false;

    private int bombFirePower = 5;

	private int bombStrength = 10;
	private int maxBombStrength = 100;

    // FIXME (raymond) This class should not need to know about the context.
    private Context context;

    private Bitmap tankBitmap = null;
    private Bitmap turretBitmap = null;

    private Matrix matrix = new Matrix();
	private Movement movement = new Movement();


    @Override
	public void paint(Canvas canvas, Paint paint) {
		float xCam = ViewCamera.getViewX(x);
		float yCam = ViewCamera.getViewY(y);

        // Get the tank bitmap.
        // We may going to need at least two images(moving left and right).
        // This bitmap should be initialized one time (can be an fps eater..).
        // Maybe move this to constructor or something like that.
         if (tankBitmap == null) {
             tankBitmap = BitmapFactory.decodeResource(
                getContext().getResources(),
                 R.drawable.game_tank);
        }

        // Get the turretBitmap bitmap. This will be done only once.
        if (turretBitmap == null) {
            turretBitmap = BitmapFactory.decodeResource(
                getContext().getResources(),
                R.drawable.turret);
        }

        canvas.drawBitmap(
            turretBitmap,
				matrix,
				null);

		canvas.drawBitmap(
            tankBitmap,
            xCam - tankBitmap.getWidth() / 2,
            yCam - tankBitmap.getHeight() + 5,
            null);

        matrix.reset();
        matrix.setTranslate(xCam - turretBitmap.getWidth() / 2, yCam - turretBitmap
            .getHeight() + 5);
        matrix.preRotate(getTurretAngle(), turretBitmap.getWidth() / 2,  turretBitmap
            .getHeight() / 2);
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

	public float getYSpeed() {
		return ySpeed;
	}

	public void setYSpeed(float ySpeed) {
		this.ySpeed = ySpeed;
	}

	public Movement getMovement() {
		return movement;
	}

	public void updateMoveSpeed() {
		switch (movement.getWalking()) {
			case left:
				xSpeed -= acceleration;
				if (xSpeed < -MAX_SPEED) {
					xSpeed = -MAX_SPEED;
				}
				break;
			case right:
				xSpeed += acceleration;
				if (xSpeed > MAX_SPEED) {
					xSpeed = MAX_SPEED;
				}
				break;
			case stop:
				breakSpeed();
		}
	}

	private void breakSpeed() {
		if (xSpeed == 0) {
			return;
		}

		if (xSpeed > 0) {
			xSpeed -= acceleration;
			if (xSpeed < 0) {
				xSpeed = 0;
			}
		} else {
			xSpeed += acceleration;
			if (xSpeed > 0) {
				xSpeed = 0;
			}
		}
	}

	public float getTurretAngle() {
		return turretAngle;
	}

	public void setTurretAngle(float turretAngle) {
		this.turretAngle = turretAngle;
	}

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public void setBombFirePower(int bombFirePower)
    {
        this.bombFirePower = bombFirePower;
    }

    public boolean isComputer()
    {
        return isComputer;
    }

    public void setComputer(boolean computer)
    {
        isComputer = computer;
    }

    public void fire() {
        Bomb bomb = new Bomb(x, y + BOMB_SHOOT_HEIGHT_ABOVE_TANK, xSpeed, ySpeed, bombFirePower, turretAngle, bombStrength);
        bomb.setContext(getContext());
		Manager.getWorld().addBomb(bomb);

        Util.playSound(getContext(), R.raw.fire);
	}

	public void increaseBombStrength() {
		bombStrength += 10;
		if (bombStrength > maxBombStrength) {
			bombStrength = 10;
		}
	}

	public int getBombStrength() {
		return bombStrength;
	}
}
