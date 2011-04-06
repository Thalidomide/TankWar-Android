package com.teamjava.tankwar.engine;

import com.teamjava.tankwar.entities.EarthSlice;
import com.teamjava.tankwar.entities.EarthSlicePiece;
import com.teamjava.tankwar.entities.World;

import java.util.Iterator;
import java.util.List;

/**
 * @author Olav Jensen
 * @since Jan 22, 2011
 */
public class GapCreator {

	public static void createGap(World world, int x, float y, float depth) {
		if (depth <= 0f) {
			return;
		}
		EarthSlice slice = world.getSurface()[x];

		String log = "Struktur FØR oppretting av hull:\n" + DebugUtils.printStructureStatus(slice);
		log += "Begynne oppretting av hull...";

		float yGapBottom = y - depth;

		EarthSlicePiece hitTopPiece = findPieceAtPosition(slice, y);
		EarthSlicePiece hitBottomPiece = findPieceAtPosition(slice, yGapBottom);

		log += "Lage gap ved y=" + y + ", dybde=" + depth + "\n";
		log += "Truffet toppbit=" + hitTopPiece + ", truffet bunnbit=" + hitBottomPiece + "\n";

		if (hitTopPiece != null) {
			log += "Fins en truffet topp...\n";
			if (hitBottomPiece == hitTopPiece) {
				log += "Topp og bunn er samme, skal splittes...\n";
				EarthSlicePiece newBottomPiece = new EarthSlicePiece();
				newBottomPiece.setSpeed(hitTopPiece.getSpeed());
				newBottomPiece.setY(yGapBottom);
				newBottomPiece.setX(x);

				float newDepth = hitTopPiece.getDepth() == -1 ? -1 : newBottomPiece.getY() - hitTopPiece.getBottomYPosition();
				newBottomPiece.setDepth(newDepth);

				slice.addSubSurface(newBottomPiece);

				log += "Opprettet bunn til å være på y=" + newBottomPiece.getY() + ", dybde=" + newBottomPiece.getDepth() + "\n";
			} else if (hitBottomPiece != null) {
				adjustButtomPiece(y, depth, hitBottomPiece);
				log += "Justert bunn til å være på y=" + hitBottomPiece.getY() + ", dybde=" + hitBottomPiece.getDepth() + "\n";
			}

			hitTopPiece.setDepth(hitTopPiece.getY() - y);
			log += "Justert truffet topp til å være på y=" + hitTopPiece.getY() + ", dybde=" + hitTopPiece.getDepth() + "\n";
		} else if (hitBottomPiece != null) {
			log += "Fins kun en truffet bunn...\n";
			adjustButtomPiece(y, depth, hitBottomPiece);
			log += "Justert bunn til å være på y=" + hitBottomPiece.getY() + ", dybde=" + hitBottomPiece.getDepth() + "\n";
		}

		removeSubSlicesCoveredByGap(slice.getSubSurfaces(), y, depth);

		slice.sortSubSurfaces();

		if (hitTopPiece == null && slice.getTopSurface().getDepth() != -1 &&
				y > slice.getTopSurface().getY() && y - depth < slice.getTopSurface().getBottomYPosition()) {
			EarthSlicePiece newTopPiece = slice.getSubSurfaces().get(slice.getSubSurfaces().size() - 1);
			slice.setTopSurface(newTopPiece);
			slice.getSubSurfaces().remove(newTopPiece);
		}

		log += "Struktur ETTER oppretting av hull:\n" + DebugUtils.printStructureStatus(slice);

		DebugUtils.printIfWrongStructure(slice, log, GapCreator.class);
	}

	private static void removeSubSlicesCoveredByGap(List<EarthSlicePiece> subSurfaces, float y, float depth) {
		float yBottom = y - depth;

		for (Iterator<EarthSlicePiece> it = subSurfaces.iterator(); it.hasNext();) {
			EarthSlicePiece subSurface = it.next();

			if (subSurface.getDepth() == -1) {
				continue;
			}

			float subYTop = subSurface.getY();
			float subYBottom = subYTop - subSurface.getDepth();

			if (subYTop <= y && subYTop > yBottom && subYBottom >= yBottom) {
				it.remove();
			}
		}
	}

	private static EarthSlicePiece findPieceAtPosition(EarthSlice earthSlice, float yPos) {
		if (EarthSlicePieceCoversPosition(earthSlice.getTopSurface(), yPos)) {
			return earthSlice.getTopSurface();
		}

		for (EarthSlicePiece earthSlicePiece : earthSlice.getSubSurfaces()) {
			if (EarthSlicePieceCoversPosition(earthSlicePiece, yPos)) {
				return earthSlicePiece;
			}
		}

		return null;
	}

	private static void adjustButtomPiece(float y, float depth, EarthSlicePiece hitBottomPiece) {
		float cutDiff = hitBottomPiece.getY() - y + depth;
		if (hitBottomPiece.getDepth() != -1) {
			hitBottomPiece.setDepth(hitBottomPiece.getDepth() - cutDiff);
		}
		hitBottomPiece.setY(hitBottomPiece.getY() - cutDiff);
	}

	private static boolean EarthSlicePieceCoversPosition(EarthSlicePiece piece, float yPos) {
		return yPos <= piece.getY() && (piece.getDepth() == -1 || yPos >= piece.getY() - piece.getDepth());
	}
}
