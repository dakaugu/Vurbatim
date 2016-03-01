package vurbatim.com.vurbatim.models;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dakaugu on 2/18/16.
 */
public class MovieCard extends VurbCard{

    @SerializedName("movieExtraImageURL")
    @Expose
    private String movieExtraImageURL;

    public MovieCard() {
        super();
        type = "movie"; //because we lose our type (null) after RuntimeTypeAdapterFactory
    }

    /**
     *
     * @return
     * The movieExtraImageURL
     */
    public String getMovieExtraImageURL() {
        return movieExtraImageURL;
    }

    /**
     *
     * @param movieExtraImageURL
     * The movieExtraImageURL
     */
    public void setMovieExtraImageURL(String movieExtraImageURL) {
        this.movieExtraImageURL = movieExtraImageURL;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(movieExtraImageURL);
    }

    protected MovieCard(Parcel in) {
        super(in);
        movieExtraImageURL = in.readString();
    }

    public static final Creator<MovieCard> CREATOR = new Creator<MovieCard>() {
        @Override
        public MovieCard createFromParcel(Parcel in) {
            return new MovieCard(in);
        }

        @Override
        public MovieCard[] newArray(int size) {
            return new MovieCard[size];
        }
    };
}
