package jiewei.popularmovies.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jie Wei on 1/18/2016.
 */
public class Movie implements Parcelable {

    public static final String PARCEL_TAG = "movie_tag";

    public String thumb;
    public String id;
    public String title;
    public String poster;
    public String back_drop;
    public String overview;
    public String release_date;
    public String rating;
    public String popularity;

    public Movie(){

    }

    public Movie(String id, String thumb, String title, String poster, String back_drop, String overview, String release_date, String rating) {

        this.thumb = thumb;
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.back_drop = back_drop;
        this.overview = overview;
        this.release_date = release_date;
        this.rating = rating;
        this.popularity = popularity;
    }

    private Movie (Parcel in) {
        thumb = in.readString();
        id = in.readString();
        title = in.readString();
        poster = in.readString();
        back_drop = in.readString();
        overview = in.readString();
        release_date = in.readString();
        rating = in.readString();
        popularity = in.readString();

    }
    @Override
    public int describeContents(){
        return  0;
    }
    @Override
    public void writeToParcel (Parcel parcel, int i) {
        parcel.writeString(thumb);
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(back_drop);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeString(rating);
        parcel.writeString(popularity);

    }
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        @Override
        public  Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }
        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBack_drop() {
        return back_drop;
    }

    public void setBack_drop(String back_drop) {
        this.back_drop = back_drop;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getPopularity(){
        return popularity;
    }
    public void setPopularity(String popularity){
        this.popularity =popularity;
    }
}
