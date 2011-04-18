package com.teamjava.tankwar.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author Olav Jensen
 * @since Jan 22, 2011
 */
public class EarthSlice implements Paintable {

	private EarthSlicePiece topSlicePiece;
	private List<EarthSlicePiece> subSlicePieces = new ArrayList<EarthSlicePiece>();

	private final int groundColor;

	public EarthSlice(EarthSlicePiece topSlicePiece, int groundColor) {
		this.topSlicePiece = topSlicePiece;
		this.groundColor = groundColor;
	}

	@Override
	public void paint(Canvas canvas, Paint paint) {
		paint.setColor(groundColor);

		topSlicePiece.paint(canvas, paint);

		for (EarthSlicePiece earthSlicePiece : subSlicePieces) {
			earthSlicePiece.paint(canvas, paint);
		}
	}

	public EarthSlicePiece getTopSurface() {
		return topSlicePiece;
	}

	public void setTopSurface(EarthSlicePiece topSlicePiece) {
		this.topSlicePiece = topSlicePiece;
	}

	/**
	 * @return the pieces sorted by deepest pieces first.
	 */
	public List<EarthSlicePiece> getSubSurfaces() {
		return subSlicePieces;
	}

	public void addSubSurface(EarthSlicePiece newBottomPiece) {
		subSlicePieces.add(newBottomPiece);
	}

	public void sortSubSurfaces() {
		Collections.sort(getSubSurfaces());
	}

	public int getGroundColor() {
		return groundColor;
	}
}
