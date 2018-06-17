package filmoteka.norles.github.com.filmoteka.network;

import filmoteka.norles.github.com.filmoteka.models.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MovieService {

    @GET("movie/popular?language=pl")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String api_key);

    @GET("movie/{movie_id}?language=pl")
    Call<MovieDetail> getMovieDetail(
            @Path("movie_id") Integer quiz_id,
            @Query("api_key") String api_key
    );

    @GET("movie/{movie_id}?language=pl")
    Call<MovieItem> getMovieItemId(
            @Path("movie_id") Integer quiz_id,
            @Query("api_key") String api_key
    );

    @GET("person/{person_id}?language=pl")
    Call<PersonDetail> getPersonDetail(
            @Path("person_id") Integer person_id,
            @Query("api_key") String api_key
    );

    @GET("person/popular?language=pl")
    Call<PeopleResponse> getPopularPeople(@Query("api_key") String api_key);


    @GET("discover/tv")
    Call<TvResponse> getBestTvShows(@Query("api_key") String api_key);


    @GET("tv/{tv_id}")
    Call<TvDetail> getTvShowDetail(
            @Path("tv_id") Integer quiz_id,
            @Query("api_key") String api_key
    );

    @GET("search/movie")
    Call<SearchResult> getSearchResults(
            @Query("api_key") String api_key,
            @Query("query") String query
    );

}
