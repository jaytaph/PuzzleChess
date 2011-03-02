package com.noxlogic.games.puzzlechess.games;

import com.noxlogic.games.puzzlechess.Board;
import com.noxlogic.games.puzzlechess.pieces.Knight;
import com.noxlogic.games.puzzlechess.pieces.Piece;

public class GameTraveller2 extends Game {	

	void initBoard() {
		boolean[][] fields = new boolean[8][8];
		for (int y=0; y!=8; y++) {
			for (int x=0; x!=8; x++) {
				fields[x][y] = false;
			}
		}
		
		// Create new board
		Board board = new Board(this, fields);
		
		// Add pieces to the board
		board.addPiece(new Knight("wk1", Piece.WHITE, true), 4, 0);
				
		addBoard(board);
	}
	
	void init() {
		setGameOptions(Game.GAMEOPTION_UNLIMITEDMOVES | Game.GAMEOPTION_TRACEMOVES);
		initBoard();
	}
	
	public boolean hasWon() {
		return (getBoard().getTraveledCells().size() == 64);
	}

	public String getObjective() {
		return "Switch the knights from position. The black knights should be placed on the fields with the black dots, and the white knights should be on the fields with white dots.";
	}

}
