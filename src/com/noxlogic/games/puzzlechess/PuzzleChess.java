package com.noxlogic.games.puzzlechess;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

 
public class PuzzleChess extends Activity {
	protected ChessPanelView _panel;
	protected ChessThread _panel_thread;
	protected ChessThread _timer_thread;
	protected int _theme_id = 0;
	protected boolean _trigger_onResume = false;

	protected static final int MESSAGE_DURATION = 0x1000;		// Message fired every second (1s)
	protected static final int MESSAGE_UPDATE	= 0x1001;		// Message fired when update is needed (10ms)
			
	class timerThread implements Runnable{
		public void run() {
			while(!Thread.currentThread().isInterrupted()){
				Message msg = new Message();
				msg.what = PuzzleChess.MESSAGE_DURATION;
				PuzzleChess.this.updateHandler.sendMessage(msg);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
    }
	
	/**
	 * Create activity
	 */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Create panel with game and set content view
        MyApp app = (MyApp)getApplicationContext();
        setContentView(R.layout.chess_layout);

        // Find panel and add activity / game
        _panel = (ChessPanelView)findViewById(R.id.ChessPanel01);
        _panel.setActivity(this);
        _panel.setGame(app.getGame());
        
        // Create chess thread for updates
        _panel_thread = new ChessThread(this, 10, PuzzleChess.MESSAGE_UPDATE);
        _panel_thread.start();
        
        // Start duration counter (at 1 second) for duration counter
        _timer_thread = new ChessThread(this, 1000, PuzzleChess.MESSAGE_DURATION);
        _timer_thread.start();        

		// Display goal box at startup
		Dialog goal = new AlertDialog.Builder(this)
			.setTitle(R.string.generalGoalTitle)
			.setIcon(R.drawable.wq)
			.setPositiveButton(R.string.generalGoalButtonCaption, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	// When clicked the button, start the threads...
                    _timer_thread.setRunning(true);
                    _panel_thread.setRunning(true);
                    
                    _trigger_onResume = true;
               }
             })
			.setMessage(app.getGame().getObjective())
			.create();
		
		goal.show();          
    }
    
    /** 
     * Handler 
     */
    public Handler updateHandler = new Handler(){
        public void handleMessage(Message msg) {
        	switch (msg.what) {
        		case MESSAGE_UPDATE :
            		_panel.update();
            	            		
            		// Check state
            		boolean lost = _panel.getGame().hasLost();
            		boolean won = _panel.getGame().hasWon();
            		if (lost || won) {
                		// Stop timer and panel updates
                        _timer_thread.setRunning(false);
                        _panel_thread.setRunning(false);
                                    			
                        // Display end results
            			String s = "";
            			if (won) {
            				s = "Congratulations. You have completed this game.";
            				DataHelper dh = DataHelper.getInstance();
            				dh.setCompleted(_panel.getGame().getPuzzleId(), 1);
            			}
            			if (lost) {
            				s = "Sorry. You've lost.";
            			}
            			
        			    new AlertDialog.Builder(PuzzleChess.this)
    			    	.setIcon(android.R.drawable.ic_dialog_alert)
    			    	.setTitle("Game ended")
    			    	.setMessage(s)
    			    	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    			    			public void onClick(DialogInterface dialog, int which) {
    		                        // Reset game
    		                        _panel.getGame().reset();

    			    				// After the click, we return to main menu
    			    				finish();
    			    			}
    			    	})
    			    	.show();
            		}
            		break;
            	case MESSAGE_DURATION :
            		_panel.getGame().increaseDuration();
            		_panel.invalidate();
            		break;        			
        	}
            super.handleMessage(msg);
        }
    };    
        
    /**
     * 
     */
    public void onSaveInstanceState(Bundle outState) {
    	//_thread.setState(ChessThread.STATE_PAUSED);
    }
    
    /**
     * 
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.gameoptionmenu, menu);
        return true;
    }

    /**
     * 
     */
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
        	case R.id.reset_menu_item :
        			    new AlertDialog.Builder(this)
        			    	.setIcon(android.R.drawable.ic_dialog_alert)
        			    	.setTitle("Are you sure?")
        			    	.setMessage("Start over again?")
        			    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        			    			public void onClick(DialogInterface dialog, int which) {
        		        				_panel.getGame().reset();
        			    			}
        			    	})
        			    	.setNegativeButton("No", null)
        			    	.show();
        			    break;	
        	case R.id.goal_menu_item :
        				// Display about box
        				Dialog goal = new AlertDialog.Builder(this)
        					.setTitle(R.string.generalGoalTitle)
        					.setIcon(R.drawable.wq)
        					.setPositiveButton(R.string.generalGoalButtonResumeCaption, null)
        					.setMessage(_panel.getGame().getObjective())
        					.create();
        				goal.show();  
        				break;        
        	case R.id.theme_menu_item :
        				final CharSequence[] items = { "Light wood", "Dark wood", "Icecold", "Marble" };
        				
        				MyApp app = (MyApp)getApplicationContext();
        				int theme_id = app.getThemeId();
        				
        				AlertDialog.Builder builder = new AlertDialog.Builder(this);
        				builder.setTitle("Pick a theme...");
        				builder.setSingleChoiceItems(items, theme_id, new DialogInterface.OnClickListener() {
        					public void onClick(DialogInterface dialog, int item) {
        						// Set new theme ID
        						MyApp app = (MyApp)getApplicationContext();
        						app.setThemeId(item);
        						
        						// Clear board cache
        						_panel.getGame().getBoard().clearBoardCache();
        						
        						// Draw again
        						_panel.invalidate();
        						dialog.dismiss();
        					}
        				});
        				AlertDialog alert = builder.create();
        				alert.show();
        				break;
            case R.id.about_menu_item :
                        // Display about box
                        Dialog about = new AlertDialog.Builder(this)
                            .setTitle(R.string.generalAboutTitle)
                            .setPositiveButton(R.string.generalAboutButtonCaption, null)
                            .setMessage(R.string.generalAboutMessage)
                            .create();
                        about.show();
                        break;
        }
        return true;
    }
    
    public void onPause () {
//    	Log.d("knights", "onPause()");
        _timer_thread.setRunning(false);
        _panel_thread.setRunning(false);
        
        super.onPause();
    }
    
    public void onResume () {
//    	Log.d("knights", "onResume()");
    	if (_trigger_onResume) {
//    		Log.d("knights", "resuming threads");
    		_timer_thread.setRunning(true);
    		_panel_thread.setRunning(true);
    	}
    	
    	super.onResume();
    }
   
}