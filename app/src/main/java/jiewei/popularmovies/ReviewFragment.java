package jiewei.popularmovies;

import android.support.v4.app.ListFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import jiewei.popularmovies.adapter.ReviewAdapter;
import jiewei.popularmovies.objects.Review;
import jiewei.popularmovies.util.CommonAsyncTask;
import jiewei.popularmovies.util.Constants;
import jiewei.popularmovies.util.onTaskCompleted;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jie Wei on 1/19/2016.
 */
public class ReviewFragment extends ListFragment implements onTaskCompleted {

    public static final String MOVIE_ID = "movie_id";
    private String mParam;
    private List<Review> reviewList = new ArrayList<>();
    private ReviewAdapter mReviewAdapter;

    public static ReviewFragment newInstance(String param) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_ID,param);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReviewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(MOVIE_ID);
            CommonAsyncTask asyncTask = new CommonAsyncTask(this, Constants.MOVIE_REVIEWS_REQUEST);
            asyncTask.execute();
        }

        mReviewAdapter = new ReviewAdapter(getActivity(),reviewList);
        setListAdapter(mReviewAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onStart() {
        super.onStart();
        getListView().setDivider(new ColorDrawable(Color.BLACK));
        getListView().setDividerHeight(1);
    }

    @Override
    public void onTaskCompleted(Object object) {
        if(null!=object) {
            reviewList.addAll((List<Review>) object);
            mReviewAdapter.notifyDataSetChanged();
            if(reviewList.size()==0){
                setEmptyText("No Review Found");
            }
        }else{
            Toast.makeText(getActivity(), "An error has occurred.", Toast.LENGTH_SHORT).show();
        }
    }

}
