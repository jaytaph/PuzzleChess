package com.noxlogic.games.puzzlechess.pieces;

import java.util.ArrayList;
import com.noxlogic.games.puzzlechess.R;

public class King extends Piece {

	public King(String tag, int color, boolean moveable) {
		super(tag, color, moveable);
	}

	protected ArrayList<int[]> _getAvailableMoves() {	
		ArrayList<int[]> ret = new ArrayList<int[]>();
		ret = singleDiagonalMoves(ret);
		ret = singleLeftRightMoves(ret);
		ret = singleUpDownMoves(ret);
		return ret;
	}

	int getResource() {
		if (getColor() == Piece.BLACK) return R.drawable.bk;
		return R.drawable.wk;
	}

	public String getName() {
		if (getColor() == Piece.BLACK) return "Black king";
		return "White king";
	}

}
