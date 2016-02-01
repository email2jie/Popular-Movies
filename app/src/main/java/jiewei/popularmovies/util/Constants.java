package jiewei.popularmovies.util;

/**
 * Created by Jie Wei on 20/12/2015.
 */
public interface Constants {

    public String REQUEST_BASE_URL = "http://api.themoviedb.org/3/discover/movie";
    public String IMAGE_PATH_BASE_URL = "http://image.tmdb.org/t/p/w185/";
    public String BACKDROP_IMAGE_PATH_BASE_URL = "http://image.tmdb.org/t/p/w342/";

    public String TRAILER_REVIEWS_BASE_URL = "http://api.themoviedb.org/3/movie";
    public String YOU_TUBE_BASE_URL = "http://www.youtube.com/watch?v=";

    public int MOST_POPULAR_REQUEST = 1;
    public int TOP_RATED_REQUEST = 2;
    public int VIDEO_TRAILER_REQUEST = 3;
    public int MOVIE_REVIEWS_REQUEST = 4;

    public String FRAGMENT_TYPE_FAVORITE = "FAVORITE";



}