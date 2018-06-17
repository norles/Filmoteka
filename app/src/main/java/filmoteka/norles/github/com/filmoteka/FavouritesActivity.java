package filmoteka.norles.github.com.filmoteka;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import filmoteka.norles.github.com.filmoteka.models.MovieDetail;
import filmoteka.norles.github.com.filmoteka.models.SearchItem;
import filmoteka.norles.github.com.filmoteka.models.SearchResult;
import filmoteka.norles.github.com.filmoteka.network.Client;
import filmoteka.norles.github.com.filmoteka.network.MovieService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {

    private static final String TAG = FavouritesActivity.class.getName();

    private RecyclerView recyclerView;

    private List<MovieDetail> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        setTitle(R.string.favourites);

        initView();
        loadJSON();
    }

    void initView() {
        recyclerView = findViewById(R.id.favourites_recycle_view);

//        SearchAdapter adapter = new SearchAdapter(this, items);
//
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
//            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//        } else {
//            recyclerView.setLayoutManager(new GridLayoutManager(this,4));
//        }

//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
    }

    void loadJSON() {
        movies = new ArrayList<>();

        MovieService service = Client
                .getRetrofit()
                .create(MovieService.class);

        List<Integer> favourites = Settings.getInstance().getFavourites();

        for (Integer id :
                favourites) {
            Call<MovieDetail> call = service.getMovieDetail(id, BuildConfig.MOVIE_DB_KEY);

            call.enqueue(new Callback<MovieDetail>() {
                @Override
                public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                    if (response.isSuccessful()){
                        MovieDetail movie = response.body();
                        movies.add(movie);
                        Log.d(TAG, movie.getTitle());
                    }
                }

                @Override
                public void onFailure(Call<MovieDetail> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                }
            });
        }

    }
}
