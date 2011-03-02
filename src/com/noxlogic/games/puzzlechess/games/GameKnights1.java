package com.noxlogic.games.puzzlechess.games;

import com.noxlogic.games.puzzlechess.Board;
import com.noxlogic.games.puzzlechess.pieces.Knight;
import com.noxlogic.games.puzzlechess.pieces.Piece;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;

public class GameKnights1 extends Game {	

	void initBoard() {
		boolean[][] fields = new boolean[8][8];
		for (int y=0; y!=8; y++) {
			for (int x=0; x!=8; x++) {
				fields[x][y] = false;
			}
		}
		fields[2][1] = true;	fields[2][2] = true;	fields[3][2] = true;
		fields[4][2] = true;	fields[5][2] = true;	fields[2][3] = true;
		fields[3][3] = true;	fields[4][3] = true;	fields[2][4] = true;
		fields[3][4] = true;
		
		// Create new board
		Board board = new Board(this, fields);
		
		// Add pieces to the board
		board.addPiece(new Knight("wk1", Piece.WHITE, true), 2, 1);
		board.addPiece(new Knight("wk2", Piece.WHITE, true), 2, 3);
		board.addPiece(new Knight("bk1", Piece.BLACK, true), 3, 3);
		board.addPiece(new Knight("bk2", Piece.BLACK, true), 5, 2);
		
		board.addDecorationToField(2, 1, plotDot(Color.BLACK));
		board.addDecorationToField(2, 3, plotDot(Color.BLACK));
		board.addDecorationToField(3, 3, plotDot(Color.WHITE));
		board.addDecorationToField(5, 2, plotDot(Color.WHITE));
		
		addBoard(board);
	}
	
	void init() {
		setGameOptions(Game.GAMEOPTION_UNLIMITEDMOVES);
		initBoard();
	}
	
	Bitmap plotDot(int color) {
		Bitmap bmp = Bitmap.createBitmap(320, 320, Config.ARGB_8888);
		Canvas bitmapcanvas = new Canvas(bmp);
		
		Paint p = new Paint();
		p.setColor(color);
		bitmapcanvas.drawCircle(20, 20, 5, p);
		
		return bmp;
	}
	

	public boolean hasWon() {
		int correctPieces = 0;
		Piece p;
		
		Board board = getBoard();
		
		p = board.findPiece("wk1");
		if (p.getX() == 3 && p.getY() == 3) { correctPieces++; }
		if (p.getX() == 5 && p.getY() == 2) { correctPieces++; }
		
		p = board.findPiece("wk2");
		if (p.getX() == 3 && p.getY() == 3) { correctPieces++; }
		if (p.getX() == 5 && p.getY() == 2) { correctPieces++; }

		p = board.findPiece("bk1");
		if (p.getX() == 2 && p.getY() == 1) { correctPieces++; }
		if (p.getX() == 2 && p.getY() == 3) { correctPieces++; }
		
		p = board.findPiece("bk2");
		if (p.getX() == 2 && p.getY() == 1) { correctPieces++; }
		if (p.getX() == 2 && p.getY() == 3) { correctPieces++; }
		
		return (correctPieces >= 4);
	}

	public String getObjective() {
		return "Switch the knights from position. The black knights should be placed on the fields with the black dots, and the white knights should be on the fields with white dots.";
	}

}
