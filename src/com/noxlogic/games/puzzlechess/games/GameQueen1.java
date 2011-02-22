package com.noxlogic.games.puzzlechess.games;

import com.noxlogic.games.puzzlechess.Board;
import com.noxlogic.games.puzzlechess.pieces.King;
import com.noxlogic.games.puzzlechess.pieces.Piece;
import com.noxlogic.games.puzzlechess.pieces.Queen;
import com.noxlogic.games.puzzlechess.pieces.Rook;

public class GameQueen1 extends Game {	
	
	void init() {
		setGameOptions(Game.GAMEOPTION_BLACKFIXED);
		setMoves(4);
		
		boolean[][] fields = new boolean[8][8];
		for (int y=0; y!=8; y++) {
			for (int x=0; x!=8; x++) {
				fields[x][y] = true;
			}
		}
		
		// Create new board
		Board board = new Board(this, fields);
		
		// Add pieces to the board
		board.addPiece(new Queen("wq", Piece.WHITE), 2, 1);
		board.addPiece(new Rook("wr2", Piece.WHITE), 2, 3);
		board.addPiece(new King("bk2", Piece.BLACK), 6, 6);
				
		addBoard(board);
	}
		
	public boolean hasWon() {
		return false;
	}

	public String getObjective() {
		return "Checkmate the white king by moving the black pieces.";
	}

}
