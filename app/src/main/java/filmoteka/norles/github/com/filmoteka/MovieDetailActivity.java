package filmoteka.norles.github.com.filmoteka;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import filmoteka.norles.github.com.filmoteka.models.MovieDetail;
import filmoteka.norles.github.com.filmoteka.models.MovieItem;
import filmoteka.norles.github.com.filmoteka.models.MovieResponse;
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
    private Integer id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        id = getIntent().getIntExtra("id", 0);

        imageView = findViewById(R.id.movie_detail_thumbnail);
        titleView = findViewById(R.id.movie_detail_title);

        loadJSON();
    }

    void loadJSON(){

        MovieService service = Client
                .getRetrofit()
                .create(MovieService.class);

        Call<MovieDetail> call = service.getMovieDetail(id,BuildConfig.MOVIE_DB_KEY);

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

        Glide.with(this)
                .load(movieDetail.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .into(imageView);

        titleView.setText(movieDetail.getTitle());
    }
}
