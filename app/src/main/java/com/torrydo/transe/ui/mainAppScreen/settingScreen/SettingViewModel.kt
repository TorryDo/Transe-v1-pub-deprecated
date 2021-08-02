package com.torrydo.transe.ui.mainAppScreen.settingScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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