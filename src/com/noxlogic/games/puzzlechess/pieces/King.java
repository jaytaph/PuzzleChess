package com.noxlogic.games.puzzlechess.pieces;

import java.util.ArrayList;
import com.noxlogic.games.puzzlechess.R;

public class King extends Piece {

	public King(String tag, int color) {
		super(tag, color);
	}

	protected ArrayList<int[]> _getAvailableMoves() {
		int x, y;		
		ArrayList<int[]> ret = new ArrayList<int[]>();
		
		x = getX()-1; 
		y = getY()-1;
		if (isValidAvailableField(x, y)) ret.add(new int[] {x, y});

		x = getX(); 
		y = getY()-1;
		if (isValidAvailableField(x, y)) ret.add(new int[] {x, y});

		x = getX()+1; 
		y = getY()-1;
		if (isValidAvailableField(x, y)) ret.add(new int[] {x, y});

		x = getX()-1; 
		y = getY();
		if (isValidAvailableField(x, y)) ret.add(new int[] {x, y});

		x = getX()+1; 
		y = getY();
		if (isValidAvailableField(x, y)) ret.add(new int[] {x, y});

		x = getX()-1; 
		y = getY()+1;
		if (isValidAvailableField(x, y)) ret.add(new int[] {x, y});

		x = getX(); 
		y = getY()+1;
		if (isValidAvailableField(x, y)) ret.add(new int[] {x, y});

		x = getX()+1; 
		y = getY()+1;
		if (isValidAvailableField(x, y)) ret.add(new int[] {x, y});
		
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
