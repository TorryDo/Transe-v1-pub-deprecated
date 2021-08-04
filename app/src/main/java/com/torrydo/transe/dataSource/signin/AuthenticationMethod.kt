package com.torrydo.transe.dataSource.signin

import com.torrydo.transe.dataSource.signin.models.UserAccountInfo

interface AuthenticationMethod {

    fun getUserAccountInfo(): UserAccountInfo?

    fun isSignedIn(): Boolean

    fun signOut()

}