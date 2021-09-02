package com.torrydo.transe.dataSource.image

import com.torrydo.transe.dataSource.image.model.ImageApiModel
import com.torrydo.transe.utils.CONSTANT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageApi {

    @GET("?key=${CONSTANT.PIXABAY_KEY}&image_type=photo")
    fun get(@Query("keyWord") keyWord: String): Call<ImageApiModel>

}