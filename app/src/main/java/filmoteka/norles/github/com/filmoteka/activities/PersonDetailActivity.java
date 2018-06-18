package filmoteka.norles.github.com.filmoteka.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import filmoteka.norles.github.com.filmoteka.BuildConfig;
import filmoteka.norles.github.com.filmoteka.R;
import filmoteka.norles.github.com.filmoteka.models.PersonDetail;
import filmoteka.norles.github.com.filmoteka.network.Client;
import filmoteka.norles.github.com.filmoteka.network.MovieService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Aktywność wyświetlająca widok szczegolwy o aktorze
public class PersonDetailActivity extends AppCompatActivity {

    private static final String TAG = PersonDetailActivity.class.getName();

    private PersonDetail personDetail;

    private ImageView imageView;

    private TextView nameView;
    private TextView birthdayView;
    private TextView biographyView;

    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        id = getIntent().getIntExtra("id", 0);

        imageView = findViewById(R.id.person_detail_img);

        nameView = findViewById(R.id.person_detail_name);
        birthdayView = findViewById(R.id.person_detail_birthday);
        biographyView = findViewById(R.id.person_detail_biography);

        loadJSON();
    }

    void loadJSON(){

        MovieService service = Client
                .getRetrofit()
                .create(MovieService.class);

        Call<PersonDetail> call = service.getPersonDetail(id, BuildConfig.MOVIE_DB_KEY);

        call.enqueue(new Callback<PersonDetail>() {
            @Override
            public void onResponse(Call<PersonDetail> call, Response<PersonDetail> response) {
                if (response.isSuccessful()){
                    personDetail = response.body();

                    initView();
                }
            }

            @Override
            public void onFailure(Call<PersonDetail> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    void initView(){
        setTitle(personDetail.getName());

        Glide.with(this)
                .load(personDetail.getProfilePath())
                .placeholder(R.drawable.person_placeholder)
                .into(imageView);

        nameView.setText(personDetail.getName());
        birthdayView.setText("ur." + personDetail.getBirthday());
        biographyView.setText(personDetail.getBiography());
    }
}
