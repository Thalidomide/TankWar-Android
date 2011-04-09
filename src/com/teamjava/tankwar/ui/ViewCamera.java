package com.teamjava.tankwar.ui;

/**
 * @author Olav Jensen
 * @since 4/9/11
 */
public class ViewCamera {

	private static float zoom = 3;
	private static float xCam = 100;
	private static float yCam = -200;

	private static int viewWidth;
	private static int viewHeight;


	public static float getViewX(float xWorld) {
		return getZoomedSize(xWorld - xCam);
	}

	public static float getViewY(float yWorld) {
		return viewHeight - getZoomedSize(yWorld - yCam);
	}

	public static float getWorldViewX(float xCamValue) {
		return getUnZoomedSize(xCamValue + xCam);
	}

	public static float getWorldViewY(float yCamValue) {
		return getUnZoomedSize(yCamValue + yCam);
	}

	public static float getZoomedSize(float size) {
		return size * zoom;
	}

	public static float getUnZoomedSize(float zoomedSize) {
		return zoomedSize / zoom;
	}

	public static void translate(float x, float y) {
		xCam += x;
		yCam += y;
	}

	public static void setViewSize(int width, int height) {
		viewWidth = width;
		viewHeight = height;
		xCam = getUnZoomedSize(viewWidth / 4);
		yCam = - getUnZoomedSize(viewHeight / 2);
	}

	public static int getViewHeight() {
		return viewHeight;
	}
}
