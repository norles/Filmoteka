package filmoteka.norles.github.com.filmoteka.network;

import filmoteka.norles.github.com.filmoteka.models.Movie;
import filmoteka.norles.github.com.filmoteka.models.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String api_key);

}