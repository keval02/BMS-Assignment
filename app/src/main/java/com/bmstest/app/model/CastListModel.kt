package com.bmstest.app.model

data class CastListModel(
    val cast: List<CastDataModel>,
    val crew: List<CrewDataModel>,
    val id: Int
)