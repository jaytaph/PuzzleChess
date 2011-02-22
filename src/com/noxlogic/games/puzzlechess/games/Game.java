package com.noxlogic.games.puzzlechess.games;

import com.noxlogic.games.puzzlechess.Board;
import com.noxlogic.games.puzzlechess.ChessPanelView;
import com.noxlogic.games.puzzlechess.History;
import com.noxlogic.games.puzzlechess.pieces.Piece;

import android.graphics.Canvas;
import android.graphics.Color;

abstract public class Game {
	protected int _movesDone = 0;
	protected int _movesLeft = 0;
	protected int _time = 0;
	protected Board _board;
	protected History _history;
	protected int _selected_x = -1;
	protected int _selected_y = -1;
	protected int _game_options = 0;
	protected String _error = "";
			
	// Set to true when something has changed...
	protected boolean _drawstate = true;
	
	public static final int GAMEOPTION_CANOVERTAKE 			= 1;
	public static final int GAMEOPTION_UNLIMITEDMOVES 		= 2;
	public static final int GAMEOPTION_WHITEFIXED			= 4;
	public static final int GAMEOPTION_BLACKFIXED			= 8;
		
	abstract void init ();
	
	public abstract boolean hasWon ();
	
	public abstract String getObjective();
	
	Game () {
		_history = new History(this);
		init ();
	}
	
	public void reset() {
		_movesDone = 0;
		_movesLeft = 0;		
		_time = 0; 
		_history = new History(this);
		init ();
	}	
		
	/**
	 * 
	 * @param error
	 */
	void setError (String error) { 
		_error = error; 
	}
	
	/**
	 * 
	 * @return
	 */
	String getError () { 
		return _error; 
	}
	
	void setGameOptions(int options) {
		_game_options = options;
	}
	
	public boolean hasGameOption(int option) {
		return ( (_game_options & option) == option);
	}
	
	public Board getBoard () {
		return _board;
	}
	
	Piece getSelectedPiece() {
		if (_selected_x == -1) return null;
		
		Piece piece = _board.getPieceFromXY(_selected_x, _selected_y);
		return piece;
	}
	
	protected void _onClickNoSelection (int x, int y) {
		if (! _board.isFieldEnabled(x, y)) {
			setError ("Nothing selected since field is not enabled");
			return;
		}
		
		Piece piece = _board.getPieceFromXY(x,y);
		if (piece == null) {
			setError ("Nothing selected since field was empty");
			return;
		}
		
		if (hasGameOption(Game.GAMEOPTION_WHITEFIXED) && piece.getColor() == Piece.WHITE) {
			setError ("Cannot move white pieces");
			return;
		}
		if (hasGameOption(Game.GAMEOPTION_BLACKFIXED) && piece.getColor() == Piece.BLACK) {
			setError ("Cannot move black pieces");
			return;
		}

		_selected_x = x;
		_selected_y = y;
		
		_board.setBorderColor(x, y, Color.YELLOW);
		
		for (int[] field : piece.getAvailableMoves()) {
			int dst_x = field[0];
			int dst_y = field[1];
			
			Piece dst_piece = _board.getPieceFromXY(dst_x, dst_y);
			int border_color = dst_piece != null ? Color.RED : Color.GREEN;
						
			_board.setBorderColor (dst_x, dst_y, border_color);
		}
	}
		
	protected void _onClickSelectionSameField (int x, int y) {
		_selected_x = -1;
		_selected_y = -1;
		_board.removeAllBorderColors ();
	}
	
	protected void _onClickSelectionOtherField(int x, int y) {
		if (! _board.isFieldEnabled(x, y)) return;
		
		Piece dst_piece = _board.getPieceFromXY(x, y);
		Piece src_piece = _board.getPieceFromXY(_selected_x, _selected_y);
		
		
		// First check if we can actually move to here
		boolean allowed_move = false;
		for (int[] field : src_piece.getAvailableMoves()) {
			int dst_x = field[0];
			int dst_y = field[1];
			if (dst_x == x && dst_y == y) allowed_move = true;
		}
		if (! allowed_move) {
			setError("Cannot move to here");
			return;
		}
				
		if (dst_piece != null && ! hasGameOption(GAMEOPTION_CANOVERTAKE)) {
			setError ("Cannot overtake");
			return;
		}
		
		if (dst_piece != null && dst_piece.getColor() == src_piece.getColor()) {
			setError ("Overtaking of the same color is not allowed");
			return;
		}
		
		boolean move_piece = false;
		if (dst_piece == null) {
			move_piece = true;
		} else {
			for (int[] field : dst_piece.getAvailableMoves()) {
				int dst_x = field[0];
				int dst_y = field[1];
				if (dst_x == x && dst_y == y) move_piece = true;
			}
		}
		
		if (move_piece) {
			_board.movePiece(src_piece, x, y);
			
			_movesDone++;
			if (_movesLeft > 0) _movesLeft--;
			
//			_history.addMove(_selected_x, _selected_y, x, y);
			
			_selected_x = -1;
			_selected_y = -1;
			
			_board.removeAllBorderColors ();
			return;
		}
		
		setError ("Piece not allowed to move to here...");
	}
	
	public void onClick (int x, int y) {
		if (_selected_x == -1) {
			_onClickNoSelection(x, y);
			return;
		}
		
		if (_selected_x == x && _selected_y == y) {
			_onClickSelectionSameField(x, y);
			return;
		}
		
		if (_selected_x != x || _selected_y != y) {
			_onClickSelectionOtherField(x, y);
			return;
		}
	}
	
	void setDrawState(boolean state) {
		_drawstate = state;
	}
	
	boolean hasDrawStateChanged() {
		return _drawstate;
	}
	
	
	/**
	 * Returns true when the board needs to be updated
	 * @return
	 */
	public boolean update () {
		boolean redraw = false;
		redraw = _board.update();
		return redraw;
	}
	
	public void draw (ChessPanelView panel, Canvas canvas) {
		// Draw board
		_board.draw(panel, canvas, 0, 0);
		
//		TextView tv1 = (TextView) panel.getActivity().findViewById(R.id.TextView01);
//		tv1.setText("Moves done: " + _movesDone);
//		TextView tv2 = (TextView) panel.getActivity().findViewById(R.id.TextView04);
//		tv2.setText("Moves left: " + _movesLeft);
		
		
//		int hy = 13;
//		int ly = 360;
//		
//        // Draw statistics
//        Paint paint = new Paint();
//        paint.setColor(Color.WHITE);
//        paint.setStyle(Paint.Style.FILL);
//        
//        canvas.drawText(new String("Moves done: " + _movesDone), 5, ly+=hy, paint);
//        if (! hasGameOption(Game.GAMEOPTION_UNLIMITEDMOVES)) {
//        	canvas.drawText(new String("Moves left: " + _movesLeft), 5, ly+=hy, paint);
//        }
//        // canvas.drawText(new String("Time spend: " + _time), 5, ly+=hy, paint);
	}
	
	protected void addBoard (Board board) {
		_board = board;
	}
	
	public boolean hasLost() {
		if (! hasGameOption(GAMEOPTION_UNLIMITEDMOVES) && _movesLeft == 0) return true;
		return false;
	}
	
	protected void setMoves (int moves) {
		_movesLeft = moves;
	}
	
	public int getMovesLeft() {
		if (hasGameOption(GAMEOPTION_UNLIMITEDMOVES)) return -1;
		return _movesLeft;
	}
	
	public int getMovesDone() {
		return _movesDone;
	}
	
	public void increaseDuration() {
		_time++;
	}
	public int getDuration() {
		return _time;
	}

}

