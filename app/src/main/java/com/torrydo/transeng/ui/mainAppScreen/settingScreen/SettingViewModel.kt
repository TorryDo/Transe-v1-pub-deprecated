package com.torrydo.transeng.ui.mainAppScreen.settingScreen

import android.util.Log
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
class SettingViewModel @Inject constructor(
    @Named("viewModelString") val mString: String
) : ViewModel() {

    private val TAG = "_TAG_SettingViewModel"

    init {
        Log.d(TAG, "setting viewmodel : $mString")
    }


}