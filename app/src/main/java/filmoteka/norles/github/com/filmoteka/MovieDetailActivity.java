package filmoteka.norles.github.com.filmoteka;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        loadJSON();
    }

    void loadJSON(){

        MovieService service = Client
                .getRetrofit()
                .create(MovieService.class);

        Call<MovieDetail> call = service.getMovieDetail(383498,BuildConfig.MOVIE_DB_KEY);

        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                if (response.isSuccessful()){
                    movieDetail = response.body();
                    Log.d(TAG, movieDetail.getTitle());
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }
}
