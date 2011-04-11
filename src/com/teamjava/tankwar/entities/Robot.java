package com.teamjava.tankwar.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.teamjava.tankwar.R;
import com.teamjava.tankwar.ui.ViewCamera;

/**
 * @author Olav Jensen
 * @since Jan 23, 2011
 */
public class Robot implements PhysicalObject {

	private final float speed = 2;

    // FIXME (raymond) This class should not need to know about the context.
    private Context context;

	private float x;
	private float y;

	private float xSpeed;
	private float ySpeed;

	private boolean falling;

	private Movement movement = new Movement();

	private int color = Color.rgb(0, 10, 30);
	private float turretAngle;
    private Bitmap tankBitmap = null;

    @Override
	public void paint(Canvas canvas, Paint paint) {
		float xCam = ViewCamera.getViewX(x);
		float yCam = ViewCamera.getViewY(y);

		paint.setColor(color);

        // Get the tank bitmap.
        // We may going to need at least two images(moving left and right).
        // This bitmap should be initialized one time (can be an fps eater..).
        // Maybe move this to constructor or something like that.
        if (tankBitmap == null) {
            tankBitmap = BitmapFactory.decodeResource(
                getContext().getResources(),
                R.drawable.game_tank);
        }

        canvas.drawBitmap(tankBitmap, xCam - tankBitmap.getWidth() / 2, yCam - tankBitmap.getHeight() + 5, paint);

        //TODO(raymond) UGLY evil hack starts here!
        // Please fix this :)
        // And move the calculation to some method (util?)
        float xTurrentEnd = 0;
        float yTurrentEnd = 0;
        float angle = getTurretAngle();

        if (angle > 0 && angle < 20) {
            xTurrentEnd = xCam - 30;
            yTurrentEnd = yCam - 5;
        } else if (angle > 20 && angle < 40) {
            xTurrentEnd = xCam - 25;
            yTurrentEnd = yCam - 20;
        }  else if (angle > 40 && angle < 60) {
            xTurrentEnd = xCam - 20;
            yTurrentEnd = yCam -  30;
        } else if (angle > 60 && angle < 90) {
            xTurrentEnd = xCam - 10;
            yTurrentEnd = yCam - 40;
        } else if (angle > 90 && angle < 110) {
            xTurrentEnd = xCam - 0;
            yTurrentEnd = yCam - 30;
        } else if (angle > 110 && angle < 130) {
            xTurrentEnd = xCam + 25;
            yTurrentEnd = yCam - 30;
        } else if (angle > 130 && angle < 150) {
            xTurrentEnd = xCam + 25;
            yTurrentEnd = yCam - 20;
        }  else if (angle > 150 && angle < 180) {
            xTurrentEnd = xCam + 30;
            yTurrentEnd = yCam - 5;
        }

        if (0 != xTurrentEnd && 0 != yTurrentEnd) {
            paint.setStrokeWidth(4f);
            canvas.drawLine(xCam,yCam,xTurrentEnd,yTurrentEnd, paint);
        }
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

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

	public void fire() {
		int strength = (int) (Math.random() * 50 + 5);
		Bomb bomb = new Bomb(x, y, 10, turretAngle, strength);

		Manager.getWorld().addBomb(bomb);
	}
}
