package test.sakshi.com.suggest_me.data;


import android.content.Context;
import   net.sqlcipher.database.SQLiteDatabase;

import   net.sqlcipher.database.SQLiteOpenHelper;

import test.sakshi.com.suggest_me.data.columnndes.*;

// COMPLETED (1) extend the SQLiteOpenHelper class
public class database extends SQLiteOpenHelper {


    // The database name
    private static final String DATABASE_NAME = "situation.db";

    // COMPLETED (3) Create a static final int called DATABASE_VERSION and set it to 1
    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    // COMPLETED (4) Create a Constructor that takes a context and calls the parent constructor
    // Constructor
    public database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // COMPLETED (5) Override the onCreate method
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        final String SQL_CREATE_SITUATION_TABLE = "CREATE TABLE " +
                columnndes.entry.TABLE_NAME + "(" +
                columnndes.entry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                columnndes.entry.COLUMN_NAME + " TEXT NOT NULL, " +
                columnndes.entry.COLUMN_HEADPHONE_STATE + " TEXT NOT NULL, " +
                columnndes.entry.COLUMN_WEATHER_STATE + " TEXT NOT NULL, " +
                columnndes.entry.COLUMN_LATITUDE + " TEXT NOT NULL, " +
                columnndes.entry.COLUMN_LONGITUDE + " TEXT NOT NULL, " +
                columnndes.entry.COLUMN_PLACE + " TEXT NOT NULL, " +
                columnndes.entry.COLUMN_ACTIVITY + " TEXT NOT NULL, " +
                columnndes.entry.COLUMN_TIME + " TEXT NOT NULL, " +
                columnndes.entry.COLUMN_ACTION + " TEXT NOT NULL, " +
                columnndes.entry.COLUMN_ACTION_NAME + " TEXT NOT NULL, " +
                columnndes.entry.COLUMN_CHECKED + " TEXT NOT NULL);";

        // COMPLETED (7) Execute the query by calling execSQL on sqLiteDatabase and pass the string query SQL_CREATE_WAITLIST_TABLE
        sqLiteDatabase.execSQL(SQL_CREATE_SITUATION_TABLE);
    }

    // COMPLETED (8) Override the onUpgrade method
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        // COMPLETED (9) Inside, execute a drop table query, and then call onCreate to re-create it
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + entry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}