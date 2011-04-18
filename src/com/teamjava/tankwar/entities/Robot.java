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
	private final static float BOMB_SHOOT_HEIGHT_ABOVE_TANK = 20;

    // FIXME (raymond) This class should not need to know about the context.
    private Context context;

    // Robot is a computer or a human.
    private boolean isComputer = false;

    // Referance to the tank image. Use the setter to change image. (different
    // tanks for computer/player??).
    private int game_tank_image_ref;

	private float x;
	private float y;

	private float xSpeed;
	private float ySpeed;

	private boolean falling;

	private Movement movement = new Movement();

	private int color = Color.rgb(0, 10, 30);
	private float turretAngle;
    private Bitmap tankBitmap = null;
    private Bitmap turret;
    private Bitmap turretRotated;

    private Matrix matrix = new Matrix();
    private float currentTurretAngle = -1;

    private int bombFirePower = 5;

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
             game_tank_image_ref = R.drawable.game_tank;
             tankBitmap = BitmapFactory.decodeResource(
                getContext().getResources(),
                 game_tank_image_ref);
        }

        canvas.drawBitmap(
            tankBitmap,
            xCam - tankBitmap.getWidth() / 2,
            yCam - tankBitmap.getHeight() + 5,
            paint);

        // Get the turret bitmap. This will be done only once.
        if (turret == null) {
            turret = BitmapFactory.decodeResource(
                getContext().getResources(),
                R.drawable.turret);
        }

        // Create a new Bitmap from turret bitmap. This
        turretRotated = Bitmap.createBitmap(turret,
            0,
            0,
            turret.getWidth(),
            turret.getHeight(),
            matrix,
            true);

        canvas.drawBitmap(
				turretRotated,
				xCam - tankBitmap.getWidth() / 2,
				yCam - tankBitmap.getHeight() - 25,
				paint);

        // If turret angle has change. Restet matrix and rotate again.
        if (currentTurretAngle != getTurretAngle()) {
            matrix.reset();
            matrix.postRotate(getTurretAngle());

            currentTurretAngle = getTurretAngle();
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

    public int getBombFirePower()
    {
        return bombFirePower;
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

    public int getGame_tank_image_ref()
    {
        return game_tank_image_ref;
    }

    public void setGame_tank_image_ref(int game_tank_image_ref)
    {
        this.game_tank_image_ref = game_tank_image_ref;
    }

    public void fire() {
		int strength = (int) (Math.random() * 50 + 5);
        Bomb bomb = new Bomb(x, y + BOMB_SHOOT_HEIGHT_ABOVE_TANK, bombFirePower, turretAngle, strength);

		Manager.getWorld().addBomb(bomb);
	}
}
