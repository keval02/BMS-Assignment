package com.bmstest.app.activities

import AppGlobal.KEY_MOVIE_ID
import AppGlobal.changeDateFormat
import RetrofitAPIInterface
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmstest.app.R
import com.bmstest.app.adapter.MovieCastListAdapter
import com.bmstest.app.adapter.MovieCrewListAdapter
import com.bmstest.app.adapter.MovieReviewsAdapter
import com.bmstest.app.adapter.SimilarMoviesListingAdapter
import com.bmstest.app.apis.APIGenerator
import com.bmstest.app.config.Config.API_KEY
import com.bmstest.app.config.Config.API_LANGUAGE
import com.bmstest.app.model.*
import com.bumptech.glide.Glide
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_movie_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsActivity : AppCompatActivity() {
    var movieId: Int = 0
    lateinit var retrofitAPIInterface: RetrofitAPIInterface
    var reviewsList: ArrayList<ReviewResultDataModel> = ArrayList()
    var castList: ArrayList<CastDataModel> = ArrayList()
    var crewList: ArrayList<CrewDataModel> = ArrayList()
    var similarMovieList: ArrayList<MovieListDataResultModel> = ArrayList()

    lateinit var castListAdapter: MovieCastListAdapter
    lateinit var crewListAdapter: MovieCrewListAdapter
    lateinit var similarMoviesListAdapter: SimilarMoviesListingAdapter
    lateinit var reviewsListAdapter: MovieReviewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        retrofitAPIInterface = APIGenerator.apiClass
        movieId = try {
            intent.getIntExtra(KEY_MOVIE_ID, 0)
        } catch (e: Exception) {
            0
        }

        val castLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val crewLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val similarLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val reviewLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        castListAdapter = object : MovieCastListAdapter(this, castList) {}
        crewListAdapter = object : MovieCrewListAdapter(this, crewList) {}
        similarMoviesListAdapter = object : SimilarMoviesListingAdapter(this, similarMovieList) {}
        reviewsListAdapter = object : MovieReviewsAdapter(this, reviewsList) {}

        rv_cast_list.layoutManager = castLayoutManager
        rv_crew_list.layoutManager = crewLayoutManager
        rv_similar_movie_list.layoutManager = similarLayoutManager
        rv_review_list.layoutManager = reviewLayoutManager

        rv_cast_list.adapter = castListAdapter
        rv_crew_list.adapter = crewListAdapter
        rv_similar_movie_list.adapter = similarMoviesListAdapter
        rv_review_list.adapter = reviewsListAdapter

        detailsSRL.setOnRefreshListener {
            detailsSRL.isRefreshing = false
        }

        getMovieSynopsis()
    }

    /**
     * FUNCTION COMMENT
     *
     * This function will call the api of a movie to fetch the details of the movie
     * @param movieId is used to fetch the details of particular movie
     * @return
     */
    private fun getMovieSynopsis() {
        detailsSRL.isRefreshing = true
        val apiCall: Call<MovieListDataResultModel> =
            retrofitAPIInterface.getMovieDetails(movieId, API_KEY, API_LANGUAGE)
        apiCall.enqueue(object : Callback<MovieListDataResultModel> {
            override fun onResponse(
                call: Call<MovieListDataResultModel>,
                response: Response<MovieListDataResultModel>
            ) {
                val apiResponse = response.body()
                apiResponse?.let {
                    movieNameTV.text = it.title
                    Glide.with(this@MovieDetailsActivity)
                        .load(it.poster_path)
                        .error(R.drawable.image_preview_bg)
                        .placeholder(R.drawable.image_preview_bg)
                        .into(moviePosterIV)

                    movieTotalVotesTV.text = it.vote_count.toString() + " Votes"
                    movieReleaseDateTV.text = changeDateFormat(it.release_date)
                    descriptionTV.text = it.overview
                }
            }

            override fun onFailure(call: Call<MovieListDataResultModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        getMovieReviews()
    }

    private fun getMovieReviews() {
        val apiCall: Call<ReviewListModel> =
            retrofitAPIInterface.getMovieReviews(movieId, API_KEY, API_LANGUAGE)
        apiCall.enqueue(object : Callback<ReviewListModel> {
            override fun onResponse(
                call: Call<ReviewListModel>,
                response: Response<ReviewListModel>
            ) {
                val apiResponse = response.body()
                apiResponse?.let {
                    reviewsList.addAll(it.results)
                    reviewsListAdapter.notifyDataSetChanged()
                    if(reviewsList.isEmpty())
                        reviewsLL.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<ReviewListModel>, t: Throwable) {

            }

        })

        getMovieCast()
    }

    private fun getMovieCast() {
        val apiCall: Call<CastListModel> =
            retrofitAPIInterface.getMovieCast(movieId, API_KEY, API_LANGUAGE)
        apiCall.enqueue(object : Callback<CastListModel> {
            override fun onResponse(call: Call<CastListModel>, response: Response<CastListModel>) {
                val apiResponse = response.body()
                apiResponse?.let {
                    castList.addAll(it.cast)
                    crewList.addAll(it.crew)

                    castListAdapter.notifyDataSetChanged()
                    crewListAdapter.notifyDataSetChanged()

                    if(castList.isEmpty())
                        castLL.visibility = View.GONE

                    if(crewList.isEmpty())
                        crewLL.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<CastListModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        getSimilarMovies()
    }

    private fun getSimilarMovies() {
        detailsSRL.isRefreshing = false
        val apiCall: Call<MovieListModel> =
            retrofitAPIInterface.getSimilarMovies(movieId, API_KEY, API_LANGUAGE)
        apiCall.enqueue(object : Callback<MovieListModel> {
            override fun onResponse(
                call: Call<MovieListModel>,
                response: Response<MovieListModel>
            ) {
                val apiResponse = response.body()
                apiResponse?.let {
                    similarMovieList.addAll(it.results)

                    similarMoviesListAdapter.notifyDataSetChanged()

                    if(similarMovieList.isEmpty())
                        similarMoviesLL.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<MovieListModel>, t: Throwable) {
            }


        })
    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }
}