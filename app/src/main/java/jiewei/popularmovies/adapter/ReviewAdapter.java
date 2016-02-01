package jiewei.popularmovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import jiewei.popularmovies.R;
import jiewei.popularmovies.objects.Review;

import java.util.List;

/**
 * Created by Jie Wei on 1/16/2016.
 */
public class ReviewAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<Review> reviewList;

    public ReviewAdapter(Context context, List<Review> reviewList) {
        this.mContext = context;
        this.reviewList = reviewList;
    }

    @Override
    public int getCount() {
        return reviewList.size();
    }

    @Override
    public Object getItem(int i) {
        return reviewList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.review_list_item, parent, false);
            holder = new ViewHolder();
            holder.review = (TextView) convertView.findViewById(R.id.review_text_view);
            holder.author = (TextView) convertView.findViewById(R.id.author_text_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.review.setText(reviewList.get(i).getContent());
        holder.author.setText("- "+reviewList.get(i).getAuthor());

        return convertView;
    }

    static class ViewHolder{
        TextView author,review;

    }
}