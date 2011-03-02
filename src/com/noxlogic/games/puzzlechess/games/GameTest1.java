package com.noxlogic.games.puzzlechess.games;

import com.noxlogic.games.puzzlechess.Board;
import com.noxlogic.games.puzzlechess.pieces.Bishop;
import com.noxlogic.games.puzzlechess.pieces.King;
import com.noxlogic.games.puzzlechess.pieces.Knight;
import com.noxlogic.games.puzzlechess.pieces.Pawn;
import com.noxlogic.games.puzzlechess.pieces.Piece;
import com.noxlogic.games.puzzlechess.pieces.Queen;
import com.noxlogic.games.puzzlechess.pieces.Rook;

public class GameTest1 extends Game {	
	
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
		board.addPiece(new Knight("wr1", Piece.WHITE, true), 1, 1);
		board.addPiece(new Rook("wr2",   Piece.WHITE, true), 2, 2);
		board.addPiece(new Bishop("wr3", Piece.WHITE, true), 3, 3);
		board.addPiece(new Pawn("wr4",   Piece.WHITE, true), 4, 4);
		board.addPiece(new King("wr5",   Piece.WHITE, true), 5, 5);
		board.addPiece(new Queen("wr6",  Piece.WHITE, true), 6, 6);
			
		addBoard(board);
	}
		
	public boolean hasWon() {
		return false;
	}

	public String getObjective() {
		return "Test game";
	}

}
