package filmoteka.norles.github.com.filmoteka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class MenuActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private LinearLayout menu_1;
    private LinearLayout menu_2;
    private LinearLayout menu_3;
    private LinearLayout menu_4;

    private LinearLayout menu_exit;

    private MenuActivity instance = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menu_1 = findViewById(R.id.menu_1);
        menu_2 = findViewById(R.id.menu_2);
        menu_3 = findViewById(R.id.menu_3);
        menu_4 = findViewById(R.id.menu_4);
        menu_exit = findViewById(R.id.menu_exit);

        initViews();
    }

    private void initViews() {
        menu_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(MainActivity.class);
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
                changeActivity(SearchActivity.class);
            }
        });
        menu_4.setOnClickListener(new View.OnClickListener() {
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

    private void changeActivity(Class<?> activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
