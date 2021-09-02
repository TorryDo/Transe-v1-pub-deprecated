package com.torrydo.transe.dataSource.image.model

data class ImageApiModel(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)