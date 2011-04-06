package com.teamjava.tankwar.ui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import com.teamjava.tankwar.entities.Bomb;
import com.teamjava.tankwar.entities.EarthSlice;
import com.teamjava.tankwar.entities.EarthSlicePiece;
import com.teamjava.tankwar.entities.Manager;
import com.teamjava.tankwar.entities.Robot;
import com.teamjava.tankwar.entities.World;

/**
 * @author Olav Jensen
 * @since Jan 19, 2011
 */
public class GamePanel extends JPanel {
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
