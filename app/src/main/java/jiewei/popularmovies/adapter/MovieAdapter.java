package jiewei.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import jiewei.popularmovies.R;
import jiewei.popularmovies.objects.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Jie Wei on 1/16/2016.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    public MovieAdapter(Context context,  ArrayList<Movie> objects) {
        super(context,0,objects);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        //get Movie object from the ArrayAdapter at the appropriate position
        Movie current = getItem(position);
        String thumbUrl = current.thumb;

        ImageView imageView;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate (R.layout.image_item,parent,false);
        }
        imageView = (ImageView) convertView;
        imageView.setAdjustViewBounds(true);

        Picasso.with(getContext())
                .load(thumbUrl)
                .fit()
                .into(imageView);



        return imageView;
    }
}