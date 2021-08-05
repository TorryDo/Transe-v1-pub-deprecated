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
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
@Named(CONSTANT.viewModelModule)
class MainViewModel @Inject constructor(
    @Named(CONSTANT.viewModelLocalDB) val localDatabaseRepository: LocalDatabaseRepository,
) : ViewModel() {

    private val TAG = "_TAG_MainViewModel"

    var vocabList: LiveData<List<Vocab>> = MutableLiveData<List<Vocab>>()

    init {
        getAllLocalVocab()
    }

    fun getAllLocalVocab(){
        viewModelScope.launch(Dispatchers.IO) {
            vocabList = localDatabaseRepository.getAll()
        }
    }

    fun deleteVocab(vocab: Vocab) {
        viewModelScope.launch(Dispatchers.IO) {
            localDatabaseRepository.delete(vocab)
        }
    }

}