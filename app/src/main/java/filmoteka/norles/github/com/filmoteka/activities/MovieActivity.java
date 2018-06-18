package filmoteka.norles.github.com.filmoteka.activities;

import android.content.res.Configuration;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import filmoteka.norles.github.com.filmoteka.BuildConfig;
import filmoteka.norles.github.com.filmoteka.adapters.MovieAdapter;
import filmoteka.norles.github.com.filmoteka.R;
import filmoteka.norles.github.com.filmoteka.models.MovieResponse;
import filmoteka.norles.github.com.filmoteka.models.MovieItem;
import filmoteka.norles.github.com.filmoteka.network.Client;
import filmoteka.norles.github.com.filmoteka.network.MovieService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

// Aktywność wyświetlająca listę popularnych filmow
public class MovieActivity extends AppCompatActivity {

    private static final String TAG = MovieActivity.class.getName();

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<MovieItem> results;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        setTitle(R.string.popular_movies);


        initViews();

        swipeRefreshLayout = findViewById(R.id.main_content);
        // Inicjacja listenera dla vidoku swipeRefresh,
        // po odświeżeniu dane mają się ponownie pobrac z serwera
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
            }
        });

    }

    // Metoda inicjuje elementy widoku
    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);

        movieAdapter = new MovieAdapter(this, results);

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

    // Metoda pobierajaca dane z serwera
    void loadJSON(){
        // Stworzenie serwisu za pomoca obiektu retrofit
        MovieService service = Client
                .getRetrofit()
                .create(MovieService.class);

        // Definicja zapytania HTTP
        Call<MovieResponse> call = service.getPopularMovies(BuildConfig.MOVIE_DB_KEY);
        // Asynchroniczne wykonanie zapytania
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    List<MovieItem> results = response.body().getResults();

                    recyclerView.setAdapter(new MovieAdapter(getApplicationContext(), results));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

    }
}
