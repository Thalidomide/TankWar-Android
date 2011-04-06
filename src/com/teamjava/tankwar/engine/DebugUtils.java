package com.teamjava.tankwar.engine;

import com.teamjava.tankwar.entities.EarthSlice;
import com.teamjava.tankwar.entities.EarthSlicePiece;

/**
 * @author Olav Jensen
 * @since Jan 23, 2011
 */
public class DebugUtils {

	public static boolean isMissingCorrectBottom(EarthSlice slice) {
		if ((!slice.getSubSurfaces().isEmpty() && slice.getSubSurfaces().get(0).getDepth() != -1) ||
				(slice.getSubSurfaces().isEmpty() && slice.getTopSurface().getDepth() != -1)) {
			return true;
		}

		return false;
	}

	public static boolean isPiecesOverlapping(EarthSlice slice) {
		if (slice.getSubSurfaces().isEmpty()) {
			return false;
		}

		float tmpY = slice.getSubSurfaces().get(0).getY();

		for (int i = 1; i < slice.getSubSurfaces().size(); i++) {
			EarthSlicePiece subPiece = slice.getSubSurfaces().get(i);

			if (subPiece.getBottomYPosition() < tmpY) {
				System.out.println("Jordstykke har bunnposisjon på y=" + subPiece.getBottomYPosition() +
						", overlapper med stykket under med y=" + tmpY);
				return true;
			}
			tmpY = subPiece.getY();
		}

		if (slice.getTopSurface().getBottomYPosition() < tmpY) {
			System.out.println("Topp jordstykke har bunnposisjon på y=" + slice.getTopSurface().getBottomYPosition() +
					", overlapper med stykket under med y=" + tmpY);
			return true;
		}

		return false;
	}

	public static String printStructureStatus(EarthSlice slice) {
		String res = "Mangler korrekt bunn: " + DebugUtils.isMissingCorrectBottom(slice) + "\n";
		res += "Har allerede overlapping: " + DebugUtils.isPiecesOverlapping(slice) + "\n";
		res += "Struktur:\n";
		res += DebugUtils.printStructure(slice);

		return res;
	}

	public static void printIfWrongStructure(EarthSlice slice, String log, Class<?> clazz) {
		if (isMissingCorrectBottom(slice)) {
			System.out.println(clazz.getSimpleName() + " - struktur mangler bunn med -1 dybde! Log: " + log + "-------------------------------");
		} else if (DebugUtils.isPiecesOverlapping(slice)) {
			System.out.println(clazz.getSimpleName() + " - struktur har overlapping! Log: " + log + "-------------------------------");
		}
	}

	private static String printStructure(EarthSlice slice) {
		String res = "";
		int index = 1;

		if (!slice.getSubSurfaces().isEmpty()) {
			for (EarthSlicePiece subPiece : slice.getSubSurfaces()) {
				res += printEarthPiece(subPiece, index++ + ". understykke:");
			}
		} else {
			res += "Ingen jordstykker under overflaten...\n";
		}
		res += printEarthPiece(slice.getTopSurface(), "Toppbit:");

		return res;
	}

	private static String printEarthPiece(EarthSlicePiece piece, String pieceDescription) {
		return pieceDescription + " y=" + piece.getY() + ", dybde=" + piece.getDepth() + ", hastighet: " + piece.getSpeed() + "\n";
	}
}
