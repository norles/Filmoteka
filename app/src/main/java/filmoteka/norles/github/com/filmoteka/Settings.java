package filmoteka.norles.github.com.filmoteka;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    private static Settings ourInstance = new Settings();

    public static Settings getInstance() {
        return ourInstance;
    }

    private final String PREFERENCES_FAVOURITE = "favourite_preferences";
    private final String FAVOUTIRE_KEY = "favourite_key";

    private List<Integer> favourites = new ArrayList<>();

    private Context context = null;

    private Settings() {
    }

    public void setContext(Context context) {
        this.context = context;
        readSavedFavourites();
    }

    private void readSavedFavourites() {
        SharedPreferences shared = context.getSharedPreferences(PREFERENCES_FAVOURITE, Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = shared.edit();

        String json = shared.getString(FAVOUTIRE_KEY, "[]");

        Log.d("READ", json);

        Gson gson = new Gson();
        favourites = gson.fromJson(json, new TypeToken<List<Integer>>() {
        }.getType());

        for (Integer i :
                favourites) {
            Log.d("FAV", i.toString());
        }
    }

    public void addFavourite(Integer id) {
        if (favourites.contains(id)){
            return;
        }

        favourites.add(id);

        saveFavourites();
    }

    private void saveFavourites() {
        Gson gson = new Gson();

        String json = gson.toJson(favourites);

        SharedPreferences shared = context.getSharedPreferences(PREFERENCES_FAVOURITE, Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = shared.edit();
        edit.remove(FAVOUTIRE_KEY).apply();

        edit.putString(FAVOUTIRE_KEY, json);
        edit.apply();

    }


    public List<Integer> getFavourites() {
        return favourites;
    }
}
