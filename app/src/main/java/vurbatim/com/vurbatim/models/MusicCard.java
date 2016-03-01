package vurbatim.com.vurbatim.models;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dakaugu on 2/18/16.
 */
public class MusicCard extends VurbCard{

    @SerializedName("musicVideoURL")
    @Expose
    private String musicVideoURL;

    public MusicCard() {
        super();
        type = "music"; //because we lose our type (null) after RuntimeTypeAdapterFactory
    }

    /**
     *
     * @return
     * The musicVideoURL
     */
    public String getMusicVideoURL() {
        return musicVideoURL;
    }

    /**
     *
     * @param musicVideoURL
     * The musicVideoURL
     */
    public void setMusicVideoURL(String musicVideoURL) {
        this.musicVideoURL = musicVideoURL;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(musicVideoURL);
    }

    protected MusicCard(Parcel in) {
        super(in);
        musicVideoURL = in.readString();
    }

    public static final Creator<MusicCard> CREATOR = new Creator<MusicCard>() {
        @Override
        public MusicCard createFromParcel(Parcel in) {
            return new MusicCard(in);
        }

        @Override
        public MusicCard[] newArray(int size) {
            return new MusicCard[size];
        }
    };
}
