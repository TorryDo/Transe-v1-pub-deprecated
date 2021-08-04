package com.torrydo.transe.ui.mainAppScreen

import androidx.lifecycle.ViewModel
import com.torrydo.transe.utils.CONSTANT
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
@Named(CONSTANT.viewModelModule)
class MainViewModel @Inject constructor(
) : ViewModel() {

}