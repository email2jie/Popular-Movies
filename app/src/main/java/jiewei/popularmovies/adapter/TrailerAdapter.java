package jiewei.popularmovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import jiewei.popularmovies.R;
import jiewei.popularmovies.objects.Trailer;

import java.util.List;

/**
 * Created by Jie Wei on 1/16/2016.
 */
public class TrailerAdapter extends BaseAdapter{


    private final Context mContext;
    private final List<Trailer> trailerList;

    public TrailerAdapter(Context context, List<Trailer> trailerList) {
        this.mContext = context;
        this.trailerList = trailerList;
    }

    @Override
    public int getCount() {
        return trailerList.size();
    }

    @Override
    public Object getItem(int i) {
        return trailerList.get(i);
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
            convertView = inflater.inflate(R.layout.trailer_list_item, parent, false);
            holder = new ViewHolder();
            holder.trailer_title = (TextView) convertView.findViewById(R.id.trailer_text_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.trailer_title.setText("Trailer " + (i+1));

        return convertView;
    }

    static class ViewHolder{

        TextView trailer_title;

    }
}
