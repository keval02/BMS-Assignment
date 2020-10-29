import ApiURLs.GET_ALL_MOVIES
import ApiURLs.GET_MOVIE_CAST
import ApiURLs.GET_MOVIE_REVIEWS
import ApiURLs.GET_MOVIE_SYNOPSIS
import ApiURLs.GET_SIMILAR_MOVIES
import com.bmstest.app.model.CastListModel
import com.bmstest.app.model.MovieListDataResultModel
import com.bmstest.app.model.MovieListModel
import com.bmstest.app.model.ReviewListModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitAPIInterface {

    @GET(GET_ALL_MOVIES)
    fun getAllMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") pageNumber: Int
    ): Call<MovieListModel>

    @GET(GET_MOVIE_SYNOPSIS)
    fun getMovieDetails(
        @Path("movie_id") movieId: Int, @Query("api_key") apiKey: String,
        @Query("language") language: String
    ) : Call<MovieListDataResultModel>

    @GET(GET_MOVIE_REVIEWS)
    fun getMovieReviews(
        @Path("movie_id") movieId: Int, @Query("api_key") apiKey: String,
        @Query("language") language: String
    ) : Call<ReviewListModel>

    @GET(GET_MOVIE_CAST)
    fun getMovieCast(
        @Path("movie_id") movieId: Int, @Query("api_key") apiKey: String,
        @Query("language") language: String
    ) : Call<CastListModel>

    @GET(GET_SIMILAR_MOVIES)
    fun getSimilarMovies(
        @Path("movie_id") movieId: Int, @Query("api_key") apiKey: String,
        @Query("language") language: String
    ) : Call<MovieListModel>


}