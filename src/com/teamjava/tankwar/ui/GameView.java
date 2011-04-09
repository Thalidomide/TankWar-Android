package com.teamjava.tankwar.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.teamjava.tankwar.engine.WorldEngine;
import com.teamjava.tankwar.entities.Bomb;
import com.teamjava.tankwar.entities.EarthSlice;
import com.teamjava.tankwar.entities.EarthSlicePiece;
import com.teamjava.tankwar.entities.GameSettings;
import com.teamjava.tankwar.entities.Manager;
import com.teamjava.tankwar.entities.Robot;
import com.teamjava.tankwar.entities.World;

/**
 * User: rak
 */
public class GameView extends View
    implements View.OnTouchListener
{
    Paint paint = new Paint();
	private World world;

    private float xPrev = 0;
    private float yPrev = 0;

	private int backgroundColor = Color.rgb(100, 160, 220);
	private int groundColor = Color.rgb(0, 255, 0);
	private int bombColor = Color.rgb(80, 80, 50);
	private DrawListener listener;
	private boolean hasDrawn;

    public GameView(Context context)
    {
        super(context);

		initData();

        this.setOnTouchListener(this);

        setBackgroundColor(backgroundColor);

        //paint.setAntiAlias(true);
		//paint.setColor(R.color.black);

		//invalidate();
    }

	public void setListener(DrawListener listener) {
		this.listener = listener;
	}

	private void initData() {
		GameSettings gameSettings = new GameSettings(300);
		World world = new World(gameSettings);

		Manager.setSettings(gameSettings);
		Manager.setWorld(world);

		Robot robot = new Robot();
		robot.setY(1000);
		robot.setX(10);

//		World world = Manager.getWorld();
//		world.addRobot(robot);

		WorldEngine worldEngine = new WorldEngine(world);

		Handler handler = new Handler();
		handler.post(new Repainter(GameView.this, worldEngine, handler));
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (!hasDrawn) {
			ViewCamera.setViewSize(getWidth(), getHeight());
			hasDrawn = true;
		}

		try {
			world = Manager.getWorld();

			//System.out.println("Tegne jord..");
			drawEarth(canvas);
			drawRobots(canvas);
			//System.out.println("Tegne bomber..");
			drawBombs(canvas);
		} finally {
			//System.out.println("FERDIG med opptegning, gi beskjed til lytteren: " + listener);
			if (listener != null) {
				listener.paintCompleted();
			}
		}
	}

    public boolean onTouch(View view, MotionEvent motionEvent)
    {
		  float x = motionEvent.getX();
		  float y = motionEvent.getY();

        //x = ViewCamera.getWorldViewX(motionEvent.getX());
        //y = ViewCamera.getWorldViewY(motionEvent.getY());

		/*if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {

        }
        else */if(motionEvent.getAction() == MotionEvent.ACTION_MOVE)
        {
			float moveFactor = 0.5f;
			float xDiff = (x - xPrev) * moveFactor;
			float yDiff = (y - yPrev) * moveFactor;

			ViewCamera.translate(-xDiff, yDiff);
        }

		xPrev = x;
        yPrev = y;

		//createBomb(x, y);

        return true;
    }

	private void drawEarth(Canvas canvas) {
		int width = getWidth();
		int i = 0;
		while (i < width && i < world.getSurface().length) {
			drawEarthSlice(world.getSurface()[i], canvas);
			i++;
		}
	}

	private void drawRobots(Canvas canvas) {
		for (Robot robot : world.getRobots()) {
			robot.paint(canvas, paint);
		}

	}

	private void drawEarthSlice(EarthSlice earthSlice, Canvas canvas) {
		earthSlice.getTopSurface().paint(canvas, paint);

		for (EarthSlicePiece earthSlicePiece : earthSlice.getSubSurfaces()) {
			earthSlicePiece.paint(canvas, paint);
		}
	}

	private void drawBombs(Canvas canvas) {
		for (Bomb bomb : world.getBombs()) {
			bomb.paint(canvas, paint);
		}
	}

	public void createBomb(float x, float y) {
		int strength = (int) (Math.random() * 50 + 5);
		//Bomb bomb = new Bomb(x, getHeight() - y, strength);
		Bomb bomb = new Bomb(x, y, strength);
		synchronized (world.getBombs()) {
			world.addBomb(bomb);
		}
	}
}
