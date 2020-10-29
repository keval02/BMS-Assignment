package com.bmstest.app.model

data class ReviewListModel(
    val id: Int,
    val page: Int,
    val results: List<ReviewResultDataModel>,
    val total_pages: Int,
    val total_results: Int
)