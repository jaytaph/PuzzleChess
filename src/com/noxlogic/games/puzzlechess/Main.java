package com.noxlogic.games.puzzlechess;

// @TODO: Implement game status & resets
// @TODO: Scalable boards + pieces

import java.util.ArrayList;

import com.noxlogic.games.puzzlechess.games.Game;
import com.noxlogic.games.puzzlechess.games.GameBishop1;
import com.noxlogic.games.puzzlechess.games.GameKnights1;
import com.noxlogic.games.puzzlechess.games.GameKnights2;
import com.noxlogic.games.puzzlechess.games.GameKnights3;
import com.noxlogic.games.puzzlechess.games.GameQueen1;
import com.noxlogic.games.puzzlechess.games.GameQueen2;
import com.noxlogic.games.puzzlechess.games.GameQueen3;
import com.noxlogic.games.puzzlechess.games.GameQueen4;
import com.noxlogic.games.puzzlechess.games.GameTest1;
import com.noxlogic.games.puzzlechess.games.GameTraveller1;
import com.noxlogic.games.puzzlechess.games.GameTraveller2;
import com.noxlogic.games.puzzlechess.DataHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
	
	public void onCreate(Bundle savedInstanceState) {
        // Create instance of the database singleton. This needs a context 
        DataHelper.createInstance (this.getApplicationContext());
		
		games = new ArrayList<GameRow>();
		games.add( new GameRow( 1, R.drawable.wn, 3, new GameTest1(),      "TEST", "TEST"));
		games.add( new GameRow( 2, R.drawable.wn, 3, new GameKnights1(),   "Knights #1", "Switch the knights around the board"));
		games.add( new GameRow( 3, R.drawable.wn, 4, new GameKnights2(),   "Knights #2", "Switch the knights around the board"));
		games.add( new GameRow( 4, R.drawable.wn, 5, new GameKnights3(),   "Knights #3", "Switch the knights around the board"));
		games.add( new GameRow( 5, R.drawable.wq, 1, new GameQueen1(),     "Queens #1",  ""));
		games.add( new GameRow( 6, R.drawable.wq, 2, new GameQueen2(),     "Queens #2", ""));
		games.add( new GameRow( 7, R.drawable.wq, 3, new GameQueen3(),     "Queens #3", ""));
		games.add( new GameRow( 8, R.drawable.wq, 3, new GameQueen4(),     "Queens #4", ""));
		games.add( new GameRow( 9, R.drawable.wb, 2, new GameBishop1(),    "Bishop #1", ""));
		games.add( new GameRow(10, R.drawable.wn, 1, new GameTraveller1(), "Traveling knights #1", "Travel the knight around the board"));
		games.add( new GameRow(11, R.drawable.wn, 5, new GameTraveller2(), "Traveling knights #2", "Travel the knight around the board"));
		
		// Create  
		super.onCreate(savedInstanceState);
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
            	app.setGame(gr.game, gr.id);
            	
				DataHelper dh = DataHelper.getInstance();
				dh.setCompleted(gr.id, 1);
				Log.d("knights", "Completed "+gr.id);
            	 
                Intent gameIntent = new Intent ();
                gameIntent.setClass(getBaseContext(), PuzzleChess.class);
                startActivity(gameIntent);
            }
        });		
	}
	
	/*
	 * The game could have been won. We need to update the listview
	 */
	public void onResume() {
		super.onResume();
		
		Log.d("knights", "onResume() called");
		
		ListView gameListview = (ListView)findViewById(R.id.GameListView);
		//EfficientAdapter adapter = (EfficientAdapter) gameListview.getAdapter();
		gameListview.setAdapter(new EfficientAdapter(this));
		//adapter.notifyDataSetChanged();
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
    	final GameRow gr = games.get((int)listrow.id);
    	the_id = (int)listrow.id;
    	
    	switch (item.getItemId()) {
    		case R.id.reset_menu_item :
    						// Ask for permission before resetting the game
    						new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
    													 .setTitle("Really reset?")
    													 .setMessage("Are you sure you want to reset this game?")
    													 .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    														 	public void onClick(DialogInterface dialog, int which) {
    														 		gr.game.reset();
    														 	}

    													 })
    													 .setNegativeButton("No", null)
    													 .show();

    						break;
    		case R.id.play_menu_item :
            				// Play the game (merge with clicklistener)
    						MyApp app = ((MyApp)getApplicationContext());
    						app.setGame(gr.game, gr.id);
            	 
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
            							// @TODO: Actually reset all games
            						}
            					})
            					.setNegativeButton("No", null)
            					.show();
            			break;            	
        }
        return true;
    }        
    
    // Simple class to hold a game (populated in the oncreate())
    static class GameRow {
    	 int id;
		 int resource;
		 int difficulty;
		 String title;
		 String subtitle;
		 Game game;
		 boolean done;
		 
		 GameRow(int id, int resource, int difficulty, Game game, String title, String subtitle) {
			 this.id = id;
			 this.resource = resource;
			 this.difficulty = difficulty;
			 this.game = game;
			 this.title = title;
			 this.subtitle = subtitle;
			 
			 DataHelper dh = DataHelper.getInstance();
			 if (! dh.gameExists(id)) dh.createGame(id);
			 this.done = dh.isCompleted(id);
		 }
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
			 if (gr.done) {
				 holder.done.setBackgroundResource(R.drawable.checkmark2);
			 }
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