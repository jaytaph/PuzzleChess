package com.noxlogic.games.puzzlechess.games;

import com.noxlogic.games.puzzlechess.Board;
import com.noxlogic.games.puzzlechess.pieces.Piece;
import com.noxlogic.games.puzzlechess.pieces.Queen;

public class GameQueen2 extends Game {	
	
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
		board.addPiece(new Queen("bq1", Piece.BLACK), 1, 1);
		board.addPiece(new Queen("bq2", Piece.BLACK), 2, 1);
		board.addPiece(new Queen("bq3", Piece.BLACK), 3, 1);
		board.addPiece(new Queen("bq4", Piece.BLACK), 4, 1);
		board.addPiece(new Queen("bq5", Piece.BLACK), 5, 1);
				
		addBoard(board);
	}
		
	public boolean hasWon() {
		return false;
	}

	public String getObjective() {
		return "Place all queens in such a pattern that none of them have another queen in line of sight.";
	}

}
