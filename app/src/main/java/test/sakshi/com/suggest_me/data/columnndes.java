package test.sakshi.com.suggest_me.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


public class columnndes {

    public static final String CONTENT_AUTHORITY = "test.sakshi.com.suggest_me.provider.sitprovider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class entry implements BaseColumns {

        public static final String TABLE_NAME = "situations";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_HEADPHONE_STATE = "head_hone_state";
        public static final String COLUMN_WEATHER_STATE = "weather_state";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_PLACE = "place";
        public static final String COLUMN_ACTIVITY = "activity";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_ACTION = "action";
        public static final String COLUMN_ACTION_NAME = "action_name";
        public static final String COLUMN_CHECKED = "checked";

        public static final String[] SITUATION_PROJECTION = {COLUMN_ID, COLUMN_NAME, COLUMN_HEADPHONE_STATE,
                COLUMN_WEATHER_STATE, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_PLACE, COLUMN_ACTIVITY, COLUMN_TIME,COLUMN_ACTION,COLUMN_ACTION_NAME,COLUMN_CHECKED};

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static Uri buildFavoritesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}