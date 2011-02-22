package com.noxlogic.games.puzzlechess.games;

import com.noxlogic.games.puzzlechess.Board;
import com.noxlogic.games.puzzlechess.pieces.Knight;
import com.noxlogic.games.puzzlechess.pieces.Piece;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;

public class GameKnights3 extends Game {	

	void init() {
		setGameOptions(Game.GAMEOPTION_UNLIMITEDMOVES);
		
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
		board.addPiece(new Knight("bk1", Piece.BLACK), 1, 1);
		board.addPiece(new Knight("bk2", Piece.BLACK), 2, 1);
		board.addPiece(new Knight("bk3", Piece.BLACK), 3, 1);
		board.addPiece(new Knight("bk4", Piece.BLACK), 4, 1);
		board.addPiece(new Knight("bk5", Piece.BLACK), 1, 2);
		board.addPiece(new Knight("bk6", Piece.BLACK), 2, 2);
		board.addPiece(new Knight("bk7", Piece.BLACK), 3, 2);
		board.addPiece(new Knight("bk8", Piece.BLACK), 1, 3);
		board.addPiece(new Knight("bk9", Piece.BLACK), 2, 3);
		board.addPiece(new Knight("bk10", Piece.BLACK), 1, 4);
		board.addPiece(new Knight("bk11", Piece.BLACK), 2, 4);
		board.addPiece(new Knight("bk12", Piece.BLACK), 1, 5);

		board.addPiece(new Knight("wk1", Piece.WHITE), 5, 1);
		board.addPiece(new Knight("wk2", Piece.WHITE), 5, 2);
		board.addPiece(new Knight("wk3", Piece.WHITE), 4, 2);
		board.addPiece(new Knight("wk4", Piece.WHITE), 5, 3);
		board.addPiece(new Knight("wk5", Piece.WHITE), 4, 3);
		board.addPiece(new Knight("wk6", Piece.WHITE), 5, 4);
		board.addPiece(new Knight("wk7", Piece.WHITE), 4, 4);
		board.addPiece(new Knight("wk8", Piece.WHITE), 3, 4);
		board.addPiece(new Knight("wk9", Piece.WHITE), 5, 5);
		board.addPiece(new Knight("wk10", Piece.WHITE), 4, 5);
		board.addPiece(new Knight("wk11", Piece.WHITE), 3, 5);
		board.addPiece(new Knight("wk12", Piece.WHITE), 2, 5);		
		
		board.addDecorationToField(1, 1, plotDot(Color.WHITE));
		board.addDecorationToField(2, 1, plotDot(Color.WHITE));
		board.addDecorationToField(3, 1, plotDot(Color.WHITE));
		board.addDecorationToField(4, 1, plotDot(Color.WHITE));
		board.addDecorationToField(1, 2, plotDot(Color.WHITE));
		board.addDecorationToField(2, 2, plotDot(Color.WHITE));
		board.addDecorationToField(3, 2, plotDot(Color.WHITE));
		board.addDecorationToField(1, 3, plotDot(Color.WHITE));
		board.addDecorationToField(2, 3, plotDot(Color.WHITE));
		board.addDecorationToField(1, 4, plotDot(Color.WHITE));
		board.addDecorationToField(2, 4, plotDot(Color.WHITE));
		board.addDecorationToField(1, 5, plotDot(Color.WHITE));
		
		board.addDecorationToField(5, 1, plotDot(Color.BLACK));
		board.addDecorationToField(5, 2, plotDot(Color.BLACK));
		board.addDecorationToField(4, 2, plotDot(Color.BLACK));
		board.addDecorationToField(5, 3, plotDot(Color.BLACK));
		board.addDecorationToField(4, 3, plotDot(Color.BLACK));
		board.addDecorationToField(5, 4, plotDot(Color.BLACK));
		board.addDecorationToField(4, 4, plotDot(Color.BLACK));
		board.addDecorationToField(3, 4, plotDot(Color.BLACK));
		board.addDecorationToField(5, 5, plotDot(Color.BLACK));
		board.addDecorationToField(4, 5, plotDot(Color.BLACK));
		board.addDecorationToField(3, 5, plotDot(Color.BLACK));
		board.addDecorationToField(2, 5, plotDot(Color.BLACK));
		
		addBoard(board);
	}
	
	Bitmap plotDot(int color) {
		Bitmap bmp = Bitmap.createBitmap(50, 50, Config.ARGB_8888);
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
		return "Switch the knights from position. The black knights should be placed on the fields with the black dots, and the white knights should be on the fields with white dots.";
	}

}
