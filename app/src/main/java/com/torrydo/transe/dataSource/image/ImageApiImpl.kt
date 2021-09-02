package com.torrydo.transe.dataSource.image

import com.torrydo.transe.dataSource.image.model.ImageApiModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ImageApiImpl : ImageApi {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pixabay.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imageApiImpl = retrofit.create(ImageApi::class.java)

    override fun get(keyWord: String) : Call<ImageApiModel>{
        return imageApiImpl.get(keyWord)
    }

}