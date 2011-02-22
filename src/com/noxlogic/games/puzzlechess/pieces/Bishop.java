package com.noxlogic.games.puzzlechess.pieces;

import java.util.ArrayList;

import com.noxlogic.games.puzzlechess.R;

public class Bishop extends Piece {

	public Bishop(String tag, int color) {
		super(tag, color);
	}

	protected ArrayList<int[]> _getAvailableMoves() {
		ArrayList<int[]> ret = new ArrayList<int[]>();
		ret = diagonalMoves(ret);
		return ret;
	}

	int getResource() {
		if (getColor() == Piece.BLACK) return R.drawable.bb;
		return R.drawable.wb;
	}

	public String getName() {
		if (getColor() == Piece.BLACK) return "Black bishop";
		return "White bishop";
	}

}
