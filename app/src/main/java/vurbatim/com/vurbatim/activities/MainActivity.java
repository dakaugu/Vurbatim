package vurbatim.com.vurbatim.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import vurbatim.com.vurbatim.R;
import vurbatim.com.vurbatim.adapters.VurbCardAdapter;
import vurbatim.com.vurbatim.models.VurbCard;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String COORDINATES = "coordinates";
    public static final String VURB_CARDS = "vurb cards";

    private TextView locationText;
    private List<VurbCard> cards;
    private RecyclerView mRecyclerView;
    private VurbCardAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        setupRecyclerView();
    }

    private void initialize() {
        locationText = (TextView) findViewById(R.id.location);

        Intent intent = getIntent();
        String loc = intent.getStringExtra(COORDINATES);
        cards = intent.getParcelableArrayListExtra(VURB_CARDS);
        locationText.setText(loc);
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.vurb_cards_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new VurbCardAdapter(this, cards);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
