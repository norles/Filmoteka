package filmoteka.norles.github.com.filmoteka.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Klasa zapewniająca dostęp do obiektu Retrofit
// Sluzy on do wykonywania zapytań HTTP do API
public class Client {
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
