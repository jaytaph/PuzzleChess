package com.noxlogic.games.puzzlechess.pieces;

import java.util.ArrayList;

import com.noxlogic.games.puzzlechess.Board;
import com.noxlogic.games.puzzlechess.ChessPanelView;
import com.noxlogic.games.puzzlechess.games.Game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public abstract class Piece {
	protected int _x;
	protected int _y;
	protected int _color;
	protected String _tag;
	protected Board _board;
	protected Bitmap _bitmap = null;
	
	protected int _in_animation = 0;
	protected int _animation_delta_x = 0;
	protected int _animation_delta_y = 0;
	protected int _animation_cur_x = 0;
	protected int _animation_cur_y = 0;		
	
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	
	abstract protected ArrayList<int[]> _getAvailableMoves();
	abstract int getResource ();
	abstract public String getName();
	
	public Bitmap getBitmap(ChessPanelView panel) {
		if (_bitmap != null) return _bitmap;
		
		_bitmap = BitmapFactory.decodeResource(panel.getResources(), getResource());
		return _bitmap;
	}
	
	public void animatedMove (int dst_x, int dst_y, int frames) {
		_animation_cur_x = getX() * 100;
		_animation_cur_y = getY() * 100;
		
//		Log.d ("knights", "Animation C X : " + _animation_cur_x);
//		Log.d ("knights", "Animation C Y : " + _animation_cur_y);

		_animation_delta_x = (dst_x - getX()) * 100 / frames;
		_animation_delta_y = (dst_y - getY()) * 100 / frames;
		
//		Log.d ("knights", "Animation D X : " + _animation_delta_x);
//		Log.d ("knights", "Animation D Y : " + _animation_delta_y);
		
		_in_animation = frames;
		setXY(dst_x, dst_y);
	}
	
	
	public void animate () {		
		if (_in_animation <= 0) return;
				
		_animation_cur_x += _animation_delta_x;
		_animation_cur_y += _animation_delta_y;
		
//		Log.d ("knights", "Animation Frame: " + _in_animation + " C X : " + _animation_cur_x);
//		Log.d ("knights", "Animation Frame: " + _in_animation + " C Y : " + _animation_cur_y);
		_in_animation--;
	}
	
	
	public boolean isAnimated() {
		return (_in_animation > 0);
	}
	
	protected Piece(String tag, int color) {
		_tag = tag;
		_color = color;
	}
	
	
	public ArrayList<int[]> getAvailableMoves () {
		return _getAvailableMoves();
	}
	
	protected boolean isValidAvailableField(int x, int y) {
		if (x < 0 || x >= 8) return false;
		if (y < 0 || y >= 8) return false;
		
		if (! _board.isFieldEnabled(x, y)) return false;
		
		Piece dst_piece = _board.getPieceFromXY(x, y);
		if (dst_piece == null) return true;
		
		if (dst_piece.getColor() == getColor()) return false;
		
		if (_board.getGame().hasGameOption(Game.GAMEOPTION_CANOVERTAKE)) return true;
		
		return false;
	}
	
	public String getTag() { return _tag; }
	void setTag(String tag) { _tag = tag; }
	
	Board getBoard() { return _board; }
	public void setBoard(Board board) { _board = board; }
	
	public int getColor() { return _color; }
	void setColor(int color) { _color = color; }
	
	public int getX() {
		if (isAnimated()) return _animation_cur_x;
		return _x; 
	}
	void setX(int x) { 
		_x = x; 
	}
	
	public int getY() { 
		if (isAnimated()) return _animation_cur_y;
		return _y; 
	}
	void setY(int y) { 
		_y = y; 
	}

	int[] getXY() {
		if (isAnimated()) return new int[] { _animation_cur_x, _animation_cur_y };
		return new int[]{_x, _y}; 
	}
	
	public void setXY(int x, int y) { 
		_x = x; 
		_y = y; 
	}
	
	
	
	
}
