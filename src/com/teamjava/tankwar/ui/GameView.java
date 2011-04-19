package com.teamjava.tankwar.ui;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.teamjava.tankwar.R;
import com.teamjava.tankwar.engine.RobotCommunicator;
import com.teamjava.tankwar.engine.WorldEngine;
import com.teamjava.tankwar.entities.Bomb;
import com.teamjava.tankwar.entities.GameSettings;
import com.teamjava.tankwar.entities.Manager;
import com.teamjava.tankwar.entities.Movement;
import com.teamjava.tankwar.entities.Robot;
import com.teamjava.tankwar.entities.World;
import com.teamjava.tankwar.util.MediaPlayerUtil;

/**
 * User: rak
 */
public class GameView extends View
    implements
        View.OnTouchListener,
        RobotCommunicator
{
    private Paint paint = new Paint();
	private World world;

    private float xPrev = 0;
    private float yPrev = 0;

    private DrawListener listener;
	private boolean hasDrawn;
    private MediaPlayerUtil mediaPlayer;

    public GameView(Context context)
    {
        super(context);

		initData();

        this.setOnTouchListener(this);
        setBackgroundResource(R.drawable.background_sky);
        mediaPlayer = new MediaPlayerUtil(this.getContext());
    }

	public void setListener(DrawListener listener) {
		this.listener = listener;
	}

	private void initData() {
		GameSettings gameSettings = new GameSettings(300);
		world = new World(gameSettings);

		Manager.setSettings(gameSettings);
		Manager.setWorld(world);

		Robot robotPlayer = new Robot();
		robotPlayer.setY(100);
		robotPlayer.setX(gameSettings.getWorldWidth() / 2); // todo random?

        // The robotPlayer need (a least for now) the context for this application
        // in order to create a bitmap from the resources.
        robotPlayer.setContext(this.getContext());

        final Robot robotComputer = new Robot();
        robotComputer.setY(100);
        robotComputer.setX(gameSettings.getWorldWidth()/4); // todo random?
        robotComputer.setContext(this.getContext());
        robotComputer.setComputer(true);

        world.addRobot(robotPlayer);
        world.addRobot(robotComputer);

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

			drawEarth(canvas);
			drawRobots(canvas);
			drawBombs(canvas);
		} finally {
			if (listener != null) {
				listener.paintCompleted();
			}
		}
	}

    public boolean onTouch(View view, MotionEvent motionEvent)
    {
		float x = motionEvent.getX();
		float y = motionEvent.getY();

        if(motionEvent.getAction() == MotionEvent.ACTION_MOVE)
        {
			float moveFactor = 0.5f;
			float xDiff = (x - xPrev) * moveFactor;
			float yDiff = (y - yPrev) * moveFactor;

			ViewCamera.translate(-xDiff, yDiff);
        }

		xPrev = x;
        yPrev = y;

        return true;
    }

	private void drawEarth(Canvas canvas) {
		int width = getWidth();
		int i = 0;
		while (i < width && i < world.getSurface().length) {
			world.getSurface()[i].paint(canvas, paint);
			i++;
		}
	}

	private void drawRobots(Canvas canvas) {
		for (Robot robot : world.getRobots()) {
			robot.paint(canvas, paint);
		}
	}

	private void drawBombs(Canvas canvas) {
		for (Bomb bomb : world.getBombs()) {
			bomb.paint(canvas, paint);
		}
	}

    @Override
    public void robotMoveLeft()
    {
        List<Robot> robotList = world.getRobots();

        // TODO(raymond) handle more robots.
        Robot robot = robotList.get(0);

		Movement movement = robot.getMovement();
		movement.setWalking(movement.getWalking() == Movement.Walk.left ? Movement.Walk.stop : Movement.Walk.left);
    }

    @Override
    public void robotMoveRight()
    {
        List<Robot> robotList = world.getRobots();

        // TODO(raymond) handle more robots.
        Robot robot = robotList.get(0);

		Movement movement = robot.getMovement();
		movement.setWalking(movement.getWalking() == Movement.Walk.right ? Movement.Walk.stop : Movement.Walk.right);
    }

    @Override
    public void robotTurretAngleChanged(float degrees)
    {
        List<Robot> robotList = world.getRobots();

        // TODO(raymond) handle more robots.
        Robot robot = robotList.get(0);
        robot.setTurretAngle(degrees);
    }

    @Override
    public void robotFire()
    {
        List<Robot> robotList = world.getRobots();

        // TODO(raymond) handle more robots.
        Robot robot = robotList.get(0);
        robot.fire();
        mediaPlayer.playSound(R.raw.fire);
    }

    @Override
    public void robotSetFirePower(int newFirePower)
    {
        // TODO(raymond) handle more robots.
        Robot robot = world.getRobots().get(0);

        robot.setBombFirePower(newFirePower);
    }

    @Override
    public void setWorldZoomLevel(float zoomLevel)
    {
       ViewCamera.setZoom(zoomLevel);
    }
}
