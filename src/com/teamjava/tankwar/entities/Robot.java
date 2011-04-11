package com.teamjava.tankwar.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
    private Bitmap turrent;
    private Matrix matrix = new Matrix();
    private float currentTurrentAngle = -1;
    private Bitmap turrentRotated;

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

        canvas.drawBitmap(
            tankBitmap,
            xCam - tankBitmap.getWidth() / 2,
            yCam - tankBitmap.getHeight() + 5,
            paint);

        // Get the turrent bitmap. This will be done only once.
        if (turrent == null) {
            turrent = BitmapFactory.decodeResource(
                getContext().getResources(),
                R.drawable.turrent);
        }

        // Create a new Bitmap from turrent bitmap. This
        turrentRotated = Bitmap.createBitmap(
            turrent,
            0,
            0,
            turrent.getWidth(),
            turrent.getHeight(),
            matrix,
            true);

        canvas.drawBitmap(
            turrentRotated,
            xCam - tankBitmap.getWidth() / 2,
            yCam - tankBitmap.getHeight() - 25,
            paint);

        if (currentTurrentAngle != getTurretAngle()) {
            matrix.reset();
            matrix.postRotate(getTurretAngle());

            currentTurrentAngle = getTurretAngle();
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
}
