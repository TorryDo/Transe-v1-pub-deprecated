package com.torrydo.transeng.ui.mainAppScreen.vocabScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torrydo.transeng.dataSource.database.LocalDatabaseRepository
import com.torrydo.transeng.dataSource.database.local.models.Vocab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
@Named("viewModelModule")
class VocabViewModel @Inject constructor(
    @Named("viewModelDbRepo") val localDatabaseRepository: LocalDatabaseRepository
) : ViewModel() {

    private val TAG = "_TAG_MainViewModel"

    var resultList: LiveData<List<Vocab>> = MutableLiveData()

    init {
        viewModelScope.launch {
            resultList = localDatabaseRepository.getAll()
        }
    }

    fun deleteVocab(vocab: Vocab) {
        viewModelScope.launch(Dispatchers.IO) {
            localDatabaseRepository.delete(vocab)
        }
    }

}