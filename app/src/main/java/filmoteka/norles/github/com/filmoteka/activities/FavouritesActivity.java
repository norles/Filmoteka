package filmoteka.norles.github.com.filmoteka.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import filmoteka.norles.github.com.filmoteka.BuildConfig;
import filmoteka.norles.github.com.filmoteka.adapters.MovieAdapter;
import filmoteka.norles.github.com.filmoteka.R;
import filmoteka.norles.github.com.filmoteka.Settings;
import filmoteka.norles.github.com.filmoteka.models.MovieItem;
import filmoteka.norles.github.com.filmoteka.network.Client;
import filmoteka.norles.github.com.filmoteka.network.MovieService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {

    private static final String TAG = FavouritesActivity.class.getName();

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    private List<MovieItem> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        setTitle(R.string.favourites);

        initView();
    }

    void initView() {
        recyclerView = findViewById(R.id.favourites_recycle_view);

        movies = new ArrayList<>();

        movieAdapter = new MovieAdapter(this, movies);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();

        loadJSON();
    }

    void loadJSON() {
        movies = new ArrayList<>();

        MovieService service = Client
                .getRetrofit()
                .create(MovieService.class);

        List<Integer> favourites = Settings.getInstance().getFavourites();

        for (Integer id :
                favourites) {
            Call<MovieItem> call = service.getMovieItemId(id, BuildConfig.MOVIE_DB_KEY);

            call.enqueue(new Callback<MovieItem>() {
                @Override
                public void onResponse(Call<MovieItem> call, Response<MovieItem> response) {
                    if (response.isSuccessful()){
                        MovieItem movie = response.body();
                        movies.add(movie);
                        Log.d(TAG, movie.getTitle());

                        recyclerView.setAdapter(new MovieAdapter(getApplicationContext(), movies));
                    }
                }

                @Override
                public void onFailure(Call<MovieItem> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                }
            });
        }

    }
}
