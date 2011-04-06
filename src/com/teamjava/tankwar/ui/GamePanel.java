package com.teamjava.tankwar.ui;

import com.teamjava.tankwar.entities.*;
import com.teamjava.tankwar.entities.Robot;

import javax.swing.*;
import java.awt.*;

/**
 * @author Olav Jensen
 * @since Jan 19, 2011
 */
public class GamePanel extends JPanel {

    /*
    public class DrawView extends View implements OnTouchListener {
	private ShapeEnum currentShape = ShapeEnum.Circle;

	private int currentColor = Color.BLUE;

	private List<Shape> shapes = new ArrayList<Shape>();

	public DrawView(Context context) {
		super(context);

		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
		this.setOnTouchListener(this);
	}

	@Override
	public void onDraw(Canvas canvas) {
		for (Shape shape : shapes) {
			shape.drawShape(canvas);
		}
	}

	public void restartPainting() {
		shapes.clear();
		invalidate();
	}

	public void setCurrentShape(ShapeEnum shapeEnum) {
		currentShape = shapeEnum;
	}

	public void setCurrentColor(int color) {
		currentColor = color;
	}

	public boolean onTouch(View view, MotionEvent event) {
		Shape shape = null;

		switch (currentShape) {
			case Circle:
				shape = new Circle(event.getX(), event.getY(), 5f);
				break;
			case Text:
				shape = new Text(event.getX(), event.getY());
				break;
			case Rectangle:
				shape = new Rectangle(event.getX(), event.getY());
				break;
			case Triangle:
				shape = new Triangle(event.getX(), event.getY());
				break;
			default:
				break;
		}

		if (shape != null) {
			shape.setPaintColor(currentColor);
			shapes.add(shape);
			invalidate();
		}

		return true;
	}
}
    */




	private World world;
	private Color background = new Color(100, 160, 220);

	private DrawListener listener;

	public void setListener(DrawListener listener) {
		this.listener = listener;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		try {
			world = Manager.getWorld();
			g.setColor(background);
			g.fillRect(0, 0, getWidth(), getHeight());

			drawEarth(g);
			drawRobots(g);
			drawBombs(g);
		} finally {
			if (listener != null) {
				listener.paintCompleted();
			}
		}
	}

	private void drawEarth(Graphics g) {
		int width = getWidth();
		int i = 0;
		while (i < width && i < world.getSurface().length) {
			drawEarthSlice(world.getSurface()[i], g);
			i++;
		}
	}

	private void drawRobots(Graphics g) {
		for (Robot robot : world.getRobots()) {
			int x = Math.round(robot.getX());
			int y = getHeight() - Math.round(robot.getY());
			int radius = 4;

			g.setColor(Color.BLACK);
			g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
		}

	}

	private void drawEarthSlice(EarthSlice earthSlice, Graphics g) {
		drawEarthSlicePiece(earthSlice.getTopSurface(), g);

		for (EarthSlicePiece earthSlicePiece : earthSlice.getSubSurfaces()) {
			drawEarthSlicePiece(earthSlicePiece, g);
		}
	}

	private void drawEarthSlicePiece(EarthSlicePiece earthSlicePiece, Graphics g) {
		int x = (int) earthSlicePiece.getX();
		int yTop = Math.round(getHeight() - earthSlicePiece.getY());
		int yBottom = Math.round(earthSlicePiece.getDepth() == -1 ? getHeight() : yTop + earthSlicePiece.getDepth());

		g.setColor(Color.green);
		g.drawLine(x, yTop, x, yBottom);
	}

	private void drawBombs(Graphics g) {
		for (Bomb bomb : world.getBombs()) {
			drawBomb(bomb, g);
		}
	}

	private void drawBomb(Bomb bomb, Graphics g) {
		int radius = bomb.getStrength() / 4;
		if (radius < 1) {
			radius = 1;
		}
		int diameter = radius * 2;

		int x = Math.round(bomb.getX() - radius);
		int y = getHeight() - Math.round(bomb.getY() + radius);

		g.setColor(Color.gray);
		g.fillOval(x, y, diameter, diameter);
	}

	public void createBomb(int x, int y) {
		int strength = (int) (Math.random() * 50 + 5);
		Bomb bomb = new Bomb(x, getHeight() - y, strength);
		synchronized (world.getBombs()) {
			world.addBomb(bomb);
		}
	}
}
