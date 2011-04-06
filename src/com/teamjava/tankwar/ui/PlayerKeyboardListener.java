package com.teamjava.tankwar.ui;

import com.teamjava.tankwar.entities.Movement;
import com.teamjava.tankwar.entities.Robot;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Olav Jensen
 * @since Jan 25, 2011
 */
public class PlayerKeyboardListener extends KeyAdapter {

	private int leftKey = KeyEvent.VK_LEFT;
	private int rightKey = KeyEvent.VK_RIGHT;

	private final Robot robot;

	public PlayerKeyboardListener(Robot robot) {
		this.robot = robot;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		handleKeyEvent(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		handleKeyEvent(e.getKeyCode(), false);
	}

	private void handleKeyEvent(int keyCode, boolean pressed) {
		if (keyCode == leftKey || keyCode == rightKey) {
			if (pressed) {
				robot.getMovement().setWalking(keyCode == leftKey ? Movement.Walk.left : Movement.Walk.right);
			} else {
				robot.getMovement().setWalking(Movement.Walk.stop);
			}
		}
	}
}
