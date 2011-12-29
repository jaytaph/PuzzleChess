package com.noxlogic.games.puzzlechess;

/*
 * Data helper for the SQLite database.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public final class DataHelper {
    private static DataHelper DHinstance = null;

    private static final String DATABASE_NAME = "puzzlechess.db";
    private static final int DATABASE_VERSION = 4;  // Increasing this version number will result in automatic call to onUpgrade()
    
	private SQLiteDatabase db = null;

    private DataHelper(Context context) {
        OpenHelper openHelper = new OpenHelper (context);
        db = openHelper.getWritableDatabase();
    }

    public static DataHelper createInstance (Context context) {
        if (DHinstance == null) DHinstance = new DataHelper (context);
        return DHinstance;
    }

    public static DataHelper getInstance () {
        if (DHinstance == null) {
        	return null;
        }
        return DHinstance;
    }
    
    public boolean gameExists(int puzzle_id) {
        Cursor c = this.db.rawQuery("SELECT * FROM puzzles WHERE id = ?", new String[] {Integer.toString(puzzle_id)});
        return (c.getCount() > 0);
    }
    
    public void createGame(int puzzle_id) {
        ContentValues values = new ContentValues();
        values.put("id", puzzle_id);
        values.put("completed", 0);
        values.put("time", 0);
        
        db.insert("puzzles", "", values);
    }
    
    public boolean isCompleted(int puzzle_id) {
        Cursor c = this.db.rawQuery("SELECT completed FROM puzzles WHERE id = " + puzzle_id, null);
        c.moveToFirst();
        Log.d("knights", "completed for Id "+puzzle_id+" is " + c.getInt(0));
        boolean b = (c.getInt(0) == 1);
        c.close();
        return b;
    }
    
    public void setCompleted(int puzzle_id, int completed) {
        ContentValues values = new ContentValues();
        values.put("completed", 1);
        this.db.update("puzzles", values, "id=?", new String[] {Integer.toString(puzzle_id)});
    }

    private static class OpenHelper extends SQLiteOpenHelper {
        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Create new database (if needed)
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE [puzzles]   ([id] INTEGER, [completed] INTEGER, [time] INTEGER)");
        }

        // Upgrade database. Drop everything and call onCreate.. We do not care for old data anyway
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop everything
            db.execSQL("DROP TABLE IF EXISTS puzzles");

            // Create new database
            onCreate(db);
        }
    }

}

