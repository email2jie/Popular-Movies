package jiewei.popularmovies.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import jiewei.popularmovies.OverviewFragment;
import jiewei.popularmovies.ReviewFragment;
import jiewei.popularmovies.TrailerFragment;
import jiewei.popularmovies.objects.Movie;

/**
 * Created by Jie Wei on 1/16/2016.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private static final String LOG_TAG = PagerAdapter.class.getSimpleName();
    private Context mContext;
    private Movie movie;

    public PagerAdapter(Context mContext, FragmentManager fm, Movie movie) {
        super(fm);
        this.mContext = mContext;
        this.movie = movie;
    }

    @Override
    public Fragment getItem(int position) {
        if(getPageTitle(position).equals("OVERVIEW")){
            Log.i(LOG_TAG, "overview to be passed: " + movie.getOverview());
            return OverviewFragment.newInstance(movie.getOverview());

        }else if(getPageTitle(position).equals("REVIEWS")){
            Log.i(LOG_TAG, "ID to be passed: " + movie.getId());
            return ReviewFragment.newInstance(movie.getId());
        }else if(getPageTitle(position).equals("TRAILERS")){
            return TrailerFragment.newInstance(movie.getId());
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "OVERVIEW";
            case 1:
                return "REVIEWS";
            case 2:
                return "TRAILERS";
        }
        return null;
    }
}

