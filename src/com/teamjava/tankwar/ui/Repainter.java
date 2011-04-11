package com.teamjava.tankwar.ui;

import android.os.Handler;

import com.teamjava.tankwar.engine.WorldEngine;
import com.teamjava.tankwar.entities.GlobalSettings;

/**
 * @author Olav Jensen
 * @since Feb 4, 2011
 */
public class Repainter implements Runnable, DrawListener {

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

	@Override
	public void run() {
		long sleepTime = 0;
		if (!isDrawing) {
			isDrawing = true;

			worldEngine.updateWorld();
			gameView.invalidate();
			sleepTime = GlobalSettings.REPAINT_SLEEP - worldEngine.getLastUpdateTime();
			if (sleepTime < 0) {
				sleepTime = 0;
			}
		} else {
			//System.out.println("GamePanel var ikke ferdig med opptegning, droppe oppdatering/opptegning..");
		}

		handler.postDelayed(this, sleepTime);
	}

	@Override
	public void paintCompleted() {
		isDrawing = false;
	}
}
