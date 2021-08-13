package com.torrydo.transe.ui.mainAppScreen.vocabCollectionScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torrydo.transe.dataSource.auth.AuthenticationMethod
import com.torrydo.transe.dataSource.database.LocalDatabaseRepository
import com.torrydo.transe.dataSource.database.RemoteDatabaseRepository
import com.torrydo.transe.dataSource.database.local.models.Vocab
import com.torrydo.transe.dataSource.database.remote.BaseVocab
import com.torrydo.transe.dataSource.translation.SearchRepository
import com.torrydo.transe.dataSource.translation.eng.models.EngResult
import com.torrydo.transe.interfaces.ListResultListener
import com.torrydo.transe.interfaces.ResultListener
import com.torrydo.transe.utils.CONSTANT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
@Named(CONSTANT.viewModelModule)
class VocabCollectionViewModel @Inject constructor(
    @Named(CONSTANT.viewModelSearchRepo) val searchRepository: SearchRepository,
    @Named(CONSTANT.viewModelLocalDB) val localDatabaseRepository: LocalDatabaseRepository,
    @Named(CONSTANT.viewModelRemoteDB) val remoteDatabaseRepository: RemoteDatabaseRepository,
    @Named(CONSTANT.viewModelAuth) val authentionMethod: AuthenticationMethod
) : ViewModel() {

    private val TAG = "_TAG_VocabVM"


    fun insertAllToRemoteDatabase(
        vocabList: List<Vocab>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            getUserID()?.let { uid ->
                vocabList.forEach { vocab ->

                    remoteDatabaseRepository.setUserID(uid)
                    remoteDatabaseRepository.insert(
                        convertToBaseVocab(vocab),
                        object : ResultListener {
                            override fun <T> onSuccess(data: T?) {
                                Log.i(TAG, "insertion succeed")
                            }
                        })

                }

            }
        }
    }

    fun syncAllVocabFromRemoteDatabase(
        vocabList: List<Vocab>?,
        isFinished: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            getUserID()?.let { uid ->

                remoteDatabaseRepository.setUserID(uid)
                remoteDatabaseRepository.getAll(object : ListResultListener {
                    override fun <T> onSuccess(dataList: List<T>) {
                        if (dataList.isNotEmpty() && dataList[0] is BaseVocab) {

                            val baseVocabList = dataList as List<BaseVocab>

                            val differenceVocabList = getDifferentElements(vocabList, baseVocabList)

                            var count = 0

                            differenceVocabList.forEach { baseVocab ->
                                convertToVocab(baseVocab) { vocab ->
                                    viewModelScope.launch(Dispatchers.IO) {
                                        localDatabaseRepository.insert(vocab)
                                        count++
                                    }
                                }
                            }

                            if (count >= baseVocabList.size - 1) {
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
    }

    fun deleteVocabFromRemoteDB(
        vocab: Vocab
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            getUserID()?.let { uid ->
                remoteDatabaseRepository.setUserID(uid)
                remoteDatabaseRepository.delete(
                    convertToBaseVocab(vocab),
                    object : ResultListener {
                        override fun <T> onSuccess(data: T?) {
                            Log.i(TAG, "vocab deleted away from remoteDB")
                        }

                        override fun onError(e: Exception) {
                            Log.e(TAG, e.message.toString())
                        }
                    }
                )

                return@launch
            }

            Log.e(TAG, "pls signIn first")

        }
    }

    fun updateVocabFromRemoteDB(
        vocab: Vocab
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            getUserID()?.let { uid ->
                remoteDatabaseRepository.setUserID(uid)
                remoteDatabaseRepository.update(
                    convertToBaseVocab(vocab),
                    object : ResultListener {

                        override fun <T> onSuccess(data: T?) {
                            Log.i(TAG, "updated")
                        }

                        override fun onError(e: Exception) {
                            Log.e(TAG, e.message.toString())
                        }

                    }
                )
                return@launch
            }
            Log.e(TAG, "pls signIn first")
        }
    }



    // --------------------------- private func ---------------------------

    private fun convertToBaseVocab(vocab: Vocab): BaseVocab {
        val keyWord = vocab.vocab
        val time = vocab.time.time.toString()
        val isFinished = false.toString()
        return BaseVocab(keyWord, time.toLong(), isFinished.toBoolean())
    }

    private fun convertToVocab(
        baseVocab: BaseVocab,
        isReady: (vocab: Vocab) -> Unit
    ) {
        searchRepository.getEnglishSource(baseVocab.keyWord, object : ListResultListener {
            override fun <T> onSuccess(dataList: List<T>) {
                if (dataList[0] is EngResult) {
                    val engResultList = dataList as ArrayList<EngResult>
                    val vocab = Vocab(
                        uid = 0,
                        vocab = baseVocab.keyWord,
                        time = Date(baseVocab.time),
                        finished = baseVocab.finished,
                        contentEng = engResultList,
                        contentVi = emptyList()
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

        val strVocab = ArrayList<String>()
        val differenceRemoteVocab = ArrayList<BaseVocab>()

        vocabList.forEach { vocab ->
            strVocab.add(vocab.vocab)
        }
        baseVocabList.forEach { baseVocab ->
            val keyWord = baseVocab.keyWord

            val b = strVocab.contains(keyWord)
            if (!b) {
                differenceRemoteVocab.add(baseVocab)
            }
        }
        return differenceRemoteVocab

    }

}
