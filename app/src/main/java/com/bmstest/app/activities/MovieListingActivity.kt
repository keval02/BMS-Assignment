package com.bmstest.app.activities

import AppGlobal.KEY_MOVIE_ID
import RetrofitAPIInterface
import SharedPreferenceHelper
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bmstest.app.R
import com.bmstest.app.adapter.MovieListingAdapter
import com.bmstest.app.apis.APIGenerator
import com.bmstest.app.config.Config.API_KEY
import com.bmstest.app.config.Config.API_LANGUAGE
import com.bmstest.app.model.MovieListDataResultModel
import com.bmstest.app.model.MovieListModel
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_movie_listing.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListingActivity : AppCompatActivity() {

    lateinit var retrofitAPIInterface: RetrofitAPIInterface
    lateinit var preference: SharedPreferenceHelper
    var movieList: ArrayList<MovieListDataResultModel> = ArrayList()
    var allMovieList: ArrayList<MovieListDataResultModel> = ArrayList()
    var pageNumber: Int = 1
    var totalPage: Int = 1
    var totalMovies: Int = 1
    lateinit var movieListingAdapter: MovieListingAdapter
    var pastVisibleItems: Int = -1
    var visibleItemCount: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_listing)
        retrofitAPIInterface = APIGenerator.apiClass
        preference = SharedPreferenceHelper(applicationContext)

        movieListingAdapter = object : MovieListingAdapter(this, movieList) {
            override fun onMovieClick(id: Int) {
                val intent = Intent(this@MovieListingActivity, MovieDetailsActivity::class.java)
                intent.putExtra(KEY_MOVIE_ID , id)
                startActivity(intent)
            }
        }
        val layoutManager = GridLayoutManager(this, 2)
        rv_movie_list.layoutManager = layoutManager
        rv_movie_list.adapter = movieListingAdapter

        movieListSRL.setOnRefreshListener {
            getAllMovies(1)
        }

        rv_movie_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalMovies = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisibleItems) >= totalMovies) {
                        pageNumber++; // increase your page
                        getAllMovies(pageNumber); //  call api with new Page.
                    }
                }
            }
        })

        searchIV.setOnClickListener {
            searchIV.visibility = View.GONE
            searchFL.visibility = View.VISIBLE
            val fadeInAnimation: Animation =
                AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
            searchFL.startAnimation(fadeInAnimation)
        }

        clearIV.setOnClickListener {
            movieSearchET.setText("")
            val fadeInAnimation: Animation =
                AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)
            searchFL.startAnimation(fadeInAnimation)
            searchIV.visibility = View.VISIBLE
            searchFL.visibility = View.GONE
        }

        movieSearchET.addTextChangedListener {
            val searchedText = movieSearchET.text.toString().trim()
            if (searchedText.isEmpty()) {
                movieList.clear()
                movieList.addAll(allMovieList)
                movieListingAdapter.notifyDataSetChanged()
            } else {
                val searchedMovieList : ArrayList<MovieListDataResultModel> = ArrayList()
                allMovieList.forEach {
                    if(searchedText.length == 1){
                        if(it.title.toLowerCase().startsWith(searchedText.toLowerCase()))
                            searchedMovieList.add(it)
                    }else {
                        if (it.title.toLowerCase().contains(searchedText.toLowerCase()))
                            searchedMovieList.add(it)
                    }
                }
                movieList.clear()
                movieList.addAll(searchedMovieList)
                movieListingAdapter.notifyDataSetChanged()
            }

        }

        getAllMovies(pageNumber)
    }

    /**
     * FUNCTION COMMENT
     *
     * This function will call the api and fetch the movie list
     * @param page number will help to fetch the the movie list of particular page. By default it will be 1.
     * @return
     */
    private fun getAllMovies(pageNumber: Int) {
        movieListSRL.isRefreshing = true
        if (pageNumber == 1) {
            movieList.clear()
            allMovieList.clear()
        }
        val apiCall: Call<MovieListModel> =
            retrofitAPIInterface.getAllMovies(API_KEY, API_LANGUAGE, pageNumber)

        apiCall.enqueue(object : Callback<MovieListModel> {
            override fun onResponse(
                call: Call<MovieListModel>,
                response: Response<MovieListModel>
            ) {
                movieListSRL.isRefreshing = false
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    apiResponse?.let {
                        totalPage = it.total_pages
                        totalMovies = it.total_results
                        totalMoviesTV.text = totalMovies.toString() + " Movies"
                        movieList.addAll(it.results)
                        allMovieList.addAll(it.results)
                        rv_movie_list.visibility = View.VISIBLE
                        movieListingAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<MovieListModel>, t: Throwable) {
                movieListSRL.isRefreshing = false
                rv_movie_list.visibility = View.GONE
                txt_no_movies_found.visibility = View.GONE
            }

        })
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }
}