package com.noxlogic.games.puzzlechess.games;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;

import com.noxlogic.games.puzzlechess.Board;
import com.noxlogic.games.puzzlechess.pieces.Bishop;
import com.noxlogic.games.puzzlechess.pieces.Piece;

public class GameBishop1 extends Game {	
	
	void init() {	
		setGameOptions(GAMEOPTION_UNLIMITEDMOVES);
		
		boolean[][] fields = new boolean[8][8];
		for (int y=0; y!=8; y++) {
			for (int x=0; x!=8; x++) {
				fields[x][y] = false;
			}
		}
		
		for (int y=1; y!=6; y++) {
			for (int x=2; x!=6; x++) {
				fields[x][y] = true;
			}
		}

		// Create new board
		Board board = new Board(this, fields);
		
		// Add pieces to the board
		board.addPiece(new Bishop("wb1", Piece.WHITE, true), 2, 1);
		board.addPiece(new Bishop("wb2", Piece.WHITE, true), 3, 1);
		board.addPiece(new Bishop("wb3", Piece.WHITE, true), 4, 1);
		board.addPiece(new Bishop("wb4", Piece.WHITE, true), 5, 1);
		
		board.addPiece(new Bishop("bb1", Piece.BLACK, true), 2, 5);
		board.addPiece(new Bishop("bb2", Piece.BLACK, true), 3, 5);
		board.addPiece(new Bishop("bb3", Piece.BLACK, true), 4, 5);
		board.addPiece(new Bishop("bb4", Piece.BLACK, true), 5, 5);

		board.addDecorationToField(2, 1, plotDot(Color.BLACK));
		board.addDecorationToField(3, 1, plotDot(Color.BLACK));
		board.addDecorationToField(4, 1, plotDot(Color.BLACK));
		board.addDecorationToField(5, 1, plotDot(Color.BLACK));
		
		board.addDecorationToField(2, 5, plotDot(Color.WHITE));
		board.addDecorationToField(3, 5, plotDot(Color.WHITE));
		board.addDecorationToField(4, 5, plotDot(Color.WHITE));
		board.addDecorationToField(5, 5, plotDot(Color.WHITE));

		addBoard(board);
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
		return false;
	}

	public String getObjective() {
		return "Switch the bishops from position. The black bishops should be placed on the fields with the black dots, and the white bishops should be on the fields with white dots. Note that a bishop cannot be moved in line of sight of a bishop of the other side.";
	}

}
