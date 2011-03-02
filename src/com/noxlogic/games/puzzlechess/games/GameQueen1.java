package com.noxlogic.games.puzzlechess.games;

import com.noxlogic.games.puzzlechess.Board;
import com.noxlogic.games.puzzlechess.pieces.Piece;
import com.noxlogic.games.puzzlechess.pieces.Queen;

public class GameQueen1 extends Game {	
	
	void init() {		
		setGameOptions(GAMEOPTION_UNLIMITEDMOVES);
		
		boolean[][] fields = new boolean[8][8];
		for (int y=0; y!=8; y++) {
			for (int x=0; x!=8; x++) {
				fields[x][y] = false;
			}
		}
		
		for (int y=2; y!=6; y++) {
			for (int x=2; x!=6; x++) {
				fields[x][y] = true;
			}
		}

		// Create new board
		Board board = new Board(this, fields);
		
		// Add pieces to the board
		board.addPiece(new Queen("wq1", Piece.WHITE, true), 2, 2);
		board.addPiece(new Queen("wq2", Piece.WHITE, true), 3, 2);
		board.addPiece(new Queen("wq3", Piece.WHITE, true), 4, 2);
		board.addPiece(new Queen("wq4", Piece.WHITE, true), 5, 2);
				
		addBoard(board);
	}
		
	public boolean hasWon() {
		return false;
		
//		// Check all pieces
//		for (Piece piece : getBoard().getPieces()) {
//			// Check the moves that can be made by this piece
//			for (int[] field : piece.getLineOfSight()) {
//				// Check if there is a piece on destination. If so, we must return false
//				Piece dst_piece = getBoard().getPieceFromXY(field[0], field[1]);
//				if (dst_piece != null) return false;
//			}
//		}
//		
//		// All available moves have a empty destination (aka: no pieces are in line of sight from eachother)
//		return true;
	}

	public String getObjective() {
		return "Place all queens in such a pattern that none of them have another queen in line of sight.";
	}

}
