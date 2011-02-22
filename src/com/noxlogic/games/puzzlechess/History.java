package com.noxlogic.games.puzzlechess;

import java.util.ArrayList;

import com.noxlogic.games.puzzlechess.games.Game;
import com.noxlogic.games.puzzlechess.pieces.Piece;

public class History {
	protected Game _game;
	protected ArrayList<String> _history;
	
	public History(Game game) {
		_game = game;
	}
	
	void addMove (int src_x, int src_y, int dst_x, int dst_y) {
		String src_piece_name;
		String dst_piece_name;
		
		Piece src_piece = _game.getBoard().getPieceFromXY(src_x, src_y);
		Piece dst_piece = _game.getBoard().getPieceFromXY(dst_x, dst_y);
		
		if (src_piece == null) {
			src_piece_name = "???";
		} else {
			src_piece_name = src_piece.getName();
		}
		
		if (dst_piece == null) {
			dst_piece_name = "";
		} else {
			dst_piece_name = dst_piece.getName();
		}
		
		_history.add(src_piece_name + " " + src_x + ":" + src_y + " -> " + dst_piece_name + " " + dst_x + ":" + dst_y);
	}
	
	ArrayList<String> getHistory() {
		return _history;
	}

}
