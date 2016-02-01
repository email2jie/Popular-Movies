package jiewei.popularmovies;

import android.content.DialogInterface;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String DETAILFRAGMENT_TAG = "DFTG";

    public boolean mTwoPane;
    Fragment movieFragment;
    String[] items = {"Most Popular","Highest Rated","My Favorites"};
    private int itemSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.detail_container) != null) {
            //The detail container view will be present only in the large_screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            } else {
                mTwoPane = false;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, PopularMovieFragment.newInstance())
                    .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //if selected the sort option menu
        if(item.getItemId()==R.id.action_sort){
            //create dialog with the static options in items
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if current itemSelected is not what is selected now then replace itemSelected with what is selected
                    if(itemSelected != which) {
                        itemSelected = which;
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, getFragmentType(itemSelected))
                                .addToBackStack(null)
                                .commit();
                    }
                }
            }).create().show();
        }
        return true;
    }

    //returns the Fragment based on itemSelected value
    private Fragment getFragmentType(int itemSelected){
        switch(itemSelected){
            case 0:
                return PopularMovieFragment.newInstance();
            case 1:
                return TopRatedMovieFragment.newInstance();
            case 2:
                return FavoriteMovieFragment.newInstance();
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        itemSelected = -1;
    }
}