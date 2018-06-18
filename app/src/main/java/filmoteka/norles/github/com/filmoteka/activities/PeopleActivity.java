package filmoteka.norles.github.com.filmoteka.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import filmoteka.norles.github.com.filmoteka.BuildConfig;
import filmoteka.norles.github.com.filmoteka.adapters.PersonAdapter;
import filmoteka.norles.github.com.filmoteka.R;
import filmoteka.norles.github.com.filmoteka.models.PeopleResponse;
import filmoteka.norles.github.com.filmoteka.models.PersonItem;
import filmoteka.norles.github.com.filmoteka.network.Client;
import filmoteka.norles.github.com.filmoteka.network.MovieService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

// Aktywność wyświetlająca listę popularnych aktorów
public class PeopleActivity extends AppCompatActivity {

    private static final String TAG = PeopleActivity.class.getName();

    private RecyclerView recyclerView;
    private PersonAdapter personAdapter;
    private List<PersonItem> people;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        setTitle(R.string.popular_people);

        initViews();

        swipeRefreshLayout = findViewById(R.id.poeple_content);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
                Log.d(TAG, "REFRESH 2222!!!!!!");
            }
        });

    }

    private void initViews() {
        recyclerView = findViewById(R.id.people_recycler_view);

        personAdapter = new PersonAdapter(this, people);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(personAdapter);
        personAdapter.notifyDataSetChanged();

        loadJSON();
    }

    void loadJSON(){

        Log.d(TAG, "Load json");
        MovieService service = Client
                .getRetrofit()
                .create(MovieService.class);

        Call<PeopleResponse> call = service.getPopularPeople(BuildConfig.MOVIE_DB_KEY);
        call.enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                Log.d(TAG, "call ");
                if (response.isSuccessful()){
                    Log.d(TAG, "Succesful");
                    people = response.body().getResults();

                    for (PersonItem i :
                            people) {
                        Log.d(TAG, i.getName());
                    }

                    recyclerView.setAdapter(new PersonAdapter(getApplicationContext(), people));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<PeopleResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }
}
