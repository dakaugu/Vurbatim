package vurbatim.com.vurbatim.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vurbatim.com.vurbatim.R;
import vurbatim.com.vurbatim.api.VbApiClient;
import vurbatim.com.vurbatim.models.CardsList;
import vurbatim.com.vurbatim.models.MovieCard;
import vurbatim.com.vurbatim.models.MusicCard;
import vurbatim.com.vurbatim.models.PlaceCard;
import vurbatim.com.vurbatim.models.VurbCard;

public class LaunchActivity extends AppCompatActivity {

    private static final String TAG = LaunchActivity.class.getSimpleName();
    public static final int MY_PERMISSION_LOCATION = 42;
    private VbApiClient.CardsApiService cardsApiService;
    private List<VurbCard> cards;
    private double lat, lng;
    private LocationManager mLocationManager;
    private boolean locationReady, completed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        cardsApiService = VbApiClient.cardsInterface.create(VbApiClient.CardsApiService.class);
        getCards();
        getLocation();

    }

    public void getCards() {
        Call<CardsList> cardsCall = cardsApiService.getVurbCards();
        cardsCall.enqueue(new Callback<CardsList>() {
            @Override
            public void onResponse(Call<CardsList> call, Response<CardsList> response) {
                cards = response.body().getCards();
                logResponse();
                dataReady();
            }

            @Override
            public void onFailure(Call<CardsList> call, Throwable t) {
                showToast("Failed to retrieve cards", getApplicationContext());
                t.printStackTrace();
            }
        });
    }

    public static void showToast(String text, Context context) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public void logResponse() {
        for (VurbCard card : cards) {
            Log.d(TAG, "type is " + card.getType());
            Log.d(TAG, "title is " + card.getTitle());
            Log.d(TAG, "image url is " + card.getImageURL());
            if (card instanceof MusicCard) {
                Log.d(TAG, "music video is " + ((MusicCard) card).getMusicVideoURL());
            } else if (card instanceof MovieCard) {
                Log.d(TAG, "movie extra image is " + ((MovieCard) card).getMovieExtraImageURL());
            } else if (card instanceof PlaceCard) {
                Log.d(TAG, "place category is " + ((PlaceCard) card).getPlaceCategory());
            }
        }
    }

    public void getLocation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // only marshmallow
            checkLocationPermission();
        } else {
            getCoordinates();
        }
    }

    //using google location services would be better todo
    public void getCoordinates() {
        String context = Context.LOCATION_SERVICE;
        mLocationManager = (LocationManager) getSystemService(context);

        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //getting last know location will improve launch time
            //but may not be the best solution if getting exact location vs city
            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            updateLatLng(location);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            showToast("Please enable location Services", this);
        }
    }

    private void updateLatLng(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            Log.d(TAG, "lat " + lat);
            Log.d(TAG, "lng " + lng);
            locationReady = true;
            dataReady();
        }

    }

    final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            updateLatLng(location);
            mLocationManager.removeUpdates(this);
        }

        public void onProviderDisabled(String provider) {
            updateLatLng(null);
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    //for marshmallow support
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, MY_PERMISSION_LOCATION);

        } else {
            getCoordinates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case (MY_PERMISSION_LOCATION):
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCoordinates();
                } else {
                    //TODO
                }
                return;

        }
    }

    public void dataReady() {
        if(cards != null && locationReady && !completed) {
            completed = true;
            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.putExtra(MainActivity.COORDINATES, lat + ", " + lng);
            mainIntent.putParcelableArrayListExtra(MainActivity.VURB_CARDS, (ArrayList<? extends Parcelable>) cards);
            startActivity(mainIntent);
            finish();
        } else if(completed) {
            //TODO
        }
    }

}
