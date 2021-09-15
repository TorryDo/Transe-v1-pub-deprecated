package com.torrydo.transe.ui.mainAppScreen.settingScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.torrydo.transe.dataSource.auth.AuthenticationMethod
import com.torrydo.transe.dataSource.auth.models.UserAccountInfo
import com.torrydo.transe.utils.CONSTANT
import com.torrydo.transe.utils.worker.NotificationWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
@Named(CONSTANT.viewModelModule)
class SettingViewModel @Inject constructor(
    @Named(CONSTANT.viewModelAuth) val authenticationMethod: AuthenticationMethod,
    @Named(CONSTANT.viewModelNotificationWorker) val notiWorkManager: WorkManager
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

    val TAG_NOTI_WORKER = "tagNotiWorker"
    fun registerVocabNoti() {
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.HOURS)
            .addTag(TAG_NOTI_WORKER)
            .build()

        notiWorkManager.enqueue(workRequest)
    }
    fun unregisterVocabNoti(){
        notiWorkManager.cancelAllWorkByTag(TAG_NOTI_WORKER)
    }

}



