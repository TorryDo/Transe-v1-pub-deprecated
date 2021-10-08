package com.torrydo.transe.ui.mainAppScreen.vocabCollectionScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torrydo.transe.adapter.vocabSource.model.SearchResult
import com.torrydo.transe.dataSource.auth.AuthenticationMethod
import com.torrydo.transe.dataSource.database.LocalDatabaseRepository
import com.torrydo.transe.dataSource.database.RemoteDatabaseRepository
import com.torrydo.transe.dataSource.database.local.models.Vocab
import com.torrydo.transe.dataSource.database.remote.models.BaseVocab
import com.torrydo.transe.dataSource.translation.SearchRepository
import com.torrydo.transe.interfaces.ListResultListener
import com.torrydo.transe.interfaces.ResultListener
import com.torrydo.transe.utils.CONSTANT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date
import javax.inject.Inject
import javax.inject.Named

@Suppress("UNCHECKED_CAST")
@HiltViewModel
@Named(CONSTANT.viewModelModule)
class VocabCollectionViewModel @Inject constructor(
    @Named(CONSTANT.viewModelSearchRepo) val searchRepository: SearchRepository,
    @Named(CONSTANT.viewModelLocalDB) val localDatabaseRepository: LocalDatabaseRepository,
    @Named(CONSTANT.viewModelRemoteDB) val remoteDatabaseRepository: RemoteDatabaseRepository,
    @Named(CONSTANT.viewModelAuth) val authentionMethod: AuthenticationMethod
) : ViewModel() {

    private val TAG = "_TAG_VocabVM"

    fun uploadAllVocabToRemoteDB() {
        viewModelScope.launch(Dispatchers.IO) {

            val uid = getUserID()
            if (uid == null) {
                Log.e(TAG, "uid is null, sign in first")
                return@launch
            }

            remoteDatabaseRepository.setUserID(uid)
            localDatabaseRepository.getAll().forEach { vocab ->

                remoteDatabaseRepository.insert(
                    baseVocab = vocab.toBaseVocab(),
                    object : ResultListener {
                        override fun <T> onSuccess(data: T?) {
                            Log.i(TAG, "insertion succeed")
                        }
                    })

            }

        }
    }

    fun syncAllVocabFromRemoteDatabase(
        vocabList: List<Vocab>?,
        isFinished: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            val uid = getUserID() ?: return@launch

            remoteDatabaseRepository.setUserID(uid)
            remoteDatabaseRepository.getAll(object : ListResultListener {
                override fun <T> onSuccess(dataList: List<T>) {
                    if (dataList.isNotEmpty() && dataList[0] is BaseVocab) {

                        viewModelScope.launch(Dispatchers.IO) {
                            val BASE_VOCAB_LIST = dataList as List<BaseVocab>

                            getDifferentElements(vocabList, BASE_VOCAB_LIST).forEach { baseVocab ->
                                baseVocab.toVocab { vocab ->
                                    viewModelScope.launch(Dispatchers.IO) {
                                        localDatabaseRepository.insert(vocab)
                                    }
                                }
                            }

                            isFinished()

                        }


                    }
                }

                override fun onError(e: Exception) {
                    Log.e(TAG, e.message.toString())
                }
            })


        }
    }

    fun deleteVocabFromRemoteDB(
        vocab: Vocab
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            val uid = getUserID() ?: return@launch

            remoteDatabaseRepository.setUserID(uid)
            remoteDatabaseRepository.delete(
                baseVocab = vocab.toBaseVocab(),
                resultListener = object : ResultListener {
                    override fun <T> onSuccess(data: T?) {
                        Log.i(TAG, "vocab deleted away from remoteDB")
                    }

                    override fun onError(e: Exception) {
                        Log.e(TAG, e.message.toString())
                    }
                }
            )

        }
    }

    fun updateVocabToRemoteDB(
        vocab: Vocab
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            val uid = getUserID() ?: return@launch

            localDatabaseRepository.loadVocabByKeyword(vocab.vocab)

            remoteDatabaseRepository.setUserID(uid)
            remoteDatabaseRepository.update(
                baseVocab = vocab.toBaseVocab(),
                resultListener = object : ResultListener {

                    override fun <T> onSuccess(data: T?) {
                        Log.i(TAG, "updated")
                    }

                    override fun onError(e: Exception) {
                        Log.e(TAG, e.message.toString())
                    }

                }
            )
        }
    }


    // --------------------------- private func ---------------------------

    private fun Vocab.toBaseVocab() = BaseVocab(
        keyWord = this.vocab,
        time = this.time.time,
        finished = this.finished
    )


    private fun BaseVocab.toVocab(
        isReady: (vocab: Vocab) -> Unit
    ) {
        searchRepository.getEnglishSource(this.keyWord, object : ListResultListener {
            override fun <T> onSuccess(dataList: List<T>) {
                if (dataList[0] is SearchResult) {
                    val engResultList = dataList as ArrayList<SearchResult>
                    val vocab = Vocab(
                        uid = 0,
                        vocab = this@toVocab.keyWord,
                        time = Date(this@toVocab.time),
                        finished = this@toVocab.finished,
                        contentEng = engResultList
                    )

                    isReady(vocab)

                }
            }

        })
    }


    private fun getUserID() = authentionMethod.getUserAccountInfo()?.uid
    private fun getDifferentElements(
        vocabList: List<Vocab>?,
        baseVocabList: List<BaseVocab>
    ): List<BaseVocab> {

        if (vocabList.isNullOrEmpty()) return baseVocabList
        if (baseVocabList.isNullOrEmpty()) return emptyList()

        val strVocabList = vocabList.map { it.vocab }
        val res = baseVocabList.filterNot { baseVocab ->
            strVocabList.contains(baseVocab.keyWord)
        }

        return res

    }

}
