package com.teamjava.tankwar.engine.util;

/**
 * @author Olav Jensen
 * @since 5/1/11
 */
public class MathUtil {

	public static float getYBetweenPoints(float x1, float y1, float x2, float y2, float x) {
		assert(x2 > x1 && x >= x1 && x <= x2);

		float xT = (x - x1) / (x2 - x1);

		float yDiff = y2 - y1;

		return yDiff * xT + y1;
	}
}
