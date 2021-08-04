package com.torrydo.transe.ui.mainAppScreen.settingScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.torrydo.transe.dataSource.signin.AuthenticationMethod
import com.torrydo.transe.dataSource.signin.models.UserAccountInfo
import com.torrydo.transe.di.ViewModelModule
import com.torrydo.transe.utils.CONSTANT
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
@Named(CONSTANT.viewModelModule)
class SettingViewModel @Inject constructor(
    @Named(CONSTANT.viewModelAuth) val authenticationMethod: AuthenticationMethod
) : ViewModel() {

    private val TAG = "_TAG_SettingViewModel"

    val isSignedIn = MutableLiveData<Boolean>()
    val userAccount = MutableLiveData<UserAccountInfo>()

    fun updateSignInState(): Boolean {
        val tempBool = authenticationMethod.isSignedIn()
        isSignedIn.value = tempBool
        return tempBool
    }

    fun isSignedin() = authenticationMethod.isSignedIn()
    fun updateUserAccount() {
        userAccount.value = authenticationMethod.getUserAccountInfo()
    }
}



