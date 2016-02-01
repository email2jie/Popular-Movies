package jiewei.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jiewei.popularmovies.util.onTaskCompleted;

/**
 * Created by Jie Wei on 1/18/2016.
 */
public class OverviewFragment extends Fragment implements onTaskCompleted {
    private static final String LOG_TAG = OverviewFragment.class.getSimpleName();

    private static final String ARG_PARAM = "param";

    private String mParam;

    //private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param Parameter 1.
     * @return A new instance of fragment OverviewFragment.
     */

    public static OverviewFragment newInstance(String param) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
            Log.i(LOG_TAG, "Arg param is: "+ARG_PARAM);
            Log.i(LOG_TAG, "parameter is: "+ mParam );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pager_item, container, false);
        TextView overViewTV = (TextView)view.findViewById(R.id.overview_text_view);

        if(mParam != null )
            overViewTV.setText(mParam);
        else
            overViewTV.setText("No Overviews Found");
        return view;
    }

    @Override
    public void onTaskCompleted(Object object) {
        String res = (String)object;
        System.out.println(res);
    }

}
