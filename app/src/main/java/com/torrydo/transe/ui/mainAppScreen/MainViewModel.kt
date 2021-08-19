package com.torrydo.transe.ui.mainAppScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torrydo.transe.dataSource.database.LocalDatabaseRepository
import com.torrydo.transe.dataSource.database.local.models.Vocab
import com.torrydo.transe.utils.CONSTANT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
@Named(CONSTANT.viewModelModule)
class MainViewModel @Inject constructor(
    @Named(CONSTANT.viewModelLocalDB) val localDatabaseRepository: LocalDatabaseRepository,
) : ViewModel() {

    private val TAG = "_TAG_MainViewModel"

    var TAB_POSITION = 0


    var vocabLiveData: LiveData<List<Vocab>> = MutableLiveData()

    var vocabListNotFinished = MutableLiveData<List<Vocab>>()
    var vocabListFinished = MutableLiveData<List<Vocab>>()

    init {
        getVocabLiveData()
    }

    private fun getVocabLiveData() {
        viewModelScope.launch(Dispatchers.IO) {
            vocabLiveData = localDatabaseRepository.getAllLiveData()
        }
    }

    fun set2VocabList(
        isReady: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            vocabLiveData.value?.let { vl ->

                val newListNotFinished = vl.filterNot { it.finished }
                val newListFinished = vl.filter { it.finished }

                withContext(Dispatchers.Main) {
                    vocabListNotFinished.value = newListNotFinished
                    vocabListFinished.value = newListFinished
                    isReady()
                }
            }

        }

    }

    fun shuffle2VocabList(
        isReady: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            vocabLiveData.value?.let { vl ->
                val mVocabListFinished = vl.filter { it.finished }.shuffled()
                val mVocabListNotFinished = vl.filterNot { it.finished }.shuffled()

                withContext(Dispatchers.Main) {
                    vocabListNotFinished.value = mVocabListNotFinished
                    vocabListFinished.value = mVocabListFinished
                    isReady()
                }
            }
        }
    }


    fun updateVocab(vocab: Vocab) {
        viewModelScope.launch(Dispatchers.IO) {
            localDatabaseRepository.update(vocab)
        }
    }

    fun deleteVocab(vocab: Vocab) {
        viewModelScope.launch(Dispatchers.IO) {
            localDatabaseRepository.delete(vocab)
        }
    }


}