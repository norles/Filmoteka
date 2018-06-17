package filmoteka.norles.github.com.filmoteka;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import filmoteka.norles.github.com.filmoteka.models.MovieDetail;
import filmoteka.norles.github.com.filmoteka.models.TvDetail;
import filmoteka.norles.github.com.filmoteka.network.Client;
import filmoteka.norles.github.com.filmoteka.network.MovieService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class TvDetailActivity extends AppCompatActivity {

    private static final String TAG = TvDetailActivity.class.getName();

    private TvDetail tvDetail;

    private ImageView imageView;
    private TextView titleView;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);

        id = getIntent().getIntExtra("id", 0);

        imageView = findViewById(R.id.tv_detail_thumbnail);
        titleView = findViewById(R.id.tv_detail_title);

        loadJSON();

    }

    void loadJSON(){

        MovieService service = Client
                .getRetrofit()
                .create(MovieService.class);

        Call<TvDetail> call = service.getTvShowDetail(id,BuildConfig.MOVIE_DB_KEY);

        call.enqueue(new Callback<TvDetail>() {
            @Override
            public void onResponse(Call<TvDetail> call, Response<TvDetail> response) {
                if (response.isSuccessful()){
                    tvDetail = response.body();

                    initView();
                }
            }

            @Override
            public void onFailure(Call<TvDetail> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    void initView(){
        setTitle(tvDetail.getOriginalName());

        Glide.with(this)
                .load(tvDetail.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .into(imageView);

        titleView.setText(tvDetail.getOriginalName());
    }
}
