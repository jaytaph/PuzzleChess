package com.noxlogic.games.puzzlechess.pieces;

import java.util.ArrayList;
import com.noxlogic.games.puzzlechess.R;

public class Knight extends Piece {

	public Knight(String tag, int color) {
		super(tag, color);
	}

	protected ArrayList<int[]> _getAvailableMoves() {	
		ArrayList<int[]> ret = new ArrayList<int[]>();
		ret = knightMoves(ret);
		return ret;
	}

	int getResource() {
		if (getColor() == Piece.BLACK) return R.drawable.bn;
		return R.drawable.wn;
	}

	public String getName() {
		if (getColor() == Piece.BLACK) return "Black knight";
		return "White knight";
	}

}
