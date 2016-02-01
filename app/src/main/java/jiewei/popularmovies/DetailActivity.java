package jiewei.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import jiewei.popularmovies.objects.Movie;

/**
 * Created by Jie Wei on 1/16/2016.
 */
public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null && null != getIntent()) {
            Movie movie = getIntent().getParcelableExtra("movie");
            DetailFragment detailFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("movie", movie);
            detailFragment.setArguments(bundle);
            //add detailFragment loaded with movie object to detail_container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, detailFragment)
                    .commit();
        }
    }

}
