package filmoteka.norles.github.com.filmoteka.network;

import filmoteka.norles.github.com.filmoteka.models.MovieDetail;
import filmoteka.norles.github.com.filmoteka.models.MovieResponse;
import filmoteka.norles.github.com.filmoteka.models.PeopleResponse;
import filmoteka.norles.github.com.filmoteka.models.PersonDetail;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String api_key);

    @GET("movie/{movie_id}")
    Call<MovieDetail> getMovieDetail(
            @Path("movie_id") Integer quiz_id,
            @Query("api_key") String api_key
    );

    @GET("person/{person_id}")
    Call<PersonDetail> getPersonDetail(
            @Path("person_id") Integer person_id,
            @Query("api_key") String api_key
    );

    @GET("person/popular")
    Call<PeopleResponse> getPopularPeople(@Query("api_key") String api_key);

}
