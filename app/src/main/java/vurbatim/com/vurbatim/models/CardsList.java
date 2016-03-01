package vurbatim.com.vurbatim.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dakaugu on 2/18/16.
 */
public class CardsList {

    @SerializedName("cards")
    @Expose
    private List<VurbCard> cards = new ArrayList<>();

    /**
     *
     * @return
     * The cards
     */
    public List<VurbCard> getCards() {
        return cards;
    }

    /**
     *
     * @param cards
     * The cards
     */
    public void setCards(List<VurbCard> cards) {
        this.cards = cards;
    }

}
