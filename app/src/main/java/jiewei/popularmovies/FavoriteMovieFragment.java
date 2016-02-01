package jiewei.popularmovies;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import jiewei.popularmovies.adapter.MovieAdapter;
import jiewei.popularmovies.objects.Movie;
import jiewei.popularmovies.database.MovieContract;
import jiewei.popularmovies.util.Constants;

import java.util.ArrayList;

/**
 * Created by Jie Wei on 1/18/2016.
 */
public class FavoriteMovieFragment extends Fragment implements AdapterView.OnItemClickListener  {

    private ArrayList<Movie> movieList= new ArrayList<>();
    private MovieAdapter movieAdapter;
    private GridView gridView;
    private TextView emptyTextView;
    private int pageCount=1;
    private boolean itemClicked = false;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    //Returns a new instance of this fragment

    public static FavoriteMovieFragment newInstance() {
        FavoriteMovieFragment fragment = new FavoriteMovieFragment();
        return fragment;
    }

    public FavoriteMovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState !=null){
            movieList = savedInstanceState.getParcelableArrayList("movies");
        }
        getActivity().setTitle("My Favorites");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        emptyTextView = (TextView)rootView.findViewById(R.id.EmptyTextView);
        gridView = (GridView)rootView.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);
        movieAdapter = new MovieAdapter(getActivity(), movieList);
        gridView.setAdapter(movieAdapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation){
            gridView.setNumColumns(3);
        }else{
            gridView.setNumColumns(2);
        }
        if(movieList==null || movieList.size()==0 ) {
            Cursor cursor = getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
            getMovieListFromCursor(cursor);
            if(movieList.size()==0){
                emptyTextView.setText("No Favorites Added");
                emptyTextView.setVisibility(View.VISIBLE);
                if(((MainActivity)getActivity()).mTwoPane){
                    Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .remove(fragment)
                            .commit();
                }
            }
            else{
                movieAdapter.notifyDataSetChanged();
                if(((MainActivity)getActivity()).mTwoPane){
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.detail_container, DetailFragment.newInstance(movieList.get(0)), DETAILFRAGMENT_TAG)
                            .commit();
                }
            }
        }
    }


    private void getMovieListFromCursor(Cursor cursor) {
        while(cursor.moveToNext()){
            Movie movie = new Movie();
            movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE)));
            movie.setId(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIES_ID)));
            movie.setRating(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING)));
            movie.setRelease_date(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE)));
            movie.setPoster(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER)));
            movie.setThumb(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_THUMB)));
            movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
            movie.setBack_drop(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACK_DROP)));
            movie.setPopularity(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POPULARITY)));

            movieList.add(movie);
        }
        cursor.close();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(((MainActivity)getActivity()).mTwoPane) {
            DetailFragment detailFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("movie",movieList.get(i));
            bundle.putString("fragmenttype", Constants.FRAGMENT_TYPE_FAVORITE);
            detailFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, detailFragment, DETAILFRAGMENT_TAG)
                    .commit();
        }else {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("movie", movieList.get(i));
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies", (ArrayList<? extends Parcelable>) movieList);
    }

}

