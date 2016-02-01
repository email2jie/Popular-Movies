package jiewei.popularmovies;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import jiewei.popularmovies.adapter.PagerAdapter;
import jiewei.popularmovies.objects.Movie;
import jiewei.popularmovies.database.MovieContract;
import com.squareup.picasso.Picasso;

/**
 * Created by Jie Wei on 1/17/2016.
 */
public class DetailFragment extends Fragment implements View.OnClickListener {
    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    private static final String ARG_PARAM = "movie";

    private Movie movie;
    private ImageView movie_back_drop,movie_poster;
    private TextView movie_title,movie_release,movie_rating;
    private ViewPager viewPager;
    private Button favoriteBtn;
    private String fragmentType;
    private ShareActionProvider mShareActionProvider;


    public static DetailFragment newInstance(Movie movie) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM, movie);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(getArguments() !=null){
            movie = getArguments().getParcelable(ARG_PARAM);
            fragmentType = getArguments().getString("fragmenttype");
    }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        view.findViewById(R.id.FavoriteBtn).setOnClickListener(this);
        // set activity title
        getActivity().setTitle(movie.getTitle());
        // set backdrop image
        movie_back_drop = (ImageView)view.findViewById(R.id.backdrop_image_view);
        Picasso
                .with(getActivity())
                .load(movie.getBack_drop())
                .fit()
                .into(movie_back_drop);
        //set poster image
        movie_poster = (ImageView)view.findViewById(R.id.poster_image_view);
        Picasso
                .with(getActivity())
                .load(movie.getPoster())
                .fit()
                .into(movie_poster);
        //set movie title
        movie_title = (TextView)view.findViewById(R.id.title_text_view);
        movie_title.setText(movie.getTitle());
        //set released date
        movie_release = (TextView)view.findViewById(R.id.release_date_text_view);
        movie_release.setText(movie.getRelease_date());
        //set rating
        movie_rating = (TextView)view.findViewById(R.id.rating_text_view);
        movie_rating.setText(movie.getRating());
       //set favorite button
        favoriteBtn = (Button)view.findViewById(R.id.FavoriteBtn);
        view.findViewById(R.id.FavoriteBtn).setOnClickListener(this);
        //set pager
        viewPager = (ViewPager)view.findViewById(R.id.pager);
        PagerAdapter adapter = new PagerAdapter(getActivity(),getChildFragmentManager(), movie);
        viewPager.setAdapter(adapter);


        return view;
    }
    public void onClick(View view){
        Log.i(LOG_TAG, "Button clicked-");
        if(view == null) return;
        Log.i(LOG_TAG, "Button clicked+");
        if(view.getId()==R.id.FavoriteBtn){
            Uri uri = MovieContract.MovieEntry.CONTENT_URI.buildUpon().appendPath(movie.getId()).build();
            Log.i(LOG_TAG, "uri is: " + uri);
            try {
                final Cursor movieCursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                final Cursor databaseCursor = getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,null,null,null,null);
                int databaseRow = databaseCursor.getCount();
                Log.i(LOG_TAG, "databaseRow is: " + databaseRow);
                Log.i(LOG_TAG, "Activity: " + getActivity());
                Log.i(LOG_TAG, "resolver: " + getActivity().getContentResolver());
                Log.i(LOG_TAG, "Cursor: " + movieCursor);
                if ((movieCursor !=null) && (!(movieCursor.moveToNext()))) {
                    ContentValues contentValues = generateContentValues(movie);
                    Uri insertedUri = getActivity().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
                    Log.i(LOG_TAG, "inserted Uri is: "+ insertedUri);
                    long id = ContentUris.parseId(insertedUri);
                    Log.i (LOG_TAG, "inserted ID is: "+id);
                    Log.i (LOG_TAG, "inserted overview is: "+MovieContract.MovieEntry.COLUMN_OVERVIEW);
                    if (id != -1) {

                        Snackbar.make(view,"Added to Favorites", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    deleteFavorite();
                    Snackbar.make(view,"Deleted from Favorites", Snackbar.LENGTH_LONG).show();
                }
                if (movieCursor !=null){
                    movieCursor.close();
                }
            }catch (Exception e){

            }
        }
    }
    private ContentValues generateContentValues(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIES_ID,movie.getId());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE,movie.getTitle());
        contentValues.put(MovieContract.MovieEntry.COLUMN_THUMB,movie.getThumb());
        contentValues.put(MovieContract.MovieEntry.COLUMN_BACK_DROP, movie.getBack_drop());
        contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER,movie.getPoster());
        contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,movie.getOverview());
        contentValues.put(MovieContract.MovieEntry.COLUMN_RATING,movie.getRating());
        contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getRelease_date());
        contentValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());

        return  contentValues;
    }
    public void deleteFavorite() {
        Uri uri = MovieContract.MovieEntry.CONTENT_URI.buildUpon().appendPath(movie.getId()).build();
        getActivity().getContentResolver().delete(uri, movie.getId(), null);
        Log.i(LOG_TAG, "deleted movie_id: " + movie.getId());
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        mShareActionProvider.setShareIntent(null);
    }

    public void setShareIntent(String text){
        Log.i(LOG_TAG,"Text to be shared: "+text);
        if(mShareActionProvider != null){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
}
