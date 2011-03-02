package com.noxlogic.games.puzzlechess.pieces;

import java.util.ArrayList;
import com.noxlogic.games.puzzlechess.R;

public class Pawn extends Piece {

	public Pawn(String tag, int color, boolean moveable) {
		super(tag, color, moveable);
	}

	protected ArrayList<int[]> _getAvailableMoves() {
		ArrayList<int[]> ret = new ArrayList<int[]>();		
		ret = singleUpDownMoves(ret);
		return ret;
	}

	int getResource() {
		if (getColor() == Piece.BLACK) return R.drawable.bp;
		return R.drawable.wp;
	}

	public String getName() {
		if (getColor() == Piece.BLACK) return "Black pawn";
		return "White pawn";
	}

}
