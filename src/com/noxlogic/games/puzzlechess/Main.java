package com.noxlogic.games.puzzlechess;


// @TODO: Implement game status & resets
// @TODO: Scalable boards + pieces
// @TODO: Add pieces

import java.util.ArrayList;

import com.noxlogic.games.puzzlechess.games.Game;
import com.noxlogic.games.puzzlechess.games.GameKnights1;
import com.noxlogic.games.puzzlechess.games.GameKnights2;
import com.noxlogic.games.puzzlechess.games.GameQueen1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Main extends Activity {
	protected Game _game;
	public static ArrayList<GameRow> games;  
	
	public void onCreate(Bundle icicle) {
		games = new ArrayList<GameRow>();
		
		// These are all the puzzles that are inside this game
		GameRow gr;		
		gr = new GameRow();
		gr.resource = R.drawable.wn;
		gr.difficulty = 3;
		gr.title = "Knights #1";
		gr.subtitle = "Switch the knights around";
		gr.game = new GameKnights1();
		games.add(gr);
		
		gr = new GameRow();
		gr.resource = R.drawable.wn;
		gr.difficulty = 4;
		gr.title = "Knights #2";
		gr.subtitle = "Switch the knights around";
		gr.game = new GameKnights2();
		games.add(gr);
		
		gr = new GameRow();
		gr.resource = R.drawable.wq;
		gr.difficulty = 2;
		gr.title = "Kings mate";
		gr.subtitle = "Checkmate the white king";
		gr.game = new GameQueen1();
		games.add(gr);
		
		gr = new GameRow();
		gr.resource = R.drawable.wq;
		gr.difficulty = 2;
		gr.title = "Something else";
		gr.subtitle = "Switch the knights around";
		gr.game = new GameKnights1();
		games.add(gr);
		
		gr = new GameRow();
		gr.resource = R.drawable.wk;
		gr.difficulty = 5;
		gr.title = "Very difficult thingie";
		gr.subtitle = "Switch the knights around";
		gr.game = new GameKnights1();
		games.add(gr);
		
		// Create  
		super.onCreate(icicle);
		setContentView(R.layout.mainmenu);
		
		// Add listview with context menu
		ListView gameListview = (ListView)findViewById(R.id.GameListView);
		gameListview.setAdapter(new EfficientAdapter(this));
		registerForContextMenu(gameListview);
		
		// set onclick listener for listview
		gameListview.setOnItemClickListener(new OnItemClickListener() {
			// When clicked, we start the game @TODO: merge with contextmenu/play
            public void onItemClick(AdapterView<?>parent, View view, int pos, long id) {
            	GameRow gr = games.get((int) id);
            	
            	MyApp app = ((MyApp)getApplicationContext());
            	app.setGame(gr.game);
            	 
                Intent gameIntent = new Intent ();
                gameIntent.setClass(getBaseContext(), PuzzleChess.class);
                startActivity(gameIntent);
            }
        });		
	}
	
	// Create options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return true;
    }
    
    // Create context menu for listview
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	if (v.getId()==R.id.GameListView) {
    		AdapterView.AdapterContextMenuInfo listrow = (AdapterView.AdapterContextMenuInfo)menuInfo;
    	    
    		// Set title of contextmenu to the current game selected
    		GameRow gr = games.get((int)listrow.id);
    		menu.setHeaderTitle(gr.title);
    		
    		// Add menu
    		MenuInflater inflater = getMenuInflater();
    		inflater.inflate(R.menu.contextmenu, menu);
    	}
	}
    
    
    int the_id; // Global ID since we need it inside a deepter class
    public boolean onContextItemSelected(MenuItem item) {
    	// Get menuinfo
    	AdapterView.AdapterContextMenuInfo listrow = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
    	
    	// Get gamerow depending on the item selected
    	GameRow gr = games.get((int)listrow.id);
    	the_id = (int)listrow.id;
    	
    	switch (item.getItemId()) {
    		case R.id.reset_menu_item :
    						// Ask for permission before resetting the game
    						new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
    													 .setTitle("Really reset?")
    													 .setMessage("Are you sure you want to reset this game?")
    													 .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    														 	public void onClick(DialogInterface dialog, int which) {
    									    						// @TODO: Add reset for game
    														 	}

    													 })
    													 .setNegativeButton("No", null)
    													 .show();

    						break;
    		case R.id.play_menu_item :
            				// Play the game (merge with clicklistener)
    						MyApp app = ((MyApp)getApplicationContext());
    						app.setGame(gr.game);
            	 
    						Intent gameIntent = new Intent ();
    						gameIntent.setClass(getApplicationContext(), PuzzleChess.class);
    						startActivity(gameIntent);
							break;    			
    	}
    	return true;
	}    
    
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_menu_item :
                        // Display about box
                        Dialog about = new AlertDialog.Builder(this)
                            .setTitle(R.string.generalAboutTitle)
                            .setPositiveButton(R.string.generalAboutButtonCaption, null)
                            .setMessage(R.string.generalAboutMessage)
                            .create();
                        about.show();
                        break;
            case R.id.reset_menu_item :
            			new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
            					.setTitle("Really reset?")
            					.setMessage("Are you sure you want to reset all games?")
            					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            						public void onClick(DialogInterface dialog, int which) {
            							Toast toast = Toast.makeText(getApplicationContext(), "reset called for all items", Toast.LENGTH_SHORT);
            							toast.show();
            						}
            					})
            					.setNegativeButton("No", null)
            					.show();
            			break;            	
        }
        return true;
    }        
	
    


	// // Returns game
	// Game getGame() {
	// return _game;
	// }
	//
	// void setGame(Game game) {
	// _game = game;
	// }
    	
    
    // Simple class to hold a game (populated in the oncreate())
    static class GameRow {
		 int resource;
		 int difficulty;
		 String title;
		 String subtitle;
		 Game game;
	}
	
    
    // Adapter for custom listview rows
	private static class EfficientAdapter extends BaseAdapter {
		 private LayoutInflater mInflater;

		 public EfficientAdapter(Context context) {
			 mInflater = LayoutInflater.from(context);
		 }

		 public int getCount() {
			 return games.size();
		 }

		 public Object getItem(int position) {
			 return position;
		 }

		 public long getItemId(int position) {
			 return position;
		 }

		 public View getView(int position, View convertView, ViewGroup parent) {
			 ViewHolder holder;
			 if (convertView == null) {
				 convertView = mInflater.inflate(R.layout.mainmenu_listviewrow, null);
				 holder = new ViewHolder();
				 holder.img = (ImageView) convertView.findViewById(R.id.ImageView01);
				 holder.title = (TextView) convertView.findViewById(R.id.TextView01);
				 holder.subtitle = (TextView) convertView.findViewById(R.id.TextView02);
				 holder.rating = (RatingBar) convertView.findViewById(R.id.RatingBar01);
				 holder.done = (ImageView) convertView.findViewById(R.id.ImageView02);
				 convertView.setTag(holder);
			 } else {
				 holder = (ViewHolder) convertView.getTag();
			 }
			 
			 GameRow gr = games.get(position);
			 holder.img.setBackgroundResource(gr.resource);
			 holder.title.setText(gr.title);
			 holder.subtitle.setText(gr.subtitle);
			 holder.rating.setRating(gr.difficulty);
			 holder.done.setBackgroundResource(R.drawable.checkmark2);
			 return convertView;
		 }

		 static class ViewHolder {
			 ImageView img;
			 TextView title;
			 TextView subtitle;
			 RatingBar rating;
			 ImageView done;
		 }
	}
}