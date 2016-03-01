package vurbatim.com.vurbatim.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import vurbatim.com.vurbatim.models.CardsList;
import vurbatim.com.vurbatim.models.MovieCard;
import vurbatim.com.vurbatim.models.MusicCard;
import vurbatim.com.vurbatim.models.PlaceCard;
import vurbatim.com.vurbatim.models.VurbCard;
import vurbatim.com.vurbatim.utils.RuntimeTypeAdapterFactory;

/**
 * Created by dakaugu on 2/18/16.
 */
public class VbApiClient {

    /**
     *  a bit unnecessary to create an api client or use retrofit just for one call
     *  but creating this app as if we can expand the app and add new calls
     */

    public static final String BASE_URL = "https://gist.githubusercontent.com/";

    //this helps us de/serialize a list of polymorphic vurb cards with gson
    public static final RuntimeTypeAdapterFactory<VurbCard> typeFactory = RuntimeTypeAdapterFactory
            .of(VurbCard.class, "type")
            .registerSubtype(MovieCard.class, "movie")
            .registerSubtype(PlaceCard.class, "place")
            .registerSubtype(MusicCard.class, "music")
            .registerSubtype(VurbCard.class, RuntimeTypeAdapterFactory.DEFAULT); //all unregistered type becomes a base Vurb card
            //want to add a new type of card?
            //.registerSubtype(EventCard.class, "event");

    //registering our type adapter to our gson builder
    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(typeFactory)
            .create();

    //and converting in our retrofit builder
    public static Retrofit cardsInterface = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public interface CardsApiService {

        //@GET("dakaugu/8f5bc303eb29707792e2/raw/a37d11e3964d16dc5a67f70829ae58cdebe772e0/cards.js")
        @GET("helloandrewpark/0a407d7c681b833d6b49/raw/5f3936dd524d32ed03953f616e19740bba920bcd/gistfile1.js")
        Call<CardsList> getVurbCards();

        //where you would add new calls
        //Ex:
        //@GET("api.vurb.com/{fooMovie}/movies)
        //Call<List<MovieCard>> getMovieCards(...);

    }

}
