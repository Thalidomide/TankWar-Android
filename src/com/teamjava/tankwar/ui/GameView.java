package com.teamjava.tankwar.ui;

import java.awt.Graphics;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    private float x = 0;
    private float y = 0;

	private int backgroundColor = Color.rgb(100, 160, 220);
	private int groundColor = Color.rgb(0, 255, 0);
	private int bombColor = Color.rgb(80, 80, 50);
	private DrawListener listener;

    public GameView(Context context)
    {
        super(context);

		initData();

        this.setOnTouchListener(this);
        setBackgroundColor(backgroundColor);

        //paint.setAntiAlias(true);

		paint.setColor(R.color.black);

		invalidate();
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

//		Repainter repainter = new Repainter(this, worldEngine);
		RepainterThread repainter = new RepainterThread(this, worldEngine);
		repainter.start();
	}

	@Override
	public void onDraw(Canvas canvas) {
		try {
			world = Manager.getWorld();

			System.out.println("Tegne jord..");
			drawEarth(canvas);
//			drawRobots(g);
			System.out.println("Tegne bomber..");
			drawBombs(canvas);
			System.out.println("Ferdig?");
		} finally {
			System.out.println("FERDIG med opptegning, gi beskjed til lytteren: " + listener);
			if (listener != null) {
				listener.paintCompleted();
			}
		}
    }

    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        x = motionEvent.getX();
        y = motionEvent.getY();

		createBomb(x, y);

        //invalidate();

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

	private void drawRobots(Graphics g) {
		for (Robot robot : world.getRobots()) {
			int x = Math.round(robot.getX());
			int y = getHeight() - Math.round(robot.getY());
			int radius = 4;

			g.setColor(java.awt.Color.BLACK);
			g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
		}

	}

	private void drawEarthSlice(EarthSlice earthSlice, Canvas canvas) {
		drawEarthSlicePiece(earthSlice.getTopSurface(), canvas);

		for (EarthSlicePiece earthSlicePiece : earthSlice.getSubSurfaces()) {
			drawEarthSlicePiece(earthSlicePiece, canvas);
		}
	}

	private void drawEarthSlicePiece(EarthSlicePiece earthSlicePiece, Canvas canvas) {
		int x = (int) earthSlicePiece.getX();
		int yTop = Math.round(getHeight() - earthSlicePiece.getY());
		//int yTop = 50;
		int yBottom = Math.round(earthSlicePiece.getDepth() == -1 ? getHeight() : yTop + earthSlicePiece.getDepth());

		paint.setColor(groundColor);
		paint.setStrokeWidth(1);

		canvas.drawLine(x, yTop, x, yBottom, paint);
		//canvas.drawRect(x, yTop, x + 1, yBottom, paint);
		//g.drawLine(x, yTop, x, yBottom);
	}

	private void drawBombs(Canvas canvas) {
		for (Bomb bomb : world.getBombs()) {
			drawBomb(bomb, canvas);
		}
	}

	private void drawBomb(Bomb bomb, Canvas canvas) {
		int radius = bomb.getStrength() / 4;
		if (radius < 1) {
			radius = 1;
		}

		float x = bomb.getX();
		float y = getHeight() - bomb.getY();

		paint.setColor(bombColor);

		canvas.drawCircle(x, y, radius, paint);
	}

	public void createBomb(float x, float y) {
		int strength = (int) (Math.random() * 50 + 5);
		Bomb bomb = new Bomb(x, getHeight() - y, strength);
		synchronized (world.getBombs()) {
			world.addBomb(bomb);
		}
	}
}
