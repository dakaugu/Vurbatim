package vurbatim.com.vurbatim.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import vurbatim.com.vurbatim.R;
import vurbatim.com.vurbatim.models.MovieCard;
import vurbatim.com.vurbatim.models.MusicCard;
import vurbatim.com.vurbatim.models.PlaceCard;
import vurbatim.com.vurbatim.models.VurbCard;

/**
 * Created by dakaugu on 2/20/16.
 */
public class VurbCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = VurbCardAdapter.class.getSimpleName();

    private final int PLACE = 0;
    private final int MOVIE = 1;
    private final int MUSIC = 2;

    private LayoutInflater inflater;
    private Context context;
    private List<VurbCard> cards;

    public VurbCardAdapter(Context context, List<VurbCard> cards) {
        this.context = context;
        this.cards = cards;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        if (cards != null) {
            return cards.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (cards.get(position) instanceof PlaceCard) {
            return PLACE;
        } else if (cards.get(position) instanceof MovieCard) {
            return MOVIE;
        } else if (cards.get(position) instanceof MusicCard) {
            return MUSIC;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        View v;

        switch (viewType) {
            case PLACE:
                v = inflater.inflate(R.layout.place_card, parent, false);
                viewHolder = new PlaceCardHolder(v);
                break;

            case MOVIE:
                v = inflater.inflate(R.layout.movie_card, parent, false);
                viewHolder = new MovieCardHolder(v);
                break;

            case MUSIC:
                v = inflater.inflate(R.layout.music_card, parent, false);
                viewHolder = new MusicCardHolder(v);
                break;

            default: //if adapter encounters a type not yet known by the client, treat it as a base vurb card
                v = inflater.inflate(R.layout.vurb_card, parent, false);
                viewHolder = new VurbCardHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case PLACE:
                PlaceCardHolder placeCardHolder = (PlaceCardHolder) holder;
                placeCardHolder.bind(cards.get(position));
                break;

            case MOVIE:
                MovieCardHolder movieCardHolder = (MovieCardHolder) holder;
                movieCardHolder.bind(cards.get(position));
                break;

            case MUSIC:
                MusicCardHolder musicCardHolder = (MusicCardHolder) holder;
                musicCardHolder.bind(cards.get(position));
                break;

            default: //if adapter encounters a type not yet known by the client, treat it as a base vurb card
                VurbCardHolder vurbCardHolder = (VurbCardHolder) holder;
                vurbCardHolder.bind(cards.get(position));
                break;
        }
    }

    public class VurbCardHolder extends RecyclerView.ViewHolder {

        private TextView title, type;
        private ImageView image;

        public VurbCardHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            type = (TextView) itemView.findViewById(R.id.type);
            image = (ImageView) itemView.findViewById(R.id.image);
        }

        public void bind(VurbCard card) {
            title.setText(card.getTitle());
            type.setText(card.getType());

            if(card instanceof MovieCard) {
                Glide.with(context)
                        .load(card.getImageURL())
                        .into(image);
            } else {
                Glide.with(context)
                        .load(card.getImageURL())
                        .centerCrop()
                        .into(image);
            }
        }
    }

    public class PlaceCardHolder extends VurbCardHolder {

        private TextView category;
        private PlaceCard placeCard;

        public PlaceCardHolder(View itemView) {
            super(itemView);
            category = (TextView) itemView.findViewById(R.id.category);
        }

        public void bind(VurbCard card){
            super.bind(card);
            placeCard = (PlaceCard) card;
            category.setText(placeCard.getPlaceCategory());
        }
    }

    public class MovieCardHolder extends VurbCardHolder {

        private ImageView extra_image;
        private MovieCard movieCard;

        public MovieCardHolder(View itemView) {
            super(itemView);
            extra_image = (ImageView) itemView.findViewById(R.id.extra_image);
        }

        public void bind(VurbCard card){
            super.bind(card);
            movieCard = (MovieCard) card;
            Glide.with(context)
                    .load(movieCard.getMovieExtraImageURL())
                    .into(extra_image);
        }
    }

    public class MusicCardHolder extends VurbCardHolder {

        private TextView watch_video;
        private MusicCard musicCard;

        public MusicCardHolder(View itemView) {
            super(itemView);
            watch_video = (TextView) itemView.findViewById(R.id.watch_text);
        }

        public void bind(VurbCard card){
            super.bind(card);
            musicCard = (MusicCard) card;
            watch_video.setOnClickListener(watchVideoOnClickListener);
        }

        private View.OnClickListener watchVideoOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(musicCard.getMusicVideoURL()));
                context.startActivity(videoIntent);
            }
        };
    }
}
