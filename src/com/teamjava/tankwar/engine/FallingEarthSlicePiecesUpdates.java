package com.teamjava.tankwar.engine;

import com.teamjava.tankwar.entities.EarthSlice;
import com.teamjava.tankwar.entities.EarthSlicePiece;
import com.teamjava.tankwar.entities.GlobalSettings;

/**
 * @author Olav Jensen
 * @since Jan 22, 2011
 */
public class FallingEarthSlicePiecesUpdates {

	/**
	 * Update the position of the {@link EarthSlicePiece}'s.
	 *
	 * @param slice the {@link EarthSlice} to update.
	 * @return true if the {@link EarthSlice} no longer contains elements to update.
	 */
	public static boolean updateFallingPieces(EarthSlice slice) {
		String log = "Struktur FØR oppdatering av fallende jord:\n" + DebugUtils.printStructureStatus(slice);

		EarthSlicePiece previousPiece = slice.getSubSurfaces().get(0);

		for (int i = 1; i < slice.getSubSurfaces().size(); i++) {
			EarthSlicePiece piece = slice.getSubSurfaces().get(i);
			log += "Sjekke om bit med y=" + piece.getY() + ", dybde=" + piece.getDepth() + " treffer biten under..\n";

			boolean hitSubPiece = updateEarthPiecePosition(previousPiece, piece);

			if (hitSubPiece) {
				log += "Biten traff forrige bit, fjerne bit. Forrige bit er nå y=" + previousPiece.getY() + ", dybde=" + previousPiece.getDepth() + "\n";
				slice.getSubSurfaces().remove(piece);
				i--;
			} else {
				previousPiece = piece;
			}
		}

		boolean hitSubPiece = updateEarthPiecePosition(previousPiece, slice.getTopSurface());
		log += "Sjekke om toppbit med y=" + slice.getTopSurface().getY() + ", dybde=" + slice.getTopSurface().getDepth() + " treffer biten under..\n";

		if (hitSubPiece) {
			log += "Toppbiten traff forrige bit, fjerne bit. Forrige bit er nå y=" + previousPiece.getY() + ", dybde=" + previousPiece.getDepth() + "\n";
			slice.setTopSurface(previousPiece);
			slice.getSubSurfaces().remove(previousPiece);
		}

		slice.sortSubSurfaces();

		log += "Struktur ETTER oppdatering av fallende jord:\n" + DebugUtils.printStructureStatus(slice);

		DebugUtils.printIfWrongStructure(slice, log, FallingEarthSlicePiecesUpdates.class);

		return slice.getSubSurfaces().isEmpty();
	}

	private static boolean isBroken(EarthSlice earthSlice) {
		return earthSlice.getSubSurfaces().isEmpty() && earthSlice.getTopSurface().getDepth() != -1f;
	}

	private static boolean updateEarthPiecePosition(EarthSlicePiece previousPiece, EarthSlicePiece piece) {
		piece.increaseSpeed(GlobalSettings.GRAVITY);

		float distanceToBelowPiece = piece.getBottomYPosition() - previousPiece.getY();
		boolean hitSubPiece = piece.moveDistance(distanceToBelowPiece);

		if (hitSubPiece) {
			previousPiece.setY(piece.getY());
			if (previousPiece.getDepth() != -1) {
				previousPiece.setDepth(previousPiece.getDepth() + piece.getDepth());
			}
		}

		return hitSubPiece;
	}
}
