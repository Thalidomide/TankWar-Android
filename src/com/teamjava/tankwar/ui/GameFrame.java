package com.teamjava.tankwar.ui;

import com.teamjava.tankwar.engine.WorldEngine;
import com.teamjava.tankwar.entities.GlobalSettings;
import com.teamjava.tankwar.entities.Manager;
import com.teamjava.tankwar.entities.Robot;
import com.teamjava.tankwar.entities.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Olav Jensen
 * @since Jan 19, 2011
 */
public class GameFrame extends JFrame {

	private GamePanel gamePanel;
	private WorldEngine worldEngine;
	private Repainter repainter;

	public GameFrame() throws HeadlessException {
		gamePanel = new GamePanel();
		getContentPane().add(gamePanel);

		setTitle("Tanks war POC - version: " + GlobalSettings.VERSION);
		setLocation(0, 0);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1200, 900);
		gamePanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				for (int i = 0; i < 1; i++) {
					int offset = 0;
					int x = e.getX() + (int) Math.round((Math.random() * offset) - offset / 2);
					int y = e.getY() + (int) Math.round((Math.random() * offset) - offset / 2);

					if (x >= 0 && x < Manager.getSettings().getWorldWidth()) {
						gamePanel.createBomb(x, y);
					}
				}
			}
		});

		setVisible(true);
	}

	public void startGame() {
		Robot robot = new Robot();
		robot.setY(1000);
		robot.setX(10);

		World world = Manager.getWorld();
		world.addRobot(robot);

		addKeyListener(new PlayerKeyboardListener(robot));

		worldEngine = new WorldEngine(world);

		repainter = new Repainter(gamePanel, worldEngine);
		repainter.start();
	}
}
