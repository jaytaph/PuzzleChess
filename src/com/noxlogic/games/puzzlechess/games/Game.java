package com.noxlogic.games.puzzlechess.games;

import com.noxlogic.games.puzzlechess.Board;
import com.noxlogic.games.puzzlechess.ChessPanelView;
import com.noxlogic.games.puzzlechess.History;
import com.noxlogic.games.puzzlechess.pieces.Piece;

import android.graphics.Canvas;
import android.graphics.Color;

abstract public class Game {
	protected int _movesDone = 0;		// Number of moves that are done
	protected int _movesLeft = 0;		// Number of moves that are left
	protected int _time = 0;			// Current time spend on puzzle
	protected Board _board;				// The actual board
	protected History _history;			// History (not used)
	protected int _selected_x = -1;		// Selected cell X position
	protected int _selected_y = -1;		// Selected cell Y position
	protected int _game_options = 0;	// Game options (bitwise options, see GAMEOPTION_*)
	protected String _error = "";		// Current error (@TODO: still used?
			 
	// Set to true when something has changed...
	protected boolean _drawstate = true;		
	
	// Public constants
	public static final int GAMEOPTION_CANOVERTAKE 			= 1;	// Piece can hit another piece
	public static final int GAMEOPTION_UNLIMITEDMOVES 		= 2;	// Unlimited amount of moves
	public static final int GAMEOPTION_TRACEMOVES			= 4;	// Display trace of movement (cannot hit a trace)
		
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
		
		// No moves allowed
		if (! allowed_move) {
			setError("Cannot move to here");
			return;
		}
				
		// Check if we may overtake 
		if (dst_piece != null && ! hasGameOption(GAMEOPTION_CANOVERTAKE)) {
			setError ("Cannot overtake");
			return;
		}
		
		// Off course, we can never overtake our own color
		if (dst_piece != null && dst_piece.getColor() == src_piece.getColor()) {
			setError ("Overtaking of the same color is not allowed");
			return;
		}
		
		
		// @TODO: I'm not quite sure what this code actually does anymore :(
		
		// Check if we can move the piece
		boolean move_piece = false;
		if (dst_piece == null) {
			// We can, since there is nothing on destination present
			move_piece = true;
		} else {
			for (int[] field : dst_piece.getAvailableMoves()) {
				int dst_x = field[0];
				int dst_y = field[1];
				if (dst_x == x && dst_y == y) move_piece = true;
			}
		}
		
		// We are allowed to move
		if (move_piece) {
			// Move the actual piece
			_board.movePiece(src_piece, x, y);
			
			// Increase our moves, and decrease the moves_left-counter
			_movesDone++;
			if (_movesLeft > 0) _movesLeft--;
			
			// Add to history
//			_history.addMove(_selected_x, _selected_y, x, y);
			
			// Unselect cell again
			_selected_x = -1;
			_selected_y = -1;

			// Remove all borders
			_board.removeAllBorderColors ();
			return;
		}
		
		setError ("Piece not allowed to move to here...");
	}
	
	public void onClick (int x, int y) {
		// Nothing selected
		if (_selected_x == -1) {
			_onClickNoSelection(x, y);
			return;
		}
		
		// The selected field is clicked again
		if (_selected_x == x && _selected_y == y) {
			_onClickSelectionSameField(x, y);
			return;
		}
		
		// Another field has been clicked
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

