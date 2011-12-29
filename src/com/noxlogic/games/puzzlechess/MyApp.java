package com.noxlogic.games.puzzlechess;

import com.noxlogic.games.puzzlechess.games.Game;

import android.app.Application;

/**
 * Some global getters and setters that should be visible throughout the whole application
 * 
 */
public class MyApp extends Application {
	protected Game _game;			// The current selected game 
	protected int _theme_id;		// The current theme ID
	
	/**
	 * Returns the current theme ID
	 * 
	 * @return Theme ID
	 */
	public int getThemeId() {
		return _theme_id;
	}
	
	/**
	 * Sets the current theme ID
	 * 
	 * @param theme_id 
	 */
	public void setThemeId(int theme_id) {
		_theme_id = theme_id;
	}
	
	/**
	 * Returns the current game
	 * 
	 * @return
	 */
	public Game getGame() {
		return _game;
	}
	
	/**
	 * Sets the current active game
	 * 
	 * @param game
	 */
	public void setGame(Game game, int puzzle_id) {
		_game = game;
		_game.setPuzzleId(puzzle_id);
	}

}
