package filmoteka.norles.github.com.filmoteka.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import filmoteka.norles.github.com.filmoteka.BuildConfig;
import filmoteka.norles.github.com.filmoteka.R;
import filmoteka.norles.github.com.filmoteka.Settings;
import filmoteka.norles.github.com.filmoteka.models.MovieDetail;
import filmoteka.norles.github.com.filmoteka.network.Client;
import filmoteka.norles.github.com.filmoteka.network.MovieService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailActivity.class.getName();

    private MovieDetail movieDetail;

    private ImageView imageView;
    private TextView titleView;
    private TextView durationView;
    private TextView descriptionView;
    private Integer id;

    private Button favButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        id = getIntent().getIntExtra("id", 0);

        imageView = findViewById(R.id.movie_detail_thumbnail);

        titleView = findViewById(R.id.movie_detail_title);
        durationView = findViewById(R.id.movie_detail_duration);
        descriptionView = findViewById(R.id.movie_detail_desc);

        favButton = findViewById(R.id.favourite_button);

        loadJSON();

        initFavButton();
    }

    private void initFavButton() {
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.getInstance().addFavourite(id);
//                Settings.getInstance().saveFavourites();
                changeFavBtnColor();
            }
        });

        changeFavBtnColor();
    }

    private void changeFavBtnColor() {
        List<Integer> favourites = Settings.getInstance().getFavourites();
        if (favourites.contains(id)){
            favButton.setBackgroundColor(Color.RED);
            favButton.setText(R.string.remove_from_fav);
        } else {
            favButton.setBackgroundColor(Color.GREEN);
            favButton.setText(R.string.add_to_fav);
        }
    }

    void loadJSON(){

        MovieService service = Client
                .getRetrofit()
                .create(MovieService.class);

        Call<MovieDetail> call = service.getMovieDetail(id, BuildConfig.MOVIE_DB_KEY);

        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                if (response.isSuccessful()){
                    movieDetail = response.body();

                    initView();
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    void initView(){
        setTitle(movieDetail.getTitle());

        Glide.with(this)
                .load(movieDetail.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .into(imageView);

        titleView.setText(movieDetail.getTitle());

        int hours = movieDetail.getRuntime() / 60;
        int minutes = movieDetail.getRuntime() % 60;
        durationView.setText(String.format("%d godzin. %d minut.", hours, minutes));
        descriptionView.setText(movieDetail.getOverview());
    }
}
