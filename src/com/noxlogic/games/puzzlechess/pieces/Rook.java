package com.noxlogic.games.puzzlechess.pieces;

import java.util.ArrayList;
import com.noxlogic.games.puzzlechess.R;

public class Rook extends Piece {

	public Rook(String tag, int color, boolean moveable) {
		super(tag, color, moveable);
	}

	protected ArrayList<int[]> _getAvailableMoves() {	
		ArrayList<int[]> ret = new ArrayList<int[]>();
		ret = straightMoves(ret);
		return ret;
	}

	int getResource() {
		if (getColor() == Piece.BLACK) return R.drawable.br;
		return R.drawable.wr;
	}

	public String getName() {
		if (getColor() == Piece.BLACK) return "Black rook";
		return "White rook";
	}

}
