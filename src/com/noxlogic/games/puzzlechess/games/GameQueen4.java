package com.noxlogic.games.puzzlechess.games;

import com.noxlogic.games.puzzlechess.Board;
import com.noxlogic.games.puzzlechess.pieces.Piece;
import com.noxlogic.games.puzzlechess.pieces.Queen;

public class GameQueen4 extends Game {	
	
	void init() {	
		setGameOptions(GAMEOPTION_UNLIMITEDMOVES);
		
		boolean[][] fields = new boolean[8][8];
		for (int y=0; y!=8; y++) {
			for (int x=0; x!=8; x++) {
				fields[x][y] = false;
			}
		}
		
		for (int y=1; y!=6; y++) {
			for (int x=1; x!=6; x++) {
				fields[x][y] = true;
			}
		}
		
		// Create new board
		Board board = new Board(this, fields);
		
		// Add pieces to the board
		board.addPiece(new Queen("wq1", Piece.WHITE), 3, 3);
		board.addPiece(new Queen("wq2", Piece.WHITE), 4, 4);
		board.addPiece(new Queen("wq3", Piece.WHITE), 5, 5);
			
		addBoard(board);
	}
		
	public boolean hasWon() {
		return false;
	}

	public String getObjective() {
		return "Place all queens in such a pattern that fields are in line of sight of a queen.";
	}

}
