package com.noxlogic.games.puzzlechess;

import com.noxlogic.games.puzzlechess.games.Game;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.widget.TextView;
import android.graphics.Canvas;

 
public class ChessPanelView extends View {
    protected Game _game;								// The current game (loaded from application context)
    
    protected boolean _dirty;							// True when an update needs to take place
    protected PuzzleChess _puzzleChessActivity;		// Calling context (ACTIVITY)
    
    protected Bitmap _background = null;			// Cache background with board generated for speed
    
        
    public ChessPanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        // This view can be focusable
        setFocusable(true);
    }
    
    public void setActivity (PuzzleChess activity) {
    	// Save calling context
    	_puzzleChessActivity = activity;
    }
    
    public void setGame (Game game) {
    	// Register the game to this panel
    	_game = game;
    }
    
   
    public Game getGame() {
		return _game;
	}
        
    public void update() {    	
    	if (_game.update()) {
    		this.invalidate();
    	}
    }
    
    public void onDraw(Canvas canvas) {    	
    	canvas.drawColor(Color.BLACK);
    	
    	// Draw background
    	if (_background == null) {
    		_background = BitmapFactory.decodeResource(getResources(), R.drawable.abstrakt);
    	}
		Paint p = new Paint();
		p.setAlpha(128);    	
    	canvas.drawBitmap(_background, 0, 0, p);
    
    	_game.draw(this, canvas);

    	// Calculate hours, minutes and seconds
    	Integer i = _game.getDuration();
    	Integer hrs = i / 3600; i %= 3600;
    	Integer min = i / 60; i %= 60;
    	Integer sec = i;
    	
    	String s = new String("Unlimited");
    	Integer left = _game.getMovesLeft();
    	if (left >= 0) s = Integer.toString(left);
	
    	TextView v1 = (TextView)_puzzleChessActivity.findViewById(R.id.TextView02);
    	if (v1 != null) v1.setText(s);
    	TextView v2 = (TextView)_puzzleChessActivity.findViewById(R.id.TextView04);
    	if (v2 != null) v2.setText(Integer.toString(_game.getMovesDone()));    	
    	TextView v3 = (TextView)_puzzleChessActivity.findViewById(R.id.TextView06);
    	if (v3 != null) v3.setText(String.format("%02d:%02d:%02d", hrs, min, sec));
    	
    	
    }
      
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    
    public boolean onTouchEvent(MotionEvent event) {
    	if (event.getAction() == MotionEvent.ACTION_DOWN) {
    		int tmp_x = (int)(event.getX() / 40);
    		int tmp_y = (int)(event.getY() / 40);
    	
    		if (tmp_x < 8 && tmp_y < 8) {
    			_game.onClick (tmp_x, tmp_y);
    		}
    	}
    	
    	this.invalidate();
    	
        return true;
    }    
		 
}
