package com.teamjava.tankwar.ui;

import android.os.Handler;

import com.teamjava.tankwar.engine.WorldEngine;

/**
 * @author Olav Jensen
 * @since Feb 4, 2011
 */
public class Repainter implements DrawListener {

	private boolean isDrawing;

	private final GameView gameView;
	private final Handler handler;
	private final WorldEngine worldEngine;

	public Repainter(GameView gameView, WorldEngine worldEngine, Handler handler) {
		this.gameView = gameView;
		this.worldEngine = worldEngine;
		this.handler = handler;

		gameView.setListener(this);
	}

	public void start() {
		long sleepTime = 0;

		while (true) {
			try {
				if (!isDrawing) {
					isDrawing = true;
					worldEngine.updateWorld();
					gameView.invalidate();
					handler.post(new Runnable(){
						@Override
						public void run() {
							System.out.println("HMM");
						}
					});
					//sleepTime = GlobalSettings.REPAINT_SLEEP - worldEngine.getLastUpdateTime();
					sleepTime = 100;
					if (sleepTime < 0) {
						sleepTime = 0;
					}
				} else {
					//System.out.println("GamePanel var ikke ferdig med opptegning, droppe oppdatering/opptegning..");
				}
				Thread.sleep(sleepTime);
				break;
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
