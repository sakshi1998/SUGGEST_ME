package test.sakshi.com.suggest_me.data;

/**
 * Created by sakshi on 29-Sep-17.
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import net.sqlcipher.Cursor;
import  net.sqlcipher.database.SQLiteConstraintException;
import  net.sqlcipher.database.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;


public class sitprovider extends ContentProvider {

     Cursor cursor;

    private static final String LOG_TAG = sitprovider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private database objectdatabase;
    private static final int SITUATIONS = 100;
    //private static final int SITUATIONS_ID=201;

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = columnndes.CONTENT_AUTHORITY;

        matcher.addURI(authority, columnndes.entry.TABLE_NAME, SITUATIONS);
        //matcher.addURI(authority, columnndes.entry.TABLE_NAME + "/#", SITUATIONS_ID);
        return matcher;

    }
    public boolean onCreate(){

        objectdatabase = new database(getContext());

        //SQLiteDatabase database=SQLiteDatabase.openOrCreateDatabase ("situations","narutokun");
        return true;
    }


    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection,  String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        final SQLiteDatabase db = objectdatabase.getReadableDatabase("narutokun");
        switch(sUriMatcher.match(uri)) {
            case SITUATIONS: {
                retCursor = db.query(
                        columnndes.entry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                retCursor.setNotificationUri(getContext().getContentResolver(), uri);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }



    public String getType(Uri uri){
        final int match = sUriMatcher.match(uri);

        switch (match){
            case SITUATIONS:{
                return columnndes.entry.CONTENT_DIR_TYPE;
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }


    @Override
    public Uri insert( Uri uri,  ContentValues cv) {
        final SQLiteDatabase db = objectdatabase.getWritableDatabase("narutokun");
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case SITUATIONS: {
                long id = db.insert(columnndes.entry.TABLE_NAME, null, cv);

                if (id > 0) {
                    returnUri = columnndes.entry.buildFavoritesUri(id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            }
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        final SQLiteDatabase db = objectdatabase.getWritableDatabase("narutokun");
        final int match = sUriMatcher.match(uri);
        int numDeleted;
        switch(match){
            case SITUATIONS:
                numDeleted = db.delete(
                        columnndes.entry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return numDeleted;
    }

    @Override
    public int update( Uri uri,  ContentValues values, String selection,  String[] selectionArgs) {
        final SQLiteDatabase db = objectdatabase.getWritableDatabase("narutokun");
        int numUpdated = 0;

        if (values == null){
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch(sUriMatcher.match(uri)){
            case SITUATIONS:{
                numUpdated = db.update(columnndes.entry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (numUpdated > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }
    public int bulkInsert(Uri uri, ContentValues[] values){
        final SQLiteDatabase db = objectdatabase.getWritableDatabase("narutokun");
        Log.v("df","ffg");
        final int match = sUriMatcher.match(uri);
        switch(match){
            case SITUATIONS:
                db.beginTransaction();
                int numInserted = 0;
                try{
                    for(ContentValues value : values){
                        if (value == null){
                            throw new IllegalArgumentException("Cannot have null content values");
                        }
                        long _id = -1;
                        try{
                            _id = db.insert(columnndes.entry.TABLE_NAME,
                                    null, value);
                        }catch(SQLiteConstraintException e) {
                        }
                        if (_id != -1){
                            numInserted++;
                        }
                    }
                    if(numInserted > 0){
                        db.setTransactionSuccessful();
                    }
                } finally {
                    db.endTransaction();
                }
                if (numInserted > 0){
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return numInserted;
            default:
                return super.bulkInsert(uri, values);
        }
    }

}
