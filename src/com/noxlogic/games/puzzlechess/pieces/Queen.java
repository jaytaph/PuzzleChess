package com.noxlogic.games.puzzlechess.pieces;

import java.util.ArrayList;
import com.noxlogic.games.puzzlechess.R;


public class Queen extends Piece {

	public Queen(String tag, int color, boolean moveable) {
		super(tag, color, moveable);
	}

	protected ArrayList<int[]> _getAvailableMoves() {
		ArrayList<int[]> ret = new ArrayList<int[]>();
		ret = straightMoves(ret);
		ret = diagonalMoves(ret);
		return ret;
	}

	int getResource() {
		if (getColor() == Piece.BLACK) return R.drawable.bq;
		return R.drawable.wq;
	}

	public String getName() {
		if (getColor() == Piece.BLACK) return "Black queen";
		return "White queen";
	}

}
