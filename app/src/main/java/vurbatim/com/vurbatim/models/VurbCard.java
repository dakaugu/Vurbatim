package vurbatim.com.vurbatim.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dakaugu on 2/18/16.
 *
 * A default Vurb Card
 */
public class VurbCard implements Parcelable {

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("imageURL")
    @Expose
    private String imageURL;

    public VurbCard() {

    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The imageURL
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     *
     * @param imageURL
     * The imageURL
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(title);
        dest.writeString(imageURL);
    }

    protected VurbCard(Parcel in) {
        type = in.readString();
        title = in.readString();
        imageURL = in.readString();
    }

    public static final Creator<VurbCard> CREATOR = new Creator<VurbCard>() {
        @Override
        public VurbCard createFromParcel(Parcel in) {
            return new VurbCard(in);
        }

        @Override
        public VurbCard[] newArray(int size) {
            return new VurbCard[size];
        }
    };
}
