package com.noxlogic.games.puzzlechess;

import java.util.ArrayList;

import com.noxlogic.games.puzzlechess.games.Game;
import com.noxlogic.games.puzzlechess.pieces.Piece;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Board {
	protected ArrayList<Piece> _pieces;
	protected boolean _enabled_fields[][];
	protected ArrayList<int[]> _border_colors;
	protected Bitmap _field_decorations[][];
	protected Game _game;
	protected Bitmap _boardBitmap = null;
	
	// Animation support
	static final int ANIMATION_MAXFRAMES   = 25;
			
	
	/** 
	 * Constructor.
	 * 
	 * @param game Game to start
	 * @param fields boolean[8][8] of fields that are enabled
	 */
	public Board (Game game, boolean[][] fields) {
		_game = game;
		
		
		_pieces = new ArrayList<Piece>();
		_border_colors = new ArrayList<int[]>();
			
		_field_decorations = new Bitmap[8][8];
		for (int y=0; y!=8; y++) {
			for (int x=0; x!=8; x++) {
				_field_decorations[x][y] = null;
			}
		}
		
		enableFields(fields);
	}
	
	/**
	 * Enabled (or disables) the fields of the board
	 * 
	 * @param fields boolean[8][8] of fields that are enabled
	 */
	void enableFields (boolean[][] fields) {
		if (fields.length == 0) {
			for (int y=0; y!=8; y++) {
				for (int x=0; x!=8; x++) {
					fields[x][y] = true;					
				}
			}
		}
		
		_enabled_fields = fields;
	}
	
	
	/**
	 * Returns the current played game
	 * 
	 * @return
	 */
	public Game getGame() {
		return _game;
	}
	
	
	/**
	 * Finds the piece named 'tag' on the board and returns the Piece or NULL when not found
	 * 
	 * @param tag
	 * @return
	 */
	Piece findPiece(String tag) {
		for (Piece p : _pieces) {
			if (p.getTag() == tag) return p;
		}
		return null;
	}

	
	/**
	 * Adds a bitmap decoration to a specific field
	 * 
	 * @param x
	 * @param y
	 * @param decoration
	 */
	public void addDecorationToField(int x, int y, Bitmap decoration) {
		_field_decorations[x][y] = decoration;
	}
	
	/** 
	 * Removes a decorator from a specific field
	 * 
	 * @param x
	 * @param y
	 */
	void removeDecorationFromField(int x, int y) {
		_field_decorations[x][y] = null;
	}
	
	/**
	 * Adds a piece to this board
	 * 
	 * @param piece
	 * @param x
	 * @param y
	 */
	public void addPiece (Piece piece, int x, int y) {
		if (! isFieldEnabled(x, y)) return;
		
		piece.setXY(x, y);
		piece.setBoard(this);
		
		_pieces.add(piece);		
	}
	
	/**
	 * Moves a piece to another position. Removes piece if something is already present on the position.
	 * 
	 * @param piece
	 * @param x
	 * @param y
	 */
	public void movePiece (Piece piece, int x, int y) {
		Piece dst_piece = getPieceFromXY(x, y);
		if (dst_piece != null) {		
			for (Piece p : _pieces) {
				if (p == dst_piece) _pieces.remove (p);				
			}
		}
		
		// Move to end of array, so it will be plotted last (top bitmap)
		_pieces.remove (piece);
		_pieces.add (piece);
		
		piece.animatedMove (x, y, Board.ANIMATION_MAXFRAMES);		
	}
	
	/**
	 * Returns the piece on X:Y or null on empty field
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Piece getPieceFromXY(int x, int y) {
		for (Piece p : _pieces) {
			if (p.getX() == x && p.getY() == y) return p;
		}
		return null;
	}
	
	/**
	 * Returns true when the X:Y is empty.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	boolean isEmpty(int x, int y) {
		Piece dst_piece = getPieceFromXY(x, y);
		return (dst_piece == null);
	}
	
	/**
	 * Returns true when the field is enabled.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isFieldEnabled(int x, int y) {
		return _enabled_fields[x][y];
	}
	
	/**
	 * Removes the colors from all fields
	 */
	public void removeAllBorderColors() {
		_border_colors.clear();
	}

	/**
	 * Sets the border (or background) color of a specific field
	 * 
	 * @param x
	 * @param y
	 * @param color
	 */
	public void setBorderColor(int x, int y, int color) {
		_border_colors.add (new int[] { x, y, color } );
	}
	
	/**
	 * Removes the border (or background) color of a specific field
	 * 
	 * @param x
	 * @param y
	 */
	void removeBorderColor(int x, int y) {
		for (int[] i : _border_colors) {
			int ix = i[0];
			int iy = i[1];
			
			if (ix == x && iy == y) _border_colors.remove(i);
		}
	}
	
	/**
	 * Clears the board bitmap cache so it will be regenerated on the next draw
	 */
	void clearBoardCache() {
		_boardBitmap = null;
	}
		
	/**
	 * Creates a board bitmap and caches it.
	 * 
	 * @param panel
	 */
	protected void _cacheBoardBitmap (ChessPanelView panel) {			
		_boardBitmap = Bitmap.createBitmap(320, 320, Config.ARGB_8888);
		Canvas bitmapcanvas = new Canvas(_boardBitmap);
				
		MyApp app = ((MyApp)panel.getContext().getApplicationContext());
		int theme_id = app.getThemeId();
		
		Bitmap _dark = BitmapFactory.decodeResource(panel.getResources(), R.drawable.board_dark_1 + theme_id);    	
		Bitmap _light = BitmapFactory.decodeResource(panel.getResources(), R.drawable.board_light_1 + theme_id);				
		
		// Draw board
		Bitmap cb = _dark;
		for (int y=0; y!=8; y++) {
			for (int x=0; x!=8; x++) {
				Paint p = new Paint();
				p.setAlpha(64);

				if (_game.getBoard().isFieldEnabled(x, y)) p = null;
				bitmapcanvas.drawBitmap(cb, (x*40), (y*40), p);
				
				if (_field_decorations[x][y] != null) { 
					bitmapcanvas.drawBitmap(_field_decorations[x][y], (x*40), (y*40), p);
				}
				
				cb = cb == _dark ? _light : _dark;
			}
			cb = cb == _dark ? _light : _dark;
		}		
	}
	
	/**
	 * Update animations etc
	 * 
	 * @return
	 */
	public boolean update() {
		boolean dirty = false;
		
    	// Draw pieces on the board
    	for (Piece p : _pieces) {
    		if (p.isAnimated()) {
    			p.animate();    			
    			dirty = true;
    		}
    	}
    	return dirty;
	}
	
	public void draw(ChessPanelView panel, Canvas canvas, int off_x, int off_y) {
		// If board is not cached, create cache
		if (_boardBitmap == null) _cacheBoardBitmap(panel);

		// Draw board bitmap
		canvas.drawBitmap(_boardBitmap, off_x, off_y, null);
		
    	// Draw pieces on the board
    	for (Piece p : _pieces) {
    		if (p.isAnimated()) {
    			// Items currently animated have a different X/Y (floating point)
    			int dx = (int) ((p.getX()/100.0)*40);
    			int dy = (int) ((p.getY()/100.0)*40);
    			canvas.drawBitmap(p.getBitmap(panel), dx, dy, null);
    		} else {
        		canvas.drawBitmap(p.getBitmap(panel), p.getX()*40, p.getY()*40, null);
    		}
    		
    	}
    	
    	// Draw borders if needed
    	for (int[] i : _border_colors) {
    		int ix = i[0];
    		int iy = i[1];
    		int ic = i[2];
    		
	        Paint paint=new Paint();      
	        paint.setAntiAlias(true);
	        
	        paint.setColor(ic);
	        paint.setStyle(Paint.Style.STROKE);
	        paint.setStyle(Paint.Style.FILL);
	        paint.setStrokeWidth(2);
	        paint.setAlpha(128);
	        
	        int cx = off_x + (ix*40);
	        int cy = off_y + (iy*40);
	        
	        canvas.drawRect(new Rect(cx, cy, cx+40, cy+40), paint);			    		
    	}	
	}

}
