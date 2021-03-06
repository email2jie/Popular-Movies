package jiewei.popularmovies;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import jiewei.popularmovies.adapter.MovieAdapter;
import jiewei.popularmovies.objects.Movie;
import jiewei.popularmovies.util.CommonAsyncTask;
import jiewei.popularmovies.util.Constants;
import jiewei.popularmovies.util.onTaskCompleted;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jie Wei on 1/19/2016.
 */
public class TopRatedMovieFragment extends Fragment  implements onTaskCompleted, AdapterView.OnItemClickListener  {

    private ArrayList<Movie> movieList= new ArrayList<>();
    private MovieAdapter movieAdapter;
    private GridView gridView;
    private int pageCount=1;
    private boolean itemClicked = false;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    //Returns a new instance of this fragment
    public static TopRatedMovieFragment newInstance() {
        TopRatedMovieFragment fragment = new TopRatedMovieFragment();
        return fragment;
    }
    public TopRatedMovieFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState !=null){
            movieList = savedInstanceState.getParcelableArrayList("movies");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView)rootView.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public int currentScrollState;
            public int currentFirstVisibleItem;
            public int currentVisibleItemCount;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    this.currentFirstVisibleItem = firstVisibleItem;
                    this.currentVisibleItemCount = visibleItemCount;
                }
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            private void isScrollCompleted() {
                if (this.currentVisibleItemCount > 0 && this.currentScrollState == SCROLL_STATE_IDLE) {
                    pageCount++;
                    CommonAsyncTask asyncTask = new CommonAsyncTask(TopRatedMovieFragment.this, Constants.TOP_RATED_REQUEST);
                    asyncTask.execute(pageCount + "");
                }
            }
        });
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
        if(movieList==null || movieList.size()==0) {
            CommonAsyncTask asyncTask = new CommonAsyncTask(this, Constants.TOP_RATED_REQUEST);
            asyncTask.execute("1","vote_average.desc");
        }
        getActivity().setTitle("Top Rated Movies");
    }

    @Override
    public void onTaskCompleted(Object object) {
        if(null!=object) {
            movieList.addAll((List<Movie>) object);
            movieAdapter.notifyDataSetChanged();
            if(((MainActivity)getActivity()).mTwoPane && !itemClicked) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, DetailFragment.newInstance(movieList.get(0)), DETAILFRAGMENT_TAG)
                        .commit();
                itemClicked = true;
            }
        }else{
            Toast.makeText(getActivity(), "An error has occurred", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(((MainActivity)getActivity()).mTwoPane) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, DetailFragment.newInstance(movieList.get(i)), DETAILFRAGMENT_TAG)
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
