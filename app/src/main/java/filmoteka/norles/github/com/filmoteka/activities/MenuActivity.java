package filmoteka.norles.github.com.filmoteka.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import filmoteka.norles.github.com.filmoteka.R;
import filmoteka.norles.github.com.filmoteka.Settings;

// Aktywność wyświetlająca menu aplikacji
public class MenuActivity extends AppCompatActivity {

    private static final String TAG = MovieActivity.class.getName();

    private LinearLayout menu_1;
    private LinearLayout menu_2;
    private LinearLayout menu_3;
    private LinearLayout menu_4;
    private LinearLayout menu_5;

    private LinearLayout menu_exit;

    private MenuActivity instance = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Settings.getInstance().setContext(this);

        // Pobranie poszczegolnych widokow
        menu_1 = findViewById(R.id.menu_1);
        menu_2 = findViewById(R.id.menu_2);
        menu_3 = findViewById(R.id.menu_3);
        menu_4 = findViewById(R.id.menu_4);
        menu_5 = findViewById(R.id.menu_5);
        menu_exit = findViewById(R.id.menu_exit);

        // Metoda inicjujaca listenery dla poszczegolnych elementw menu
        initViewsListener();
    }

    private void initViewsListener() {
        menu_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(MovieActivity.class);
            }
        });
        menu_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(PeopleActivity.class);
            }
        });
        menu_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(TvActivity.class);
            }
        });
        menu_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(SearchActivity.class);
            }
        });
        menu_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(FavouritesActivity.class);
            }
        });
        menu_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // Metoda zmieniajaca wyswietlajaca aktywnosc
    private void changeActivity(Class<?> activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
