package com.teamjava.tankwar.ui;

import com.teamjava.tankwar.engine.WorldEngine;
import com.teamjava.tankwar.entities.GlobalSettings;

/**
 * @author Olav Jensen
 * @since Feb 4, 2011
 */
public class Repainter extends Thread implements DrawListener {

	private boolean isDrawing;

	private final GamePanel gamePanel;
	private final WorldEngine worldEngine;

	public Repainter(GamePanel gamePanel, WorldEngine worldEngine) {
		this.gamePanel = gamePanel;
		this.worldEngine = worldEngine;

		gamePanel.setListener(this);
	}

	@Override
	public void run() {
		super.run();

		long sleepTime = 0;

		while (true) {
			try {
				if (!isDrawing) {
					isDrawing = true;
					worldEngine.updateWorld();
					gamePanel.repaint();
					sleepTime = GlobalSettings.REPAINT_SLEEP - worldEngine.getLastUpdateTime();
					if (sleepTime < 0) {
						sleepTime = 0;
					}
				} else {
					System.out.println("GamePanel var ikke ferdig med opptegning, droppe oppdatering/opptegning..");
				}
				sleep(sleepTime);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	@Override
	public void paintCompleted() {
		isDrawing = false;
	}
}
