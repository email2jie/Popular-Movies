package jiewei.popularmovies.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Jie Wei on 1/16/2016.
 */
public class MovieContract {
    public static final String CONTENT_AUTHORITY = "jiewei.popularmovies.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";
    public static final String PATH_MOVIE = "movie";

    /* Inner class that defines the table contents of the movies table */
    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "MOVIES";

        public static final String COLUMN_MOVIES_ID= "MOVIES_ID";
        public static final String COLUMN_MOVIE_TITLE= "MOVIE_TITLE";
        public static final String COLUMN_THUMB = "THUMB";
        public static final String COLUMN_BACK_DROP = "BACK_DROP";
        public static final String COLUMN_POSTER = "POSTER";
        public static final String COLUMN_OVERVIEW = "OVERVIEW";
        public static final String COLUMN_RATING= "RATING";
        public static final String COLUMN_RELEASE_DATE= "RELEASE_DATE";
        public static final String COLUMN_POPULARITY = "POPULARITY";


        public static Uri buildMoviesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getMovieIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }
}


