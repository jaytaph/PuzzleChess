package com.noxlogic.games.puzzlechess.pieces;

import java.util.ArrayList;
import com.noxlogic.games.puzzlechess.R;

public class Pawn extends Piece {

	Pawn(String tag, int color) {
		super(tag, color);
	}

	protected ArrayList<int[]> _getAvailableMoves() {
		int x, y;		
		ArrayList<int[]> ret = new ArrayList<int[]>();
		
		x = getX()+1; 
		y = getY()-2;
		if (isValidAvailableField(x, y)) ret.add(new int[] {x, y});

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
