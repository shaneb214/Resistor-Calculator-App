package edu.shanebutler.gameapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scoreManager";
    private static final String TABLE_GAMESCORES = "gamescores";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "score";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GAMESCORES_TABLE = "CREATE TABLE " + TABLE_GAMESCORES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SCORE + " TEXT" + ")";
        db.execSQL(CREATE_GAMESCORES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMESCORES);

        // Create tables again
        onCreate(db);
    }

    public void emptyGamescores() {
        // Drop older table if existed
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMESCORES);

        // Create tables again
        onCreate(db);
    }
    // code to add the new contact
    void addGamescore(GameScore gameScore) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, gameScore.getName()); // Name
        values.put(KEY_SCORE, gameScore.getScore()); // Score

        // Inserting Row
        db.insert(TABLE_GAMESCORES, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    GameScore getGamescore(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GAMESCORES, new String[] { KEY_ID,
                        KEY_NAME, KEY_SCORE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        GameScore gameScore = new GameScore(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)));
        // return contact
        return gameScore;
    }

    // code to get all contacts in a list view
    public List<GameScore> getAllGameScores() {
        List<GameScore> gamescoreList = new ArrayList<GameScore>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_GAMESCORES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                GameScore gameScore = new GameScore();
                gameScore.setID(Integer.parseInt(cursor.getString(0)));
                gameScore.setName(cursor.getString(1));
                gameScore.setScore(Integer.parseInt(cursor.getString(2)));
                // Adding contact to list
                gamescoreList.add(gameScore);
            } while (cursor.moveToNext());
        }

        // return contact list
        return gamescoreList;
    }


    // code to update the single contact
    public int updateGamescore(GameScore gameScore) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, gameScore.getName());
        values.put(KEY_SCORE, gameScore.getScore());

        // updating row
        return db.update(TABLE_GAMESCORES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(gameScore.getID()) });
    }

    // Deleting single contact
    public void deleteGamescore(GameScore gameScore) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GAMESCORES, KEY_ID + " = ?",
                new String[] { String.valueOf(gameScore.getID()) });
        db.close();
    }

    // Getting contacts Count
    public int getGamescoresCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_GAMESCORES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
}
