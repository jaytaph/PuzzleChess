package com.noxlogic.games.puzzlechess;

import com.noxlogic.games.puzzlechess.games.Game;

import android.app.Application;

public class MyApp extends Application {
	protected Game _game;
	protected int _theme_id;
	
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
