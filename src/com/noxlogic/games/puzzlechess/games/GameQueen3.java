package com.noxlogic.games.puzzlechess.games;

import com.noxlogic.games.puzzlechess.Board;
import com.noxlogic.games.puzzlechess.pieces.Piece;
import com.noxlogic.games.puzzlechess.pieces.Queen;

public class GameQueen3 extends Game {	
	
	void init() {	
		setGameOptions(GAMEOPTION_UNLIMITEDMOVES);
		
		boolean[][] fields = new boolean[8][8];
		for (int y=0; y!=8; y++) {
			for (int x=0; x!=8; x++) {
				fields[x][y] = true;
			}
		}
		
		// Create new board
		Board board = new Board(this, fields);
		
		// Add pieces to the board
		board.addPiece(new Queen("wq1", Piece.WHITE, true), 0, 0);
		board.addPiece(new Queen("wq2", Piece.WHITE, true), 1, 0);
		board.addPiece(new Queen("wq3", Piece.WHITE, true), 2, 0);
		board.addPiece(new Queen("wq4", Piece.WHITE, true), 3, 0);
		board.addPiece(new Queen("wq5", Piece.WHITE, true), 4, 0);
		board.addPiece(new Queen("wq6", Piece.WHITE, true), 5, 0);
		board.addPiece(new Queen("wq7", Piece.WHITE, true), 6, 0);
		board.addPiece(new Queen("wq8", Piece.WHITE, true), 7, 0);
			
		addBoard(board);
	}
		
	public boolean hasWon() {
		return false;
	}

	public String getObjective() {
		return "Place all queens in such a pattern that none of them have another queen in line of sight.";
	}

}
