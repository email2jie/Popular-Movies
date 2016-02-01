package jiewei.popularmovies.util;

import jiewei.popularmovies.objects.Movie;
import jiewei.popularmovies.objects.Review;
import jiewei.popularmovies.objects.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jie Wei on 29/11/2015.
 */
public class JSONParser {

    public List<Object> parseMovieList(String json){
        List<Object> movieList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int i =0;i<jsonArray.length();i++){
                Movie movie = new Movie();
                JSONObject object = jsonArray.getJSONObject(i);
                movie.setId(object.getString("id"));
                movie.setThumb(Constants.IMAGE_PATH_BASE_URL + object.get("poster_path"));
                movie.setPoster(Constants.IMAGE_PATH_BASE_URL + object.get("poster_path"));
                movie.setOverview(object.getString("overview"));
                movie.setRelease_date(object.getString("release_date"));
                movie.setTitle(object.getString("original_title"));
                movie.setRating(object.getString("vote_average"));
                movie.setBack_drop(Constants.BACKDROP_IMAGE_PATH_BASE_URL + object.getString("backdrop_path"));
                movie.setPopularity(object.getString("popularity"));

                movieList.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;
    }

    public List<Object> parseMovieTrailers(String json){
        List<Object> trailerList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int i =0;i<jsonArray.length();i++){
                Trailer trailer = new Trailer();
                JSONObject object = jsonArray.getJSONObject(i);
                trailer.setId(object.getString("id"));
                trailer.setKey(object.getString("key"));
                trailer.setName(object.getString("name"));
                trailer.setSite(object.getString("site"));
                trailer.setSize(object.getString("size"));
                trailer.setType(object.getString("type"));
                trailerList.add(trailer);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailerList;
    }

    public List<Object> parseMovieReviews(String json){
        List<Object> reviewList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int i =0;i<jsonArray.length();i++){
                Review review = new Review();
                JSONObject object = jsonArray.getJSONObject(i);
                review.setId(object.getString("id"));
                review.setAuthor(object.getString("author"));
                review.setContent(object.getString("content"));
                reviewList.add(review);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviewList;
    }

}
