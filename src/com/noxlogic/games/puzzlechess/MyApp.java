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
	
	public int getThemeId() {
		return _theme_id;
	}
	
	public void setThemeId(int theme_id) {
		_theme_id = theme_id;
	}
	
	public Game getGame() {
		return _game;
	}
	
	public void setGame(Game game) {
		_game = game;
	}

}
