package com.torrydo.transe.dataSource.translation

import com.torrydo.transe.dataSource.image.ImageApi
import com.torrydo.transe.dataSource.image.model.ImageApiModel
import com.torrydo.transe.dataSource.translation.eng.EngSearch
import com.torrydo.transe.interfaces.ListResultListener
import com.torrydo.transe.interfaces.ResultListener
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await

class SearchRepositoryImpl(
    private val engSearch: EngSearch,
    private val imageSearch: ImageApi
) : SearchRepository {

    override fun getEnglishSource(
        keyWord: String,
        listResultListener: ListResultListener
    ) {
        engSearch.getResult(keyWord, listResultListener)
    }

    override fun getImageList(keyWord: String, resultListener: ResultListener) {

        Thread{
            imageSearch.get(keyWord).enqueue(object : Callback<ImageApiModel>{
                override fun onResponse(
                    call: Call<ImageApiModel>,
                    response: Response<ImageApiModel>
                ) {
                    resultListener.onSuccess(response.body())
                }

                override fun onFailure(call: Call<ImageApiModel>, t: Throwable) {
                    resultListener.onError(Exception(t))
                }

            })
        }.start()

    }

    override fun getViSource() {}


}