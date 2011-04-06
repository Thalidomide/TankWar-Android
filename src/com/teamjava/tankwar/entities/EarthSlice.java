package com.teamjava.tankwar.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Olav Jensen
 * @since Jan 22, 2011
 */
public class EarthSlice {

	private EarthSlicePiece topSlicePiece;
	private List<EarthSlicePiece> subSlicePieces = new ArrayList<EarthSlicePiece>();

	public EarthSlice(EarthSlicePiece topSlicePiece) {
		this.topSlicePiece = topSlicePiece;
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
}
