package com.bmstest.app.model

data class MovieListModel(
    val dates: MovieListDatesModel,
    val page: Int,
    val results: List<MovieListDataResultModel>,
    val total_pages: Int,
    val total_results: Int
)