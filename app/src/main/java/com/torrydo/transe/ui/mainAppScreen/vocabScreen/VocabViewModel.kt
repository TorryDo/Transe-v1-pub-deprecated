package com.torrydo.transe.ui.mainAppScreen.vocabScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torrydo.transe.dataSource.database.LocalDatabaseRepository
import com.torrydo.transe.dataSource.database.RemoteDatabaseRepository
import com.torrydo.transe.dataSource.database.local.models.Vocab
import com.torrydo.transe.dataSource.database.remote.RemoteVocab
import com.torrydo.transe.dataSource.database.remote.RemoteVocabProperties
import com.torrydo.transe.interfaces.ResultListener
import com.torrydo.transe.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
@Named("viewModelModule")
class VocabViewModel @Inject constructor(
    @Named("viewModelDbRepo") val localDatabaseRepository: LocalDatabaseRepository,
    @Named("viewModelRemoteDatabase") val remoteDatabaseRepository: RemoteDatabaseRepository
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

    fun insertToRemoteDatabase(remoteVocab: RemoteVocab){
        viewModelScope.launch(Dispatchers.IO) {
            remoteDatabaseRepository.insert(remoteVocab, object : ResultListener{
                override fun <T> onSuccess(data: T?) {
                    Log.i(TAG, "insertion succeed")
                }
            })
        }
    }

    fun insertAllToRemoteDatabase(listRemoteVocab: List<RemoteVocab>){
        viewModelScope.launch(Dispatchers.IO) {
            remoteDatabaseRepository.insertAll(listRemoteVocab, object : ResultListener{
                override fun <T> onSuccess(data: T?) {
                    Log.i(TAG, "insertion succeed")
                }
            })
        }
    }

    fun transformVocabToRemoteVocab(vocab: Vocab): RemoteVocab{
        val keyWord = vocab.vocab
        val time = vocab.time.time
        val isFinished = false
        return RemoteVocab(keyWord, RemoteVocabProperties(time, isFinished))
    }

}