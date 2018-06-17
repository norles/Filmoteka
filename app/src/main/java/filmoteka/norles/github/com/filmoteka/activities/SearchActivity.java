package filmoteka.norles.github.com.filmoteka.activities;

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
import filmoteka.norles.github.com.filmoteka.BuildConfig;
import filmoteka.norles.github.com.filmoteka.R;
import filmoteka.norles.github.com.filmoteka.adapters.SearchAdapter;
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

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getName();

    private EditText editSearch;
    private RecyclerView recyclerView;
    private Button button;

    private String query = "";

    private List<SearchItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
    }

    void initView() {
        editSearch = findViewById(R.id.search_edit);
        recyclerView = findViewById(R.id.search_recycle_view);
        button = findViewById(R.id.search_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = editSearch.getText().toString();
                editSearch.clearFocus();
                loadJSON();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        SearchAdapter adapter = new SearchAdapter(this, items);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    void loadJSON() {
        items = new ArrayList<>();

        MovieService service = Client
                .getRetrofit()
                .create(MovieService.class);

        Call<SearchResult> call = service.getSearchResults(BuildConfig.MOVIE_DB_KEY, query);

        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (response.isSuccessful()) {
                    SearchResult searchResult = response.body();
                    List<SearchItem> items = searchResult.getResults();

                    Log.d(TAG, String.valueOf(searchResult.getTotalResults()));
                    Log.d(TAG, String.valueOf(items.size()));
                    Iterator<SearchItem> iter = items.iterator();

                    while (iter.hasNext()){
                        SearchItem item = iter.next();
                        if (item.getTitle() == null || item.getId()== null) {
                            iter.remove();
                        }
                    }

                    recyclerView.setAdapter(new SearchAdapter(getApplicationContext(), items));
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });


    }
}
