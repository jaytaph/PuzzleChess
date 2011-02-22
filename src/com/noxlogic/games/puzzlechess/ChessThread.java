package com.noxlogic.games.puzzlechess;

import android.os.Message;
import android.util.Log;


class ChessThread extends Thread {
	protected PuzzleChess _activity;			// Need to know where to send messages to.
	protected int _fire_message;
	protected int _time;
	protected boolean _running = false;			// True when we are running the thread
	
	/** 
	 * Creates new thread linked to activity
	 */
    public ChessThread(PuzzleChess activity, int time, int fire_message) {
    	_activity = activity;
    	_time = time;
    	_fire_message = fire_message;
    	setRunning(false);
    }
    
    /**
     * Sets the thread running or not
     * 
     * @param state
     */
	public void setRunning(boolean state) {
		Log.d("knights", "SetRunning "+state);
		_running = state;
	}
	
	/**
	 * 
	 */
    public void run() {
    	while (true) {
    		if (!_running) {    			
    			try {
					sleep(20);
				} catch (InterruptedException e) { }
    			yield();	// Don't thrash, but yield 
    		}
    	
    		while (_running) {
    			try {
    				sleep(_time);		
    			} catch (InterruptedException e) {
    				// 	Not a problem...
    			}
    		
    			// Send message to the activity to force an update
    			Message msg = new Message();
    			msg.what = _fire_message;			
    			_activity.updateHandler.sendMessage(msg);
    		}
    	}
	}	    
       
}

