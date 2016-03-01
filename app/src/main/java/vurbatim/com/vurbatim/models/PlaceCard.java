package vurbatim.com.vurbatim.models;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dakaugu on 2/18/16.
 */
public class PlaceCard extends VurbCard{

    @SerializedName("placeCategory")
    @Expose
    private String placeCategory;

    public PlaceCard() {
        super();
        type = "place"; //because we lose our type (null) after RuntimeTypeAdapterFactory
    }

    /**
     *
     * @return
     * The placeCategory
     */
    public String getPlaceCategory() {
        return placeCategory;
    }

    /**
     *
     * @param placeCategory
     * The placeCategory
     */
    public void setPlaceCategory(String placeCategory) {
        this.placeCategory = placeCategory;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(placeCategory);
    }

    protected PlaceCard(Parcel in) {
        super(in);
        placeCategory = in.readString();
    }

    public static final Creator<PlaceCard> CREATOR = new Creator<PlaceCard>() {
        @Override
        public PlaceCard createFromParcel(Parcel in) {
            return new PlaceCard(in);
        }

        @Override
        public PlaceCard[] newArray(int size) {
            return new PlaceCard[size];
        }
    };
}
